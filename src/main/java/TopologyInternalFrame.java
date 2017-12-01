package main.java;

import javax.swing.*;

public class TopologyInternalFrame extends JInternalFrame
{
    int openFrameCount;
    static final int xOffset = 25, yOffset = 25;

    public TopologyInternalFrame(JPanel inputTopology, int openFrame)
    {
        super("Network Topology",
                true, //resizable
                true, //closable
                true, //maximizable
                true);//iconifiable
        openFrameCount = openFrame;
        setSize(500, 350);

        //Set the window's location.
        setLocation(xOffset*openFrameCount, yOffset*openFrameCount);

        add(inputTopology);

        pack();
        setVisible(true);
    }

    public int getXBounds()
    {
        return getBounds().width;
    }

    public int getYBounds()
    {
        return getBounds().height;
    }

    public void closeFrame(int openFrame){
        openFrame--;
        dispose();
    }
}
