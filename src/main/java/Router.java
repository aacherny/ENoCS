package main.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

/**
 * Router Object that handles the location of flits within the pipeline, and their route computation
 *
 * @author Alex Cherny
 */
public class Router {
    private int routerNumber;
    private int routerLocation;

    private int routerNorth;
    private int routerSouth;
    private int routerEast;
    private int routerWest;

    private int homeIndex;  // These hold the X index of each output/intput
    private int northIndex; // If the router is connected to the east and west, then home = 0, east = 1, west = 2...
    private int southIndex;
    private int eastIndex;
    private int westIndex;

    private String[] channelIndex = {"", "", "", "", "", "", ""};
    private int cIndex = 0;

    private String nextRouter = "home"; // Keeps track of the router that's going to pass the next flit into the pipeline

    private LinkedList<Flit> channelHome = null;   // Channels to hold incoming flits
    private LinkedList<Flit> channelNorth = null;
    private LinkedList<Flit> channelSouth = null;
    private LinkedList<Flit> channelEast = null;
    private LinkedList<Flit> channelWest = null;

    private Flit RouteComputation = null;   // Hold the flits that are in certain stages of the pipeline
    private Flit VCAllocator = null;
    private Flit SwitchAllocator = null;
    private Flit SwitchTraversal = null;

    public Flit outputHome = null; // Hold the flits that are in certain output channels
    public Flit outputNorth = null;
    public Flit outputSouth = null;
    public Flit outputEast = null;
    public Flit outputWest = null;

    private JDesktopPane desktopPane;
    private RouterDiagram routerDiagram;
    private Network network;

    protected Flit blankFlit = new Flit(0, 0, 0, 0, 0, Color.WHITE,0);

    Circle circle = new Circle();

    /**
     * Creates the router object
     * @param inputNodeNumber   The index of the router
     * @param inputLocation     The location of the router
     * @param inputNorth        The location of the router to its north
     * @param inputSouth        The location of the router to its south
     * @param inputEast         The location of the router to its east
     * @param inputWest         The location of the router to its west
     * @param inputDesktopPane  The desktop pane that holds the windows
     * @param inputNetwork      The network that created the router
     */
    public Router(int inputNodeNumber, int inputLocation, int inputNorth, int inputSouth, int inputEast, int inputWest, JDesktopPane inputDesktopPane, Network inputNetwork) {
        routerNumber = inputNodeNumber;
        routerLocation = inputLocation;
        desktopPane = inputDesktopPane;
        network = inputNetwork;

        routerNorth = inputNorth;
        routerSouth = inputSouth;
        routerEast = inputEast;
        routerWest = inputWest;


        channelHome = new LinkedList<Flit>();
        homeIndex = cIndex;
        channelIndex[cIndex++] = "home";

        if (inputNorth != -1) {
            channelNorth = new LinkedList<Flit>();
            northIndex = cIndex;
            channelIndex[cIndex++] = "north";
        }
        if (inputSouth != -1) {
            channelSouth = new LinkedList<Flit>();
            southIndex = cIndex;
            channelIndex[cIndex++] = "south";
        }
        if (inputEast != -1) {
            channelEast = new LinkedList<Flit>();
            eastIndex = cIndex;
            channelIndex[cIndex++] = "east";
        }
        if (inputWest != -1) {
            channelWest = new LinkedList<Flit>();
            westIndex = cIndex;
            channelIndex[cIndex++] = "west";
        }

        routerDiagram = new RouterDiagram(this);
        JPanel jpan = routerDiagram.addDiagram();
        routerDiagram.add(jpan);
    }

