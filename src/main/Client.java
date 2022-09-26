package main;

import DB.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;
import java.io.*;

public class Client extends Information{
    SocketChannel socket;
    ByteBuffer buffer;
    public Client(int port) {
        // 서버 IP와 포트로 연결되는 소켓채널 생성
        try{
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
            int size = Serialized(pk);
            buffer.flip();
            socket.write(buffer);
        }
        catch (IOException e) {
            System.out.println("서버와 연결이 종료되었습니다.");
        }
    }
    public void Read(){
        try{
            socket.read(buffer);
            buffer.flip();
            Packet pk = DeSerialized(Packet.class);
            System.out.println(pk.isState());
        }
        catch(IOException e){
            System.out.println("서버와 연결이 종료되었습니다.");
        }
    }
    public static byte[] toByteArray (Object obj)
    {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            oos.close();
            bos.close();
            bytes = bos.toByteArray();
        }
        catch (IOException ex) {
            System.out.println("변경 실패0");
        }
        return bytes;
    }
    public static <T> T toObject (byte[] bytes, Class<T> type)
    {
        Object obj = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream (bytes);
            ObjectInputStream ois = new ObjectInputStream (bis);
            obj = ois.readObject();
        }
        catch (IOException ex) {
            System.out.println("변경 실패1");
        }
        catch (ClassNotFoundException ex) {
            System.out.println("변경 실패2");
        }
        return type.cast(obj);
    }

    <T> T DeSerialized(Class<T> type){ //역직렬화코드 PKT_Header + Object Data
        try {
            byte[] byte_Header = new byte[45];
            buffer.get(byte_Header, 0, 45);
            PKT_Header header = toObject(byte_Header, PKT_Header.class);
            System.out.println("입력받은 데이터 : " + header.size);

            byte[] bytes = new byte[header.size];
            buffer.get(bytes, 0, header.size);
            return toObject(bytes, type);
        }
        catch(Exception e){
            System.out.println("불가능합니다.");
            return null;
        }
    }
    //직렬화 코드
    public int Serialized(Object obj){
        byte[] buf = toByteArray(obj);
        PKT_Header header = new PKT_Header(buf.length);
        byte[] head = toByteArray(header);
        buffer.put(head);
        buffer.put(buf);

        return buf.length;
    }
}