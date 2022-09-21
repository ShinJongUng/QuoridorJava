package main;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;

public class Client {
    SocketChannel socket;
    public Client(int port) throws IOException{
        // 서버 IP와 포트로 연결되는 소켓채널 생성SocketChannel socket = SocketChannel.open
        socket = SocketChannel.open(new InetSocketAddress("127.0.0.1", port));
    }
}