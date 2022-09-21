package Server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Server {
    Set<SocketChannel> allClient;
    ServerSocketChannel socket;
    public Selector selector;
    ByteBuffer inputBuffer;
    ByteBuffer outputBuffer;

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
        outputBuffer = ByteBuffer.allocate(1024);
    }

    void Accept(ServerSocketChannel key) throws IOException{
        SocketChannel clientSock = key.accept();

        clientSock.configureBlocking(false);
        // 추가
        allClient.add(clientSock);
        clientSock.register(selector, SelectionKey.OP_READ, new ClientInfo());
        System.out.println(clientSock.getLocalAddress());
    }
    void Recv(SelectionKey key) throws IOException{
        SocketChannel readSocket = (SocketChannel) key.channel();
        ClientInfo info = (ClientInfo) key.attachment();
        try {
            readSocket.read(inputBuffer);
            System.out.println("정보 입력받음");
        }
        catch(Exception e){
            key.cancel();
            allClient.remove(readSocket);
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
                    server.Accept((ServerSocketChannel) key.channel());
                }else if(key.isReadable()){
                    server.Recv(key);
                }

            }
        }
    }
}

class ClientInfo{

    private boolean idCheck = true;
    String id;
    boolean isID(){
        return idCheck;
    }
    private void setCheck(){
        idCheck = false;
    }

    void setID(String id){
        this.id = id;
        setCheck();
    }
}