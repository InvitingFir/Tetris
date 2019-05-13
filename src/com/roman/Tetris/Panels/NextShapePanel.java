package com.roman.Tetris.Panels;

import javax.swing.*;
import java.awt.*;

public class NextShapePanel extends JPanel {
    private JLabel NextShapeLabel = new JLabel();
    private JTextField Score = new JTextField("Score: 000");
    private Icon ColumnIcon= new ImageIcon(getClass().getResource("/com/roman/Tetris/Resource/Column.png"));
    private Icon SquareIcon= new ImageIcon(getClass().getResource("/com/roman/Tetris/Resource/Square.png"));
    private Icon LightningIcon= new ImageIcon(getClass().getResource("/com/roman/Tetris/Resource/Lightning.png"));
    private Icon TShapeIcon= new ImageIcon(getClass().getResource("/com/roman/Tetris/Resource/TShape.png"));

    public NextShapePanel(){
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        Score.setFont(new Font("Impact", Font.PLAIN, 14));
        Score.setEditable(false);

        add(Score);
        add(NextShapeLabel);
        setBackground(Color.GRAY);
    }

    public void setNextShape(String s){
        switch(s){
            case("Lightning"):
                NextShapeLabel.setIcon(LightningIcon);
                break;
            case("Square"):
                NextShapeLabel.setIcon(SquareIcon);
                break;
            case("TShape"):
                NextShapeLabel.setIcon(TShapeIcon);
                break;
            case("Column"):
                NextShapeLabel.setIcon(ColumnIcon);
                break;
        }
    }

    public void SetScore(int s){
        Score.setText(String.format("Score: %d", s));
    }
}
