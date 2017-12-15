package main.java;

import javax.swing.*;
import java.awt.*;

/**
 * Blank space object
 * <P>
 * Creates a white rectangle, is used when drawing the topology of a network
 *
 * @author Alex Cherny
 */
public class blankSpace extends JPanel {

    public blankSpace(){
        setPreferredSize(new Dimension(36, 36));
        setLocation(0,0);
    }
}
