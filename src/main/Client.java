package main;

import DB.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.io.*;

public class Client extends Information{
    SocketChannel socket;
    ByteBuffer buffer;
    PKT_Serialized pt;
    public Client(int port) {
        // 서버 IP와 포트로 연결되는 소켓채널 생성
        try{
            pt = new PKT_Serialized();
            buffer = ByteBuffer.allocate(1024);
            buffer.clear();
            socket = SocketChannel.open(new InetSocketAddress("127.0.0.1", port));
            Read();
        } catch (IOException e) {
            System.out.println("서버와 연결이 종료되었습니다.");
        }
    }
    //칸 이동 또는 벽 생성 후 서버에게 전달.
    public void Write(Packet pk){
        try {
            buffer.clear();
            pt.Serialized(pk, buffer);
            buffer.flip();
            socket.write(buffer);
            changeTurn();
            Read();
        }
        catch (IOException e) {
            System.out.println("서버와 연결이 종료되었습니다.");
        }
    }
    public void Read(){
        try{
            buffer.clear();
            socket.read(buffer);
            buffer.flip();
            Packet pk = pt.DeSerialized(Packet.class, buffer);
            System.out.println(pk.getState() + " " + pk.getX() + " " + pk.getY());

            // 게임 접속
            if(getMyId() == -1) {
                setId(pk.getId());
                if(getMyId() == 1)
                    setStartGame(true);
            }// 게임 시작(적 접속 확인) -> 호스트가 먼저 턴
            else if(!isStartGame()) {
                setStartGame(true);
                changeTurn();
            }// 적 행동
            else if(pk.isTurn() && pk.getId() != getMyId() && !isTurn()) {
                if (pk.getState() == Packet.State.Move) {
                    //이동하는 행동 - 적이 시행
                } else if (pk.getState() == Packet.State.Horizontal_Wall) {
                    //벽 생성 - 적이 시행
                } else if (pk.getState() == Packet.State.Vertical_Wall) {
                    //벽 생성 - 적이 시행
                }
                changeTurn();
            }// 오류, 다시 Read()
            else
                Read();
        }
        catch(IOException e){
            System.out.println("서버와 연결이 종료되었습니다.");
        }
    }
}