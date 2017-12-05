package main.java;

import javax.swing.*;
import java.awt.*;

public class Bus implements Network
{
    private int nodes;

    JDesktopPane desktopPane;
    protected Router[] routerArray;

    public Bus(int inputNodes, JDesktopPane inputDesktopPane) {
        nodes = inputNodes;

        routerArray = new Router[nodes];
    }

    public void nextCycle()
    {
        // sometimes create new packets
        // call the nextCycle of each router
        // check if each router has a packet ready to send, send to the next router if it is
    }

    public void newCycle()
    {

    }


    public JPanel drawTopology() {

        JPanel panelYContainer = new JPanel();

        int nodeCounter = 0;

        for (int i = 0; i < nodes; i++) {    // Creates the same number of circle objects that there are number of nodes
            //routerArray[i] = new Router(i, desktopPane);
        }

        panelYContainer.setLayout(new GridLayout(0, nodes));

        for (int i = 0; i < nodes / 2; i++) {

            JPanel panelXContainer = new JPanel();
            panelXContainer.setLayout(new GridLayout(5, 0));

            panelXContainer.add(routerArray[nodeCounter++].drawCircle());  // Adds an upper branch from the bus linking to a node
            panelXContainer.add(new vertLine());
            panelXContainer.add(new tSegmentUp());
            panelXContainer.add(new blankSpace());
            panelXContainer.add(new blankSpace());

            panelYContainer.add(panelXContainer);

            panelXContainer = new JPanel();
            panelXContainer.setLayout(new GridLayout(5, 0));

            panelXContainer.add(new blankSpace());  // Adds a lower branch
            panelXContainer.add(new blankSpace());
            panelXContainer.add(new tSegmentDown());
            panelXContainer.add(new vertLine());
            panelXContainer.add(routerArray[nodeCounter++].drawCircle());

            panelYContainer.add(panelXContainer);
        }

        if (nodes % 2 == 1) {

            JPanel panelXContainer = new JPanel();
            panelXContainer.setLayout(new GridLayout(5, 0));

            panelXContainer.add(routerArray[nodeCounter].drawCircle());  // If there's an odd number of nodes, adds a final upper branch at the end of the bus
            panelXContainer.add(new vertLine());
            panelXContainer.add(new tSegmentUp());
            panelXContainer.add(new blankSpace());
            panelXContainer.add(new blankSpace());

            panelYContainer.add(panelXContainer);
        }

        return panelYContainer;
    }

    public void setNodes(int inputNodes)
    {
        nodes = inputNodes;
    }

    public int getNodes()
    {
        return nodes;
    }

    public String getTopology()
    {
        return "bus";
    }

    public void removeTextWindow(){}
}