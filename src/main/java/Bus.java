package main.java;

import javax.swing.*;
import java.awt.*;

public class Bus implements Network
{
    private int nodes;

    public Bus(int inputNodes)
    {
        nodes = inputNodes;
    }

    public JPanel drawTopology() {

        JPanel panelYContainer = new JPanel();

        int nodesSqrt = (int) Math.sqrt(nodes);

        Node[] nodeArray = new Node[nodes];

        int nodeCounter = 0;

        for (int i = 0; i < nodes; i++) {    // Creates the same number of circle objects that there are number of nodes
            nodeArray[i] = new Node(i);
        }

        panelYContainer.setLayout(new GridLayout(0, nodes));

        for (int i = 0; i < nodes / 2; i++) {

            JPanel panelXContainer = new JPanel();
            panelXContainer.setLayout(new GridLayout(5, 0));

            panelXContainer.add(nodeArray[nodeCounter++].drawCircle());  // Adds an upper branch from the bus linking to a node
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
            panelXContainer.add(nodeArray[nodeCounter++].drawCircle());

            panelYContainer.add(panelXContainer);
        }

        if (nodes % 2 == 1) {

            JPanel panelXContainer = new JPanel();
            panelXContainer.setLayout(new GridLayout(5, 0));

            panelXContainer.add(nodeArray[nodeCounter].drawCircle());  // If there's an odd number of nodes, adds a final upper branch at the end of the bus
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
}