package main;

import DB.Packet;

import javax.swing.*;
import java.awt.*;

public class Main {
    public Main()
    {
        Board b = new Board();
        b.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //창 관리자
        b.setTitle("쿼리도 게임");
        b.initBoard();
        b.setSize(900,690);
        b.setVisible(true); //창 열기
    }
    public static void main(String[] args) throws Throwable
    {
        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); // UI 디자인 라이브러리
        Main m = new Main();
    }
}