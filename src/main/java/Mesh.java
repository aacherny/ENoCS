package main.java;

import javax.swing.*;
import java.awt.*;

public class Mesh implements Network
{
    private int nodes;

    JDesktopPane desktopPane;

    protected Router[] routerArray;

    public Mesh(int inputNodes, JDesktopPane inputDesktopPane)
    {
        nodes = inputNodes;

        desktopPane = inputDesktopPane;

        routerArray = createRouterArray(nodes);
    }

    public void nextCycle()
    {
        Flit[] packet = createPacket(1, 1, 1, 3, 3);

        for (int i = 0; i < nodes; i++) {    // Creates the same number of circle objects that there are number of nodes
            routerArray[i].nextCycle();
        }
        // sometimes create new packets
        // call the nextCycle of each router
        // check if each router has a packet ready to send, send to the next router if it is
    }

    public Flit[] createPacket(int numberOfFlits, int locX, int locY, int destX, int destY)
    {
        Flit[] packet = {};

        switch(numberOfFlits) {
            case 1:
                packet[0] = new Flit(0, locX, locY, destX, destY);
                break;
            case 4:
                packet[0] = new Flit(1, locX, locY, destX, destY);
                packet[1] = new Flit(2, locX, locY, destX, destY);
                packet[2] = new Flit(3, locX, locY, destX, destY);
                packet[3] = new Flit(4, locX, locY, destX, destY);
                break;
        }

        return packet;
    }

    /**
     * Creates an array of Router objects depending on the amount of nodes there are
     * @return Router[]
     */
    public Router[] createRouterArray(int inputNodes)
    {
        Router[] routers = new Router[inputNodes];

        for (int i = 0; i < nodes; i++) {    // Creates the same number of router objects that there are number of nodes
            routers[i] = new Router(i, desktopPane);
        }

        return routers;
    }

    /**
     * Draws the topology of the network using the existing array of routers
     * @return JPanel
     */
    public JPanel drawTopology()
    {
        JPanel panelYContainer = new JPanel();

        int nodesSqrt = (int) Math.sqrt(nodes);
        int nodeCounter = 0;


        panelYContainer.setLayout(new GridLayout(0, nodesSqrt + nodesSqrt - 1));

        JPanel panelXContainer = new JPanel();
        panelXContainer.setLayout(new GridLayout(nodesSqrt + nodesSqrt - 1, 0));

        panelXContainer.add(routerArray[nodeCounter++].drawCircle());  // Adds the very top-left circle

        for (int i = 0; i < nodesSqrt - 1; i++) {   // Adds on more line+circle combos to the same column

            panelXContainer.add(new vertLine());
            panelXContainer.add(routerArray[nodeCounter++].drawCircle());

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

            panelXContainer.add(routerArray[nodeCounter++].drawCircle());  // Adds a top circle

            for (int j = 0; j < nodesSqrt - 1; j++) {   // Adds on more line+circle combos to the column

                panelXContainer.add(new vertLine());
                panelXContainer.add(routerArray[nodeCounter++].drawCircle());

            }
            panelYContainer.add(panelXContainer);   // Adds the column of circles+vertLines to the right of the previous column
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
        return "mesh";
    }
}