package main.java;

import javax.swing.*;
import java.awt.*;

public class Circle extends JPanel {
    private int x, y;

    Circle(int posx, int posy){
        this.x = posx;
        this.y = posy;
    }

    public void setX(int posx){
        this.x = posx;
    }

    public void setY(int posy){
        this.y = posy;
    }

    public void paintComponent(Graphics c){
        super.paintComponent(c);

        c.setColor(Color.BLACK);
        c.drawOval(this.x, this.y, 25, 25);
    }
}
