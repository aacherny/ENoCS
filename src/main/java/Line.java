package main.java;

import javax.swing.*;
import java.awt.*;

public class Line extends JPanel {
    private int x1, x2, y1, y2;

    Line(int posx1, int posy1, int posx2, int posy2){
        this.x1 = posx1;
        this.y1 = posy1;
        this.x2 = posx2;
        this.y2 = posy2;
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.drawLine(x1, y1, x2, y2);
    }
}
