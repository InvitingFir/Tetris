package com.roman.Tetris.Engine;

import javax.swing.*;
import java.util.ArrayList;

public abstract class Shape {

    protected final static int HEIGHT = 12;
    protected final static int WIWDTH = 10;
    private int Currentj = 0;
    private int Currenti = 0;
    private Icon Red = new ImageIcon(getClass().getResource("/com/roman/Tetris/Resource/Red.png"));
    private Icon Yellow = new ImageIcon(getClass().getResource("/com/roman/Tetris/Resource/Yellow.png"));
    private Icon Blue = new ImageIcon(getClass().getResource("/com/roman/Tetris/Resource/Blue.png"));
    private Icon Green = new ImageIcon(getClass().getResource("/com/roman/Tetris/Resource/Green.png"));
    private Icon Purple = new ImageIcon(getClass().getResource("/com/roman/Tetris/Resource/Purple.png"));
    protected int rotation = 0;
    private Icon CurrentIcon;


    protected Icon ColorSet(){
        int i = (int)(Math.random()*5);
        switch(i){
            case 0:
                CurrentIcon = Red;
                break;
            case 1:
                CurrentIcon = Yellow;
                break;
            case 2:
                CurrentIcon = Blue;
                break;
            case 3:
                CurrentIcon = Green;
                break;
            case 4:
                CurrentIcon = Purple;
                break;

        }
        return CurrentIcon;
    }

    public abstract ArrayList CreateShape();


    public Icon GetCurrentIcon(){
        return CurrentIcon;
    }

    public int Getj(){
        return Currentj;
    }

    public void Setj(int j){ Currentj = Currentj + j; }

    public int Geti(){
        return Currenti;
    }

    public void Seti(int i){ Currenti = Currenti + i; }

    public int GetiPlus(){
        Currenti++;
        return Currenti; }

    public void SetIcon(Icon Temp){
        CurrentIcon = Temp;
    }

    protected abstract ArrayList<Shape> rotate(ArrayList<Shape> temp);

}
