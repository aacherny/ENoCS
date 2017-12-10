package main.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Circle extends JPanel {

    public Circle(){
        setPreferredSize(new Dimension(36, 36));
        setLocation(0,0);
    }

    public void paintComponent(Graphics l){
        super.paintComponent(l);

        l.setColor(Color.BLACK);
        l.drawOval(0, 0, 35, 35);
    }
}
