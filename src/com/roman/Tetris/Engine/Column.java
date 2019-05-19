package com.roman.Tetris.Engine;

import javax.swing.*;
import java.util.ArrayList;

public class Column extends Shape{

    Column(){rotation = 0;}
    private Column(int i, int j, Icon TempIcon){ super(i, j, TempIcon); }

    @Override
    public ArrayList CreateShape() {
        ArrayList <Column> Temp = new ArrayList();
        Icon TempIcon = ColorSet();
        Temp.add(new Column(-1, 5, TempIcon));
        Temp.add(new Column(-2, 5, TempIcon));
        Temp.add(new Column(-3, 5, TempIcon));
        return Temp;
    }

    @Override
    public ArrayList<Shape> rotate(ArrayList<Shape> temp) {
        int ii;
        int jj;
        switch(rotation){
            case 0:
                ii = temp.get(2).Geti();
                jj = temp.get(2).Getj();
                if(jj == 0 || Gameplay.getNoMovement(ii+1, jj-1)) return(temp);
                ii = temp.get(0).Geti();
                jj = temp.get(0).Getj();
                if(jj == WIDTH-1 || Gameplay.getNoMovement(ii-1, jj+1)) return(temp);
                temp = rotate1(temp);
                rotation++;
                break;

            case 1:
                rotation = 0;
                ii = temp.get(2).Geti();
                jj = temp.get(2).Getj();
                if(Gameplay.getNoMovement(ii-1, jj+1)) return(temp);
                ii = temp.get(0).Geti();
                jj = temp.get(0).Getj();
                if(ii == HEIGHT-1 || Gameplay.getNoMovement(ii+1, jj-1)) return(temp);
                temp = rotate2(temp);
                break;
        }
        return temp;
    }

    private ArrayList<Shape> rotate1(ArrayList<Shape> InnerTemp){
        InnerTemp.get(2).Seti(1);
        InnerTemp.get(2).Setj(-1);
        InnerTemp.get(0).Seti(-1);
        InnerTemp.get(0).Setj(1);
        return(InnerTemp);
    }

    private ArrayList<Shape> rotate2(ArrayList<Shape> InnerTemp){
        InnerTemp.get(2).Seti(-1);
        InnerTemp.get(2).Setj(1);
        InnerTemp.get(0).Seti(1);
        InnerTemp.get(0).Setj(-1);
        return(InnerTemp);
    }
}
