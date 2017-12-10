package main.java;

import javax.swing.*;
import java.awt.*;

public class shapeTSegmentDown extends JPanel {

    public shapeTSegmentDown(){
        setPreferredSize(new Dimension(36, 36));
        setLocation(0,0);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.drawLine(18, 18, 18, 36);
        g.drawLine(0, 18, 36, 18);
    }
}
