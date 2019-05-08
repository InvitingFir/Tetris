package com.roman.Tetris.Engine;

import com.roman.Tetris.Panels.GamePanel;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Gameplay implements  Runnable
{
    private final static int HEIGHT = 12;
    private final static int WIDTH = 10;
    private final static int NEW_ZERO = 4;
    private int FPS = 4;
    private long TargetTime = 1000/FPS;
    private boolean isRunning;
    private static boolean[][] NoMovement = new boolean [HEIGHT+NEW_ZERO][WIDTH];
    private static ArrayList <Shape> Shapes = new ArrayList();
    private Thread Timer = new Thread(this);
    private Thread GameOverTimer;
    private com.roman.Tetris.Panels.GamePanel GamePanel = new GamePanel();

    public Gameplay(){
        ShapeGenerator();
        isRunning = true;
        Timer.start();
    }

    public static boolean getNoMovement(int i, int j){ return NoMovement[i+NEW_ZERO][j]; }

    //Создает ArrayList, новую фигуру
    private void ShapeGenerator(){
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

    @Override
    public synchronized void run() {    //Смещение фигур вниз
        long start = System.nanoTime();
        long elapsed, wait;

        int ii;
        int jj;
        while(isRunning){
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
        elapsed = System.nanoTime() - start;
        wait = TargetTime - elapsed/1000000;
        if(wait <= 0) wait = 300;
        try{
            Timer.sleep(wait);
        }
        catch(Exception e){e.printStackTrace(); }
        }
    }

    //удаление из ArrayList, заморозка фигуры
    private void freeze(){
        int Checked = 100;
        int ii;
        int jj;
        for (int i = 0; i < Shapes.size(); i++) {
            ii = Shapes.get(i).Geti();
            jj = Shapes.get(i).Getj();
            NoMovement[ii+NEW_ZERO][jj] = true;
            if(NoMovement[NEW_ZERO][jj]){
                isRunning = false;
                GameOverTimer = new Thread(new GameOver());
                GameOverTimer.start();
                return;
            }
        }

        for (int i = Shapes.size()-1; i >=0 ; i--) {
            ii = Shapes.get(i).Geti();
            if(Checked != ii){Destroy(ii); Checked = ii;}
        }
        Shapes.clear();
        ShapeGenerator();
    }

    //Стирание фигуры
    private void Destroy(int ii){
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
    }

    //движение вправо
    private void GoRight(){
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
    private void GoLeft(){
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
    private void ChangeShape(){
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
    class GameOver implements Runnable{
        int Layer = 0;
        @Override
        public void run() {
            long start, elapsed, wait;

            while (Layer != HEIGHT+1) {
                start = System.nanoTime();

                GameOverDestroy();

                elapsed = System.nanoTime() - start;
                wait = TargetTime - elapsed / 1000000;
                if (wait <= 0) wait = 500;
                try {
                    Timer.sleep(wait);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public void GameOverDestroy() {
            if(Layer<=HEIGHT-1){
                for (int j = 0; j < WIDTH; j++) {
                    GamePanel.ShapeMovement(Layer, j, GamePanel.Clear());
                }
                Layer++;
            }
        }
    }
}
