package main.java;

//import com.sun.xml.internal.bind.v2.TODO;

import javax.swing.*;
import java.awt.*;

public class TopologyInternalFrame extends JInternalFrame
{
    static int openFrameCount = 0;
    static final int xOffset = 25, yOffset = 25;

    public TopologyInternalFrame(JPanel inputTopology)
    {
        super("Network Topology",
                true, //resizable
                true, //closable
                true, //maximizable
                true);//iconifiable

        setSize(500, 350);

        //Set the window's location.
        setLocation(xOffset, yOffset);



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
}
