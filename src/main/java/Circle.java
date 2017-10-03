package main.java;

import javax.swing.*;
import java.awt.*;

public class Circle extends JPanel {
    public void paintComponent(Graphics l){
        super.paintComponent(l);

        l.setColor(Color.BLACK);
        l.drawOval(25, 25, 25, 25);
    }
}
