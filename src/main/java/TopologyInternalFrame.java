package main.java;

import javax.swing.*;

/**
 * A window that holds the topology of a router
 *
 * @author Alex Cherny
 */
public class TopologyInternalFrame extends JInternalFrame
{
    int openFrameCount;
    static final int xOffset = 50, yOffset = 50;

    public TopologyInternalFrame(JPanel inputTopology)
    {
        super("Network Topology",
                true, //resizable
                true, //closable
                true, //maximizable
                false);//iconifiable
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

    public void resetLocationCascade(int openFrameCount){
        setLocation(xOffset*openFrameCount, yOffset*openFrameCount);
    }

    public void resetLocationTile(int winPosX, int winPosY){
        setLocation(winPosX, winPosY);
    }
}
