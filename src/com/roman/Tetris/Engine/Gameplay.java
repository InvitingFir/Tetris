package com.roman.Tetris.Engine;

import com.roman.Tetris.Panels.GamePanel;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Gameplay implements  Runnable
{
    private final static int HEIGHT = 12;
    private final static int WIDTH = 10;
    private final static int NEW_ZERO = 4;
    private int wait = 500;
    private int Score = 0;
    private boolean isRunning = true;
    private static boolean[][] NoMovement = new boolean [HEIGHT+NEW_ZERO][WIDTH];
    private ArrayList <Shape> Shapes;
    private ArrayList <Shape> NextShape;
    private Thread Timer = new Thread(this);
    private com.roman.Tetris.Panels.GamePanel GamePanel;

    public Gameplay(GamePanel g){
        GamePanel = g;
        Shapes = ShapeGenerator(); //генерация основной фигуры
        NextShape = ShapeGenerator();//генерация следующей фигуры
        Timer.start();
    }


    public static boolean getNoMovement(int i, int j){ return NoMovement[i+NEW_ZERO][j]; }

    //Создает ArrayList с новой фигурой
    private ArrayList ShapeGenerator(){
        ArrayList <Shape> Temp = new ArrayList();
        int i = (int)(Math.random()*4);
        switch(i){
            case 0:
                Square s = new Square();
                Temp = s.CreateShape();
                GamePanel.setNextShape("Square");
                break;
            case 1:
                Lightning l = new Lightning();
                Temp = l.CreateShape();
                GamePanel.setNextShape("Lightning");
                break;
            case 2:
                TShape t = new TShape();
                Temp = t.CreateShape();
                GamePanel.setNextShape("TShape");
                break;
            case 3:
                Column c = new Column();
                Temp = c.CreateShape();
                GamePanel.setNextShape("Column");
                break;
        }
        return Temp;
    }

    //Смещение фигур вниз
    public synchronized void run() {
        int ii;
        int jj;
        while(isRunning){
        for (int i = 0; i < Shapes.size(); i++) {
            ii = Shapes.get(i).Geti();
            jj = Shapes.get(i).Getj();
            if (ii == HEIGHT -1 || NoMovement[NEW_ZERO + ii + 1][jj]) { freeze(); }
        }

        for (int i = 0; i < Shapes.size(); i++) {
            ii = Shapes.get(i).GetiPlus();
            jj = Shapes.get(i).Getj();
            GamePanel.ShapeMovement(ii, jj, Shapes.get(i).GetCurrentIcon());
            if(ii>0){ GamePanel.ShapeMovement(ii-1, jj, GamePanel.Clear()); }
        }
        try{ Timer.sleep(wait); }
        catch(Exception e){e.printStackTrace(); }
        }
    }

    //удаление из ArrayList, заморозка фигуры
    private void freeze(){
        int ii, jj, levelChecked = 100;
        for (int i = 0; i < Shapes.size(); i++) {
            ii = Shapes.get(i).Geti();
            jj = Shapes.get(i).Getj();
            NoMovement[ii+NEW_ZERO][jj] = true;
            if(NoMovement[NEW_ZERO][jj]){
                GameOver g = new GameOver();
                return;
            }
        }

        for (int i = Shapes.size()-1; i >=0 ; i--) {
            ii = Shapes.get(i).Geti();
            if(levelChecked != ii) Destroy(ii);
            levelChecked = ii;
        }
        Score+=100;
        GamePanel.SetScore(Score);
        if(wait>=100) wait-=5;
        Shapes = NextShape;
        NextShape = ShapeGenerator();
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
        if(key == KeyEvent.VK_ENTER){ restart();}
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

    //поворот фигуры по часовой стрелке
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

    private void restart(){ //новая игра при нажатии на Enter
        NoMovement = new boolean [HEIGHT+NEW_ZERO][WIDTH];
        wait = 500;
        isRunning = true;
        Timer = new Thread(this);
        Timer.start();
        Shapes = ShapeGenerator();
        NextShape = ShapeGenerator();
        GamePanel.ClearGUI();
        Score = 0;
        GamePanel.SetScore(Score);
    }


    //анимация конца игры
    class GameOver implements Runnable{
        int Layer = 0;
        private Thread GameOverTimer = new Thread(this);

        public GameOver(){
            isRunning = false;
            GameOverTimer.start();
        }
        @Override
        public void run() {
            while (Layer != HEIGHT) {
                GameOverDestroy();
                try { Timer.sleep(300); }
                catch (Exception e) { e.printStackTrace(); }
            }
        }

        private void GameOverDestroy() {
            if(Layer<=HEIGHT-1){
                for (int j = 0; j < WIDTH; j++) {
                    GamePanel.ShapeMovement(Layer, j, GamePanel.Clear());
                }
                Layer++;
            }
        }
    }
}
