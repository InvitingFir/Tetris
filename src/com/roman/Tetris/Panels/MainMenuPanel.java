package com.roman.Tetris.Panels;

import javax.swing.*;
import java.awt.*;

public class MainMenuPanel extends OtherPanels{
    private JButton start = new JButton("Start");
    private JButton Score = new JButton("Score");
    private JLabel Pic = new JLabel();

    public MainMenuPanel(MainPanel newmp){
        super(newmp);
        setBackground(Color.BLACK);
        Pic.setIcon(new ImageIcon(getClass().getResource("/com/roman/Tetris/Resource/Tetris.png")));
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        start.addActionListener(event -> mp.swap(MainPanel.GAMEPANEL));
        add(Pic);
        add(start);
        add(Score);
    }
}
