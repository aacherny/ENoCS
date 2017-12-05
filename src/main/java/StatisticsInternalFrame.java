package main.java;

import javax.swing.*;
import java.awt.*;

public class StatisticsInternalFrame extends JInternalFrame{

    public static int openFrameCount = 0;
    static final int xOffset = 50, yOffset = 50;
    private int avgNetworkLatCC = 0;
    private int avgNetworkLatHops = 0;
    private int networkBandwidth = 0;
    private int networkThroughput = 0;
    private int networkArea = 9;
    private int powerConsumption = 0;
    private int numFlitsDropped = 0;

    //A place for the statisticcs to go
    private JPanel statPanel = new JPanel();

    private JLabel avgNetworkLatCCLabel;
    private JLabel avgNetworkLatHopsLabel;
    private JLabel networkBandwidthLabel;
    private JLabel networkThroughputLabel;
    private JLabel networkAreaLabel;
    private JLabel powerConsumptionLabel;
    private JLabel numFlitsDroppedLabel;


    StatisticsInternalFrame(int openFrames){
        super(
                "Statistics",
                true,
                true,
                true,
                false
        );

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
