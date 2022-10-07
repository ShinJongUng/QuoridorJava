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
import static main.Board.CheckHwalls;
import main.Pawn.*;
import DB.*;

public class Quoridor extends JFrame implements MouseListener, ActionListener{

    public void mouseClicked(MouseEvent event) {}
    public void mousePressed(MouseEvent event) {}
    public void mouseReleased(MouseEvent event) {}
    public void mouseEntered(MouseEvent event) {}
    public void mouseExited(MouseEvent event) {}
    private void setVerticalWall(int r, int c) {
        Board.VerticalWalls[r + 1][c].setBackground(Color.orange); // 누른곳 밑
        Board.VerticalWalls[r][c].setBackground(Color.orange);
        Board.CenterWalls[r][c].setBackground(Color.orange);
    }

    private void setHorizontalWall(int r, int c){
        Board.HorizontalWalls[r][c + 1].setBackground(Color.orange); // 누른곳 오른쪽
        Board.HorizontalWalls[r][c].setBackground(Color.orange); // 누른곳
        Board.CenterWalls[r][c].setBackground(Color.orange); // 가운데
    }

    private void canMovePawn(ActionEvent e){
        List<Integer> select_space = getSquare(e.getSource());
        if (CheckPawn1[select_space.get(0)][select_space.get(1)] == "setPawn") {
            if (select_space.get(0) + 1 >= ROWS) {
                if (select_space.get(1) + 1 >= COLS) {
                    Spaces[select_space.get(0) - 1][select_space.get(0)].setBackground(Color.gray);
                    Spaces[select_space.get(0)][select_space.get(0) - 1].setBackground(Color.gray);

                    CheckPawn1[select_space.get(1) - 1][select_space.get(0)] = "cango";
                    CheckPawn1[select_space.get(1)][select_space.get(0) - 1] = "cango";

                    x = select_space.get(0);
                    y = select_space.get(1);
                } else if (select_space.get(1) - 1 < 0) {
                    Spaces[select_space.get(0) - 1][select_space.get(1)].setBackground(Color.gray);
                    Spaces[select_space.get(0)][select_space.get(1) + 1].setBackground(Color.gray);

                    CheckPawn1[select_space.get(0) - 1][select_space.get(1)] = "cango";
                    CheckPawn1[select_space.get(0)][select_space.get(1) + 1] = "cango";

                    x = select_space.get(0);
                    y = select_space.get(1);
                }else{
                    Spaces[select_space.get(0) - 1][select_space.get(1)].setBackground(Color.gray);
                    Spaces[select_space.get(0)][select_space.get(1) + 1].setBackground(Color.gray);
                    Spaces[select_space.get(0)][select_space.get(1) - 1].setBackground(Color.gray);

                    CheckPawn1[select_space.get(0) - 1][select_space.get(1)] = "cango";
                    CheckPawn1[select_space.get(0)][select_space.get(1) + 1] = "cango";
                    CheckPawn1[select_space.get(0)][select_space.get(1) - 1] = "cango";

                    x = select_space.get(0);
                    y = select_space.get(1);
                }
            } else if (select_space.get(0) - 1 < 0) {
                if (select_space.get(1) + 1 >= COLS) {
                    Spaces[select_space.get(0) + 1][select_space.get(1)].setBackground(Color.gray);
                    Spaces[select_space.get(0)][select_space.get(1) - 1].setBackground(Color.gray);

                    CheckPawn1[select_space.get(0) + 1][select_space.get(1)] = "cango";
                    CheckPawn1[select_space.get(0)][select_space.get(1) - 1] = "cango";

                    x = select_space.get(0);
                    y = select_space.get(1);
                } else if (select_space.get(1) - 1 < 0) {
                    Spaces[select_space.get(0) + 1][select_space.get(1)].setBackground(Color.gray);
                    Spaces[select_space.get(0)][select_space.get(1) + 1].setBackground(Color.gray);

                    CheckPawn1[select_space.get(0) + 1][select_space.get(1)] = "cango";
                    CheckPawn1[select_space.get(0)][select_space.get(1) + 1] = "cango";

                    x = select_space.get(0);
                    y = select_space.get(1);
                }else{
                    Spaces[select_space.get(0) + 1][select_space.get(1)].setBackground(Color.gray);
                    Spaces[select_space.get(0)][select_space.get(1) - 1].setBackground(Color.gray);
                    Spaces[select_space.get(0)][select_space.get(1) + 1].setBackground(Color.gray);

                    CheckPawn1[select_space.get(0) + 1][select_space.get(1)] = "cango";
                    CheckPawn1[select_space.get(0)][select_space.get(1) - 1] = "cango";
                    CheckPawn1[select_space.get(0)][select_space.get(1) + 1] = "cango";

                    x = select_space.get(0);
                    y = select_space.get(1);
                }
            } else if (select_space.get(1) + 1 >= COLS) {
                Spaces[select_space.get(0) + 1][select_space.get(1)].setBackground(Color.gray);
                Spaces[select_space.get(0) - 1][select_space.get(1)].setBackground(Color.gray);
                Spaces[select_space.get(0)][select_space.get(1) - 1].setBackground(Color.gray);

                CheckPawn1[select_space.get(0) + 1][select_space.get(1)] = "cango";
                CheckPawn1[select_space.get(0) - 1][select_space.get(1)] = "cango";
                CheckPawn1[select_space.get(0)][select_space.get(1) - 1] = "cango";

                x = select_space.get(0);
                y = select_space.get(1);
            } else if (select_space.get(1) - 1 < 0) {
                Spaces[select_space.get(0) + 1][select_space.get(1)].setBackground(Color.gray);
                Spaces[select_space.get(0) - 1][select_space.get(1)].setBackground(Color.gray);
                Spaces[select_space.get(0)][select_space.get(1) + 1].setBackground(Color.gray);

                CheckPawn1[select_space.get(0) + 1][select_space.get(1)] = "cango";
                CheckPawn1[select_space.get(0) - 1][select_space.get(1)] = "cango";
                CheckPawn1[select_space.get(0)][select_space.get(1) + 1] = "cango";

                x = select_space.get(0);
                y = select_space.get(1);
            }else{
                Spaces[select_space.get(0) + 1][select_space.get(1)].setBackground(Color.gray);
                Spaces[select_space.get(0) - 1][select_space.get(1)].setBackground(Color.gray);
                Spaces[select_space.get(0)][select_space.get(1) + 1].setBackground(Color.gray);
                Spaces[select_space.get(0)][select_space.get(1) - 1].setBackground(Color.gray);

                CheckPawn1[select_space.get(0) + 1][select_space.get(1)] = "cango";
                CheckPawn1[select_space.get(0) - 1][select_space.get(1)] = "cango";
                CheckPawn1[select_space.get(0)][select_space.get(1) + 1] = "cango";
                CheckPawn1[select_space.get(0)][select_space.get(1) - 1] = "cango";

                x = select_space.get(0);
                y = select_space.get(1);
            }
        }
    }

