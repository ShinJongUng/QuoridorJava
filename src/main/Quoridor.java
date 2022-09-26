package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import static java.util.Arrays.*;
import static main.Board.*;
import static main.Board.CheckHwalls;

public class Quoridor extends JFrame implements MouseListener, ActionListener{
    Pawn pawn = new Pawn();

    public void mouseClicked(MouseEvent event) {}
    public void mousePressed(MouseEvent event) {}
    public void mouseReleased(MouseEvent event) {}
    public void mouseEntered(MouseEvent event) {}
    public void mouseExited(MouseEvent event) {}
    private void setVerticalWall(int r, int c) {
        Board.VerticalWalls[r + 1][c].setBackground(Color.GRAY);
        Board.VerticalWalls[r][c].setBackground(Color.GRAY);
        Board.CenterWalls[r][c].setBackground(Color.GRAY);
    }

    private void setHorizontalWall(int r, int c){
        Board.HorizontalWalls[r][c + 1].setBackground(Color.GRAY); // 누른곳 오른쪽
        Board.HorizontalWalls[r][c].setBackground(Color.GRAY); // 누른곳
        Board.CenterWalls[r][c].setBackground(Color.GRAY); // 가운데
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        List<Integer> select_space = getSquare(event.getSource());
        List<Integer> select_verticalWall = getVerticalWall(event.getSource());
        List<Integer> select_horizontalWall = getHorizontalWall(event.getSource());

        CheckPawn1[ROWS-1][COLS / 2] = "setPawn";
        CheckPawn2[0][COLS / 2] = "setPawn";


        if (select_space != null){
            pawn.setPawn(pawn.turn, (JButton) event.getSource());

            if(CheckPawn1[select_space.get(0)][select_space.get(1)] == "setPawn" || CheckPawn2[select_space.get(0)][select_space.get(1)] == "setPawn") {
                System.out.println("(" + (select_space.get(0) + 1) + "," + (select_space.get(1) + 1) + ")");
                //Spaces[select_space.get(0) + 1][select_space.get(1)].setBackground(Color.gray); // to do
                //Spaces[select_space.get(0) - 1][select_space.get(1)].setBackground(Color.gray); // to do
                //Spaces[select_space.get(0)][select_space.get(1) + 1].setBackground(Color.gray); // to do
                //Spaces[select_space.get(0)][select_space.get(1) - 1].setBackground(Color.gray); // to do
                if(select_space != null){
                }
            }
        }
        else if(select_verticalWall != null && CheckVwalls[select_verticalWall.get(0)][select_verticalWall.get(1)] != "Checked" && CheckVwalls[select_verticalWall.get(0) + 1][select_verticalWall.get(1)] != "Checked") {
            setVerticalWall(select_verticalWall.get(0), select_verticalWall.get(1));

            System.out.println("(" + (select_verticalWall.get(0) + 1 + "," + (select_verticalWall.get(1) + 1)) + ")," + "(" + (select_verticalWall.get(0) + 2 + "," + (select_verticalWall.get(1) + 1)) + ")"); // to do

            CheckVwalls[select_verticalWall.get(0)][select_verticalWall.get(1)] = "Checked";
            CheckVwalls[select_verticalWall.get(0) + 1][select_verticalWall.get(1)] = "Checked";
        }else if(select_horizontalWall != null && CheckHwalls[select_horizontalWall.get(0)][select_horizontalWall.get(1)] != "Checked" && CheckHwalls[select_horizontalWall.get(0)][select_horizontalWall.get(1) + 1] != "Checked"){
            setHorizontalWall(select_horizontalWall.get(0), select_horizontalWall.get(1));

            System.out.println("(" + (select_horizontalWall.get(0) + 1 + "," + (select_horizontalWall.get(1) + 1)) + ")," + "(" + (select_horizontalWall.get(0) + 1 + "," + (select_horizontalWall.get(1) + 2)) + ")"); //to do

            CheckHwalls[select_horizontalWall.get(0)][select_horizontalWall.get(1)] = "Checked";
            CheckHwalls[select_horizontalWall.get(0)][select_horizontalWall.get(1) + 1] = "Checked";
        }else{
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