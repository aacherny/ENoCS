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

        int nodesSqrt = (int) Math.sqrt(nodes);

        if(topology.equals("mesh"))
        {
            panelYContainer.setLayout(new GridLayout(0, nodesSqrt + nodesSqrt - 1));

            JPanel panelXContainer = new JPanel();
            panelXContainer.setLayout(new GridLayout(nodesSqrt + nodesSqrt - 1, 0));

            panelXContainer.add(new Circle());  // Adds the very top-left circle
            System.out.println("First circle made");

            for (int i = 0; i < nodesSqrt - 1; i++) {   // Adds on more line+circle combos to the same column

                panelXContainer.add(new vertLine());
                System.out.println("Line under first circle made");
                panelXContainer.add(new Circle());
                System.out.println("Circle under first circle made");

            }

            panelYContainer.add(panelXContainer);   // Adds that column of circle+vertLines as the leftmost column

            for (int i = 0; i < nodesSqrt - 1; i++) {

                panelXContainer = new JPanel();
                panelXContainer.setLayout(new GridLayout(nodesSqrt + nodesSqrt - 1, 0));

                panelXContainer.add(new horiLine());  // Adds the top-leftmost horizontal line

                for (int j = 0; j < nodesSqrt - 1; j++) {   // Adds on more horiline+blankspace combos to the same column

                    panelXContainer.add(new blankSpace());
                    panelXContainer.add(new horiLine());

                }

                panelYContainer.add(panelXContainer);



                panelXContainer = new JPanel();
                panelXContainer.setLayout(new GridLayout(nodesSqrt + nodesSqrt - 1, 0));

                panelXContainer.add(new Circle());  // Adds the very top-left circle

                for (int j = 0; j < nodesSqrt - 1; j++) {   // Adds on more line+circle combos to the same column

                    panelXContainer.add(new vertLine());
                    panelXContainer.add(new Circle());

                }

                panelYContainer.add(panelXContainer);





            }


        }else if (true/* other topologies */)
        {

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
