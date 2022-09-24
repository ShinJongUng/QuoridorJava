package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import static java.util.Arrays.*;

public class Quoridor extends JFrame implements MouseListener, ActionListener{
    public void mouseClicked(MouseEvent event) {}
    public void mousePressed(MouseEvent event) {}
    public void mouseReleased(MouseEvent event) {}
    public void mouseEntered(MouseEvent event) {}
    public void mouseExited(MouseEvent event) {}

    private void setVerticalWall(int r, int c, int h) {
        Board.VerticalWalls[r + 2 * h - 1][c][h].setBackground(Color.GRAY);
        Board.VerticalWalls[r + 2 * h - 1][c][1 - h].setBackground(Color.GRAY);
        Board.VerticalWalls[r][c][h].setBackground(Color.GRAY);
        Board.VerticalWalls[r][c][1-h].setBackground(Color.GRAY);
        Board.CenterWalls[r + h - 1][c].setBackground(Color.GRAY);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        List<Integer> select_space = getSquare(event.getSource());
        List<Integer> select_verticalWall = getVerticalWall(event.getSource());
        List<Integer> select_horizontalWall = getHorizontalWall(event.getSource());

        if(select_verticalWall != null){
            setVerticalWall(select_verticalWall.get(0), select_verticalWall.get(1), select_verticalWall.get(2));
        }else if(select_horizontalWall != null){

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
                for (int k = 0; k < Board.VerticalWalls[i][j].length; ++k) {
                    if (Board.VerticalWalls[i][j][k] == source) {
                        return asList(i, j, k); //asList
                    }
                }
            }
        }
        return null;
    }

    private List<Integer> getHorizontalWall(Object source) {
        for (int i = 0; i < Board.VerticalWalls.length; ++i) {
            for (int j = 0; j < Board.VerticalWalls[i].length; ++j) {
                for (int k = 0; k < Board.VerticalWalls[i][j].length; ++k) {
                    if (Board.VerticalWalls[i][j][k] == source) {
                        return asList(i, j, k);
                    }
                }
            }
        }
        return null;
    }
}
