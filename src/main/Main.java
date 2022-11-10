package main;

import DB.Packet;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static Client client;
    public static Board board;
    Thread network;
    public Main()
    {
        network = new Thread(new Network_Thread());
        network.start();

        board = new Board();
        board.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //창 관리자
        board.setTitle("쿼리도 게임");
        board.initBoard();
        board.setSize(900,690);
        board.setVisible(true); //창 열기
    }
    public static void main(String[] args) throws Throwable
    {
        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); // UI 디자인 라이브러리
        Main m = new Main();
    }
}

class Network_Thread implements Runnable{
    public void run(){
        Main.client = new Client(5000);
        try {
            while (true) {
                Main.client.Read();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}