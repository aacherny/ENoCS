package main.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Router
{
    private int routerNumber;

    private int routerNorth;
    private int routerSouth;
    private int routerEast;
    private int routerWest;

    private Flit[] channelNorth;    // Channels to hold incoming flits
    private Flit[] channelSouth;
    private Flit[] channelEast;
    private Flit[] channelWest;
    private Flit[] channelHome;

    private JDesktopPane desktopPane;
    //RouterDiagram routerDiagram;
    Circle circle = new Circle();

    public Router(int inputNodeNumber, int inputNorth, int inputSouth, int inputEast, int inputWest, JDesktopPane inputDesktopPane)
    {
        routerNumber = inputNodeNumber;
        desktopPane = inputDesktopPane;

        routerNorth = inputNorth;
        routerSouth = inputSouth;
        routerEast = inputEast;
        routerWest = inputWest;
    }

    public void nextCycle()
    {
        System.out.println("Next cycle for router: " + routerNumber);
    }

    public void inputPacket(Flit[] inputFlit, int source)
    {

    }

    /**
     * Returns a Jpanel containing a circle with the number of the node in the center
     * @return Jpanel
     */
    public JPanel drawCircle()
    {
        JLabel nodeNum = new JLabel(routerNumber+"");   // Creates a label for the circle
        Router currentRouter = this; // Creates a router object to pass

        circle.add(nodeNum);    // Adds the label to the circle

        circle.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e){
                System.out.println("The node that was clicked is: " + routerNumber);

                RouterDiagram routerDiagram = new RouterDiagram(currentRouter);
                desktopPane.add(routerDiagram);
            }
        });

        return circle;
    }

    public int getRouterNorth()
    {
        return routerNorth;
    }

    public int getRouterSouth()
    {
        return routerSouth;
    }

    public int getRouterEast()
    {
        return routerEast;
    }

    public int getRouterWest()
    {
        return routerWest;
    }

    public int getRouterNumber()
    {
        return routerNumber;
    }
}
