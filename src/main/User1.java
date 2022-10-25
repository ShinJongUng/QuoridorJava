package main;

import DB.Packet;

import javax.swing.*;

public class User1 {
    public User1()
    {
        Board b = new Board();
        b.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //창 관리자
        b.setTitle("쿼리도 게임");
        b.initBoard();
        b.setSize(1200,1000);
        b.setVisible(true); //창 열기
    }
    public static void main(String[] args) throws Throwable
    {
        Client c = new Client(5000);
        c.Write(new Packet(0, 0, 5, Packet.State.H_Move));
        c.Read();

        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); // UI 디자인 라이브러리
        User1 m = new User1();
    }
}