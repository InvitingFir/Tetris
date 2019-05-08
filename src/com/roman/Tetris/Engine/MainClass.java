package com.roman.Tetris.Engine;

import com.roman.Tetris.Panels.MainPanel;

import javax.swing.*;

public class MainClass{
    public static void main(String[] args) {
       JFrame MainFrame = new JFrame("Tetris");
       MainFrame.setSize(514, 655);
       MainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MainPanel m = new MainPanel();
        m.Setup();
       MainFrame.getContentPane().add(m.GetMainPanel());

       MainFrame.setVisible(true);
    }
}
