package com.roman.Tetris.Panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPanel extends JPanel{
    public final static String GAMEPANEL = "Switch to GamePanel";
    public final static String MAINMENUPANEL = "Switch to MainMenu";
    public final static String HIGHSCOREPANEL = "Switch to HighScorePanel";

    private CardLayout cl = new CardLayout();
    private GamePanel gpanel;
    private MainMenuPanel mmpanel;

    public MainPanel(){
        this.setLayout(cl);
        mmpanel = new MainMenuPanel(this);
        gpanel = new GamePanel(this);
        Setup();
    }

    public void Setup(){
        add(mmpanel, MAINMENUPANEL);
        add(gpanel, GAMEPANEL);
    }

    public void swap(String s){
        if(s.equals(GAMEPANEL)){
            gpanel.SetGui();
            cl.show(this, s);
            gpanel.getFieldPanel().requestFocusInWindow();
            this.revalidate();
        }
    }
}
