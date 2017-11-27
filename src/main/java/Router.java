package main.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

public class Router {
    private int routerNumber;
    private int routerLocation;

    private int routerNorth;
    private int routerSouth;
    private int routerEast;
    private int routerWest;

    private boolean frameActive;

    private LinkedList<Flit> channelHome;   // Channels to hold incoming flits
    private LinkedList<Flit> channelNorth;
    private LinkedList<Flit> channelSouth;
    private LinkedList<Flit> channelEast;
    private LinkedList<Flit> channelWest;

    private JDesktopPane desktopPane;
    private RouterDiagram routerDiagram;
    private Network network;

    Circle circle = new Circle();

    public Router(int inputNodeNumber, int inputLocation, int inputNorth, int inputSouth, int inputEast, int inputWest, JDesktopPane inputDesktopPane, Network inputNetwork) {
        routerNumber = inputNodeNumber;
        routerLocation = inputLocation;
        desktopPane = inputDesktopPane;
        frameActive = false;
        network = inputNetwork;

        routerNorth = inputNorth;
        routerSouth = inputSouth;
        routerEast = inputEast;
        routerWest = inputWest;

        channelHome = new LinkedList<Flit>();
        if(inputNorth != -1) {
            channelNorth = new LinkedList<Flit>();
        }
        if(inputSouth != -1) {
            channelSouth = new LinkedList<Flit>();
        }
        if(inputEast != -1) {
            channelEast = new LinkedList<Flit>();
        }
        if(inputWest != -1) {
            channelWest = new LinkedList<Flit>();
        }

        routerDiagram = new RouterDiagram(this);
        JPanel jpan = routerDiagram.addDiagram();
        routerDiagram.add(jpan);

        System.out.println("Router " + routerNumber + " created");
    }

    public void nextCycle() {
        System.out.println("Next cycle for router: " + routerNumber);

        routerDiagram.addRectangle(new ColoredRectangle(Color.RED, 20, 20));



        //routerDiagram.add(routerDiagram.addDiagram());
        //routerDiagram.addRectangle(new ColoredRectangle(Color.RED, 20, 20));

        routerDiagram.invalidate();
        routerDiagram.validate();
        routerDiagram.repaint();



    }

    public void newCycle() {
        System.out.print("Objects in Home channel are: ");
        for(int i = 0; i < channelHome.size(); i++) {
            System.out.print(channelHome.get(i).getIndex());
        }

        System.out.println("");

        System.out.print("Objects in North channel are: ");
        for(int i = 0; i < channelNorth.size(); i++) {
            System.out.print(channelNorth.get(i).getIndex());
        }

        System.out.println("");

        System.out.print("Objects in South channel are: ");
        for(int i = 0; i < channelSouth.size(); i++) {
            System.out.print(channelSouth.get(i).getIndex());
        }

        System.out.println("");

        System.out.print("Objects in East channel are: ");
        for(int i = 0; i < channelEast.size(); i++) {
            System.out.print(channelEast.get(i).getIndex());
        }

        System.out.println("");

        System.out.print("Objects in West channel are: ");
        for(int i = 0; i < channelWest.size(); i++) {
            System.out.print(channelWest.get(i).getIndex());
        }
    }

    /**
     * Inputs a packet into the router, adding it to the correct channel
     *
     * @param inputPacket The array of flits (packet) that's being input
     * @param source      The source of the packet, 0 = home, 1 = north, 2 = south, 3 = east, 4 = west
     */
    public void inputPacket(Flit[] inputPacket, int source) {
        if (inputPacket[0].getIndex() == 0) {  // If it's a single Flit packet, add the flit to the correct channel
            if (source == -1)  // If the source of the packet is from the home node
            {
                channelHome.addLast(inputPacket[0]);  // Add the packet to the list of packets from the home node
            } else if (source == routerLocation)  // If the source of the packet is also from the home node
            {
                channelHome.addLast(inputPacket[0]);
            }else if (source + 10 == routerLocation)  // If the source of the packet is from the north
            {
                channelNorth.addLast(inputPacket[0]);
            } else if (source - 10 == routerLocation)  // If the source of the packet is from the south
            {
                channelSouth.addLast(inputPacket[0]);
            } else if (source - 1 == routerLocation)  // If the source of the packet is from the east
            {
                channelEast.addLast(inputPacket[0]);
            } else if (source + 1 == routerLocation)  // If the source of the packet is from the west
            {
                channelWest.addLast(inputPacket[0]);
            }

        } else if (inputPacket[0].getIndex() == 1) {
            if (source == -1)  // If the source of the packet is from the home node
            {
                channelHome.addLast(inputPacket[0]);  // Add the packet to the list of packets from the home node
                channelHome.addLast(inputPacket[1]);
                channelHome.addLast(inputPacket[2]);
                channelHome.addLast(inputPacket[3]);

            } if (source == routerLocation)  // If the source of the packet is also from the home node
            {
                channelHome.addLast(inputPacket[0]);  // Add the packet to the list of packets from the home node
                channelHome.addLast(inputPacket[1]);
                channelHome.addLast(inputPacket[2]);
                channelHome.addLast(inputPacket[3]);
            }else if (source + 10 == routerLocation)  // If the source of the packet is from the north
            {
                channelNorth.addLast(inputPacket[0]);
                channelNorth.addLast(inputPacket[1]);
                channelNorth.addLast(inputPacket[2]);
                channelNorth.addLast(inputPacket[3]);
            } else if (source - 10 == routerLocation)  // If the source of the packet is from the south
            {
                channelSouth.addLast(inputPacket[0]);
                channelSouth.addLast(inputPacket[1]);
                channelSouth.addLast(inputPacket[2]);
                channelSouth.addLast(inputPacket[3]);
            } else if (source - 1 == routerLocation)  // If the source of the packet is from the east
            {
                channelEast.addLast(inputPacket[0]);
                channelEast.addLast(inputPacket[1]);
                channelEast.addLast(inputPacket[2]);
                channelEast.addLast(inputPacket[3]);
            } else if (source + 1 == routerLocation)  // If the source of the packet is from the west
            {
                channelWest.addLast(inputPacket[0]);
                channelWest.addLast(inputPacket[1]);
                channelWest.addLast(inputPacket[2]);
                channelWest.addLast(inputPacket[3]);
            }
        }
    }

    /**
     * Returns a Jpanel containing a circle with the number of the node in the center
     *
     * @return Jpanel
     */
    public JPanel drawCircle() {
        JLabel nodeNum = new JLabel(routerNumber + "");   // Creates a label for the circle

        circle.add(nodeNum);    // Adds the label to the circle

        circle.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

                routerDiagram.setVisible(true);

                desktopPane.add(routerDiagram);

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
}
