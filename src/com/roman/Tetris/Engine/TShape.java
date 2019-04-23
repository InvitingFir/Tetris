package com.roman.Tetris.Engine;

import javax.swing.*;
import java.util.ArrayList;

public class TShape extends Shape{

    TShape(){rotation = 0;}

    TShape(int i, int j, Icon TempIcon){
        this.Seti(i);
        this.Setj(j);
        this.SetIcon(TempIcon);
    }

    public ArrayList CreateShape() {
        ArrayList <TShape> Temp = new ArrayList();
        Icon TempIcon = ColorSet();
        Temp.add(new TShape(-1, 6, TempIcon));
        Temp.add(new TShape(-1, 5, TempIcon));
        Temp.add(new TShape(-1, 4, TempIcon));
        Temp.add(new TShape(-2, 5, TempIcon));
        return Temp;
    }

    public ArrayList<Shape> rotate(ArrayList<Shape> temp) {
        int ii;
        int jj;
        switch(rotation){
            case 0:
                ii = temp.get(1).Geti();
                jj = temp.get(1).Getj();
                if(ii == 0 || Gameplay.getNoMovement(ii+1, jj)) return(temp);
                temp = rotate1(temp);
                rotation++;
                break;

            case 1:
                ii = temp.get(3).Geti();
                jj = temp.get(3).Getj();
                if(jj==0 || Gameplay.getNoMovement(ii+1, jj-1)) return(temp);
                temp = rotate2(temp);
                rotation++;
                break;

            case 2:
                ii = temp.get(2).Geti();
                jj = temp.get(2).Getj();
                if(Gameplay.getNoMovement(ii-1, jj)) return temp;
                temp = rotate3(temp);
                rotation++;
                break;
            case 3:
                ii = temp.get(2).Geti();
                jj = temp.get(2).Getj();
                if(jj == WIWDTH-1 || Gameplay.getNoMovement(ii, jj+1)) return temp;
                temp = rotate4(temp);
                rotation = 0;
                break;
        }
        return temp;
    }

    private ArrayList <Shape> rotate1(ArrayList <Shape> InnerTemp){
        InnerTemp.get(1).Seti(1);
        InnerTemp.get(2).Setj(1);
        return(InnerTemp);
    }

    private ArrayList <Shape> rotate2(ArrayList <Shape> InnerTemp){
        InnerTemp.get(3).Seti(1);
        InnerTemp.get(3).Setj(-1);
        return(InnerTemp);
    }

    private ArrayList <Shape> rotate3(ArrayList <Shape> InnerTemp){
        InnerTemp.get(0).Setj(-1);
        InnerTemp.get(0).Seti(1);
        InnerTemp.get(1).Seti(-1);
        InnerTemp.get(2).Seti(-1);
        return InnerTemp;
    }

    private ArrayList <Shape> rotate4(ArrayList <Shape> InnerTemp){
        InnerTemp.get(0).Seti(-1);
        InnerTemp.get(0).Setj(1);
        InnerTemp.get(2).Seti(1);
        InnerTemp.get(2).Setj(-1);
        InnerTemp.get(3).Seti(-1);
        InnerTemp.get(3).Setj(1);
        return InnerTemp;
    }

}
