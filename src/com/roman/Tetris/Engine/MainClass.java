package com.roman.Tetris.Engine;

import com.roman.Tetris.Panels.MainPanel;

import javax.swing.*;

public class MainClass{
    private static final int FRAME_WIDTH = 570;
    private static final int FRAME_HEIGHT = 620;
    public static void main(String[] args) {
       JFrame MainFrame = new JFrame("Tetris");
       MainFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
       MainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       MainFrame.setResizable(false);
       MainFrame.getContentPane().add(new MainPanel());
       MainFrame.setVisible(true);
    }
}
