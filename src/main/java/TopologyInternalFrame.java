package main.java;

import javax.swing.*;

public class TopologyInternalFrame extends JInternalFrame
{
    static int openFrameCount = 0;
    static final int xOffset = 30, yOffset = 30;

    public TopologyInternalFrame()
    {
        super("Network Topology",
                true, //resizable
                true, //closable
                true, //maximizable
                true);//iconifiable

        ++openFrameCount;

        setSize(500, 350);

        //Set the window's location.
        setLocation(xOffset*openFrameCount, yOffset*openFrameCount);
    }
}