    /**
     * Decides which input channel sends its flit into the pipeline, sends flits down the pipeline,
     * and computes the correct output channel for each flit
     */
    public void nextCycle() {
        System.out.println("Next cycle for router: " + routerNumber + ", next channel is: " + nextRouter);


        switch (network.getPipelineStages()){
            default:
                pipeline(4);
                break;
            case 0:
                pipeline(0);
                break;
            case 1:
                pipeline(1);
                break;
            case 2:
                pipeline(2);
                break;
            case 3:
                pipeline(3);
                break;
            case 4:
                pipeline(4);
                break;
        }

        // Go through each list and draw the rectangle on the diagram for every flit in the router

            for (int i = 0; i <= cIndex; i++) {
                if (channelIndex[i] == "home") {
                    createRectanglesFromFlitList(channelHome, i);
                } else if (channelIndex[i] == "north") {
                    createRectanglesFromFlitList(channelNorth, i);
                } else if (channelIndex[i] == "south") {
                    createRectanglesFromFlitList(channelSouth, i);
                } else if (channelIndex[i] == "east") {
                    createRectanglesFromFlitList(channelEast, i);
                } else if (channelIndex[i] == "west") {
                    createRectanglesFromFlitList(channelWest, i);
                }
            }

        // Adds rectangles for the flits inside the pipeline
        createRectanglesFromPipeline(RouteComputation, 16);
        createRectanglesFromPipeline(VCAllocator, 17);
        createRectanglesFromPipeline(SwitchAllocator, 18);
        createRectanglesFromPipeline(SwitchTraversal, 19);

        // If a slot in the pipeline is empty, paint a white rectangle over it
        clearPipelineSlots();

        // Update the window for each router
        routerDiagram.invalidate();
        routerDiagram.validate();
        routerDiagram.repaint();
    }

    /**
     * Clears the lists and pipeline stages of any flits they were holding
     */
    public void newCycle() {
        channelHome = new LinkedList<Flit>();

        if(channelNorth != null) {
            channelNorth = new LinkedList<Flit>();
        }

        if(channelSouth != null) {
            channelSouth = new LinkedList<Flit>();
        }

        if(channelEast != null) {
            channelEast = new LinkedList<Flit>();
        }

        if(channelWest != null) {
            channelWest = new LinkedList<Flit>();
        }

        RouteComputation = null;
        VCAllocator = null;
        SwitchAllocator = null;
        SwitchTraversal = null;

        outputHome = null;
        outputNorth = null;
        outputSouth = null;
        outputEast = null;
        outputWest = null;

        clearPipelineSlots();
    }

