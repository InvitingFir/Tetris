package com.roman.Tetris.Engine;

import javax.swing.*;
import java.util.ArrayList;

public class Square extends Shape{

    public Square(){}
    private Square(int i, int j, Icon TempIcon){ super(i, j, TempIcon); }

    public ArrayList CreateShape() {
        ArrayList <Square> Temp = new ArrayList();
        Icon TempIcon = super.ColorSet();
        Temp.add(new Square(-1, 5, TempIcon));
        return Temp;
    }

    public ArrayList<Shape> rotate(ArrayList<Shape> temp) {
        return temp;
    }
}
