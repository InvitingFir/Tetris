package com.roman.Tetris.Engine;

import javax.swing.*;
import java.util.ArrayList;

public class Lightning extends Shape{

    Lightning(){rotation = 0;}

    private Lightning(int i, int j, Icon TempIcon){ super(i, j, TempIcon); }

    public ArrayList CreateShape() {
        ArrayList <Lightning> Temp = new ArrayList();
        Icon TempIcon = ColorSet();
        Temp.add(new Lightning(-1, 5, TempIcon));
        Temp.add(new Lightning(-2, 6, TempIcon));
        Temp.add(new Lightning(-2, 5, TempIcon));
        Temp.add(new Lightning(-1, 4, TempIcon));
        return Temp;
    }

    @Override
    public ArrayList<Shape> rotate(ArrayList<Shape> temp) {
        int ii;
        int jj;
        switch(rotation){
            case 0:
                ii = temp.get(0).Geti();
                jj = temp.get(0).Getj();
                if(Gameplay.getNoMovement(ii, jj+1)) return(temp);
                ii = temp.get(3).Geti();
                jj = temp.get(3).Getj();
                if(Gameplay.getNoMovement(ii-2, jj+1)) return(temp);
                temp = rotate1(temp);
                rotation++;
                break;

            case 1:
                ii = temp.get(0).Geti();
                jj = temp.get(0).Getj();
                if(Gameplay.getNoMovement(ii, jj-1)) return(temp);
                ii = temp.get(3).Geti();
                jj = temp.get(3).Getj();
                if(jj == 0 || Gameplay.getNoMovement(ii+2, jj-1)) return(temp);
                temp = rotate2(temp);
                rotation = 0;
                break;
        }
        return temp;
    }

    private ArrayList<Shape> rotate1(ArrayList<Shape> InnerTemp){
        InnerTemp.get(0).Setj(1);
        InnerTemp.get(3).Setj(1);
        InnerTemp.get(3).Seti(-2);
        return InnerTemp;
    }

    private ArrayList<Shape> rotate2(ArrayList<Shape> InnerTemp){
        InnerTemp.get(0).Setj(-1);
        InnerTemp.get(3).Setj(-1);
        InnerTemp.get(3).Seti(2);
        return InnerTemp;
    }
}
