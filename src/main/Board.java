package main;
import DB.Packet;
import main.Client;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

public class Board extends JFrame{
    static final int ROWS = 9, COLS = 9;
    public static JButton[][] Spaces = new JButton[ROWS][COLS];
    public static JButton[][] CenterWalls = new JButton[ROWS-1][COLS-1];
    public static JButton[][][] VerticalWalls = new JButton[ROWS][COLS-1][2];
    public static JButton[][][] HorizontalWalls = new JButton[ROWS-1][COLS][2];

    //2차원 배열 2개 만드셈

    private Client client;
    private JButton space(Color bg)
    {
        Quoridor quoridor = new Quoridor();
        JButton space = new JButton();
        space.setBorder(new EmptyBorder(0,0,0,0));
        space.setBackground(bg);
        space.setForeground(Color.WHITE);
        space.addActionListener(quoridor);
        space.addMouseListener(quoridor);
        return space;
    }

    public void initBoard()
    {
        initialBoardArray();
        Component[][] grid = new Component[2 * ROWS - 1][2 * COLS - 1];

        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS  ; j++){
                grid[2 * i][2 * j] = Spaces[i][j];
            }
        }

        for(int i = 0; i < ROWS; i++) {
            for(int j = 0; j < COLS-1; j++) {
                JPanel panel = new JPanel(new GridLayout(2, 1));
                for (int k = 0; k < 2; k++) {
                    panel.add(VerticalWalls[i][j][k]);
                }
                grid[2 * i][2 * j + 1] = panel;
            }
        }

        for(int i = 0; i < ROWS-1; i++) {
            for(int j = 0; j < COLS; j++){
                JPanel panel = new JPanel(new GridLayout(1,2));
                for(int k = 0; k < 2; k++){
                    panel.add(HorizontalWalls[i][j][k]);
                }
                grid[2 * i + 1][2 * j] = panel;
            }
        }


        for(int i = 0; i < ROWS-1; i++) {
            for(int j = 0; j < COLS-1; j++){
                grid[2 * i + 1][2 * j + 1] = CenterWalls[i][j];
            }
        }

        JPanel pane = new JPanel();
        GroupLayout layout = new GroupLayout(pane);
        Pawn pawn = new Pawn();
        pane.setLayout(layout);

        // layout 배치
        GroupLayout.SequentialGroup verticalSequentialGroup = layout.createSequentialGroup();
        GroupLayout.SequentialGroup horizontalSequentialGroup = layout.createSequentialGroup();
        for(int i = 0; i < grid.length; i++)
        {
            GroupLayout.ParallelGroup horizontalParallelGroup = layout.createParallelGroup();
            GroupLayout.ParallelGroup verticalParallelGroup = layout.createParallelGroup();
            for(int j = 0; j < grid.length; j++)
            {
                // 칸은 사이즈 80으로 막는 줄은 20으로
                horizontalParallelGroup.addComponent(grid[i][j], GroupLayout.PREFERRED_SIZE,
                        (i % 2 == 0) ? 100 : 20 , GroupLayout.PREFERRED_SIZE);
                verticalParallelGroup.addComponent(grid[j][i], GroupLayout.PREFERRED_SIZE,
                        (i % 2 == 0) ? 100 : 20, GroupLayout.PREFERRED_SIZE);
            }
            verticalSequentialGroup.addGroup(horizontalParallelGroup);
            horizontalSequentialGroup.addGroup(verticalParallelGroup);
        }
        layout.setHorizontalGroup(horizontalSequentialGroup);
        layout.setVerticalGroup(verticalSequentialGroup);

        pawn.setPawn(0, Spaces[ROWS-1][COLS / 2]); // 말 첫 위치 지정
        pawn.setPawn(1, Spaces[0][COLS / 2]); // 말 첫 위치 지정

        setContentPane(pane); //프레임에 content 붙이기
    }

    private void initialBoardArray(){

        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                Spaces[i][j] = space(new Color(150,75,0));
            }
        }

        for(int i = 0; i < ROWS ; i++) {
            for (int j = 0; j < COLS - 1; j++) {
                for (int k = 0; k < 2; k++) { //클릭 할 수 있는 칸을 2개로 나눔
                    VerticalWalls[i][j][k] = space(Color.WHITE);
                }
            }
        }
        for(int i = 0; i < ROWS-1; i++) {
            for (int j = 0; j < COLS; j++) {
                for (int k = 0; k < 2; k++) { //클릭 할 수 있는 칸을 2개로 나눔
                    HorizontalWalls[i][j][k] = space(Color.WHITE);
                }
            }
        }
        for(int i = 0; i < ROWS-1; i++) {
            for (int j = 0; j < COLS - 1; j++) { //클릭 할 수 있는 칸을 2개로 나눔
                CenterWalls[i][j] = space(Color.WHITE);
            }
        }
    }
}
