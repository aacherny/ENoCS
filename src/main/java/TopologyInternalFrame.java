package main.java;

//import com.sun.xml.internal.bind.v2.TODO;

import javax.swing.*;

public class TopologyInternalFrame extends JInternalFrame
{
    static int openFrameCount = 0;
    static final int xOffset = 25, yOffset = 25;

    public TopologyInternalFrame(Network inputNetwork)
    {
        super("Network Topology",
                false, //resizable
                true, //closable
                true, //maximizable
                true);//iconifiable

        setSize(500, 350);

        //Set the window's location.
        setLocation(xOffset, yOffset);

        //TODO: Figure out why you can't have both a line and a circle
        //Circle circ1 = new Circle();
        //add(circ1);
//        Line line1 = new Line();
//        add(line1);

        if(inputNetwork.getTopology().equals("mesh"))
        {
            JPanel panelYContainer = new JPanel();
            panelYContainer.setLayout(new BoxLayout(panelYContainer, BoxLayout.Y_AXIS));
            for(int county = 0; county < Math.sqrt(inputNetwork.getNodes()); county++)
            {
                JPanel panelXContainer = new JPanel();
                for(int countx = 0; countx < Math.sqrt(inputNetwork.getNodes()); countx++)
                {
                    Circle circle = new Circle();
                    panelXContainer.add(circle);
                }
                panelYContainer.add(panelXContainer);
            }

            add(panelYContainer);
        }

        pack();
        setVisible(true);
    }
}
