package main;

import DB.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.io.*;

public class Client extends Information{
    SocketChannel socket;
    ByteBuffer buffer;
    int headerSize = 0;
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
            int size = pt.Serialized(pk, buffer);
            buffer.flip();
            socket.write(buffer);
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
            setId(pk.getId());
        }
        catch(IOException e){
            System.out.println("서버와 연결이 종료되었습니다.");
        }
    }
}