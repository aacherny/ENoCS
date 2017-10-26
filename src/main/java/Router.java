package main.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Router
{
    private int routerNumber;

    public Router(int nodeNumberInput)
    {
        routerNumber = nodeNumberInput;
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
        Circle circle = new Circle();
        JLabel nodeNum = new JLabel(routerNumber+"");

        circle.add(nodeNum);

        circle.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e){
                System.out.println("The node that was clicked is: " + routerNumber);
            }
        });

        return circle;
    }
}
