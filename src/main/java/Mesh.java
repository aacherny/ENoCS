package main.java;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Mesh implements Network
{
    private int nodes;

    private JDesktopPane desktopPane;

    private TextFrame scrollingTextFrame;


    protected Router[] routerArray;

    public Mesh(int inputNodes, JDesktopPane inputDesktopPane)
    {
        nodes = inputNodes;
        desktopPane = inputDesktopPane;
        scrollingTextFrame = new TextFrame();

        routerArray = createRouterArray(nodes);
    }

    public void nextCycle()
    {
        generatePacket();
        //Flit[] packet = createPacket(1, 0, 0, 1, 1);
        //routerArray[0].inputPacket(packet, 00);
        //scrollingTextFrame.addText("A " + 1 + " flit packet has been created at router " + 0);

        for (int i = 0; i < nodes; i++) {    // Creates the same number of circle objects that there are number of nodes
            routerArray[i].nextCycle();
        }

        for (int i = 0; i < nodes; i++) {    // Sends ready-to-send packets from one router to the other
            if(routerArray[i].outputNorth != null){
                Flit[] tempFlit = new Flit[] {routerArray[i].outputNorth};
                routerArray[i-1].inputPacket(tempFlit, routerArray[i].getRouterLocation());

                routerArray[i].outputNorth = null;
                System.out.println("Router " + routerArray[i].getRouterNumber() + " has a flit to send to " + routerArray[i-1].getRouterNumber());
            }

            if(routerArray[i].outputSouth != null){
                Flit[] tempFlit = new Flit[] {routerArray[i].outputSouth};
                routerArray[i+1].inputPacket(tempFlit, routerArray[i].getRouterLocation());

                routerArray[i].outputSouth = null;
                System.out.println("Router " + routerArray[i].getRouterNumber() + " has a flit to send to " + routerArray[i+1].getRouterNumber());
            }

            if(routerArray[i].outputEast != null){
                Flit[] tempFlit = new Flit[] {routerArray[i].outputEast};
                routerArray[i+(int)Math.sqrt(nodes)].inputPacket(tempFlit, routerArray[i].getRouterLocation());

                routerArray[i].outputEast = null;
                System.out.println("Router " + routerArray[i].getRouterNumber() + " has a flit to send to " + routerArray[i+(int)Math.sqrt(nodes)].getRouterNumber());
            }

            if(routerArray[i].outputWest != null){
                Flit[] tempFlit = new Flit[] {routerArray[i].outputWest};
                routerArray[i-(int)Math.sqrt(nodes)].inputPacket(tempFlit, routerArray[i].getRouterLocation());

                routerArray[i].outputWest = null;
                System.out.println("Router " + routerArray[i].getRouterNumber() + " has a flit to send to " + routerArray[i-(int)Math.sqrt(nodes)].getRouterNumber());
            }
        }
    }

    public void generatePacket() {
        int randomRouter = ThreadLocalRandom.current().nextInt(0, nodes + 1);
        int randomNumberOfFlits = ThreadLocalRandom.current().nextInt(1, 2 + 1);
        if(randomNumberOfFlits == 2) {
            randomNumberOfFlits = 4;
        }
        int randomSourceX = ThreadLocalRandom.current().nextInt(0, (int) Math.sqrt(nodes));
        int randomSourceY = ThreadLocalRandom.current().nextInt(0, (int) Math.sqrt(nodes));
        int randomSource = randomSourceX * 10 + randomSourceY;
        int randomDestinationX = ThreadLocalRandom.current().nextInt(0, (int) Math.sqrt(nodes));
        int randomDestinationY = ThreadLocalRandom.current().nextInt(0, (int) Math.sqrt(nodes));

        Flit[] packet = createPacket(randomNumberOfFlits, 0, 0, randomDestinationX, randomDestinationY);
        routerArray[randomRouter].inputPacket(packet, -1);

        System.out.println("Random packet, Flits: " + randomNumberOfFlits + ", Source: " + randomSource + ", Destination: " + randomDestinationX + "" + randomDestinationX);

        scrollingTextFrame.addText("A " + randomNumberOfFlits + "-flit packet has been created at router " + randomRouter);
        scrollingTextFrame.addText("Destination: " + randomDestinationX + "" + randomDestinationX);
    }

    public void newCycle()
    {
        for (int i = 0; i < nodes; i++) {    // Creates the same number of circle objects that there are number of nodes
            routerArray[i].newCycle();
        }
    }

    public Flit[] createPacket(int numberOfFlits, int locX, int locY, int destX, int destY)
    {
        // Creates a random color for the Flit to be assigned as
        Random rand = new Random();
        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();
        Color randomColor = new Color(r, g, b);

        switch(numberOfFlits) {
            default: {
                Flit[] packet = new Flit[]{new Flit(0, locX, locY, destX, destY, randomColor)};
                return packet;
            }
            case (1): {
                Flit[] packet = new Flit[]{new Flit(0, locX, locY, destX, destY, randomColor)};
                return packet;
            }
            case (4): {
                Flit[] packet = new Flit[]{new Flit(1, locX, locY, destX, destY, randomColor),
                        new Flit(2, locX, locY, destX, destY, randomColor),
                        new Flit(3, locX, locY, destX, destY, randomColor),
                        new Flit(4, locX, locY, destX, destY, randomColor)};
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
                routers[0] = new Router(0, 00,-1, 01, 10, -1, desktopPane, this);
                routers[1] = new Router(1, 01,00, -1, 11, -1, desktopPane, this);
                routers[2] = new Router(2, 10,-1, 11, -1, 00, desktopPane, this);
                routers[3] = new Router(3, 11,10, -1, -1, 01, desktopPane, this);
                scrollingTextFrame.addText("Router 0 Created");
                scrollingTextFrame.addText("Router 1 Created");
                scrollingTextFrame.addText("Router 2 Created");
                scrollingTextFrame.addText("Router 3 Created");
                break;
            case 9:
                routers[0] = new Router(0, 00,-1, 01, 10, -1, desktopPane, this);
                routers[1] = new Router(1, 01,00, 02, 11, -1, desktopPane, this);
                routers[2] = new Router(2, 02,01, -1, 12, -1, desktopPane, this);
                routers[3] = new Router(3, 10,-1, 11, 20, 00, desktopPane, this);
                routers[4] = new Router(4, 11,10, 12, 21, 01, desktopPane, this);
                routers[5] = new Router(5, 12,11, -1, 22, 02, desktopPane, this);
                routers[6] = new Router(6, 20,-1, 21, -1, 10, desktopPane, this);
                routers[7] = new Router(7, 21,20, 22, -1, 11, desktopPane, this);
                routers[8] = new Router(8, 22,21, -1, -1, 12, desktopPane, this);
                scrollingTextFrame.addText("Router 0 Created");
                scrollingTextFrame.addText("Router 1 Created");
                scrollingTextFrame.addText("Router 2 Created");
                scrollingTextFrame.addText("Router 3 Created");
                scrollingTextFrame.addText("Router 4 Created");
                scrollingTextFrame.addText("Router 5 Created");
                scrollingTextFrame.addText("Router 6 Created");
                scrollingTextFrame.addText("Router 7 Created");
                scrollingTextFrame.addText("Router 8 Created");
                break;
            case 16:
                routers[0] = new Router(0, 00,-1, 01, 10, -1, desktopPane, this);
                routers[1] = new Router(1, 01,00, 02, 11, -1, desktopPane, this);
                routers[2] = new Router(2, 02,01, 03, 12, -1, desktopPane, this);
                routers[3] = new Router(3, 03,02, -1, 13, -1, desktopPane, this);
                routers[4] = new Router(4, 10,-1, 11, 20, 00, desktopPane, this);
                routers[5] = new Router(5, 11,10, 12, 21, 01, desktopPane, this);
                routers[6] = new Router(6, 12,11, 13, 22, 02, desktopPane, this);
                routers[7] = new Router(7, 13,12, -1, 23, 03, desktopPane, this);
                routers[8] = new Router(8, 20,-1, 21, 30, 10, desktopPane, this);
                routers[9] = new Router(9, 21,20, 22, 31, 11, desktopPane, this);
                routers[10] = new Router(10, 22,21, 23, 32, 12, desktopPane, this);
                routers[11] = new Router(11, 23,22, -1, 33, 13, desktopPane, this);
                routers[12] = new Router(12, 30,-1, 31, -1, 20, desktopPane, this);
                routers[13] = new Router(13, 31,30, 32, -1, 21, desktopPane, this);
                routers[14] = new Router(14, 32,31, 33, -1, 22, desktopPane, this);
                routers[15] = new Router(15, 33,32, -1, -1, 23, desktopPane, this);
                scrollingTextFrame.addText("Router 0 Created");
                scrollingTextFrame.addText("Router 1 Created");
                scrollingTextFrame.addText("Router 2 Created");
                scrollingTextFrame.addText("Router 3 Created");
                scrollingTextFrame.addText("Router 4 Created");
                scrollingTextFrame.addText("Router 5 Created");
                scrollingTextFrame.addText("Router 6 Created");
                scrollingTextFrame.addText("Router 7 Created");
                scrollingTextFrame.addText("Router 8 Created");
                scrollingTextFrame.addText("Router 9 Created");
                scrollingTextFrame.addText("Router 10 Created");
                scrollingTextFrame.addText("Router 11 Created");
                scrollingTextFrame.addText("Router 12 Created");
                scrollingTextFrame.addText("Router 13 Created");
                scrollingTextFrame.addText("Router 14 Created");
                scrollingTextFrame.addText("Router 15 Created");
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
        desktopPane.add(scrollingTextFrame);

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

    public void removeTextWindow(){
        try{
            desktopPane.remove(scrollingTextFrame);
        }catch(Exception e) {
            System.out.println(e);
        }
    }
}