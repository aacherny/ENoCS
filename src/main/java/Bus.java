package main.java;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Bus Object
 * <P>
 * Handles the functions of a Bus network like the interactions between routers
 *
 * @author Alex Cherny
 */
public class Bus implements Network
{
    private int nodes;
    private double packetChance;
    protected int pipelineStages;

    private JDesktopPane desktopPane;
    private TextFrame scrollingTextFrame;
    private StatsFrame statisticsFrame;
    private OuterJFrame OJFrame;

    protected Router[] routerArray;

    /**
     * Creates a Bus object
     *
     * @param inputNodes        the number of nodes in the Bus network
     * @param inputDesktopPane  the desktop object of the program, used to update certain windows on it when things happen
     * @param inputOJFrame      the JOuterFrame that holds the desktop, used to update values that are used by the windows
     */
    public Bus(int inputNodes, JDesktopPane inputDesktopPane, OuterJFrame inputOJFrame)
    {
        nodes = inputNodes;
        desktopPane = inputDesktopPane;
        OJFrame = inputOJFrame;
        scrollingTextFrame = new TextFrame();
        statisticsFrame = new StatsFrame();

        packetChance = OJFrame.getPacketChance();
        pipelineStages = 4;

        routerArray = createRouterArray(nodes);
    }

    /**
     * Calls the next cycle for each router, passes ready-to-send packets between routers, and generates packets
     */
    public void nextCycle()
    {
        scrollingTextFrame.addText("Next cycle");

        for (int i = 0; i < nodes; i++) {    // Creates the same number of circle objects that there are number of nodes
            routerArray[i].nextCycle();
        }

        for (int i = 0; i < nodes; i++) {    // Sends ready-to-send packets from one router to the other

            // If the router has a flit ready to send in it's South channel (the only output channel that Bus Routers have is south)
            if(routerArray[i].outputSouth != null){
                Flit tempFlit = routerArray[i].outputSouth;
                // Calculates the destination coordinates of the flit that's being sent
                int tempFlitDestination = tempFlit.getDestinationX()*10 + tempFlit.getDestinationY();
                // Inputs the packet into the packet's destination router, from that router's southern direction
                routerArray[locationToIndex(tempFlitDestination)].addPacket(tempFlit);

                routerArray[i].outputSouth = null;
            }
        }

        generatePacket(packetChance);
    }

    /**
     * Calls the new cycle for each router
     */
    public void newCycle()
    {
        for (int i = 0; i < nodes; i++) {    // Creates the same number of circle objects that there are number of nodes
            routerArray[i].newCycle();
        }

        scrollingTextFrame.addText("Simulation restarted");
        statisticsFrame.reset();
    }

    /**
     * Creates an array of Router objects
     * @param inputNodes    The number of routers to create
     * @return              The array of router objects
     */
    @SuppressWarnings("Duplicates")
    public Router[] createRouterArray(int inputNodes) {

        Router[] routers = new Router[inputNodes];

        for(int i = 0; i < nodes; i++){
            routers[i] = new Router(i, indexToLocation(i),-1, 01, -1, -1, desktopPane, this);
            scrollingTextFrame.addText("Router " + i + " Created");
        }

        return routers;
    }

    /**
     * Sometimes generates a packet with a random number of flits and with a random destination.
     * Then inputs the packet to a random router
     * @param chance    The chance that a packet will be created
     */
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
                    statisticsFrame.addFlitCreated(randomNumberOfFlits);
                    statisticsFrame.addPacketCreated();

