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
        Flit[] packet = createPacket(1, 0, 0, 1, 1);

        System.out.println("Next cycle selected");

        for (int i = 0; i < nodes; i++) {    // Creates the same number of circle objects that there are number of nodes
            routerArray[i].nextCycle();
        }

        // check if each router has a packet ready to send, send to the next router if it is
    }

    public Flit[] createPacket(int numberOfFlits, int locX, int locY, int destX, int destY)
    {
        switch(numberOfFlits) {
            default: {
                Flit[] packet = new Flit[]{new Flit(0, locX, locY, destX, destY)};
                return packet;
            }
            case (1): {
                Flit[] packet = new Flit[]{new Flit(0, locX, locY, destX, destY)};
                return packet;
            }
            case (4): {
                Flit[] packet = new Flit[]{new Flit(1, locX, locY, destX, destY),
                        new Flit(2, locX, locY, destX, destY),
                        new Flit(3, locX, locY, destX, destY),
                        new Flit(4, locX, locY, destX, destY)};
                return packet;
            }
        }
    }

    /**
     * Creates an array of Router objects depending on the amount of nodes there are
     * @return Router[] An array of router objects
     */
    public Router[] createRouterArray(int inputNodes)
    {
        Router[] routers = new Router[inputNodes];

        switch(nodes) { // All routers are manually assigned their number, and all of their neighboring routers
            case 4:
                routers[0] = new Router(0, -1, 01, 10, -1, desktopPane);
                routers[1] = new Router(1, 00, -1, 11, -1, desktopPane);
                routers[2] = new Router(2, -1, 11, -1, 00, desktopPane);
                routers[3] = new Router(3, 10, -1, -1, 01, desktopPane);
                break;
            case 9:
                routers[0] = new Router(0, -1, 01, 10, -1, desktopPane);
                routers[1] = new Router(1, 00, 02, 11, -1, desktopPane);
                routers[2] = new Router(2, 01, -1, 12, -1, desktopPane);
                routers[3] = new Router(3, -1, 11, 20, 00, desktopPane);
                routers[4] = new Router(4, 10, 12, 21, 01, desktopPane);
                routers[5] = new Router(5, 11, -1, 22, 02, desktopPane);
                routers[6] = new Router(6, -1, 21, -1, 10, desktopPane);
                routers[7] = new Router(7, 20, 22, -1, 11, desktopPane);
                routers[8] = new Router(8, 21, -1, -1, 12, desktopPane);
                break;
            case 16:
                routers[0] = new Router(0, -1, 01, 10, -1, desktopPane);
                routers[1] = new Router(1, 00, 02, 11, -1, desktopPane);
                routers[2] = new Router(2, 01, 03, 12, -1, desktopPane);
                routers[3] = new Router(3, 02, -1, 13, -1, desktopPane);
                routers[4] = new Router(4, -1, 11, 20, 00, desktopPane);
                routers[5] = new Router(5, 10, 12, 21, 01, desktopPane);
                routers[6] = new Router(6, 11, 13, 22, 02, desktopPane);
                routers[7] = new Router(7, 12, -1, 23, 03, desktopPane);
                routers[8] = new Router(8, -1, 21, 30, 10, desktopPane);
                routers[9] = new Router(9, 20, 22, 31, 11, desktopPane);
                routers[10] = new Router(10, 21, 23, 32, 12, desktopPane);
                routers[11] = new Router(11, 22, -1, 33, 13, desktopPane);
                routers[12] = new Router(12, -1, 31, -1, 20, desktopPane);
                routers[13] = new Router(13, 30, 32, -1, 21, desktopPane);
                routers[14] = new Router(14, 31, 33, -1, 22, desktopPane);
                routers[15] = new Router(15, 32, -1, -1, 23, desktopPane);
                break;
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