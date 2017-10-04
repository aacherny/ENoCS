package main.java;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;
import javafx.scene.*;
import javafx.stage.Stage;

public class TopologyInternalFrame extends JInternalFrame
{
    static int openFrameCount = 0;
    static final int xOffset = 30, yOffset = 30;
    int nodes = 4;
    String topology = "mesh";
    private int nodex = 25;
    private int nodey = 25;
    List<Node> nodeList = new LinkedList<>();
    Group topologyGroup = new Group();
    Scene topologyScene = new Scene(topologyGroup, 500, 350);


    public TopologyInternalFrame()
    {
        super("Network Topology" + (++openFrameCount),
                true, //resizable
                true, //closable
                true, //maximizable
                true);//iconifiable

        setSize(500, 350);

        //Set the window's location.
        setLocation(xOffset*openFrameCount, yOffset*openFrameCount);

        //TODO: Figure out why you can't have both a line and a circle
        if (topology.toLowerCase() == "mesh"){
            for (int i = 0; i < nodes; i++){

                nodeList.add(new Node(i, nodex, nodey));
                nodex = nodex + 50;
                if (i == Math.ceil(Math.sqrt(nodes))){
                    nodex = 25;
                    nodey = nodey + 50;
                }

            }
        }

        setVisible(true);
    }
}
