package main.java;

import javax.swing.*;
import java.awt.*;

interface Network
{
    JPanel drawTopology();

    void setNodes(int inputNodes);

    int getNodes();

    String getTopology();

//    void newCycle();
//
//    void nextCycle();
}
