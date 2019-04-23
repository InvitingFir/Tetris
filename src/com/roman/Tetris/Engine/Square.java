package com.roman.Tetris.Engine;

import javax.swing.*;
import java.util.ArrayList;

public class Square extends Shape{

    Square(){}
    Square(int i, int j, Icon TempIcon){
        this.Seti(i);
        this.Setj(j);
        this.SetIcon(TempIcon);
    }

    public ArrayList CreateShape() {
        ArrayList <Square> Temp = new ArrayList();
        Icon TempIcon = ColorSet();
        Temp.add(new Square(-1, 5, TempIcon));
        return Temp;
    }

    public ArrayList<Shape> rotate(ArrayList<Shape> temp) {
        return temp;
    }
}
