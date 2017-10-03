package main.java;

//import com.sun.xml.internal.bind.v2.TODO;

import javax.swing.*;
import java.awt.*;

public class TopologyInternalFrame extends JInternalFrame
{
    static int openFrameCount = 0;
    static final int xOffset = 25, yOffset = 25;

    public TopologyInternalFrame(Network inputNetwork)
    {
        super("Network Topology",
                true, //resizable
                true, //closable
                true, //maximizable
                true);//iconifiable

        setSize(500, 350);

        //Set the window's location.
        setLocation(xOffset, yOffset);


        if(inputNetwork.getTopology().equals("mesh"))
        {
            JPanel panelYContainer = new JPanel();
            panelYContainer.setLayout(new GridLayout(0, (int)Math.sqrt(inputNetwork.getNodes())));

            for(int county = 0; county < Math.sqrt(inputNetwork.getNodes()); county++)
            {
                JPanel panelXContainer = new JPanel();
                panelXContainer.setLayout(new GridLayout((int)Math.sqrt(inputNetwork.getNodes()), 0));


                Circle circle = new Circle();
                Line line = new Line();

                panelXContainer.add(circle);

                for(int countx = 0; countx < (Math.sqrt(inputNetwork.getNodes()) - 1); countx++)
                {
                    panelXContainer.add(line);

                    panelXContainer.add(circle);
                }
                panelYContainer.add(panelXContainer);
            }

            add(panelYContainer);
        }

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
