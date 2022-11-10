package main;

import DB.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.io.*;

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
    //칸 이동 또는 벽 생성 후 서버에게 전달.
    public void Write(Packet pk){
        try {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.clear();
            pt.Serialized(pk, buffer);
            buffer.flip();
            socket.write(buffer);
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
                    if(getMyId() == 0)
                        Read();
                }
                if(pk.getId() == 1) {
                    System.out.println("게임 시작");
                    setStartGame(true);
                    if(getMyId() == 0) {
                        changeTurn();
                    }
                }
                if(getMyId() == 1)
                    Read();
            }// 적 행동
            System.out.println(pk.isTurn() + " " + pk.getId() + " " + isTurn());
            if(pk.isTurn() && pk.getId() != getMyId() && !isTurn()) {
                System.out.println("Enemy Action");
                if (pk.getState() == Packet.State.Move) {
                    if (getMyId() == 0) {
                        System.out.println("2번 움직임");
                        Main.board.quoridor.Enemy_Move(pk.getX(), pk.getY(), 1);
                    }else if (getMyId() == 1) {
                        System.out.println("1번 움직임");
                        Main.board.quoridor.Enemy_Move(pk.getX(), pk.getY(), 0);
                    }
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
            }
        }
        catch(IOException e){
            System.out.println("서버와 연결이 종료되었습니다.");
        }
    }
}