                    scrollingTextFrame.addText("A " + randomNumberOfFlits + "-flit packet has been created at router " + i + ", destination: " + locationToIndex(randomDestination));
                }


            }
        }
    }

    /**
     * Creates a packet (which is an array of flits)
     * @param numberOfFlits The number of flits that the packet will be made out of
     * @param locX          The X location of the flit
     * @param locY          The Y location of the flit
     * @param destX         The X destination of the flit
     * @param destY         The Y destination of the flit
     * @return              An array of flits
     */
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
                Flit[] packet = new Flit[]{new Flit(0, locX, locY, destX, destY, randomColor, OJFrame.getCycleNumber())};
                return packet;
            }
            case (1): {
                Flit[] packet = new Flit[]{new Flit(0, locX, locY, destX, destY, randomColor, OJFrame.getCycleNumber())};
                return packet;
            }
            case (4): {
                Flit[] packet = new Flit[]{new Flit(1, locX, locY, destX, destY, randomColor, OJFrame.getCycleNumber()),
                        new Flit(2, locX, locY, destX, destY, randomColor, OJFrame.getCycleNumber()),
                        new Flit(3, locX, locY, destX, destY, randomColor, OJFrame.getCycleNumber()),
                        new Flit(4, locX, locY, destX, destY, randomColor, OJFrame.getCycleNumber())};
                return packet;
            }
        }
    }

    /**
     * Draws the topology for a Bus network, and adds a "Simulation Events" scrolling list and a statistics window
     * @return  A JPanel of the network's topology
     */
    public JPanel drawTopology() {

        desktopPane.add(scrollingTextFrame);
        desktopPane.add(statisticsFrame);

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
            panelXContainer.add(new shapeTSegmentUp());
            panelXContainer.add(new blankSpace());
            panelXContainer.add(new blankSpace());

            panelYContainer.add(panelXContainer);

            panelXContainer = new JPanel();
            panelXContainer.setLayout(new GridLayout(5, 0));

            panelXContainer.add(new blankSpace());  // Adds a lower branch
            panelXContainer.add(new blankSpace());
            panelXContainer.add(new shapeTSegmentDown());
            panelXContainer.add(new vertLine());
            panelXContainer.add(routerArray[nodeCounter++].drawCircle());

            panelYContainer.add(panelXContainer);
        }

        if (nodes % 2 == 1) {

            JPanel panelXContainer = new JPanel();
            panelXContainer.setLayout(new GridLayout(5, 0));

            panelXContainer.add(routerArray[nodeCounter].drawCircle());  // If there's an odd number of nodes, adds a final upper branch at the end of the bus
            panelXContainer.add(new vertLine());
            panelXContainer.add(new shapeTSegmentUp());
            panelXContainer.add(new blankSpace());
            panelXContainer.add(new blankSpace());

            panelYContainer.add(panelXContainer);
        }

        return panelYContainer;
    }

    /** Converts a two digit location like "01" to the router number that would be at that location (01 would be router 1)*/
    private int locationToIndex(int inputLocation){
        int locationX = inputLocation / 10;
        int locationY = inputLocation % 10;
        return ((int)Math.sqrt(nodes) * locationX + locationY);
    }

    /** Converts a router number to what its location would be in a grid*/
    private int indexToLocation(int inputIndex){
        int locationX = inputIndex / (int)Math.sqrt(nodes);
        int locationY = inputIndex % (int)Math.sqrt(nodes);
        return locationX * 10 + locationY;
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

    public TextFrame getScrollingTextFrame(){
        return scrollingTextFrame;
    }

    public StatsFrame getStatisticsFrame(){
        return statisticsFrame;
    }

    public void setPacketChance(double inputPacketChance ){
        packetChance = inputPacketChance / 100;

        scrollingTextFrame.addText("Injection rate is now " + String.format("%.0f", inputPacketChance) + "%");
    }

    public void setPipelineStages(int inputStages){
        pipelineStages = inputStages;
        newCycle();

        scrollingTextFrame.addText("The pipeline now has " + inputStages + " stages");
    }

    public int getPipelineStages(){
        return pipelineStages;
    }


    public OuterJFrame getOJFrame(){
        return OJFrame;
    }

    /** Removes the Statistics window and Events window if they're visible*/
    public void removeTextWindow(){
        try{
            desktopPane.remove(scrollingTextFrame);
        }catch(Exception e) {
            System.out.println(e);
        }

        try{
            desktopPane.remove(statisticsFrame);
        }catch(Exception e) {
            System.out.println(e);
        }
    }
}