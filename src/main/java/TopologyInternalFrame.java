package main.java;

import javax.swing.*;

public class TopologyInternalFrame extends JInternalFrame
{
    static int openFrameCount = 0;
    static final int xOffset = 30, yOffset = 30;

    public TopologyInternalFrame()
    {
        super("Network Topology" + (++openFrameCount),
                true, //resizable
                true, //closable
                true, //maximizable
                true);//iconifiable

        setSize(500, 350);

        //Set the window's location.
        setLocation(xOffset*openFrameCount, yOffset*openFrameCount);

        //TODO: Figure out why you can't have both a line and a circle
        Circle circ1 = new Circle();
        add(circ1);

//        Line line1 = new Line();
//        add(line1);

        setVisible(true);
    }
}
