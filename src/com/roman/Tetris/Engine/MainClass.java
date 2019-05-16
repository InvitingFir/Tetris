package com.roman.Tetris.Engine;

import com.roman.Tetris.Panels.MainPanel;

import javax.swing.*;

public class MainClass{
    public static final int FRAME_WIDTH = 570;
    public static final int FRAME_HEIGHT = 620;
    public static void main(String[] args) {
       JFrame MainFrame = new JFrame("Tetris");
       MainFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
       MainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       MainFrame.setResizable(false);

        MainPanel m = new MainPanel();
        m.Setup();
       MainFrame.getContentPane().add(m.GetMainPanel());
       MainFrame.setVisible(true);
    }
}
