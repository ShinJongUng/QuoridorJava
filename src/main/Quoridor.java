package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import static java.util.Arrays.*;
import static main.Board.*;
import DB.*;
import javafx.util.Pair;

public class Quoridor extends JFrame implements ActionListener{

    Queue<Pair<Integer, Integer>> queue = new LinkedList<>();
    boolean[][] visited = new boolean[ROWS][COLS];
    int[] dist_x = { 1, -1, 0, 0};
    int[] dist_y = { 0, 0, 1, -1};
    private boolean BFS_Path(int ID){
        queue.clear();
        for(int i = 0 ;i<ROWS;i++){
            for(int j = 0;j<COLS; j++){
                visited[i][j] = false;
            }
        }
        int x = Pawn.pawn_Location[ID][0];
        int y = Pawn.pawn_Location[ID][1];
        System.out.println("시작 위치 : " + x + " " + y);
        visited[x][y] = true;
        queue.add(new Pair<Integer, Integer>(x, y));

        while(!queue.isEmpty()){
            Pair<Integer, Integer> pair = queue.poll();
            for(int i = 0;i<4;i++){
                int rx = pair.getKey() + dist_x[i];
                int ry = pair.getValue() + dist_y[i];
                if(Is_Path(rx, ry) && !visited[rx][ry]){
                    visited[rx][ry] = true;
                    if(i == 0 && Is_Horizontal(pair.getKey(),pair.getValue()))
                        continue;
                    else if(i == 1 && Is_Horizontal(pair.getKey()-1,pair.getValue()))
                        continue;
                    else if(i == 2 && Is_Vertical(pair.getKey(),pair.getValue()))
                        continue;
                    else if(i == 3 && Is_Vertical(pair.getKey(),pair.getValue()-1))
                        continue;
                    else
                        queue.add(new Pair<Integer, Integer>(rx, ry));
                }
                if(ID == 0 && rx == 0)
                    return true;
                else if(ID == 1 && rx == 8)
                    return true;
            }
        }
        System.out.println("실패함");
        return false;
    }
    private boolean Is_Path(int x, int y){
        if(x < 0|| y < 0 || x > COLS -1 || y > ROWS - 1) {
            return false;
        }
        return true;
    }
    private boolean Is_Vertical(int x, int y){
        return CheckVwalls[x][y] == "Checked";
    }
    private boolean Is_Horizontal(int x, int y){
        return CheckHwalls[x][y] == "Checked";
    }
    public void setVerticalWall(int r, int c) {
        Board.VerticalWalls[r + 1][c].setBackground(Color.orange); // 누른곳 밑
        Board.VerticalWalls[r][c].setBackground(Color.orange);
        Board.CenterWalls[r][c].setBackground(Color.orange);
    }
    public void setHorizontalWall(int r, int c){
        Board.HorizontalWalls[r][c + 1].setBackground(Color.orange); // 누른곳 오른쪽
        Board.HorizontalWalls[r][c].setBackground(Color.orange); // 누른곳
        Board.CenterWalls[r][c].setBackground(Color.orange); // 가운데
    }
    public void CreateVerticalWall(int x, int y, boolean turn){
        boolean possible = false;
        try {
            if (x + 1 >= ROWS) {
                if (CheckVwalls[x][y] != "Checked" && CheckVwalls[x - 1][y] != "Checked") {
                    CheckVwalls[x][y] = "Checked";
                    CheckVwalls[x - 1][y] = "Checked";
                    if (turn && (!BFS_Path(0) || !BFS_Path(1))) {
                        CheckVwalls[x][y] = "";
                        CheckVwalls[x - 1][y] = "";
                    }
                    else {
                        setVerticalWall(x - 1, y);
                        possible = true;
                    }
                }
            } else if (CheckVwalls[x][y] != "Checked" && CheckVwalls[x + 1][y] != "Checked") {
                CheckVwalls[x][y] = "Checked";
                CheckVwalls[x + 1][y] = "Checked";
                if (turn && (!BFS_Path(0) || !BFS_Path(1))) {
                    CheckVwalls[x][y] = "";
                    CheckVwalls[x + 1][y] = "";
                }
                else{
                    setVerticalWall(x, y);
                    possible = true;
                }
            }
            System.out.println("(" + (y + 1 + "," + (x + 1)) + ")," + "(" + (y + 1 + "," + (x + 2)) + ")"); // to do
            if (turn && possible)
                Main.client.Write(new Packet(Main.client.getMyId(), x, y, Packet.State.Vertical_Wall, Main.client.isTurn()));
        } catch (Exception e) {
            System.out.println("Error VerticalWalls");
        }
    }
    public void CreateHorizontalWall(int x, int y, boolean turn){
        System.out.println("가로 벽 실행");
        boolean possible = false;
        try {
            if (y + 1 >= COLS) {
                if (CheckHwalls[x][y] != "Checked" && CheckHwalls[x][y - 1] != "Checked") {
                    CheckHwalls[x][y] = "Checked";
                    CheckHwalls[x][y - 1] = "Checked";
                    if (turn && (!BFS_Path(0) || !BFS_Path(1))) {
                        CheckHwalls[x][y] = "";
                        CheckHwalls[x][y - 1] = "";
                    }
                    else {
                        setHorizontalWall(x, y - 1);
                        possible = true;
                    }
                }
            } else if (CheckHwalls[x][y] != "Checked" && CheckHwalls[x][y + 1] != "Checked") {
                CheckHwalls[x][y] = "Checked";
                CheckHwalls[x][y + 1] = "Checked";
                if (turn && (!BFS_Path(0) || !BFS_Path(1))) {
                    CheckHwalls[x][y] = "";
                    CheckHwalls[x][y + 1] = "";
                    System.out.println("실패했어요. ");
                }
                else {
                    setHorizontalWall(x, y);
                    possible = true;
                }
            }
            System.out.println("(" + (y + 1 + "," + (x + 1)) + ")," + "(" + (y + 2 + "," + (x + 1)) + ")"); //to do
            if (turn && possible)
                Main.client.Write(new Packet(Main.client.getMyId(), x, y, Packet.State.Horizontal_Wall, Main.client.isTurn()));
        }
        catch(Exception e){
            System.out.println("Error Horizontal Wall");
        }
    }
    public void canMovePawn1(int pawn1x, int pawn1y){
        if(player_checked_state == 0) {
            if (CheckPawn1[pawn1x][pawn1y] == "setPawn") {
                if (pawn1x + 1 >= ROWS) {
                    if (pawn1y + 1 >= COLS) {
                        Spaces[pawn1x - 1][pawn1y].setBackground(Color.gray);
                        Spaces[pawn1x][pawn1y - 1].setBackground(Color.gray);

                        CheckPawn1[pawn1x - 1][pawn1y] = "cango";
                        CheckPawn1[pawn1x][pawn1y - 1] = "cango";

                        x = pawn1x;
                        y = pawn1y;
                    } else if (pawn1y - 1 < 0) {
                        Spaces[pawn1x - 1][pawn1y].setBackground(Color.gray);
                        Spaces[pawn1x][pawn1y + 1].setBackground(Color.gray);

                        CheckPawn1[pawn1x - 1][pawn1y] = "cango";
                        CheckPawn1[pawn1x][pawn1y + 1] = "cango";

                        x = pawn1x;
                        y = pawn1y;
                    } else {
                        Spaces[pawn1x - 1][pawn1y].setBackground(Color.gray);
                        Spaces[pawn1x][pawn1y + 1].setBackground(Color.gray);
                        Spaces[pawn1x][pawn1y - 1].setBackground(Color.gray);

                        CheckPawn1[pawn1x - 1][pawn1y] = "cango";
                        CheckPawn1[pawn1x][pawn1y + 1] = "cango";
                        CheckPawn1[pawn1x][pawn1y - 1] = "cango";

                        x = pawn1x;
                        y = pawn1y;
                    }
                } else if (pawn1y + 1 >= COLS) {
                    Spaces[pawn1x + 1][pawn1y].setBackground(Color.gray);
                    Spaces[pawn1x - 1][pawn1y].setBackground(Color.gray);
                    Spaces[pawn1x][pawn1y - 1].setBackground(Color.gray);

                    CheckPawn1[pawn1x + 1][pawn1y] = "cango";
                    CheckPawn1[pawn1x - 1][pawn1y] = "cango";
                    CheckPawn1[pawn1x][pawn1y - 1] = "cango";

                    x = pawn1x;
                    y = pawn1y;
                } else if (pawn1y - 1 < 0) {
                    Spaces[pawn1x + 1][pawn1y].setBackground(Color.gray);
                    Spaces[pawn1x - 1][pawn1y].setBackground(Color.gray);
                    Spaces[pawn1x][pawn1y + 1].setBackground(Color.gray);

                    CheckPawn1[pawn1x + 1][pawn1y] = "cango";
                    CheckPawn1[pawn1x - 1][pawn1y] = "cango";
                    CheckPawn1[pawn1x][pawn1y + 1] = "cango";

                    x = pawn1x;
                    y = pawn1y;
                } else {
                    Spaces[pawn1x + 1][pawn1y].setBackground(Color.gray);
                    Spaces[pawn1x - 1][pawn1y].setBackground(Color.gray);
                    Spaces[pawn1x][pawn1y + 1].setBackground(Color.gray);
                    Spaces[pawn1x][pawn1y - 1].setBackground(Color.gray);

                    CheckPawn1[pawn1x + 1][pawn1y] = "cango";
                    CheckPawn1[pawn1x - 1][pawn1y] = "cango";
                    CheckPawn1[pawn1x][pawn1y + 1] = "cango";
                    CheckPawn1[pawn1x][pawn1y - 1] = "cango";

                    x = pawn1x;
                    y = pawn1y;
                }

                if (pawn1y > 0) {
                    if (CheckVwalls[pawn1x][pawn1y - 1] == "Checked") {
                        Spaces[pawn1x][pawn1y - 1].setBackground(new Color(150, 75, 0));
                        CheckPawn1[pawn1x][pawn1y - 1] = "";
                    }
                }

                if (CheckVwalls[pawn1x][pawn1y] == "Checked") {
                    Spaces[pawn1x][pawn1y + 1].setBackground(new Color(150, 75, 0));
                    CheckPawn1[pawn1x][pawn1y + 1] = "";
                }
                if (pawn1x > 0) {
                    if (CheckHwalls[pawn1x - 1][pawn1y] == "Checked") {
                        Spaces[pawn1x - 1][pawn1y].setBackground(new Color(150, 75, 0));
                        CheckPawn1[pawn1x - 1][pawn1y] = "";
                    }
                }

                if (CheckHwalls[pawn1x][pawn1y] == "Checked") {
                    Spaces[pawn1x + 1][pawn1y].setBackground(new Color(150, 75, 0));
                    CheckPawn1[pawn1x + 1][pawn1y] = "";
                }
            }
            player_checked_state = 1;
            walld = 1;
            for(int i = 0 ;i<4; i++) {
                int x = pawn1x + dist_x[i];
                int y = pawn1x + dist_y[i];
                if (Is_Path(x, y) && Pawn.pawn_Location[1][0] == x && Pawn.pawn_Location[1][1] == y) {
                    System.out.println(x + " " + y);
                    CheckPawn1[x][y] = "";
                    Spaces[x][y].setBackground(Color.GRAY);
                    break;
                }
            }
        }else{
            if (CheckPawn1[pawn1x][pawn1y] == "setPawn") {
                if (pawn1x + 1 >= ROWS) {
                    if (pawn1y + 1 >= COLS) {
                        Spaces[pawn1x - 1][pawn1y].setBackground(new Color(150, 75, 0));
                        Spaces[pawn1x][pawn1y - 1].setBackground(new Color(150, 75, 0));

                        CheckPawn1[pawn1x - 1][pawn1y] = "";
                        CheckPawn1[pawn1x][pawn1y - 1] = "";

                        x = pawn1x;
                        y = pawn1y;
                    } else if (pawn1y - 1 < 0) {
                        Spaces[pawn1x - 1][pawn1y].setBackground(new Color(150, 75, 0));
                        Spaces[pawn1x][pawn1y + 1].setBackground(new Color(150, 75, 0));

                        CheckPawn1[pawn1x - 1][pawn1y] = "";
                        CheckPawn1[pawn1x][pawn1y + 1] = "";

                        x = pawn1x;
                        y = pawn1y;
                    } else {
                        Spaces[pawn1x - 1][pawn1y].setBackground(new Color(150, 75, 0));
                        Spaces[pawn1x][pawn1y + 1].setBackground(new Color(150, 75, 0));
                        Spaces[pawn1x][pawn1y - 1].setBackground(new Color(150, 75, 0));

                        CheckPawn1[pawn1x - 1][pawn1y] = "";
                        CheckPawn1[pawn1x][pawn1y + 1] = "";
                        CheckPawn1[pawn1x][pawn1y - 1] = "";

                        x = pawn1x;
                        y = pawn1y;
                    }
                } else if (pawn1y + 1 >= COLS) {
                    Spaces[pawn1x + 1][pawn1y].setBackground(new Color(150, 75, 0));
                    Spaces[pawn1x - 1][pawn1y].setBackground(new Color(150, 75, 0));
                    Spaces[pawn1x][pawn1y - 1].setBackground(new Color(150, 75, 0));

                    CheckPawn1[pawn1x + 1][pawn1y] = "";
                    CheckPawn1[pawn1x - 1][pawn1y] = "";
                    CheckPawn1[pawn1x][pawn1y - 1] = "";

                    x = pawn1x;
                    y = pawn1y;
                } else if (pawn1y - 1 < 0) {
                    Spaces[pawn1x + 1][pawn1y].setBackground(new Color(150, 75, 0));
                    Spaces[pawn1x - 1][pawn1y].setBackground(new Color(150, 75, 0));
                    Spaces[pawn1x][pawn1y + 1].setBackground(new Color(150, 75, 0));

                    CheckPawn1[pawn1x + 1][pawn1y] = "";
                    CheckPawn1[pawn1x - 1][pawn1y] = "";
                    CheckPawn1[pawn1x][pawn1y + 1] = "";

                    x = pawn1x;
                    y = pawn1y;
                } else {
                    Spaces[pawn1x + 1][pawn1y].setBackground(new Color(150, 75, 0));
                    Spaces[pawn1x - 1][pawn1y].setBackground(new Color(150, 75, 0));
                    Spaces[pawn1x][pawn1y + 1].setBackground(new Color(150, 75, 0));
                    Spaces[pawn1x][pawn1y - 1].setBackground(new Color(150, 75, 0));

                    CheckPawn1[pawn1x + 1][pawn1y] = "";
                    CheckPawn1[pawn1x - 1][pawn1y] = "";
                    CheckPawn1[pawn1x][pawn1y + 1] = "";
                    CheckPawn1[pawn1x][pawn1y - 1] = "";

                    x = pawn1x;
                    y = pawn1y;
                }
                player_checked_state = 0;
            }
            walld = 0;
        }
    }
    public void canMovePawn2(int pawn2x, int pawn2y) {
        if (player_checked_state == 0) {
            if (CheckPawn2[pawn2x][pawn2y] == "setPawn") {
                 if (pawn2x - 1 < 0) {
                    if (pawn2y + 1 >= COLS) {
                        Spaces[pawn2x + 1][pawn2y].setBackground(Color.gray);
                        Spaces[pawn2x][pawn2y - 1].setBackground(Color.gray);

                        CheckPawn2[pawn2x + 1][pawn2y] = "cango";
                        CheckPawn2[pawn2x][pawn2y - 1] = "cango";

                        x = pawn2x;
                        y = pawn2y;
                    } else if (pawn2y - 1 < 0) {
                        Spaces[pawn2x + 1][pawn2y].setBackground(Color.gray);
                        Spaces[pawn2x][pawn2y + 1].setBackground(Color.gray);

                        CheckPawn2[pawn2x + 1][pawn2y] = "cango";
                        CheckPawn2[pawn2x][pawn2y + 1] = "cango";

                        x = pawn2x;
                        y = pawn2y;
                    } else {
                        System.out.println("dd");
                        Spaces[pawn2x + 1][pawn2y].setBackground(Color.gray);
                        Spaces[pawn2x][pawn2y - 1].setBackground(Color.gray);
                        Spaces[pawn2x][pawn2y + 1].setBackground(Color.gray);

                        CheckPawn2[pawn2x + 1][pawn2y] = "cango";
                        CheckPawn2[pawn2x][pawn2y - 1] = "cango";
                        CheckPawn2[pawn2x][pawn2y + 1] = "cango";

                        x = pawn2x;
                        y = pawn2y;
                    }
                } else if (pawn2y + 1 >= COLS) {
                    Spaces[pawn2x + 1][pawn2y].setBackground(Color.gray);
                    Spaces[pawn2x - 1][pawn2y].setBackground(Color.gray);
                    Spaces[pawn2x][pawn2y - 1].setBackground(Color.gray);

                    CheckPawn2[pawn2x + 1][pawn2y] = "cango";
                    CheckPawn2[pawn2x - 1][pawn2y] = "cango";
                    CheckPawn2[pawn2x][pawn2y - 1] = "cango";

                    x = pawn2x;
                    y = pawn2y;
                } else if (pawn2y - 1 < 0) {
                    Spaces[pawn2x + 1][pawn2y].setBackground(Color.gray);
                    Spaces[pawn2x - 1][pawn2y].setBackground(Color.gray);
                    Spaces[pawn2x][pawn2y + 1].setBackground(Color.gray);

                    CheckPawn2[pawn2x + 1][pawn2y] = "cango";
                    CheckPawn2[pawn2x - 1][pawn2y] = "cango";
                    CheckPawn2[pawn2x][pawn2y + 1] = "cango";

                    x = pawn2x;
                    y = pawn2y;
                } else {
                    Spaces[pawn2x + 1][pawn2y].setBackground(Color.gray);
                    Spaces[pawn2x - 1][pawn2y].setBackground(Color.gray);
                    Spaces[pawn2x][pawn2y + 1].setBackground(Color.gray);
                    Spaces[pawn2x][pawn2y - 1].setBackground(Color.gray);

                    CheckPawn2[pawn2x + 1][pawn2y] = "cango";
                    CheckPawn2[pawn2x - 1][pawn2y] = "cango";
                    CheckPawn2[pawn2x][pawn2y + 1] = "cango";
                    CheckPawn2[pawn2x][pawn2y - 1] = "cango";

                    x = pawn2x;
                    y = pawn2y;
                }

                if (pawn2y > 0) {
                    if (CheckVwalls[pawn2x][pawn2y - 1] == "Checked") {
                        Spaces[pawn2x][pawn2y - 1].setBackground(new Color(150, 75, 0));
                        CheckPawn2[pawn2x][pawn2y - 1] = "";
                    }
                }
                if (CheckVwalls[pawn2x][pawn2y] == "Checked") {
                    Spaces[pawn2x][pawn2y + 1].setBackground(new Color(150, 75, 0));
                    CheckPawn2[pawn2x][pawn2y + 1] = "";
                }
                if (pawn2x > 0) {
                    if (CheckHwalls[pawn2x - 1][pawn2y] == "Checked") {
                        Spaces[pawn2x - 1][pawn2y].setBackground(new Color(150, 75, 0));
                        CheckPawn2[pawn2x - 1][pawn2y] = "";
                    }
                }
                if (CheckHwalls[pawn2x][pawn2y] == "Checked") {
                    Spaces[pawn2x + 1][pawn2y].setBackground(new Color(150, 75, 0));
                    CheckPawn2[pawn2x + 1][pawn2y] = "";
                }
                walld = 1;
                player_checked_state = 1;
                for(int i = 0 ;i<4; i++) {
                    int x = pawn2x + dist_x[i];
                    int y = pawn2y + dist_y[i];
                    if (Is_Path(x, y) && Pawn.pawn_Location[0][0] == x && Pawn.pawn_Location[0][1] == y) {
                        System.out.println("겹침 " + x + " " + y);
                        CheckPawn2[x][y] = "";
                        Spaces[x][y].setBackground(Color.GRAY);
                        break;
                    }
                }
            }
        } else {
            if (CheckPawn2[pawn2x][pawn2y] == "setPawn") {
                if (pawn2x - 1 < 0) {
                    if (pawn2y + 1 >= COLS) {
                        Spaces[pawn2x + 1][pawn2y].setBackground(new Color(150, 75, 0));
                        Spaces[pawn2x][pawn2y - 1].setBackground(new Color(150, 75, 0));

                        CheckPawn2[pawn2x + 1][pawn2y] = "";
                        CheckPawn2[pawn2x][pawn2y - 1] = "";

                        x = pawn2x;
                        y = pawn2y;
                    } else if (pawn2y - 1 < 0) {
                        Spaces[pawn2x + 1][pawn2y].setBackground(new Color(150, 75, 0));
                        Spaces[pawn2x][pawn2y + 1].setBackground(new Color(150, 75, 0));

                        CheckPawn2[pawn2x + 1][pawn2y] = "";
                        CheckPawn2[pawn2x][pawn2y + 1] = "";

                        x = pawn2x;
                        y = pawn2y;
                    } else {
                        Spaces[pawn2x + 1][pawn2y].setBackground(new Color(150, 75, 0));
                        Spaces[pawn2x][pawn2y - 1].setBackground(new Color(150, 75, 0));
                        Spaces[pawn2x][pawn2y + 1].setBackground(new Color(150, 75, 0));

                        CheckPawn2[pawn2x + 1][pawn2y] = "";
                        CheckPawn2[pawn2x][pawn2y - 1] = "";
                        CheckPawn2[pawn2x][pawn2y + 1] = "";

                        x = pawn2x;
                        y = pawn2y;
                    }
                } else if (pawn2y + 1 >= COLS) {
                    Spaces[pawn2x + 1][pawn2y].setBackground(new Color(150, 75, 0));
                    Spaces[pawn2x - 1][pawn2y].setBackground(new Color(150, 75, 0));
                    Spaces[pawn2x][pawn2y - 1].setBackground(new Color(150, 75, 0));

                    CheckPawn2[pawn2x + 1][pawn2y] = "";
                    CheckPawn2[pawn2x - 1][pawn2y] = "";
                    CheckPawn2[pawn2x][pawn2y - 1] = "";

                    x = pawn2x;
                    y = pawn2y;
                } else if (pawn2y - 1 < 0) {
                    Spaces[pawn2x + 1][pawn2y].setBackground(new Color(150, 75, 0));
                    Spaces[pawn2x - 1][pawn2y].setBackground(new Color(150, 75, 0));
                    Spaces[pawn2x][pawn2y + 1].setBackground(new Color(150, 75, 0));

                    CheckPawn2[pawn2x + 1][pawn2y] = "";
                    CheckPawn2[pawn2x - 1][pawn2y] = "";
                    CheckPawn2[pawn2x][pawn2y + 1] = "";

                    x = pawn2x;
                    y = pawn2y;
                } else {
                    Spaces[pawn2x + 1][pawn2y].setBackground(new Color(150, 75, 0));
                    Spaces[pawn2x - 1][pawn2y].setBackground(new Color(150, 75, 0));
                    Spaces[pawn2x][pawn2y + 1].setBackground(new Color(150, 75, 0));
                    Spaces[pawn2x][pawn2y - 1].setBackground(new Color(150, 75, 0));

                    CheckPawn2[pawn2x + 1][pawn2y] = "";
                    CheckPawn2[pawn2x - 1][pawn2y] = "";
                    CheckPawn2[pawn2x][pawn2y + 1] = "";
                    CheckPawn2[pawn2x][pawn2y - 1] = "";

                    x = pawn2x;
                    y = pawn2y;
                }
                player_checked_state = 0;
            }
            walld = 0;
        }
    }
    public void deletePawnSet1(int pawn1x, int pawn1y) {
            if (CheckPawn1[pawn1x][pawn1y] == "cango") {
                player_checked_state = 0;
                if (x + 1 >= ROWS) {
                    if (y + 1 >= COLS) {
                        Pawn.setPawn(0, pawn1x, pawn1y);
                        //회색 칠 지우기
                        Spaces[x - 1][y].setBackground(new Color(150, 75, 0));
                        Spaces[x][y - 1].setBackground(new Color(150, 75, 0));

                        //setPawn 자리 색칠
                        Spaces[pawn1x][pawn1y].setBackground(Color.gray);

                        //cango 지우기
                        CheckPawn1[x - 1][y] = "";
                        CheckPawn1[x][y - 1] = "";

                        CheckPawn1[x][y] = ""; // setPawn 지우기

                        CheckPawn1[pawn1x][pawn1y] = "setPawn";
                    } else if (y - 1 < 0) {
                        Pawn.setPawn(0, pawn1x, pawn1y);
                        //회색 칠 지우기
                        Spaces[x - 1][y].setBackground(new Color(150, 75, 0));
                        Spaces[x][y + 1].setBackground(new Color(150, 75, 0));

                        //setPawn 자리 색칠
                        Spaces[pawn1x][pawn1y].setBackground(Color.gray);

                        //cango 지우기
                        CheckPawn1[x - 1][y] = "";
                        CheckPawn1[x][y + 1] = "";

                        CheckPawn1[x][y] = ""; // setPawn 지우기

                        CheckPawn1[pawn1x][pawn1y] = "setPawn";
                    } else {
                        Pawn.setPawn(0, pawn1x, pawn1y);
                        //회색 칠 지우기
                        Spaces[x - 1][y].setBackground(new Color(150, 75, 0));
                        Spaces[x][y + 1].setBackground(new Color(150, 75, 0));
                        Spaces[x][y - 1].setBackground(new Color(150, 75, 0));

                        //setPawn 자리 색칠
                        Spaces[pawn1x][pawn1y].setBackground(Color.gray);

                        //cango 지우기
                        CheckPawn1[x - 1][y] = "";
                        CheckPawn1[x][y + 1] = "";
                        CheckPawn1[x][y - 1] = "";

                        CheckPawn1[x][y] = ""; // setPawn 지우기

                        CheckPawn1[pawn1x][pawn1y] = "setPawn";
                    }
                } else if (y + 1 >= COLS) {
                    Pawn.setPawn(0, pawn1x, pawn1y);
                    //회색 칠 지우기
                    Spaces[x + 1][y].setBackground(new Color(150, 75, 0));
                    Spaces[x - 1][y].setBackground(new Color(150, 75, 0));
                    Spaces[x][y - 1].setBackground(new Color(150, 75, 0));

                    //setPawn 자리 색칠
                    Spaces[pawn1x][pawn1y].setBackground(Color.gray);

                    //cango 지우기
                    CheckPawn1[x + 1][y] = "";
                    CheckPawn1[x - 1][y] = "";
                    CheckPawn1[x][y - 1] = "";

                    CheckPawn1[x][y] = ""; // setPawn 지우기

                    CheckPawn1[pawn1x][pawn1y] = "setPawn";
                } else if (y - 1 < 0) {
                    Pawn.setPawn(0, pawn1x, pawn1y);
                    //회색 칠 지우기
                    Spaces[x + 1][y].setBackground(new Color(150, 75, 0));
                    Spaces[x - 1][y].setBackground(new Color(150, 75, 0));
                    Spaces[x][y + 1].setBackground(new Color(150, 75, 0));

                    //setPawn 자리 색칠
                    Spaces[pawn1x][pawn1y].setBackground(Color.gray);

                    //cango 지우기
                    CheckPawn1[x + 1][y] = "";
                    CheckPawn1[x - 1][y] = "";
                    CheckPawn1[x][y + 1] = "";

                    CheckPawn1[x][y] = ""; // setPawn 지우기

                    CheckPawn1[pawn1x][pawn1y] = "setPawn";
                } else {
                    Pawn.setPawn(0, pawn1x, pawn1y);
                    //회색 칠 지우기
                    Spaces[x + 1][y].setBackground(new Color(150, 75, 0));
                    Spaces[x - 1][y].setBackground(new Color(150, 75, 0));
                    Spaces[x][y + 1].setBackground(new Color(150, 75, 0));
                    Spaces[x][y - 1].setBackground(new Color(150, 75, 0));

                    //setPawn 자리 색칠
                    Spaces[pawn1x][pawn1y].setBackground(Color.gray);

                    //cango 지우기
                    CheckPawn1[x + 1][y] = "";
                    CheckPawn1[x - 1][y] = "";
                    CheckPawn1[x][y + 1] = "";
                    CheckPawn1[x][y - 1] = "";

                    CheckPawn1[x][y] = ""; // setPawn 지우기

                    CheckPawn1[pawn1x][pawn1y] = "setPawn";
                }
                walld = 0;
                Win(pawn1x, Main.client.getMyId());
                Main.client.Write(new Packet(Main.client.getMyId(), pawn1x, pawn1y, Packet.State.Move, Main.client.isTurn()));
            }
    }
    public void deletePawnSet2(int pawn2x, int pawn2y) {
        if (CheckPawn2[pawn2x][pawn2y] == "cango") {
            player_checked_state = 0;
            if (x - 1 < 0) {
                if (y + 1 >= COLS) {
                    Pawn.setPawn(1, pawn2x, pawn2y);
                    //회색 칠 지우기
                    Spaces[x + 1][y].setBackground(new Color(150, 75, 0));
                    Spaces[x][y - 1].setBackground(new Color(150, 75, 0));

                    //setPawn 자리 색칠
                    Spaces[pawn2x][pawn2y].setBackground(Color.gray);

                    //cango 지우기
                    CheckPawn2[x + 1][y] = "";
                    CheckPawn2[x][y - 1] = "";

                    CheckPawn2[x][y] = ""; // setPawn 지우기

                    CheckPawn2[pawn2x][pawn2y] = "setPawn";
                } else if (y - 1 < 0) {
                    Pawn.setPawn(1, pawn2x, pawn2y);
                    //회색 칠 지우기
                    Spaces[x + 1][y].setBackground(new Color(150, 75, 0));
                    Spaces[x][y + 1].setBackground(new Color(150, 75, 0));

                    //setPawn 자리 색칠
                    Spaces[pawn2x][pawn2y].setBackground(Color.gray);

                    //cango 지우기
                    CheckPawn2[x + 1][y] = "";
                    CheckPawn2[x][y + 1] = "";

                    CheckPawn2[x][y] = ""; // setPawn 지우기

                    CheckPawn2[pawn2x][pawn2y] = "setPawn";
                }else{
                    Pawn.setPawn(1, pawn2x, pawn2y);
                    //회색 칠 지우기
                    Spaces[x + 1][y].setBackground(new Color(150, 75, 0));
                    Spaces[x][y + 1].setBackground(new Color(150, 75, 0));
                    Spaces[x][y - 1].setBackground(new Color(150, 75, 0));

                    //setPawn 자리 색칠
                    Spaces[pawn2x][pawn2y].setBackground(Color.gray);

                    //cango 지우기
                    CheckPawn2[x + 1][y] = "";
                    CheckPawn2[x][y + 1] = "";
                    CheckPawn2[x][y - 1] = "";

                    CheckPawn2[x][y] = ""; // setPawn 지우기

                    CheckPawn2[pawn2x][pawn2y] = "setPawn";
                }
            } else if (y + 1 >= COLS) {
                Pawn.setPawn(1, pawn2x, pawn2y);
                //회색 칠 지우기
                Spaces[x + 1][y].setBackground(new Color(150, 75, 0));
                Spaces[x - 1][y].setBackground(new Color(150, 75, 0));
                Spaces[x][y - 1].setBackground(new Color(150, 75, 0));

                //setPawn 자리 색칠
                Spaces[pawn2x][pawn2y].setBackground(Color.gray);

                //cango 지우기
                CheckPawn2[x + 1][y] = "";
                CheckPawn2[x - 1][y] = "";
                CheckPawn2[x][y - 1] = "";

                CheckPawn2[x][y] = ""; // setPawn 지우기

                CheckPawn2[pawn2x][pawn2y] = "setPawn";
            } else if (y - 1 < 0) {
                Pawn.setPawn(1, pawn2x, pawn2y);
                //회색 칠 지우기
                Spaces[x + 1][y].setBackground(new Color(150, 75, 0));
                Spaces[x - 1][y].setBackground(new Color(150, 75, 0));
                Spaces[x][y + 1].setBackground(new Color(150, 75, 0));

                //setPawn 자리 색칠
                Spaces[pawn2x][pawn2y].setBackground(Color.gray);

                //cango 지우기
                CheckPawn2[x + 1][y] = "";
                CheckPawn2[x- 1][y] = "";
                CheckPawn2[x][y + 1] = "";

                CheckPawn2[x][y] = ""; // setPawn 지우기

                CheckPawn2[pawn2x][pawn2y] = "setPawn";
            }else{
                Pawn.setPawn(1, pawn2x, pawn2y);
                //회색 칠 지우기
                Spaces[x + 1][y].setBackground(new Color(150, 75, 0));
                Spaces[x - 1][y].setBackground(new Color(150, 75, 0));
                Spaces[x][y + 1].setBackground(new Color(150, 75, 0));
                Spaces[x][y - 1].setBackground(new Color(150, 75, 0));

                //setPawn 자리 색칠
                Spaces[pawn2x][pawn2y].setBackground(Color.gray);

                //cango 지우기
                CheckPawn2[x + 1][y] = "";
                CheckPawn2[x - 1][y] = "";
                CheckPawn2[x][y + 1] = "";
                CheckPawn2[x][y - 1] = "";

                CheckPawn2[x][y] = ""; // setPawn 지우기

                CheckPawn2[pawn2x][pawn2y] = "setPawn";
            }
            Win(pawn2x, Main.client.getMyId());
            Main.client.Write(new Packet(Main.client.getMyId(), pawn2x, pawn2y, Packet.State.Move, Main.client.isTurn()));
        }
    }
    public void Win(int x, int ID){
        if(ID == 0) {
            if (x == 0) {
                JOptionPane.showMessageDialog(this, "Player1 승리", "게임 종료", JOptionPane.INFORMATION_MESSAGE, null);
                answer = 1;
            }
        }else if(ID == 1) {
            if(x == 8) {
                JOptionPane.showMessageDialog(this, "Player2 승리", "게임 종료", JOptionPane.INFORMATION_MESSAGE, null);
                answer = 1;
            }
        }
        if(answer== 1) {  //사용자가 yes를 눌렀을 경우
            System.out.println("프로그램을 종료합니다.");
            System.exit(0);
        }
    }
    public void Enemy_Move(int px, int py, int ID) {
        Pawn.setPawn(ID, px, py);
        Spaces[px][py].setBackground(Color.gray);
        if (ID == 0) {
            CheckPawn1[x][y] = ""; // setPawn 지우기
            CheckPawn1[px][py] = "setPawn1";
            Pawn.pawn_Location[0][0] = px;
            Pawn.pawn_Location[0][1] = py;
        }else if (ID == 1) {
            CheckPawn2[x][y] = ""; // setPawn 지우기
            CheckPawn2[px][py] = "setPawn2";
            Pawn.pawn_Location[1][0] = px;
            Pawn.pawn_Location[1][1] = py;
        }
    }
    @Override
    public void actionPerformed(ActionEvent event) {
        List<Integer> select_space = getSquare(event.getSource());
        List<Integer> select_verticalWall = getVerticalWall(event.getSource());
        List<Integer> select_horizontalWall = getHorizontalWall(event.getSource());

        System.out.println("My Turn Check : " + Main.client.isTurn());
        if(Main.client.isTurn()) {
            if (select_space != null) {
                int x = select_space.get(0);
                int y = select_space.get(1);
                System.out.println("(" + y + "," + x + ")");
                if(Main.client.getMyId() == 0) {
                    canMovePawn1(x, y);
                    deletePawnSet1(x, y);
                }else {
                    canMovePawn2(x, y);
                    deletePawnSet2(x, y);
                }
            } else if (walld == 0 && select_verticalWall != null) {
                int x = select_verticalWall.get(0);
                int y = select_verticalWall.get(1);
                if(Main.client.getMyId() == 0 && wall_count_player1 < 6){
                    wall_count_player1 += 1;
                    CreateVerticalWall(x, y, Main.client.isTurn());
                }else if (Main.client.getMyId() == 1 && wall_count_player2 < 6){
                    wall_count_player2 += 1;
                    CreateVerticalWall(x, y, Main.client.isTurn());
                }
            }
            else if (walld == 0 && select_horizontalWall != null) {
                int x = select_horizontalWall.get(0);
                int y = select_horizontalWall.get(1);
                if(Main.client.getMyId() == 0 && wall_count_player1 < 6){
                    wall_count_player1 += 1;
                    CreateHorizontalWall(x, y, Main.client.isTurn());
                }else if (Main.client.getMyId() == 1 && wall_count_player2 < 6){
                    wall_count_player2 += 1;
                    CreateHorizontalWall(x, y, Main.client.isTurn());
                }
            } else {
                System.out.println("확인되지 않은 플레이");
            }
        }
   }

    private List<Integer> getSquare(Object source) {
        for (int i = 0; i < Board.Spaces.length; ++i) {
            for (int j = 0; j < Board.Spaces[i].length; ++j) {
                if (Board.Spaces[i][j] == source) {
                    return asList(i, j);
                }
            }
        }
        return null;
    }

    private List<Integer> getVerticalWall(Object source) {
        for (int i = 0; i < Board.VerticalWalls.length; ++i) {
            for (int j = 0; j < Board.VerticalWalls[i].length; ++j) {
                if (Board.VerticalWalls[i][j] == source) {
                    return asList(i, j); //asList
                }
            }
        }
        return null;
    }

    private List<Integer> getHorizontalWall(Object source) {
        for (int i = 0; i < Board.HorizontalWalls.length; ++i) {
            for (int j = 0; j < Board.HorizontalWalls[i].length; ++j) {
                if (Board.HorizontalWalls[i][j] == source) {
                    return asList(i, j);
                }
            }
        }
        return null;
    }
}
