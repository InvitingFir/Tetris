package com.roman.Tetris.Engine;

import com.roman.Tetris.Panels.GamePanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Gameplay implements ActionListener
{
    private final static int HEIGHT = 12;
    private final static int WIDTH = 10;
    private final static int NEW_ZERO = 4;
    private static boolean[][] NoMovement = new boolean [HEIGHT+NEW_ZERO][WIDTH];
    private static ArrayList <Shape> Shapes = new ArrayList();
    private  Timer Counter = new Timer(400, this);
    private com.roman.Tetris.Panels.GamePanel GamePanel = new GamePanel();

    public Gameplay(){
        ShapeGenerator();
        Counter.start();
    }

    public static boolean getNoMovement(int i, int j){ return NoMovement[i+NEW_ZERO][j]; }

    //Создает ArrayList, новую фигуру
    void ShapeGenerator(){
        int i = (int)(Math.random()*4);
        switch(i){
            case 0:
                Square s = new Square();
                Shapes = s.CreateShape();
                break;
            case 1:
                Lightning l = new Lightning();
                Shapes = l.CreateShape();
                break;
            case 2:
                TShape t = new TShape();
                Shapes = t.CreateShape();
                break;
            case 3:
                Column c = new Column();
                Shapes = c.CreateShape();
                break;
        }
    }

    //Смещение фигур вниз
    @Override
    public void actionPerformed(ActionEvent e) {
        int ii;
        int jj;
        for (int i = 0; i < Shapes.size(); i++) {
            ii = Shapes.get(i).Geti();
            jj = Shapes.get(i).Getj();
            if (ii == HEIGHT -1 || NoMovement[NEW_ZERO + ii + 1][jj]) {
                freeze(); }
        }

        for (int i = 0; i < Shapes.size(); i++) {
            ii = Shapes.get(i).GetiPlus();
            jj = Shapes.get(i).Getj();
            GamePanel.ShapeMovement(ii, jj, Shapes.get(i).GetCurrentIcon());
            if(ii>0){ GamePanel.ShapeMovement(ii-1, jj, GamePanel.Clear()); }

        }
    }

    //удаление из ArrayList, заморозка фигуры
    void freeze(){
        int Checked = 100;
        int ii;
        int jj;
        for (int i = 0; i < Shapes.size(); i++) {
            ii = Shapes.get(i).Geti();
            jj = Shapes.get(i).Getj();
            NoMovement[ii+NEW_ZERO][jj] = true;
            if(NoMovement[NEW_ZERO][jj]){
                Counter.stop();
                GameOver g = new GameOver();
                return;
            }
        }
        int Delay = Counter.getDelay();
        if(Delay >50){ Counter.setDelay(Delay-2); }

        for (int i = Shapes.size()-1; i >=0 ; i--) {
            ii = Shapes.get(i).Geti();
            if(Checked != ii){Destroy(ii); Checked = ii;}
        }
        Shapes.clear();
        ShapeGenerator();
    }

    //Стирание фигуры
    void Destroy(int ii){
        for (int j = 0; j < WIDTH; j++) {
            if(!NoMovement[ii+NEW_ZERO][j]) return;
        }
        for (int i =ii; i >= 1; i--) {
            for (int j = 0; j < WIDTH; j++) {
                GamePanel.LabelCut(i, j);
                NoMovement[i+NEW_ZERO][j] = NoMovement[i-1+NEW_ZERO][j];
            }
        }
    }

    //движение фигуры встороны и переворот
    public void move(int key){
        if(key == KeyEvent.VK_RIGHT){ GoRight(); }
        if(key == KeyEvent.VK_LEFT){ GoLeft(); }
        if(key == KeyEvent.VK_UP){ ChangeShape(); }
        if(key == KeyEvent.VK_DOWN){}
        Counter.restart();
    }

    //движение вправо
    void GoRight(){
        int ii;
        int jj;
        for (int i = 0; i < Shapes.size(); i++) {
            jj = Shapes.get(i).Getj();
            ii = Shapes.get(i).Geti();
            if(jj==WIDTH-1 || NoMovement[NEW_ZERO+ii][jj+1]) return;
        }

        for (int i = 0; i < Shapes.size(); i++) {
            ii = Shapes.get(i).Geti();
            jj = Shapes.get(i).Getj();
            GamePanel.ShapeMovement(ii, jj, GamePanel.Clear());
            GamePanel.ShapeMovement(ii, jj + 1, Shapes.get(i).GetCurrentIcon());
            jj++;
            Shapes.get(i).Setj(1);
        }
    }

    //движение влево
    void GoLeft(){
        int ii;
        int jj;
        for (int i = 0; i < Shapes.size(); i++) {
            jj = Shapes.get(i).Getj();
            ii = Shapes.get(i).Geti();
            if(jj==0 || NoMovement[NEW_ZERO+ii][jj-1]) return;
        }

        for (int i = Shapes.size()-1; i >=0; i--) {
            ii = Shapes.get(i).Geti();
            jj = Shapes.get(i).Getj();
            GamePanel.ShapeMovement(ii, jj, GamePanel.Clear());
            GamePanel.ShapeMovement(ii, jj-1, Shapes.get(i).GetCurrentIcon());
            jj--;
            Shapes.get(i).Setj(-1);
        }
    }

    //поворот фигуры
    void ChangeShape(){
        int ii;
        int jj;

        if(Shapes.size()>1){
            for (int i = 0; i < Shapes.size(); i++) {
                ii = Shapes.get(i).Geti();
                jj = Shapes.get(i).Getj();
                GamePanel.ShapeMovement(ii, jj, GamePanel.Clear());
            }
            Shapes = Shapes.get(0).rotate(Shapes);
        }
        for (int i = 0; i < Shapes.size(); i++) {
            ii = Shapes.get(i).Geti();
            jj = Shapes.get(i).Getj();
            GamePanel.ShapeMovement(ii, jj, Shapes.get(i).GetCurrentIcon());
        }
    }


    //анимация конца игры
    class GameOver implements ActionListener{
        int Layer = 0;
        private Timer GameOverTimer = new Timer(200,this);


        GameOver(){
            GameOverTimer.start();
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            if(Layer<=HEIGHT-1){
                for (int i = 0; i < WIDTH; i++) {
                    GamePanel.ShapeMovement(Layer, i, GamePanel.Clear());
                }
                Layer++;
            }
            if(Layer == HEIGHT)
                GameOverTimer.stop();
        }
    }
}
