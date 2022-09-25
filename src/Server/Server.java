package Server;

import DB.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Server {
    Set<SocketChannel> allClient;
    ServerSocketChannel socket;
    public Selector selector;
    ByteBuffer inputBuffer;
    Server(int port) throws IOException{
        allClient = new HashSet<>();
        socket = ServerSocketChannel.open();

        socket.bind(new InetSocketAddress(port));
        // 논블로킹 형식 변경
        socket.configureBlocking(false);

        selector = Selector.open();
        // Select 등록
        socket.register(selector, SelectionKey.OP_ACCEPT);
        inputBuffer = ByteBuffer.allocate(1024);
        inputBuffer.clear();
    }

    void Accept(SelectionKey key) throws IOException{
        ServerSocketChannel s = (ServerSocketChannel)key.channel();
        SocketChannel clientSock = s.accept();

        clientSock.configureBlocking(false);
        // 추가
        allClient.add(clientSock);
        clientSock.register(selector, SelectionKey.OP_READ, new Information());
        System.out.println(clientSock.getLocalAddress());

        //테스트 용
        Packet pk = new Packet(Packet.State.Start, 1, 2);
        Write(clientSock, pk);
    }
    void Read(SelectionKey key){
        SocketChannel readSocket = (SocketChannel) key.channel();
        Information info = (Information) key.attachment();
        inputBuffer.clear();
        try {
            readSocket.read(inputBuffer);
            inputBuffer.flip();
            Packet pk = DeSerialized(Packet.class);
            System.out.println(pk.isState());
        }
        catch(IOException e){
            key.cancel();
            allClient.remove(readSocket);
            System.out.println("클라이언트와 연결이 종료되었습니다.");
        }
    }

    void Write(SocketChannel socket, Object data){ //ByteBuffer 전달
        inputBuffer.clear();
        try {
            int size = Serialized(data);
            for(SocketChannel sock : allClient){
                inputBuffer.flip();
                sock.write(inputBuffer);
                System.out.println(size + " byte 정보 보냄");
            }
        }
        catch(Exception e){
            allClient.remove(socket);
            System.out.println("클라이언트와 연결이 종료되었습니다.");
        }
    }
    public int Serialized(Object obj){
        byte[] buf = toByteArray(obj);
        PKT_Header header = new PKT_Header(buf.length);
        byte[] head = toByteArray(header);
        inputBuffer.put(head);
        inputBuffer.put(buf);

        return buf.length;
    }
    <T> T DeSerialized(Class<T> type){ //역역직렬화코드 PKT_Header + Object Data
        try {
            byte[] byte_Header = new byte[45];
            inputBuffer.get(byte_Header, 0, 45);
            PKT_Header header = toObject(byte_Header, PKT_Header.class);
            System.out.println("입력받은 데이터 : " + header.size);

            byte[] bytes = new byte[header.size];
            inputBuffer.get(bytes, 0, header.size);
            return toObject(bytes, type);
        }
        catch(Exception e){
            System.out.println("불가능합니다.");
            return null;
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
    public static void main(String[] args) throws IOException{
        Server server= new Server(5000);

        while(true){
            server.selector.select();

            Iterator<SelectionKey> iterator = server.selector.selectedKeys().iterator();

            while(iterator.hasNext()){
                SelectionKey key = iterator.next();
                iterator.remove();

                if(key.isAcceptable()) {
                    server.Accept(key);
                }else if(key.isReadable()){
                    server.Read(key);
                }
            }
        }
    }
}