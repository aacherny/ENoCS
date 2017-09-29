package main.java;

import javax.swing.*;
import java.awt.*;

public class TopologyInternalFrame extends JInternalFrame
{
    static int openFrameCount = 0;
    static final int xOffset = 30, yOffset = 30;
    JDesktopPane desktopPane;

    public TopologyInternalFrame()
    {
        super("Network Topology" + (++openFrameCount),
                true, //resizable
                true, //closable
                true, //maximizable
                true);//iconifiable

        setSize(500, 350);
        Container contentPane = getContentPane();
        //Set the window's location.
        setLocation(xOffset*openFrameCount, yOffset*openFrameCount);

        Circle circ1 = new Circle(9);
        contentPane.add(circ1);

        setVisible(true);
    }
}
