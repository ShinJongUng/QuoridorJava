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
            if(getMyId() == -1) {
                setId(pk.getId());
                setX(pk.getX());
                setY(pk.getY());
            }else if(pk.isTurn() && pk.getId() != getMyId() && !isTurn()){
                if(pk.getState() == Packet.State.H_Move && pk.getState() == Packet.State.C_Move){
                    //이동하는 행동 - 적이 시행
                }
                else if(pk.getState() == Packet.State.H_Wall && pk.getState() == Packet.State.C_Wall){
                    //벽 생성 - 적이 시행
                }
                changeTurn();
            }
            else
                Read();
        }
        catch(IOException e){
            System.out.println("서버와 연결이 종료되었습니다.");
        }
    }
}