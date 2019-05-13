package com.roman.Tetris.Panels;

import com.roman.Tetris.Engine.Gameplay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel{
    //константы
    public final static int HEIGHT = 12;
    public final static int WIDTH = 10;

    //панели, окна и все прилагающееся
    private static JPanel FieldPanel;
    private JPanel ScorePanel;
    private static NextShapePanel NextShape;

    //прочее
    private static JLabel [][] field;
    private static Icon [][] Icons;
    private Gameplay gm;
    private Icon Black  = new ImageIcon(getClass().getResource("/com/roman/Tetris/Resource/Black.png"));


    // Инициализация игры //
    public void SetGui(){
        FieldPanel = new JPanel(new GridLayout(HEIGHT, WIDTH));
        ScorePanel = new JPanel();
        NextShape = new NextShapePanel();

        setLayout(new BorderLayout());
        add(FieldPanel, BorderLayout.CENTER);
        add(NextShape, BorderLayout.EAST);

        FieldPanel.setFocusable(true);
        FieldPanel.addKeyListener(new MyKeyListener());
        SetField();
        gm = new Gameplay();
    }

    public void paintComponent(Graphics g){
        if(!this.isFocusOwner())
            g.drawString("Press Tab to start", 285, 310);
    }

    public void setNextShape(String s){
        NextShape.setNextShape(s);
    }

    public void SetScore(int s){NextShape.SetScore(s);}


    private void SetField(){
        field = new JLabel[HEIGHT][WIDTH];
        Icons = new Icon[HEIGHT][WIDTH];

        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                field[i][j] = new JLabel();
                field[i][j].setIcon(Black);
                Icons[i][j] = Clear();
                FieldPanel.add(field[i][j]);
            }
        }
    }

    public Icon Clear(){
        return Black;
    }

    private void  AddIcon(int i, int j, Icon temp){
        Icons[i][j] = temp;
    }

    public void ShapeMovement(int i, int j, Icon icon) {
        if (i >= 0) {
            field[i][j].setIcon(icon);
            AddIcon(i, j, icon);}
    }

    public void LabelCut(int i, int j){
        Icons[i][j] = Icons[i-1][j];
        field[i][j].setIcon(Icons[i][j]);
    }

    public void ClearGUI(){
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                Icons[i][j] = Black;
                field[i][j].setIcon(Black);
            }
        }
    }

    private class MyKeyListener extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            gm.move(key);

        }
    }

}

