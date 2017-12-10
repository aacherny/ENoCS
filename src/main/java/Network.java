package main.java;

import javax.swing.*;
import java.awt.*;

interface Network
{
    JPanel drawTopology();

    void setNodes(int inputNodes);

    public void setPacketChance(double inputPacketChance);

    int getNodes();

    String getTopology();

    void nextCycle();

    void newCycle();

    void removeTextWindow();

    void setPipelineStages(int inputStages);

    int getPipelineStages();

    TextFrame getScrollingTextFrame();
}
