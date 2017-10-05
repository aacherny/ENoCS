package main.java;

import javax.swing.*;
import java.awt.*;

public class Network
{
    private String topology;
    protected int nodes;

    public Network(String inputTopology, int inputNodes)
    {
        // Default values
        topology = inputTopology;
        nodes = inputNodes;
    }

    public JPanel drawTopology() {

        JPanel panelYContainer = new JPanel();

        if(topology.equals("mesh"))
        {
            panelYContainer.setLayout(new GridLayout(0, (int) Math.sqrt(nodes)));

            for (int county = 0; county < Math.sqrt(nodes); county++) {
                JPanel panelXContainer = new JPanel();
                panelXContainer.setLayout(new GridLayout((int) Math.sqrt(nodes), 0));


                Circle circle = new Circle();
                Line line = new Line();

                panelXContainer.add(circle);

                for (int countx = 0; countx < (Math.sqrt(nodes) - 1); countx++) {
                    panelXContainer.add(line);

                    panelXContainer.add(circle);
                }
                panelYContainer.add(panelXContainer);
            }
        }else if (topology.equals("bus"))
        {
            //Make there be as many columns as half of the number of nodes and
            //space them out over two rows
            panelYContainer.setLayout(new GridLayout(0,(int) Math.ceil(nodes/2)));

            for (int county = 0; county < nodes/2; county++){
                JPanel panelXContainer = new JPanel();
                panelXContainer.setLayout(new GridLayout(2, 0));

                Circle circle = new Circle();
                Line line = new Line(25, 25,25,25);

                //panelXContainer.add(circle); //Is this necessary with the code below
                for (int countx = 0; countx < Math.ceil(nodes/2); countx++){
                    panelXContainer.add(line);
                    panelXContainer.add(circle);
                }
                panelYContainer.add(panelXContainer);
            }
        }else
        {
            // do nothing
        }

        return panelYContainer;
    }

    public void setTopology(String inputTopology)
    {
        topology = inputTopology;
    }

    public void setNodes(int inputNodes)
    {
        nodes = inputNodes;
    }

    public String getTopology()
    {
        return topology;
    }

    public int getNodes()
    {
        return nodes;
    }
}
