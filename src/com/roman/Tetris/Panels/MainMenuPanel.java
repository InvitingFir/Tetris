package com.roman.Tetris.Panels;

import javax.swing.*;
import java.awt.*;

public class MainMenuPanel extends JPanel{
    private JButton start = new JButton("Start");
    private JButton Score = new JButton("Score");
    private JLabel Pic = new JLabel();

    public MainMenuPanel(){
        setBackground(Color.BLACK);
        Pic.setIcon(new ImageIcon(getClass().getResource("/com/roman/Tetris/Resource/Tetris.png")));
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(Pic);
        add(start);
        add(Score);
    }

    public JButton GetStartButton(){return start;}
}
