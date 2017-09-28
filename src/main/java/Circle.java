package main.java;

import javax.swing.*;
import java.awt.*;

public class Circle extends JPanel {
    public void paintComponent(Graphics c){
        super.paintComponent(c);

        c.setColor(Color.BLACK);
        c.drawOval(25, 25, 25, 25);
    }
}
