package com.roman.Tetris.Panels;

import com.roman.Tetris.Engine.Gameplay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel{
    //константы
    public final static int HEIGHT = 12;
    public final static int WIWDTH = 10;

    //панели, окна и все прилагающееся
    private static JPanel FieldPanel = new JPanel(new GridLayout(HEIGHT, WIWDTH));
    private JPanel ScorePanel = new JPanel();

    //прочее
    private static JLabel [][] field = new JLabel[HEIGHT][WIWDTH];
    private static Icon [][] Icons = new Icon[HEIGHT][WIWDTH];
    private static Gameplay gm;
    protected Icon Black = new ImageIcon(getClass().getResource("/com/roman/Tetris/Resource/Black.png"));


    // Инициализация игры //
    public void SetGui(){
        setLayout(new BorderLayout());
        add(ScorePanel, BorderLayout.NORTH);
        add(FieldPanel, BorderLayout.CENTER);

        FieldPanel.setFocusable(true);
        FieldPanel.addKeyListener(new MyKeyListener());
        SetField();
        gm = new Gameplay();
    }


    private void SetField(){
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIWDTH; j++) {
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

    private class MyKeyListener extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            gm.move(key);
        }
    }

}

