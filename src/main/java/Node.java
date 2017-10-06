package main.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Node
{
    private int nodeNumber;

    public Node(int nodeNumberInput)
    {
        nodeNumber = nodeNumberInput;

    }

    /**
     * Returns a Jpanel containing a circle with the number of the node in the center
     * @return Jpanel
     */
    public JPanel drawCircle()
    {
        Circle circle = new Circle();
        JLabel nodeNum = new JLabel(nodeNumber+"");

        circle.add(nodeNum);

        circle.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e){
                System.out.println("The node that was clicked is: " + nodeNumber);
            }
        });

        return circle;
    }
}
