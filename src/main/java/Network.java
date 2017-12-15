package main.java;

import javax.swing.*;
import java.awt.*;

/**
 * Network object that handles the interactions between Routers
 *
 * @author Alex Cherny
 */
interface Network
{
    /** Calls nextCycle for each of the router objects*/
    void nextCycle();

    /** Calls newCycle for each of the router objects*/
    void newCycle();

    /**
     * Creates an array of Router objects
     * @param inputNodes    The number of routers to create
     * @return              The array of router objects
     */
    Router[] createRouterArray(int inputNodes);

    /**
     * Sometimes generates a packet with a random number of flits and with a random destination,
     * then inputs the packet to a random router
     * @param chance    The chance that a packet will be created
     */
    @SuppressWarnings("Duplicates")
    void generatePacket(double chance);

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
    Flit[] createPacket(int numberOfFlits, int locX, int locY, int destX, int destY);

    /**
     * Draws the topology for the network
     * @return  A JPanel with the drawn topology
     */
    JPanel drawTopology();

    void setNodes(int inputNodes);

    void setPacketChance(double inputPacketChance);

    int getNodes();

    String getTopology();

    void removeTextWindow();

    void setPipelineStages(int inputStages);

    int getPipelineStages();

    OuterJFrame getOJFrame();

    TextFrame getScrollingTextFrame();

    StatsFrame getStatisticsFrame();
}
