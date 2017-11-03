package main.java;

import javax.swing.*;

public class RouterDiagram extends JInternalFrame
{
    static final int xOffset = 25, yOffset = 25;

    public RouterDiagram(int routerNumber)
    {
        super("Router " + routerNumber,
                true, //resizable
                true, //closable
                true, //maximizable
                true);//iconifiable

        setSize(500, 350);

        //Set the window's location.
        setLocation(xOffset, yOffset);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

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
