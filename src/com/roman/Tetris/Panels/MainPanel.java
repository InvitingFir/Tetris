package com.roman.Tetris.Panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPanel{
    public final static String GAMEPANEL = "Switch to GamePanel";
    public final static String MAINMENUPANEL = "Switch to MainMenu";
    public final static String HIGHSCOREPANEL = "Switch to HighScorePanel";

    private CardLayout cl = new CardLayout();
    private GamePanel gpanel = new GamePanel();
    private MainMenuPanel mmpanel = new MainMenuPanel();
    private HighScorePanel hspanel = new HighScorePanel();
    private JPanel mp = new JPanel();

    public void Setup(){
        mp.setLayout(cl);
        mp.add(mmpanel, MAINMENUPANEL);
        mmpanel.GetStartButton().addActionListener(new StartButtonListener());
        mp.add(gpanel, GAMEPANEL);
        mp.add(hspanel, HIGHSCOREPANEL);
        cl.show(mp, MAINMENUPANEL);
    }

    public JPanel GetMainPanel(){ return mp;}

    public class StartButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            gpanel.SetGui();
            cl.show(mp, GAMEPANEL);
            gpanel.requestFocus(true);
            mp.revalidate();
        }
    }
}
