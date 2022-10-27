package main;

import DB.Packet;

import javax.swing.*;

public class User2 {
    public User2()
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
        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); // UI 디자인 라이브러리
        User2 m = new User2();
    }
}