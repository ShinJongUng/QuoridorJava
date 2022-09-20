package main;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Board extends JFrame{
    static final int ROWS = 9, COLS = 9;

    JButton[][] spaces = new JButton[ROWS][COLS];
    JButton[][] cwalls = new JButton[ROWS-1][COLS-1];
    JButton[][][] vwalls = new JButton[ROWS][COLS-1][2];
    JButton[][][] hwalls = new JButton[ROWS-1][COLS][2];


    private JButton space(Color bg)
    {
        JButton space = new JButton();
        space.setBorder(new EmptyBorder(0,0,0,0));
        space.setBackground(bg);
        space.setForeground(Color.WHITE);
        return space;
    }

    public void initBoard()
    {
        initialBoardArray();
        Component[][] grid = new Component[2 * ROWS - 1][2 * COLS - 1];

        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS  ; j++){
                grid[2 * i][2 * j] = spaces[i][j];
            }
        }

        for(int i = 0; i < ROWS; i++) {
            for(int j = 0; j < COLS-1; j++) {
                JPanel panel = new JPanel(new GridLayout(2, 1));
                for (int k = 0; k < 2; k++) {
                    panel.add(vwalls[i][j][k]);
                }
                grid[2 * i][2 * j + 1] = panel;
            }
        }

        for(int i = 0; i < ROWS-1; i++) {
            for(int j = 0; j < COLS; j++){
                JPanel panel = new JPanel(new GridLayout(1,2));
                for(int k = 0; k < 2; k++){
                    panel.add(hwalls[i][j][k]);
                }
                grid[2 * i + 1][2 * j] = panel;
            }
        }


        for(int i = 0; i < ROWS-1; i++) {
            for(int j = 0; j < COLS-1; j++){
                grid[2 * i + 1][2 * j + 1] = cwalls[i][j];
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

        pawn.setPawn(0, spaces[ROWS-1][COLS / 2]); // 말 첫 위치 지정
        pawn.setPawn(1, spaces[0][COLS / 2]); // 말 첫 위치 지정

        setContentPane(pane); //프레임에 content 붙이기
    }


    private void initialBoardArray(){

        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                spaces[i][j] = space(Color.BLACK);
            }
        }

        for(int i = 0; i < ROWS ; i++) {
            for (int j = 0; j < COLS - 1; j++) {
                for (int k = 0; k < 2; k++) { //클릭 할 수 있는 칸을 2개로 나눔
                    vwalls[i][j][k] = space(Color.WHITE);
                }
            }
        }
        for(int i = 0; i < ROWS-1; i++) {
            for (int j = 0; j < COLS; j++) {
                for (int k = 0; k < 2; k++) { //클릭 할 수 있는 칸을 2개로 나눔
                    hwalls[i][j][k] = space(Color.WHITE);
                }
            }
        }
        for(int i = 0; i < ROWS-1; i++) {
            for (int j = 0; j < COLS - 1; j++) { //클릭 할 수 있는 칸을 2개로 나눔
                cwalls[i][j] = space(Color.WHITE);
            }
        }
    }

    public Board()
    {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //창 관리자
        setTitle("쿼리도 게임");
        setVisible(true); //창 열기
        setSize(1200,1200);
        initBoard();
    }

    public static void main(String[] args) throws Throwable
    {
        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); // UI 디자인 라이브러리
        Board b = new Board();
    }
}