    private void deletePawnSet(ActionEvent event) {
        List<Integer> select_space = getSquare(event.getSource());
        if (CheckPawn1[select_space.get(0)][select_space.get(1)] == "cango") {
            if (x + 1 >= ROWS) {
                if (y + 1 >= COLS) {
                    Pawn.setPawn(0, (JButton) event.getSource());
                    //회색 칠 지우기
                    Spaces[x - 1][y].setBackground(new Color(150, 75, 0));
                    Spaces[x][y - 1].setBackground(new Color(150, 75, 0));

                    //setPawn 자리 색칠
                    Spaces[select_space.get(0)][select_space.get(1)].setBackground(Color.gray);

                    //cango 지우기
                    CheckPawn1[x - 1][y] = "";
                    CheckPawn1[x][y - 1] = "";

                    CheckPawn1[x][y] = ""; // setPawn 지우기

                    CheckPawn1[select_space.get(0)][select_space.get(1)] = "setPawn";
                } else if (y - 1 < 0) {
                    Pawn.setPawn(0, (JButton) event.getSource());
                    //회색 칠 지우기
                    Spaces[x - 1][y].setBackground(new Color(150, 75, 0));
                    Spaces[x][y + 1].setBackground(new Color(150, 75, 0));

                    //setPawn 자리 색칠
                    Spaces[select_space.get(0)][select_space.get(1)].setBackground(Color.gray);

                    //cango 지우기
                    CheckPawn1[x - 1][y] = "";
                    CheckPawn1[x][y + 1] = "";

                    CheckPawn1[x][y] = ""; // setPawn 지우기

                    CheckPawn1[select_space.get(0)][select_space.get(1)] = "setPawn";
                }else{
                    Pawn.setPawn(0, (JButton) event.getSource());
                    //회색 칠 지우기
                    Spaces[x - 1][y].setBackground(new Color(150, 75, 0));
                    Spaces[x][y + 1].setBackground(new Color(150, 75, 0));
                    Spaces[x][y - 1].setBackground(new Color(150, 75, 0));

                    //setPawn 자리 색칠
                    Spaces[select_space.get(0)][select_space.get(1)].setBackground(Color.gray);

                    //cango 지우기
                    CheckPawn1[x - 1][y] = "";
                    CheckPawn1[x][y + 1] = "";
                    CheckPawn1[x][y - 1] = "";

                    CheckPawn1[x][y] = ""; // setPawn 지우기

                    CheckPawn1[select_space.get(0)][select_space.get(1)] = "setPawn";
                }
            } else if (x - 1 < 0) {
                if (y + 1 >= COLS) {
                    Pawn.setPawn(0, (JButton) event.getSource());
                    //회색 칠 지우기
                    Spaces[x + 1][y].setBackground(new Color(150, 75, 0));
                    Spaces[x][y - 1].setBackground(new Color(150, 75, 0));

                    //setPawn 자리 색칠
                    Spaces[select_space.get(0)][select_space.get(1)].setBackground(Color.gray);

                    //cango 지우기
                    CheckPawn1[x + 1][y] = "";
                    CheckPawn1[x][y - 1] = "";

                    CheckPawn1[x][y] = ""; // setPawn 지우기

                    CheckPawn1[select_space.get(0)][select_space.get(1)] = "setPawn";
                } else if (y - 1 < 0) {
                    Pawn.setPawn(0, (JButton) event.getSource());
                    //회색 칠 지우기
                    Spaces[x - 1][y].setBackground(new Color(150, 75, 0));
                    Spaces[x][y + 1].setBackground(new Color(150, 75, 0));

                    //setPawn 자리 색칠
                    Spaces[select_space.get(0)][select_space.get(1)].setBackground(Color.gray);

                    //cango 지우기
                    CheckPawn1[x - 1][y] = "";
                    CheckPawn1[x][y + 1] = "";

                    CheckPawn1[x][y] = ""; // setPawn 지우기

                    CheckPawn1[select_space.get(0)][select_space.get(1)] = "setPawn";
                }else{
                    Pawn.setPawn(0, (JButton) event.getSource());
                    //회색 칠 지우기
                    Spaces[x + 1][y].setBackground(new Color(150, 75, 0));
                    Spaces[x][y + 1].setBackground(new Color(150, 75, 0));
                    Spaces[x][y - 1].setBackground(new Color(150, 75, 0));

                    //setPawn 자리 색칠
                    Spaces[select_space.get(0)][select_space.get(1)].setBackground(Color.gray);

                    //cango 지우기
                    CheckPawn1[x + 1][y] = "";
                    CheckPawn1[x][y + 1] = "";
                    CheckPawn1[x][y - 1] = "";

                    CheckPawn1[x][y] = ""; // setPawn 지우기

                    CheckPawn1[select_space.get(0)][select_space.get(1)] = "setPawn";
                }
            } else if (y + 1 >= COLS) {
                Pawn.setPawn(0, (JButton) event.getSource());
                //회색 칠 지우기
                Spaces[x + 1][y].setBackground(new Color(150, 75, 0));
                Spaces[x - 1][y].setBackground(new Color(150, 75, 0));
                Spaces[x][y - 1].setBackground(new Color(150, 75, 0));

                //setPawn 자리 색칠
                Spaces[select_space.get(0)][select_space.get(1)].setBackground(Color.gray);

                //cango 지우기
                CheckPawn1[x + 1][y] = "";
                CheckPawn1[x - 1][y] = "";
                CheckPawn1[x][y - 1] = "";

                CheckPawn1[x][y] = ""; // setPawn 지우기

                CheckPawn1[select_space.get(0)][select_space.get(1)] = "setPawn";
            } else if (y - 1 < 0) {
                Pawn.setPawn(0, (JButton) event.getSource());
                //회색 칠 지우기
                Spaces[x + 1][y].setBackground(new Color(150, 75, 0));
                Spaces[x - 1][y].setBackground(new Color(150, 75, 0));
                Spaces[x][y + 1].setBackground(new Color(150, 75, 0));

                //setPawn 자리 색칠
                Spaces[select_space.get(0)][select_space.get(1)].setBackground(Color.gray);

                //cango 지우기
                CheckPawn1[x + 1][y] = "";
                CheckPawn1[x- 1][y] = "";
                CheckPawn1[x][y + 1] = "";

                CheckPawn1[x][y] = ""; // setPawn 지우기

                CheckPawn1[select_space.get(0)][select_space.get(1)] = "setPawn";
            }else{
                Pawn.setPawn(0, (JButton) event.getSource());
                //회색 칠 지우기
                Spaces[x + 1][y].setBackground(new Color(150, 75, 0));
                Spaces[x - 1][y].setBackground(new Color(150, 75, 0));
                Spaces[x][y + 1].setBackground(new Color(150, 75, 0));
                Spaces[x][y - 1].setBackground(new Color(150, 75, 0));

                //setPawn 자리 색칠
                Spaces[select_space.get(0)][select_space.get(1)].setBackground(Color.gray);

                //cango 지우기
                CheckPawn1[x + 1][y] = "";
                CheckPawn1[x - 1][y] = "";
                CheckPawn1[x][y + 1] = "";
                CheckPawn1[x][y - 1] = "";

                CheckPawn1[x][y] = ""; // setPawn 지우기

                CheckPawn1[select_space.get(0)][select_space.get(1)] = "setPawn";
            }
        }
    }
    @Override
    public void actionPerformed(ActionEvent event) {
        List<Integer> select_space = getSquare(event.getSource());
        List<Integer> select_verticalWall = getVerticalWall(event.getSource());
        List<Integer> select_horizontalWall = getHorizontalWall(event.getSource());

        if (select_space != null) {
            System.out.println("(" + (select_space.get(1) + 1) + "," + (select_space.get(0) + 1) + ")");
            canMovePawn(event);
            deletePawnSet(event);
        }
        else if(select_verticalWall != null) {
            if(select_verticalWall.get(0) + 1 >= ROWS){
                if(CheckVwalls[select_verticalWall.get(0)][select_verticalWall.get(1)] != "Checked" && CheckVwalls[select_verticalWall.get(0) - 1][select_verticalWall.get(1)] != "Checked"){
                    setVerticalWall(select_verticalWall.get(0) - 1, select_verticalWall.get(1));

                    System.out.println("(" + (select_verticalWall.get(1) + 1 + "," + (select_verticalWall.get(0) + 1)) + ")," + "(" + (select_verticalWall.get(1) + 1 + "," + (select_verticalWall.get(0) + 2)) + ")"); // to do

                    CheckVwalls[select_verticalWall.get(0)][select_verticalWall.get(1)] = "Checked";
                    CheckVwalls[select_verticalWall.get(0) - 1][select_verticalWall.get(1)] = "Checked";
                }
            }
            else if(CheckVwalls[select_verticalWall.get(0)][select_verticalWall.get(1)] != "Checked" && CheckVwalls[select_verticalWall.get(0) + 1][select_verticalWall.get(1)] != "Checked"){
                setVerticalWall(select_verticalWall.get(0), select_verticalWall.get(1));

                System.out.println("(" + (select_verticalWall.get(1) + 1 + "," + (select_verticalWall.get(0) + 1)) + ")," + "(" + (select_verticalWall.get(1) + 1 + "," + (select_verticalWall.get(0) + 2)) + ")"); // to do

                CheckVwalls[select_verticalWall.get(0)][select_verticalWall.get(1)] = "Checked";
                CheckVwalls[select_verticalWall.get(0) + 1][select_verticalWall.get(1)] = "Checked";
            }
        }else if(select_horizontalWall != null){
            if(select_horizontalWall.get(1) + 1 >= COLS){
                if(CheckHwalls[select_horizontalWall.get(0)][select_horizontalWall.get(1)] != "Checked" && CheckHwalls[select_horizontalWall.get(0)][select_horizontalWall.get(1) - 1] != "Checked"){
                    setHorizontalWall(select_horizontalWall.get(0), select_horizontalWall.get(1) - 1);

                    System.out.println("(" + (select_horizontalWall.get(1) + 1 + "," + (select_horizontalWall.get(0) + 1)) + ")," + "(" + (select_horizontalWall.get(1) + 2 + "," + (select_horizontalWall.get(0) + 1)) + ")"); // to do

                    CheckHwalls[select_horizontalWall.get(0)][select_horizontalWall.get(1)] = "Checked";
                    CheckHwalls[select_horizontalWall.get(0)][select_horizontalWall.get(1) - 1] = "Checked";
                }
            }
            else if( CheckHwalls[select_horizontalWall.get(0)][select_horizontalWall.get(1)] != "Checked" && CheckHwalls[select_horizontalWall.get(0)][select_horizontalWall.get(1) + 1] != "Checked"){
                setHorizontalWall(select_horizontalWall.get(0), select_horizontalWall.get(1));

                System.out.println("(" + (select_horizontalWall.get(0) + 1 + "," + (select_horizontalWall.get(1) + 1)) + ")," + "(" + (select_horizontalWall.get(1) + 2 + "," + (select_horizontalWall.get(0) + 1)) + ")"); //to do

                CheckHwalls[select_horizontalWall.get(0)][select_horizontalWall.get(1)] = "Checked";
                CheckHwalls[select_horizontalWall.get(0)][select_horizontalWall.get(1) + 1] = "Checked";
            }
        }else {
            System.out.println("확인되지 않은 플레이");
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