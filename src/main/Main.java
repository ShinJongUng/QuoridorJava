package main;
import javax.swing.*;

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