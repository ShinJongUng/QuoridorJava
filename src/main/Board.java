package main;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
public class Board extends JFrame{
    // 보
    public static int x, y, walld, player_checked_state = 0, answer, wall_count_player1 = 0, wall_count_player2 = 0;
    static final int ROWS = 9, COLS = 9;
    public static JButton[][] Spaces = new JButton[ROWS][COLS];
    public static JButton[][] CenterWalls = new JButton[ROWS-1][COLS-1];
    public static JButton[][] VerticalWalls = new JButton[ROWS][COLS-1];
    public static JButton[][] HorizontalWalls = new JButton[ROWS-1][COLS];
    public static String [][] CheckVwalls = new String[ROWS][COLS];
    public static String [][] CheckHwalls = new String[ROWS][COLS];
    public static String [][] CheckPawn1 = new String[ROWS][COLS];
    public static String [][] CheckPawn2 = new String[ROWS][COLS];
    public static JLabel turnStr = new JLabel("현재 턴: Player1, 나의 벽 개수: 0/6", SwingConstants.CENTER);


    public Quoridor quoridor;
    private JButton space(Color bg)
    {
        quoridor = new Quoridor();
        JButton space = new JButton();
        space.setBorder(new EmptyBorder(0,0,0,0));
        space.setBackground(bg);
        space.setForeground(Color.WHITE);
        space.addActionListener(quoridor);
        //space.addMouseListener(quoridor);
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
                JPanel panel = new JPanel(new GridLayout(1, 1));
                panel.add(VerticalWalls[i][j]);
                grid[2 * i][2 * j + 1] = panel;
            }
        }

        for(int i = 0; i < ROWS-1; i++) {
            for(int j = 0; j < COLS; j++){
                JPanel panel = new JPanel(new GridLayout(1,2));
                panel.add(HorizontalWalls[i][j]);
                grid[2 * i + 1][2 * j] = panel;
            }
        }


        for(int i = 0; i < ROWS-1; i++) {
            for(int j = 0; j < COLS-1; j++){
                grid[2 * i + 1][2 * j + 1] = CenterWalls[i][j];
            }
        }
        Container c = getContentPane();
        c.setLayout(new BorderLayout());
        JPanel pane = new JPanel();
        GroupLayout layout = new GroupLayout(pane);
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
                // 칸은 사이즈 90으로 막는 줄은 20으로
                horizontalParallelGroup.addComponent(grid[i][j], GroupLayout.PREFERRED_SIZE,
                        (i % 2 == 0) ? 60 : 15 , GroupLayout.PREFERRED_SIZE);
                verticalParallelGroup.addComponent(grid[j][i], GroupLayout.PREFERRED_SIZE,
                        (i % 2 == 0) ? 60 : 15, GroupLayout.PREFERRED_SIZE);
            }
            verticalSequentialGroup.addGroup(horizontalParallelGroup);
            horizontalSequentialGroup.addGroup(verticalParallelGroup);
        }
        layout.setHorizontalGroup(horizontalSequentialGroup);
        layout.setVerticalGroup(verticalSequentialGroup);

        Pawn.setPawn(0, ROWS-1, COLS / 2); // 말 첫 위치 지정
        Pawn.setPawn(1, 0, COLS / 2); // 말 첫 위치 지정

        CheckPawn1[ROWS-1][COLS / 2] = "setPawn";
        CheckPawn2[0][COLS / 2] = "setPawn";

        JPanel mainUI = new JPanel();
        mainUI.setLayout(new BorderLayout(5, 5));
        mainUI.setPreferredSize(new Dimension(230, 400));
        JLabel label1 = new JLabel("당신은 Player "+(Main.client.getMyId() + 1) + "입니다.", SwingConstants.CENTER);
        JPanel btmUI = new JPanel();
        btmUI.setLayout(null);
        btmUI.setPreferredSize(new Dimension(230, 400));
        JButton btn1 = new JButton("나가기 / 항복");
        btn1.setHorizontalAlignment(SwingConstants.CENTER);
        btn1.setBounds(50, 250, 100, 50);
        btmUI.add(btn1);
        mainUI.add(label1, BorderLayout.NORTH);
        mainUI.add(turnStr, BorderLayout.CENTER);
        mainUI.add(btmUI, BorderLayout.SOUTH);
        c.add(mainUI, BorderLayout.EAST);
        c.add(pane, BorderLayout.CENTER); //프레임에 content 붙이기
        c.setVisible(true);
    }

    private void initialBoardArray(){

        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                Spaces[i][j] = space(new Color(150,75,0));
            }
        }

        for(int i = 0; i < ROWS ; i++) {
            for (int j = 0; j < COLS - 1; j++) {
                //클릭 할 수 있는 칸을 2개로 나눔
                VerticalWalls[i][j] = space(Color.WHITE);
            }
        }
        for(int i = 0; i < ROWS-1; i++) {
            for (int j = 0; j < COLS; j++) {
                //클릭 할 수 있는 칸을 2개로 나눔
                HorizontalWalls[i][j] = space(Color.WHITE);

            }
        }
        for(int i = 0; i < ROWS-1; i++) {
            for (int j = 0; j < COLS - 1; j++) { //클릭 할 수 있는 칸을 2개로 나눔
                CenterWalls[i][j] = space(Color.WHITE);
            }
        }
    }
}