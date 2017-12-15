package main.java;

import javax.swing.*;
        import java.awt.*;

/**
 * Vertical line object
 * <P>
 * Creates a vertical line, is used when drawing the topology of a network
 *
 * @author Alex Cherny
 */
public class vertLine extends JPanel {

    public vertLine(){
        setPreferredSize(new Dimension(36, 36));
        setLocation(0,0);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.drawLine(18, 0, 18, 36);
    }
}
