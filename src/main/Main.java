package main;

import DB.Packet;

import javax.swing.*;

public class Main {
    public Main()
    {
        Board b = new Board();
        b.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //창 관리자
        b.setTitle("쿼리도 게임");
        b.setVisible(true); //창 열기
        b.setSize(1300,1100);
        b.initBoard();
        Client client = new Client(5000);
        client.Write(new Packet(Packet.State.Start, 1, 1));
    }
    public static void main(String[] args) throws Throwable
    {
        //Client c = new Client(5000);
        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); // UI 디자인 라이브러리
        Main m = new Main();
    }
}
