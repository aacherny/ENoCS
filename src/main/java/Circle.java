package main.java;

import javax.swing.*;
import java.awt.*;

public class Circle extends JPanel {

    public Circle(){
        setPreferredSize(new Dimension(26, 26));
        setLocation(0,0);
    }

    public void paintComponent(Graphics l){
        super.paintComponent(l);

        l.setColor(Color.BLACK);
        l.drawOval(0, 0, 25, 25);
    }
}
