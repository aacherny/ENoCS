package main.java;

import javax.swing.*;
import java.awt.*;

public class Network
{
    private String topology;
    protected int nodes;

    public Network(String inputTopology, int inputNodes)
    {
        // Default values
        topology = inputTopology;
        nodes = inputNodes;
    }

    public void nextCycle()
    {

    }

    public void newCycle()
    {

    }

    public JPanel drawTopology() {

        JPanel panelYContainer = new JPanel();

        int nodesSqrt = (int) Math.sqrt(nodes);

        if(topology.equals("mesh"))
        {
            Node[] nodeArray = new Node[nodes];

            int nodeCounter = 0;

            for(int i = 0; i < nodes; i++) {    // Creates the same number of circle objects that there are number of nodes
                nodeArray[i] = new Node(i);
            }

            panelYContainer.setLayout(new GridLayout(0, nodesSqrt + nodesSqrt - 1));

            JPanel panelXContainer = new JPanel();
            panelXContainer.setLayout(new GridLayout(nodesSqrt + nodesSqrt - 1, 0));

            panelXContainer.add(nodeArray[nodeCounter++].drawCircle());  // Adds the very top-left circle

            for (int i = 0; i < nodesSqrt - 1; i++) {   // Adds on more line+circle combos to the same column

                panelXContainer.add(new vertLine());
                panelXContainer.add(nodeArray[nodeCounter++].drawCircle());

            }

            panelYContainer.add(panelXContainer);   // Adds that column of circle+vertLines as the leftmost column

            for (int i = 0; i < nodesSqrt - 1; i++) {   // for the rest of the columns in the mesh

                panelXContainer = new JPanel();
                panelXContainer.setLayout(new GridLayout(nodesSqrt + nodesSqrt - 1, 0));

                panelXContainer.add(new horiLine());  // Adds the top horizontal line to the column to the right of the previous one

                for (int j = 0; j < nodesSqrt - 1; j++) {   // Adds on more blankspace+horiline combos to the same column

                    panelXContainer.add(new blankSpace());
                    panelXContainer.add(new horiLine());

                }
                panelYContainer.add(panelXContainer);   // Adds the column of horiline+blackspaces to the right of the previous column

                panelXContainer = new JPanel();
                panelXContainer.setLayout(new GridLayout(nodesSqrt + nodesSqrt - 1, 0));

                panelXContainer.add(nodeArray[nodeCounter++].drawCircle());  // Adds a top circle

                for (int j = 0; j < nodesSqrt - 1; j++) {   // Adds on more line+circle combos to the column

                    panelXContainer.add(new vertLine());
                    panelXContainer.add(nodeArray[nodeCounter++].drawCircle());

                }
                panelYContainer.add(panelXContainer);   // Adds the column of circles+vertLines to the right of the previous column
            }
        }
        else if (topology.equals("bus"))    // Draw the topology for a bus network, depending on the number of nodes it has
        {
            Node[] nodeArray = new Node[nodes];

            int nodeCounter = 0;

            for(int i = 0; i < nodes; i++) {    // Creates the same number of circle objects that there are number of nodes
                nodeArray[i] = new Node(i);
            }

            panelYContainer.setLayout(new GridLayout(0, nodes));

            for(int i = 0; i < nodes/2; i++) {

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

            if(nodes % 2 == 1) {

                JPanel panelXContainer = new JPanel();
                panelXContainer.setLayout(new GridLayout(5, 0));

                panelXContainer.add(nodeArray[nodeCounter].drawCircle());  // If there's an odd number of nodes, adds a final upper branch at the end of the bus
                panelXContainer.add(new vertLine());
                panelXContainer.add(new tSegmentUp());
                panelXContainer.add(new blankSpace());
                panelXContainer.add(new blankSpace());

                panelYContainer.add(panelXContainer);
            }
        }

        return panelYContainer;
    }

    public void setTopology(String inputTopology)
    {
        topology = inputTopology;
    }

    public void setNodes(int inputNodes)
    {
        nodes = inputNodes;
    }

    public String getTopology()
    {
        return topology;
    }

    public int getNodes()
    {
        return nodes;
    }
}
