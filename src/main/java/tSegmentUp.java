package main.java;

import javax.swing.*;
import java.awt.*;

public class tSegmentUp extends JPanel {

    public tSegmentUp(){
        setPreferredSize(new Dimension(26, 26));
        setLocation(0,0);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.drawLine(13, 0, 13, 13);
        g.drawLine(0, 13, 26, 13);
    }
}
