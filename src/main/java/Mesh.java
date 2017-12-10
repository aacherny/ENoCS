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

    private OuterJFrame OJFrame;

    private double packetChance;

    protected Router[] routerArray;

    public Mesh(int inputNodes, JDesktopPane inputDesktopPane, OuterJFrame inputOJFrame)
    {
        nodes = inputNodes;
        desktopPane = inputDesktopPane;
        OJFrame = inputOJFrame;
        scrollingTextFrame = new TextFrame();

        packetChance = OJFrame.getPacketChance();

        routerArray = createRouterArray(nodes);
    }

    public void nextCycle()
    {
        scrollingTextFrame.addText("Next cycle");

        for (int i = 0; i < nodes; i++) {    // Creates the same number of circle objects that there are number of nodes
            routerArray[i].nextCycle();
        }

        for (int i = 0; i < nodes; i++) {    // Sends ready-to-send packets from one router to the other
            if(routerArray[i].outputNorth != null){
                Flit[] tempFlit = new Flit[] {routerArray[i].outputNorth};
                routerArray[i-1].inputPacket(tempFlit, routerArray[i].getRouterLocation());

                routerArray[i].outputNorth = null;
            }

            if(routerArray[i].outputSouth != null){
                Flit[] tempFlit = new Flit[] {routerArray[i].outputSouth};
                routerArray[i+1].inputPacket(tempFlit, routerArray[i].getRouterLocation());

                routerArray[i].outputSouth = null;
            }

            if(routerArray[i].outputEast != null){
                Flit[] tempFlit = new Flit[] {routerArray[i].outputEast};
                routerArray[i+(int)Math.sqrt(nodes)].inputPacket(tempFlit, routerArray[i].getRouterLocation());

                routerArray[i].outputEast = null;
            }

            if(routerArray[i].outputWest != null){
                Flit[] tempFlit = new Flit[] {routerArray[i].outputWest};
                routerArray[i-(int)Math.sqrt(nodes)].inputPacket(tempFlit, routerArray[i].getRouterLocation());

                routerArray[i].outputWest = null;
            }
        }

        generatePacket(packetChance);
    }

    public void newCycle()
    {
        for (int i = 0; i < nodes; i++) {    // Creates the same number of circle objects that there are number of nodes
            routerArray[i].newCycle();
        }

        scrollingTextFrame.addText("Simulation restarted");
    }

    /**
     * Creates an array of Router objects depending on the amount of nodes there are
     * @return Router[] An array of router objects
     */
    @SuppressWarnings("Duplicates")
    public Router[] createRouterArray(int inputNodes)
    {
        Router[] routers = new Router[inputNodes];

        switch(nodes) { // All routers are manually assigned their number, and all of their neighboring routers
            case 4:
                routers[0] = new Router(0, 00,-1, 01, 10, -1, desktopPane, this);
                routers[1] = new Router(1, 01,00, -1, 11, -1, desktopPane, this);
                routers[2] = new Router(2, 10,-1, 11, -1, 00, desktopPane, this);
                routers[3] = new Router(3, 11,10, -1, -1, 01, desktopPane, this);

                for(int i = 0; i < nodes; i++) {
                    scrollingTextFrame.addText("Router " + i + " Created");
                }
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

                for(int i = 0; i < nodes; i++) {
                    scrollingTextFrame.addText("Router " + i + " Created");
                }
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

                for(int i = 0; i < nodes; i++) {
                    scrollingTextFrame.addText("Router " + i + " Created");
                }
                break;
        }

        return routers;
    }

    @SuppressWarnings("Duplicates")
    public void generatePacket(double chance) {
        for(int i = 0; i < nodes; i++)
        {
            int probability = ThreadLocalRandom.current().nextInt(0, 101);

            if(probability < (int) (chance * 100))
            {
                int randomNumberOfFlits = ThreadLocalRandom.current().nextInt(1, 2 + 1);
                if (randomNumberOfFlits == 2) {
                    randomNumberOfFlits = 4;
                }
                int randomDestinationX = ThreadLocalRandom.current().nextInt(0, (int) Math.sqrt(nodes));
                int randomDestinationY = ThreadLocalRandom.current().nextInt(0, (int) Math.sqrt(nodes));
                int randomDestination = randomDestinationX * 10 + randomDestinationY;

                Flit[] packet = createPacket(randomNumberOfFlits, 0, 0, randomDestinationX, randomDestinationY);
                if(routerArray[i].getChannelHome().size() < 13) {
                    routerArray[i].inputPacket(packet, 999);
                }

                scrollingTextFrame.addText("A " + randomNumberOfFlits + "-flit packet has been created at router " + i + ", destination: " + locationToIndex(randomDestination));
            }
        }
    }

    @SuppressWarnings("Duplicates")
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

    public TextFrame getScrollingTextFrame(){
        return scrollingTextFrame;
    }

    public void setPacketChance(double inputPacketChance ){
        packetChance = inputPacketChance / 100;

        scrollingTextFrame.addText("Injection rate is now " + String.format("%.0f", inputPacketChance) + "%");
    }

    public void removeTextWindow(){
        try{
            desktopPane.remove(scrollingTextFrame);
        }catch(Exception e) {
            System.out.println(e);
        }
    }

    private int locationToIndex(int inputLocation){
        int locationX = inputLocation / 10;
        int locationY = inputLocation % 10;
        return ((int)Math.sqrt(nodes) * locationX + locationY);
    }
}