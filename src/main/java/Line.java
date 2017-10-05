package main.java;

import javax.swing.*;
import java.awt.*;

public class Line extends JPanel {
    private int x1 = 0;
    private int x2 = 0;
    private int y1 = 0;
    private int y2 = 0;

    public Line(){
        setPreferredSize(new Dimension(26, 26));
        setLocation(0,0);
    }

    public Line(int xPosition1, int xPosition2, int yPostition1, int yPosition2){
        x1 = xPosition1;
        x2 = xPosition2;
        y1 = yPostition1;
        y2 = yPosition2;
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.drawLine(x1, y1, x2, y2);
    }
}