    /**
     * Computes which output channel the flit should be put in depending on its location and destinationm, and the network topology
     * @param inputFlit The flit that's about to be sorted
     */
    public void routeComputation(Flit inputFlit) {
        if(network.getTopology() == "mesh") {
            if ((routerLocation / 10 == inputFlit.getDestinationX()) && (routerLocation % 10 == inputFlit.getDestinationY())) {
                outputHome = inputFlit;

                network.getScrollingTextFrame().addText("A packet has reached its destination at router " + locationToIndex(inputFlit.getDestinationX()*10 + inputFlit.getDestinationY()));

                network.getStatisticsFrame().updateLatency(network.getOJFrame().cycleNumber, inputFlit);

                if(inputFlit.getIndex() == 0){
                    network.getStatisticsFrame().addFlitReceived(1);
                    network.getStatisticsFrame().addPacketReceived();
                }else if(inputFlit.getIndex() == 4){
                    network.getStatisticsFrame().addFlitReceived(4);
                    network.getStatisticsFrame().addPacketReceived();
                }
            } else if (routerLocation / 10 > inputFlit.getDestinationX()) {
                outputWest = inputFlit;

            } else if (routerLocation / 10 < inputFlit.getDestinationX()) {
                outputEast = inputFlit;

            } else if (routerLocation % 10 > inputFlit.getDestinationY()) {
                outputNorth = inputFlit;

            } else if (routerLocation % 10 < inputFlit.getDestinationY()) {
                outputSouth = inputFlit;
            }
        }
        else if(network.getTopology() == "bus") {
            if ((routerLocation / 10 == inputFlit.getDestinationX()) && (routerLocation % 10 == inputFlit.getDestinationY())) {
                outputHome = inputFlit;

                network.getScrollingTextFrame().addText("A packet has reached its destination at router " + locationToIndex(inputFlit.getDestinationX()*10 + inputFlit.getDestinationY()));

                network.getStatisticsFrame().updateLatency(network.getOJFrame().cycleNumber, inputFlit);

                if(inputFlit.getIndex() == 0){
                    network.getStatisticsFrame().addFlitReceived(1);
                    network.getStatisticsFrame().addPacketReceived();
                }else if(inputFlit.getIndex() == 4){
                    network.getStatisticsFrame().addFlitReceived(4);
                    network.getStatisticsFrame().addPacketReceived();
                }
            }else {
                System.out.println("PASSED: " + routerLocation / 10 + ", " + inputFlit.getDestinationX() + ", " + routerLocation % 10 + ", " + inputFlit.getDestinationY());
                outputSouth = inputFlit;
            }
        }
        else if(network.getTopology() == "torus") {
            int routerLocationX = routerLocation / 10 + 1;
            int routerLocationY = routerLocation % 10 + 1;
            int flitDestinationX = inputFlit.getDestinationX() + 1;
            int flitDestinationY = inputFlit.getDestinationY() + 1;
            int sqrtNodes = (int) Math.sqrt(network.getNodes());

            if ((routerLocationX == flitDestinationX) && (routerLocationY == flitDestinationY)) {
                outputHome = inputFlit;

                network.getScrollingTextFrame().addText("A packet has reached its destination at router " + locationToIndex(inputFlit.getDestinationX()*10 + inputFlit.getDestinationY()));

                network.getStatisticsFrame().updateLatency(network.getOJFrame().cycleNumber, inputFlit);

                if(inputFlit.getIndex() == 0){
                    network.getStatisticsFrame().addFlitReceived(1);
                    network.getStatisticsFrame().addPacketReceived();
                }else if(inputFlit.getIndex() == 4){
                    network.getStatisticsFrame().addFlitReceived(4);
                    network.getStatisticsFrame().addPacketReceived();
                }
            } else if (routerLocationX == flitDestinationX) {
                if (sqrtNodes - routerLocationY + flitDestinationY < routerLocationY - flitDestinationY) {
                    outputSouth = inputFlit;
                } else if (sqrtNodes - flitDestinationY + routerLocationY < flitDestinationY - routerLocationY) {
                    outputNorth = inputFlit;
                } else if (routerLocationY - flitDestinationY <= sqrtNodes - routerLocationY + flitDestinationY) {
                    outputSouth = inputFlit;
                } else if (flitDestinationY - routerLocationY <= sqrtNodes - flitDestinationY + routerLocationY) {
                    outputNorth = inputFlit;
                }
            } else if (sqrtNodes - routerLocationX + flitDestinationX < routerLocationX - flitDestinationX) {
                outputEast = inputFlit;
            } else if (sqrtNodes - flitDestinationX + routerLocationX < flitDestinationX - routerLocationX) {
                outputWest = inputFlit;
            } else if (routerLocationX - flitDestinationX <= sqrtNodes - routerLocationX + flitDestinationX) {
                outputWest = inputFlit;
            } else if (flitDestinationX - routerLocationX <= sqrtNodes - flitDestinationX + routerLocationX) {
                outputEast = inputFlit;
            }
        }
    }

