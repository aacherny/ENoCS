package main.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Router
{
    private int routerNumber;
    private Flit[] channelNorth;    // Channels to hold incoming flits
    private Flit[] channelSouth;
    private Flit[] channelEast;
    private Flit[] channelWest;
    private Flit[] channelHome;

    JDesktopPane desktopPane;
    Circle circle = new Circle();

    public Router(int nodeNumberInput, JDesktopPane inputDesktopPane)
    {
        routerNumber = nodeNumberInput;
        desktopPane = inputDesktopPane;
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

        circle.add(nodeNum);    // Adds the label to the circle

        circle.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e){
                System.out.println("The node that was clicked is: " + routerNumber);

                RouterDiagram routerDiagram = new RouterDiagram(routerNumber);
                desktopPane.add(routerDiagram);
            }
        });

        return circle;
    }
}
