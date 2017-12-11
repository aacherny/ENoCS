package main.java;

import javax.swing.*;
import java.awt.*;

public class StatisticsInternalFrame extends JInternalFrame{

    public static int openFrameCount = 0;
    static final int xOffset = 50, yOffset = 50;
    private int avgNetworkLatCC;
    private int avgNetworkLatHops;
    private int networkBandwidth;
    private int networkThroughput;
    private int networkArea;
    private int powerConsumption;
    private int numFlitsDropped;

    //A place for the statistics to go
    private JPanel statPanel = new JPanel();

    private JLabel avgNetworkLatCCLabel;
    private JLabel avgNetworkLatHopsLabel;
    private JLabel networkBandwidthLabel;
    private JLabel networkThroughputLabel;
    private JLabel networkAreaLabel;
    private JLabel powerConsumptionLabel;
    private JLabel numFlitsDroppedLabel;

    private PropertiesWindow propertiesWindow;
    private OuterJFrame outerFrame;

    StatisticsInternalFrame(int openFrames, OuterJFrame outer, PropertiesWindow prop){
        super(
                "Statistics",
                true,
                true,
                true,
                false
        );

        outerFrame = outer;
        propertiesWindow = prop;

        avgNetworkLatCC = 0;
        avgNetworkLatHops = 0;
        //Below sets the network bandwidth
        switch (propertiesWindow.topology){
            case "Bus":
                networkBandwidth = 1;
                break;
            case "Mesh":
                switch (propertiesWindow.nodes){
                    case 4:
                        networkBandwidth = 4;
                        break;
                    case 9:
                        networkBandwidth = 12;
                        break;
                    case 16:
                        networkBandwidth = 24;
                        break;
                }
            case "Torus":
                switch (propertiesWindow.nodes){
                    case 4:
                        networkBandwidth = 4;
                        break;
                    case 9:
                        networkBandwidth = 18;
                        break;
                    case 16:
                        networkBandwidth = 32;
                        break;
                }
        }
        //This sets the network throughput statistic
//        networkThroughput = outerFrame.getCycleNumber()/(numFlitsExited*networkBandwidth);

        networkArea = 0;
        powerConsumption = 0;
        numFlitsDropped = 0;

        openFrameCount = openFrames;
        setSize(500, 350);
        setLocation(xOffset*openFrameCount, yOffset*openFrameCount);
        statPanel.setLayout(new BoxLayout(statPanel, BoxLayout.Y_AXIS));
        avgNetworkLatCCLabel = new JLabel("Average Network Latency in ClockCycles: " + avgNetworkLatCC);
        avgNetworkLatHopsLabel = new JLabel("Average Network Latency in Hops: " + avgNetworkLatHops);
        networkBandwidthLabel = new JLabel("Network Bandwidth: " + networkBandwidth);
        networkThroughputLabel = new JLabel("Network Throughput: " + networkThroughput);
        networkAreaLabel = new JLabel("Area of Network: " + networkArea);
        powerConsumptionLabel = new JLabel("Power Consumption: " + powerConsumption);
        numFlitsDroppedLabel = new JLabel("Number of Flits Dropped: " + numFlitsDropped);
        statPanel.add(avgNetworkLatCCLabel);
        statPanel.add(avgNetworkLatHopsLabel);
        statPanel.add(networkBandwidthLabel);
        statPanel.add(networkAreaLabel);
        statPanel.add(networkAreaLabel);
        statPanel.add(powerConsumptionLabel);
        statPanel.add(numFlitsDroppedLabel);
        add(statPanel);
        setVisible(true);
    }

    public void resetLocationCascade(int openFrameCount){
        setLocation(xOffset*openFrameCount, yOffset*openFrameCount);
    }

    public void resetLocationTile(int winPosX, int winPosY){
        setLocation(winPosX, winPosY);
    }

    public int getAvgNetworkLatCC() {
        return avgNetworkLatCC;
    }

    public void setAvgNetworkLatCC(int avgNetworkLatCC) {
        this.avgNetworkLatCC = avgNetworkLatCC;
    }

    public int getAvgNetworkLatHops() {
        return avgNetworkLatHops;
    }

    public void setAvgNetworkLatHops(int avgNetworkLatHops) {
        this.avgNetworkLatHops = avgNetworkLatHops;
    }

    public int getNetworkBandwidth() {
        return networkBandwidth;
    }

    public void setNetworkBandwidth(int networkBandwidth) {
        this.networkBandwidth = networkBandwidth;
    }

    public int getNetworkThroughput() {
        return networkThroughput;
    }

    public void setNetworkThroughput(int networkThroughput) {
        this.networkThroughput = networkThroughput;
    }

    public int getNetworkArea() {
        return networkArea;
    }

    public void setNetworkArea(int networkArea) {
        this.networkArea = networkArea;
    }

    public int getPowerConsumption() {
        return powerConsumption;
    }

    public void setPowerConsumption(int powerConsumption) {
        this.powerConsumption = powerConsumption;
    }

    public int getNumFlitsDropped() {
        return numFlitsDropped;
    }

    public void setNumFlitsDropped(int numFlitsDropped) {
        this.numFlitsDropped = numFlitsDropped;
    }
}
