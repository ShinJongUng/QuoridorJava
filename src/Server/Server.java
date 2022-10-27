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
    private ByteBuffer inputBuffer;
    PKT_Serialized pt;

    Server(int port) throws IOException{
        pt = new PKT_Serialized();

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

        //테스트 용
        Packet pk;
        Information information = new Information();
        if(allClient.isEmpty()) {
            pk = new Packet(0, 4, 0, Packet.State.Start, false);
            information.setId(0);
        }
        else {
            pk = new Packet(1, 4, 8, Packet.State.Start, false);
            information.setId(1);
        }
        information.setX(1);
        information.setY(2);

        // 추가
        allClient.add(clientSock);
        clientSock.register(selector, SelectionKey.OP_READ, information);
        System.out.println(clientSock.getLocalAddress());

        Write(pk);
    }
    void Read(SelectionKey key){
        SocketChannel readSocket = (SocketChannel) key.channel();
        Information info = (Information) key.attachment();
        inputBuffer.clear();
        try {
            readSocket.read(inputBuffer);
            inputBuffer.flip();
            Packet pk = pt.DeSerialized(Packet.class, inputBuffer);
            if(pk.getId() == 0) {
                System.out.println("Host : " + pk.getX() + " " + pk.getY());
            }
            else {
                System.out.println("Client : " + pk.getX() + " " + pk.getY());
            }
            info.setX(pk.getX());
            info.setY(pk.getY());
            Write(pk);
        }
        catch(IOException e){
            key.cancel();
            allClient.remove(readSocket);
            System.out.println("클라이언트와 연결이 종료되었습니다.");
        }
    }

    void Write(Object data){ //ByteBuffer 전달
        SocketChannel socketChannel = null;
        inputBuffer.clear();
        try {
            pt.Serialized(data, inputBuffer);
            for(SocketChannel sock : allClient){
                inputBuffer.flip();
                socketChannel = sock;
                sock.write(inputBuffer);
                System.out.println("정보 보냄");
            }
        }
        catch(Exception e){
            allClient.remove(socketChannel);
            System.out.println("클라이언트와 연결이 종료되었습니다.");
        }
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