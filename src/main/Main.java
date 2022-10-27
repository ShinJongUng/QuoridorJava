package main;

import DB.Packet;

import javax.swing.*;

public class Main {
    public Main()
    {
        Board b = new Board();
        b.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //창 관리자
        b.setTitle("쿼리도 게임");
        b.initBoard(1);
        b.setSize(1200,1000);
        b.setVisible(true); //창 열기
    }
    public static void main(String[] args) throws Throwable
    {
//        Client c = new Client(5000);
//        c.Write(new Packet(1, 2, Packet.State.H_Move));
        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); // UI 디자인 라이브러리
        Main m = new Main();
    }
}