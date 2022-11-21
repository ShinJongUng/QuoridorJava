package main;

import DB.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.io.*;
import main.Board.*;
import java.util.jar.Manifest;

public class Client extends Information{
    SocketChannel socket;
    PKT_Serialized pt;
    public Client(int port) {
        // 서버 IP와 포트로 연결되는 소켓채널 생성
        try{
            pt = new PKT_Serialized();

            socket = SocketChannel.open(new InetSocketAddress("127.0.0.1", port));
        } catch (IOException e) {
            System.out.println("서버와 연결이 종료되었습니다.");
        }
    }

    public void TurnUpdate(){
        int nowTurnId = -1;
        if(Main.client.isTurn()) {
            if(Main.client.getMyId() == 0){
                nowTurnId = 2;
            }else{
                nowTurnId = 1;
            }
        }else {
            if(Main.client.getMyId() == 0){
                nowTurnId = 1;
            }else{
                nowTurnId = 2;
            }
        }
        System.out.println("현재 턴" + nowTurnId);
        Board.turnStr.setText("현재 턴: Player" + nowTurnId + ", 나의 벽 개수: " + (Main.client.getMyId() == 0 ? Board.wall_count_player1: Board.wall_count_player2)+ "/6");
    }

    //칸 이동 또는 벽 생성 후 서버에게 전달.
    public void Write(Packet pk){
        try {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.clear();
            pt.Serialized(pk, buffer);
            buffer.flip();
            socket.write(buffer);
            TurnUpdate();
            changeTurn();
        }
        catch (IOException e) {
            System.out.println("서버와 연결이 종료되었습니다.");
        }
    }
    public void Read(){
        try{
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.clear();

            socket.read(buffer);
            buffer.flip();
            Packet pk = pt.DeSerialized(Packet.class, buffer);
            System.out.println(pk.getState() + " " + pk.getX() + " " + pk.getY());

            // 게임 시작 전 (적 접속 확인) -> 호스트가 먼저 턴
            if(!isStartGame()) {
                // 게임 접속
                if(getMyId() == -1) {
                    setId(pk.getId());
                    setX(pk.getX());
                    setY(pk.getY());
                    if(getMyId() == 0) {
                        Read();

                    }
                }
                if(pk.getId() == 1) {
                    System.out.println("게임 시작");
                    setStartGame(true);
                    if(getMyId() == 0) {
                        TurnUpdate();
                        changeTurn();
                    }
                }
                if(getMyId() == 1)
                    Read();
            }// 적 행동

            System.out.println(pk.isTurn() + " " + pk.getId() + " " + isTurn());
            if(pk.isTurn() && pk.getId() != getMyId() && !isTurn()) {
                System.out.println("Enemy Action");
                TurnUpdate();
                System.out.println("3");
                if (pk.getState() == Packet.State.Move) {
                    Main.board.quoridor.Enemy_Move(pk.getX(), pk.getY(), pk.getId());
                    changeTurn();
                }
                else if (pk.getState() == Packet.State.Horizontal_Wall) {
                    Main.board.quoridor.CreateHorizontalWall(pk.getX(), pk.getY(), isTurn());
                    changeTurn();
                }
                else if (pk.getState() == Packet.State.Vertical_Wall) {
                    Main.board.quoridor.CreateVerticalWall(pk.getX(), pk.getY(), isTurn());
                    changeTurn();
                }
                else if(pk.getState() == Packet.State.Winner){
                    Main.board.quoridor.Win(pk.getX(), pk.getId());
                    System.out.println("패배");
                }
                else if(pk.getState() == Packet.State.Surrender){
                    Main.board.quoridor.Surrender(pk.getId());
                    System.out.println("패배");
                }
            }
        }
        catch(IOException e){
            System.out.println("서버와 연결이 종료되었습니다.");
        }
    }
}