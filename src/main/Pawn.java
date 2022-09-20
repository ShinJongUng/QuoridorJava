package main;

import javax.swing.*;

public class Pawn {
    static final String[] NAMES = {"W", "B"};
    JButton[] pawns = new JButton[2];
    protected void setPawn(int turn, JButton square) {
        if(pawns[turn] != null) {
            pawns[turn].setText("");
        }
        pawns[turn] = square;
        pawns[turn].setText(NAMES[turn]);
    }
}