    /**
     * Selects the next channel whose flit will be taken from the front of the list, following a Round Robin order,
     * and checking if the channel actually exists and has a flit in it
     * <p>
     * Also takes into account if the flit is part of a packet, sending keeping flits of a packet together as they move
     * through the pipeline
     *
     * @param channel           The current channel that's next in order
     * @param inputFlitIndex    The index of the flit within the packet
     */
    private void setNextChannel(String channel, int inputFlitIndex) {
        Integer zero = 0;
        Integer four = 4;
        Integer flitIndex = inputFlitIndex;

        if (!flitIndex.equals(four) && !flitIndex.equals(zero)) {
            nextRouter = channel;
        } else if (channel == "home") {

            System.out.println("In Router " + routerNumber + ", the next router is " + nextRouter);

            if (channelNorth != null && channelNorth.peekFirst() != null) {
                nextRouter = "north";
            } else if (channelSouth != null && channelSouth.peekFirst() != null) {
                nextRouter = "south";
            } else if (channelEast != null && channelEast.peekFirst() != null) {
                nextRouter = "east";
            } else if (channelWest != null && channelWest.peekFirst() != null) {
                nextRouter = "west";
            } else {
                nextRouter = "home";
            }
        } else if (channel == "north") {
            if (channelSouth != null && channelSouth.peekFirst() != null) {
                nextRouter = "south";
            } else if (channelEast != null && channelEast.peekFirst() != null) {
                nextRouter = "east";
            } else if (channelWest != null && channelWest.peekFirst() != null) {
                nextRouter = "west";
            } else if (channelHome != null && channelHome.peekFirst() != null) {
                nextRouter = "home";
            } else {
                nextRouter = "north";
            }
        } else if (channel == "south") {
            if (channelEast != null && channelEast.peekFirst() != null) {
                nextRouter = "east";
            } else if (channelWest != null && channelWest.peekFirst() != null) {
                nextRouter = "west";
            } else if (channelHome != null && channelHome.peekFirst() != null) {
                nextRouter = "home";
            } else if (channelNorth != null && channelNorth.peekFirst() != null) {
                nextRouter = "north";
            } else {
                nextRouter = "south";
            }
        } else if (channel == "east") {
            if (channelWest != null && channelWest.peekFirst() != null) {
                nextRouter = "west";
            } else if (channelHome != null && channelHome.peekFirst() != null) {
                nextRouter = "home";
            } else if (channelNorth != null && channelNorth.peekFirst() != null) {
                nextRouter = "north";
            } else if (channelSouth != null && channelSouth.peekFirst() != null) {
                nextRouter = "south";
            } else {
                nextRouter = "east";
            }
        } else if (channel == "west") {
            if (channelHome != null && channelHome.peekFirst() != null) {
                nextRouter = "home";
            } else if (channelNorth != null && channelNorth.peekFirst() != null) {
                nextRouter = "north";
            } else if (channelSouth != null && channelSouth.peekFirst() != null) {
                nextRouter = "south";
            } else if (channelEast != null && channelEast.peekFirst() != null) {
                nextRouter = "east";
            } else {
                nextRouter = "west";
            }


        }
    }

    /**
     * Checks for stages in the pipeline that are null, paints a while rectangle at that spot if it's null.
     * Paints the correct color at that spot if they're not null
     */
    private void clearPipelineSlots() {

        if(outputHome != null) {
            routerDiagram.addRectangle(new ColoredRectangle(outputHome, 20, homeIndex));
        } else {
            routerDiagram.addRectangle(new ColoredRectangle(Color.WHITE, 20, homeIndex));
        }
        if(outputNorth != null) {
            routerDiagram.addRectangle(new ColoredRectangle(outputNorth, 20, northIndex));
        } else {
            routerDiagram.addRectangle(new ColoredRectangle(Color.WHITE, 20, northIndex));
        }
        if(outputSouth != null) {
            routerDiagram.addRectangle(new ColoredRectangle(outputSouth, 20, southIndex));
        } else {
            routerDiagram.addRectangle(new ColoredRectangle(Color.WHITE, 20, southIndex));
        }
        if(outputEast != null) {
            routerDiagram.addRectangle(new ColoredRectangle(outputEast, 20, eastIndex));
        } else {
            routerDiagram.addRectangle(new ColoredRectangle(Color.WHITE, 20, eastIndex));
        }
        if(outputWest != null) {
            routerDiagram.addRectangle(new ColoredRectangle(outputWest, 20, westIndex));
        } else {
            routerDiagram.addRectangle(new ColoredRectangle(Color.WHITE, 20, westIndex));
        }

        if (SwitchTraversal == null) {
            routerDiagram.addRectangle(new ColoredRectangle(Color.WHITE, 19, southIndex));
        }

        if (SwitchAllocator == null) {
            routerDiagram.addRectangle(new ColoredRectangle(Color.WHITE, 18, southIndex));
        }

        if (VCAllocator == null) {
            routerDiagram.addRectangle(new ColoredRectangle(Color.WHITE, 17, southIndex));
        }

        if (RouteComputation == null) {
            routerDiagram.addRectangle(new ColoredRectangle(Color.WHITE, 16, southIndex));
        }

        if(channelHome != null) {
            for (int i = 0; i < 16 - channelHome.size(); i++){
                routerDiagram.addRectangle(new ColoredRectangle(Color.WHITE, i, homeIndex));
            }
        }

        if(channelNorth != null) {
            for (int i = 0; i < 16 - channelNorth.size(); i++){
                routerDiagram.addRectangle(new ColoredRectangle(Color.WHITE, i, northIndex));
            }
        }

        if(channelSouth != null) {
            for (int i = 0; i < 16 - channelSouth.size(); i++){
                routerDiagram.addRectangle(new ColoredRectangle(Color.WHITE, i, southIndex));
            }
        }

        if(channelEast != null) {
            for (int i = 0; i < 16 - channelEast.size(); i++){
                routerDiagram.addRectangle(new ColoredRectangle(Color.WHITE, i, eastIndex));
            }
        }

        if(channelWest != null) {
            for (int i = 0; i < 16 - channelWest.size(); i++){
                routerDiagram.addRectangle(new ColoredRectangle(Color.WHITE, i, westIndex));
            }
        }
    }

