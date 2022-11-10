package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLOutput;
import java.util.List;
import java.util.*;
import static java.util.Arrays.*;
import static main.Board.*;
import static main.Board.y;

import main.Pawn.*;
import DB.*;

public class Quoridor extends JFrame implements ActionListener{

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
        if (x + 1 >= ROWS) {
            if (CheckVwalls[x][y] != "Checked" && CheckVwalls[x - 1][y] != "Checked") {
                setVerticalWall(x - 1, y);
                CheckVwalls[x][y] = "Checked";
                CheckVwalls[x - 1][y] = "Checked";
            }
        } else if (CheckVwalls[x][y] != "Checked" && CheckVwalls[x + 1][y] != "Checked") {
            setVerticalWall(x, y);
            CheckVwalls[x][y] = "Checked";
            CheckVwalls[x + 1][y] = "Checked";
        }
        System.out.println("(" + (y + 1 + "," + (x + 1)) + ")," + "(" + (y + 1 + "," + (x + 2)) + ")"); // to do
        if(turn)
            Main.client.Write(new Packet(Main.client.getMyId(), x, y, Packet.State.Vertical_Wall, Main.client.isTurn()));
    }
    public void CreateHorizontalWall(int x, int y, boolean turn){
        System.out.println("가로 벽 실행");
        try {
            if (y + 1 >= COLS) {
                if (CheckHwalls[x][y] != "Checked" && CheckHwalls[x][y - 1] != "Checked") {
                    setHorizontalWall(x, y - 1);
                    CheckHwalls[x][y] = "Checked";
                    CheckHwalls[x][y - 1] = "Checked";
                }
            } else if (CheckHwalls[x][y] != "Checked" && CheckHwalls[x][y + 1] != "Checked") {
                setHorizontalWall(x, y);
                CheckHwalls[x][y] = "Checked";
                CheckHwalls[x][y + 1] = "Checked";
            }
            System.out.println("(" + (y + 1 + "," + (x + 1)) + ")," + "(" + (y + 2 + "," + (x + 1)) + ")"); //to do
            if (turn)
                Main.client.Write(new Packet(Main.client.getMyId(), x, y, Packet.State.Horizontal_Wall, Main.client.isTurn()));
        }
        catch(Exception e){
            System.out.println("Error");
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
        }else{
            if (CheckPawn1[pawn1x][pawn1y] == "setPawn") {
                Spaces[pawn1x + 1][pawn1y].setBackground(new Color(150, 75, 0));
                Spaces[pawn1x - 1][pawn1y].setBackground(new Color(150, 75, 0));
                Spaces[pawn1x][pawn1y + 1].setBackground(new Color(150, 75, 0));
                Spaces[pawn1x][pawn1y - 1].setBackground(new Color(150, 75, 0));
                CheckPawn1[pawn1x][pawn1y + 1] = "";
                CheckPawn1[pawn1x][pawn1y - 1] = "";
                CheckPawn1[pawn1x + 1][pawn1y] = "";
                CheckPawn1[pawn1x - 1][pawn1y] = "";
                player_checked_state = 0;
            }
        }
    }

    public void canMovePawn2(int pawn2x, int pawn2y) {
        if (player_checked_state == 0) {
            System.out.println("작동");
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
            }
        } else {
            if (CheckPawn2[pawn2x][pawn2y] == "setPawn") {
                Spaces[pawn2x + 1][pawn2y].setBackground(new Color(150, 75, 0));
                Spaces[pawn2x - 1][pawn2y].setBackground(new Color(150, 75, 0));
                Spaces[pawn2x][pawn2y + 1].setBackground(new Color(150, 75, 0));
                Spaces[pawn2x][pawn2y - 1].setBackground(new Color(150, 75, 0));
                CheckPawn2[pawn2x][pawn2y + 1] = "";
                CheckPawn2[pawn2x][pawn2y - 1] = "";
                CheckPawn2[pawn2x + 1][pawn2y] = "";
                CheckPawn2[pawn2x - 1][pawn2y] = "";
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
                Main.client.Write(new Packet(Main.client.getMyId(), pawn1x, pawn1y, Packet.State.Move, Main.client.isTurn()));
            }
    }

    public void deletePawnSet2(int pawn2x, int pawn2y) {
        if (CheckPawn2[pawn2x][pawn2y] == "cango") {
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
            walld = 0;
            Main.client.Write(new Packet(Main.client.getMyId(), pawn2x, pawn2y, Packet.State.Move, Main.client.isTurn()));
        }
    }

    public void Enemy_Move(int px, int py, int ID) {
        Pawn.setPawn(ID, px, py);
        Spaces[px][py].setBackground(Color.gray);
        if (ID == 0) {
            CheckPawn1[x][y] = ""; // setPawn 지우기
            CheckPawn1[px][py] = "setPawn";
        }else if (ID == 1) {
            CheckPawn2[x][y] = ""; // setPawn 지우기
            CheckPawn2[px][py] = "setPawn";
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
                System.out.println("(" + (y + 1) + "," + (x + 1) + ")");
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
                CreateVerticalWall(x, y, Main.client.isTurn());
            }
            else if (walld == 0 && select_horizontalWall != null) {
                int x = select_horizontalWall.get(0);
                int y = select_horizontalWall.get(1);
                CreateHorizontalWall(x, y, Main.client.isTurn());
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