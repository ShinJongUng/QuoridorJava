package main;

import javax.swing.*;
import java.awt.*;

public class Pawn {
    static final String[] NAMES = {"Player1", "Player2"};
    JButton[] pawns = new JButton[2];

    int turn = 0;
    public void setPawn(int turn, JButton square) {
        if(pawns[turn] != null) {
            pawns[turn].setText("");
        }
        pawns[turn] = square;
        pawns[turn].setText(NAMES[turn]);
        pawns[turn].setBackground(Color.gray);
    }
}
