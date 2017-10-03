package main.java;

import javax.swing.*;
import java.awt.*;

public class Line extends JPanel {
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.drawLine(25, 25, 50, 50);
    }
}
