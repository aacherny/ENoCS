package main.java;

import javax.swing.*;
import java.awt.*;

/**
 * T-segment object
 * <P>
 * Creates a t-segment with the single segment facing north, is used when drawing the topology of a network
 *
 * @author Alex Cherny
 */
public class shapeTSegmentUp extends JPanel {

    public shapeTSegmentUp(){
        setPreferredSize(new Dimension(36, 36));
        setLocation(0,0);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.drawLine(18, 0, 18, 18);
        g.drawLine(0, 18, 36, 18);
    }
}