    /**
     * Goes through a list and paints rectangles at the spots that have flits in them
     * @param list      The list that's being checked
     * @param channel   The row that the rectangle will be painted on
     */
    private void createRectanglesFromFlitList(LinkedList<Flit> list, int channel) {
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                Flit flit = list.get(i);
                routerDiagram.addRectangle(new ColoredRectangle(flit, 15 - i, channel));
            }
        }
    }

    /**
     * Checks each stage of the pipeline and paints rectangles on the topology if it's not null
     * @param flit
     * @param stage
     */
    private void createRectanglesFromPipeline(Flit flit, int stage) {
        if (flit != null) {
            if (stage == 16) {
                routerDiagram.addRectangle(new ColoredRectangle(flit, 16, routerNumber));
            } else if (stage == 17) {
                routerDiagram.addRectangle(new ColoredRectangle(flit, 17, routerNumber));
            } else if (stage == 18) {
                routerDiagram.addRectangle(new ColoredRectangle(flit, 18, routerNumber));
            } else if (stage == 19) {
                routerDiagram.addRectangle(new ColoredRectangle(flit, 19, routerNumber));
            }
        }
    }

    /**
     * Adds a packet to the end of the South list (exclusively used by Bus networks)
     * @param inputPacket   The flit to be added
     */
    public void addPacket(Flit inputPacket) {
        if(channelSouth != null && channelSouth.size() < 16){
            channelSouth.addLast(inputPacket);
        }
    }

    /**
     * Inputs a packet into the router, adding it to the correct channel
     *
     * @param inputPacket The array of flits (packet) that's being input
     * @param source      The source of the packet, 999 = home, 1 = north, 2 = south, 3 = east, 4 = west
     */
    public void inputPacket(Flit[] inputPacket, int source) {

        if (inputPacket.length == 1) {  // If it's a single Flit packet, add the flit to the correct channel
            if (source == 999)  // If the source of the packet is from the home node
            {
                channelHome.addLast(inputPacket[0]);  // Add the packet to the list of packets from the home node
            } else if (source == routerLocation)  // If the source of the packet is also from the home node
            {
                channelHome.addLast(inputPacket[0]);
            } else if (source == routerNorth)  // If the source of the packet is from the north
            {
                channelNorth.addLast(inputPacket[0]);
            } else if (source == routerSouth)  // If the source of the packet is from the south
            {
                channelSouth.addLast(inputPacket[0]);
            } else if (source == routerEast)  // If the source of the packet is from the east
            {
                channelEast.addLast(inputPacket[0]);
            } else if (source == routerWest)  // If the source of the packet is from the west
            {
                channelWest.addLast(inputPacket[0]);
            }
        } else if (inputPacket.length == 4) {
            if (source == 999)  // If the source of the packet is from the home node
            {
                channelHome.addLast(inputPacket[0]);  // Add the packet to the list of packets from the home node
                channelHome.addLast(inputPacket[1]);
                channelHome.addLast(inputPacket[2]);
                channelHome.addLast(inputPacket[3]);
            }
            if (source == routerLocation)  // If the source of the packet is also from the home node
            {
                channelHome.addLast(inputPacket[0]);  // Add the packet to the list of packets from the home node
                channelHome.addLast(inputPacket[1]);
                channelHome.addLast(inputPacket[2]);
                channelHome.addLast(inputPacket[3]);
            } else if (source == routerNorth)  // If the source of the packet is from the north
            {
                channelNorth.addLast(inputPacket[0]);
                channelNorth.addLast(inputPacket[1]);
                channelNorth.addLast(inputPacket[2]);
                channelNorth.addLast(inputPacket[3]);
            } else if (source == routerSouth)  // If the source of the packet is from the south
            {
                channelSouth.addLast(inputPacket[0]);
                channelSouth.addLast(inputPacket[1]);
                channelSouth.addLast(inputPacket[2]);
                channelSouth.addLast(inputPacket[3]);
            } else if (source == routerEast)  // If the source of the packet is from the east
            {
                channelEast.addLast(inputPacket[0]);
                channelEast.addLast(inputPacket[1]);
                channelEast.addLast(inputPacket[2]);
                channelEast.addLast(inputPacket[3]);
            } else if (source == routerWest)  // If the source of the packet is from the west
            {
                channelWest.addLast(inputPacket[0]);
                channelWest.addLast(inputPacket[1]);
                channelWest.addLast(inputPacket[2]);
                channelWest.addLast(inputPacket[3]);
            }
        }
    }

    /**
     * Sends flits from one pipeline stage to the other
     * @param inputPipelineStages   The number of stages in the pipeline
     */
    public void pipeline(int inputPipelineStages) {
        if (inputPipelineStages == 4) {
            if (SwitchTraversal != null) {
                routeComputation(SwitchTraversal);
                SwitchTraversal = null;
            }

            if (SwitchAllocator != null) {
                SwitchTraversal = SwitchAllocator;
                SwitchAllocator = null;
            }

            if (VCAllocator != null) {
                SwitchAllocator = VCAllocator;
                VCAllocator = null;
            }

            if (RouteComputation != null) {
                VCAllocator = RouteComputation;
                RouteComputation = null;
            }

            if ((nextRouter == "home") && (channelHome.peekFirst() != null) && (channelHome != null)) {
                RouteComputation = channelHome.getFirst();

                setNextChannel(nextRouter, channelHome.getFirst().getIndex());

                channelHome.removeFirst();

            } else if ((nextRouter == "north") && (channelNorth.peekFirst() != null) && (channelNorth != null)) {
                RouteComputation = channelNorth.getFirst();

                setNextChannel(nextRouter, channelNorth.getFirst().getIndex());

                channelNorth.removeFirst();

            } else if ((nextRouter == "south") && (channelSouth.peekFirst() != null) && (channelSouth != null)) {
                RouteComputation = channelSouth.getFirst();

                setNextChannel(nextRouter, channelSouth.getFirst().getIndex());

                channelSouth.removeFirst();

            } else if ((nextRouter == "east") && (channelEast.peekFirst() != null) && (channelEast != null)) {
                RouteComputation = channelEast.getFirst();

                setNextChannel(nextRouter, channelEast.getFirst().getIndex());

                channelEast.removeFirst();

            } else if ((nextRouter == "west") && (channelWest.peekFirst() != null) && (channelWest != null)) {
                RouteComputation = channelWest.getFirst();

                setNextChannel(nextRouter, channelWest.getFirst().getIndex());

                channelWest.removeFirst();
            } else {
                setNextChannel(nextRouter, 0);
            }
        } else if(inputPipelineStages == 3){
            if (SwitchTraversal != null) {
                routeComputation(SwitchTraversal);
                SwitchTraversal = null;
            }

            if (SwitchAllocator != null) {
                SwitchTraversal = SwitchAllocator;
                SwitchAllocator = null;
            }

            if (VCAllocator != null) {
                SwitchAllocator = VCAllocator;
                VCAllocator = null;
            }

            if ((nextRouter == "home") && (channelHome.peekFirst() != null) && (channelHome != null)) {
                VCAllocator = channelHome.getFirst();

                setNextChannel(nextRouter, channelHome.getFirst().getIndex());

                channelHome.removeFirst();

            } else if ((nextRouter == "north") && (channelNorth.peekFirst() != null) && (channelNorth != null)) {
                VCAllocator = channelNorth.getFirst();

                setNextChannel(nextRouter, channelNorth.getFirst().getIndex());

                channelNorth.removeFirst();

            } else if ((nextRouter == "south") && (channelSouth.peekFirst() != null) && (channelSouth != null)) {
                VCAllocator = channelSouth.getFirst();

                setNextChannel(nextRouter, channelSouth.getFirst().getIndex());

                channelSouth.removeFirst();

            } else if ((nextRouter == "east") && (channelEast.peekFirst() != null) && (channelEast != null)) {
                VCAllocator = channelEast.getFirst();

                setNextChannel(nextRouter, channelEast.getFirst().getIndex());

                channelEast.removeFirst();

            } else if ((nextRouter == "west") && (channelWest.peekFirst() != null) && (channelWest != null)) {
                VCAllocator = channelWest.getFirst();

                setNextChannel(nextRouter, channelWest.getFirst().getIndex());

                channelWest.removeFirst();
            } else {
                setNextChannel(nextRouter, 0);
            }
        } else if(inputPipelineStages == 2){
            if (SwitchTraversal != null) {
                routeComputation(SwitchTraversal);
                SwitchTraversal = null;
            }

            if (SwitchAllocator != null) {
                SwitchTraversal = SwitchAllocator;
                SwitchAllocator = null;
            }

            if ((nextRouter == "home") && (channelHome.peekFirst() != null) && (channelHome != null)) {
                SwitchAllocator = channelHome.getFirst();

                setNextChannel(nextRouter, channelHome.getFirst().getIndex());

                channelHome.removeFirst();

            } else if ((nextRouter == "north") && (channelNorth.peekFirst() != null) && (channelNorth != null)) {
                SwitchAllocator = channelNorth.getFirst();

                setNextChannel(nextRouter, channelNorth.getFirst().getIndex());

                channelNorth.removeFirst();

            } else if ((nextRouter == "south") && (channelSouth.peekFirst() != null) && (channelSouth != null)) {
                SwitchAllocator = channelSouth.getFirst();

                setNextChannel(nextRouter, channelSouth.getFirst().getIndex());

                channelSouth.removeFirst();

            } else if ((nextRouter == "east") && (channelEast.peekFirst() != null) && (channelEast != null)) {
                SwitchAllocator = channelEast.getFirst();

                setNextChannel(nextRouter, channelEast.getFirst().getIndex());

                channelEast.removeFirst();

            } else if ((nextRouter == "west") && (channelWest.peekFirst() != null) && (channelWest != null)) {
                SwitchAllocator = channelWest.getFirst();

                setNextChannel(nextRouter, channelWest.getFirst().getIndex());

                channelWest.removeFirst();
            } else {
                setNextChannel(nextRouter, 0);
            }
        } else if(inputPipelineStages == 1){
            if (SwitchTraversal != null) {
                routeComputation(SwitchTraversal);

                SwitchTraversal = null;
            }

            if ((nextRouter == "home") && (channelHome.peekFirst() != null) && (channelHome != null)) {
                SwitchTraversal = channelHome.getFirst();

                setNextChannel(nextRouter, channelHome.getFirst().getIndex());

                channelHome.removeFirst();

            } else if ((nextRouter == "north") && (channelNorth.peekFirst() != null) && (channelNorth != null)) {
                SwitchTraversal = channelNorth.getFirst();

                setNextChannel(nextRouter, channelNorth.getFirst().getIndex());

                channelNorth.removeFirst();

            } else if ((nextRouter == "south") && (channelSouth.peekFirst() != null) && (channelSouth != null)) {
                SwitchTraversal = channelSouth.getFirst();

                setNextChannel(nextRouter, channelSouth.getFirst().getIndex());

                channelSouth.removeFirst();

            } else if ((nextRouter == "east") && (channelEast.peekFirst() != null) && (channelEast != null)) {
                SwitchTraversal = channelEast.getFirst();

                setNextChannel(nextRouter, channelEast.getFirst().getIndex());

                channelEast.removeFirst();

            } else if ((nextRouter == "west") && (channelWest.peekFirst() != null) && (channelWest != null)) {
                SwitchTraversal = channelWest.getFirst();

                setNextChannel(nextRouter, channelWest.getFirst().getIndex());

                channelWest.removeFirst();
            } else {
                setNextChannel(nextRouter, 0);
            }
        } else if(inputPipelineStages == 0){
            if ((nextRouter == "home") && (channelHome.peekFirst() != null) && (channelHome != null)) {
                routeComputation(channelHome.getFirst());

                setNextChannel(nextRouter, channelHome.getFirst().getIndex());

                channelHome.removeFirst();

            } else if ((nextRouter == "north") && (channelNorth.peekFirst() != null) && (channelNorth != null)) {
                routeComputation(channelNorth.getFirst());

                setNextChannel(nextRouter, channelNorth.getFirst().getIndex());

                channelNorth.removeFirst();

            } else if ((nextRouter == "south") && (channelSouth.peekFirst() != null) && (channelSouth != null)) {
                routeComputation(channelSouth.getFirst());

                setNextChannel(nextRouter, channelSouth.getFirst().getIndex());

                channelSouth.removeFirst();

            } else if ((nextRouter == "east") && (channelEast.peekFirst() != null) && (channelEast != null)) {
                routeComputation(channelEast.getFirst());

                setNextChannel(nextRouter, channelEast.getFirst().getIndex());

                channelEast.removeFirst();

            } else if ((nextRouter == "west") && (channelWest.peekFirst() != null) && (channelWest != null)) {
                routeComputation(channelWest.getFirst());

                setNextChannel(nextRouter, channelWest.getFirst().getIndex());

                channelWest.removeFirst();
            } else {
                setNextChannel(nextRouter, 0);
            }
        }
    }




    /**
     * Returns a Jpanel containing a clickable circle with the number of the node in the center
     *
     * @return Jpanel   Clickable circle that opens a Router pipeline window
     */
    public JPanel drawCircle() {
        JLabel nodeNum = new JLabel(routerNumber + "");   // Creates a label for the circle

        circle.add(nodeNum);    // Adds the label to the circle

        circle.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

                try {
                    routerDiagram.setVisible(true);

                    desktopPane.add(routerDiagram);
                } catch(Exception a) {
                    System.out.println(a);
                }

            }
        });

        return circle;
    }

    public int getRouterNorth() {
        return routerNorth;
    }

    public int getRouterSouth() {
        return routerSouth;
    }

    public int getRouterEast() {
        return routerEast;
    }

    public int getRouterWest() {
        return routerWest;
    }

    public int getRouterNumber() {
        return routerNumber;
    }

    public int getRouterLocation() {
        return routerLocation;
    }

    private int locationToIndex(int inputLocation){
        int locationX = inputLocation / 10;
        int locationY = inputLocation % 10;
        return ((int)Math.sqrt(network.getNodes()) * locationX + locationY);
    }

    public LinkedList<Flit> getChannelHome() { return channelHome; }

    public LinkedList<Flit> getChannelNorth() { return channelNorth; }

    public LinkedList<Flit> getChannelSouth() { return channelSouth; }

    public LinkedList<Flit> getChannelEast() { return channelEast; }

    public LinkedList<Flit> getChannelWest() { return channelWest; }
}
