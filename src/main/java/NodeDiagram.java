package main.java;

import javax.swing.*;

public class NodeDiagram extends JInternalFrame
{
    static final int xOffset = 25, yOffset = 25;

    public NodeDiagram()
    {
        super("Network Topology",
                true, //resizable
                true, //closable
                true, //maximizable
                true);//iconifiable

        setSize(500, 350);

        //Set the window's location.
        setLocation(xOffset, yOffset);

        add(addDiagram());

        pack();
        setVisible(true);
    }

    public JPanel addDiagram()
    {
        JPanel routerPanel = new JPanel();



        return routerPanel;
    }
}
