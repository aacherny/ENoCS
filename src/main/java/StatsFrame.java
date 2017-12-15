package main.java;

import javax.swing.*;
import java.awt.*;

/**
 * Window that displays different statistics about the simulation
 */
public class StatsFrame extends JInternalFrame {

    private int packetsCreated = 0;
    private int flitsCreated = 0;
    private int packetsRecieved = 0;
    private int flitsRecieved = 0;

    private double avgLatency = 0;
    private double totalLatency = 0;
    private int totalSamples = 0;

    private JLabel packetsCreatedNum;
    private JLabel flitsCreatedNum;
    private JLabel packetsRecievedNum;
    private JLabel flitsRecievedNum;
    private JLabel avgLatencyNum;

    /**
     * Creates the object
     */
    public StatsFrame()
    {
        super("Statistics",
                true, //resizable
                true, //closable
                true, //maximizable
                false);//iconifiable

        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        setSize(500, 500);
        setLayout(new GridLayout(5, 0));

        initTextWindow();

        pack();
        setVisible(true);
    }

    /**
     * Initializes the statistics window
     */
    private void initTextWindow(){

        JPanel packetsCreatedContainer = new JPanel(new GridLayout(0, 2));
        JLabel packetsCreatedLabel = new JLabel("Packets generated:");
        packetsCreatedNum =  new JLabel();
        packetsCreatedContainer.add(packetsCreatedLabel);
        packetsCreatedContainer.add(packetsCreatedNum);
        getContentPane().add(packetsCreatedContainer);

        JPanel flitsCreatedContainer = new JPanel(new GridLayout(0, 2));
        JLabel flitsCreatedLabel = new JLabel("Flits generated:");
        flitsCreatedNum =  new JLabel();
        flitsCreatedContainer.add(flitsCreatedLabel);
        flitsCreatedContainer.add(flitsCreatedNum);
        getContentPane().add(flitsCreatedContainer);

        JPanel packetsRecievedContainer = new JPanel(new GridLayout(0, 2));
        JLabel packetsRecievedLabel = new JLabel("Packets received:");
        packetsRecievedNum =  new JLabel();
        packetsRecievedContainer.add(packetsRecievedLabel);
        packetsRecievedContainer.add(packetsRecievedNum);
        getContentPane().add(packetsRecievedContainer);

        JPanel flitsRecievedContainer = new JPanel(new GridLayout(0, 2));
        JLabel flitsRecievedLabel = new JLabel("Flits received:");
        flitsRecievedNum =  new JLabel();
        flitsRecievedContainer.add(flitsRecievedLabel);
        flitsRecievedContainer.add(flitsRecievedNum);
        getContentPane().add(flitsRecievedContainer);

        JPanel avgLatencyContainer = new JPanel(new GridLayout(0, 2));
        JLabel avgLatencyLabel = new JLabel("Average latency per packet:  ");
        avgLatencyNum =  new JLabel();
        avgLatencyContainer.add(avgLatencyLabel);
        avgLatencyContainer.add(avgLatencyNum);
        getContentPane().add(avgLatencyContainer);
    }

    /**
     * Resets the the window's values
     */
    public void reset(){
        packetsCreated = 0;
        flitsCreated = 0;
        packetsRecieved = 0;
        flitsRecieved = 0;

        avgLatency = 0;
        totalLatency = 0;
        totalSamples = 0;

        packetsCreatedNum.setText(packetsCreated+"");
        flitsCreatedNum.setText(flitsCreated+"");
        packetsRecievedNum.setText(packetsRecieved+"");
        flitsRecievedNum.setText(flitsRecieved+"");
        avgLatencyNum.setText(avgLatency+"");
    }

    /** Increments the number of packets that have been created*/
    public void addPacketCreated(){
        packetsCreated = packetsCreated + 1;
        packetsCreatedNum.setText(packetsCreated + " packets");
    }

    /** Increases the number of flits that have been created*/
    public void addFlitCreated(int flits){
        flitsCreated = flitsCreated + flits;
        flitsCreatedNum.setText(flitsCreated + " flits");
    }

    /** Increments the number of packets that have been received*/
    public void addPacketReceived(){
        packetsRecieved = packetsRecieved + 1;
        packetsRecievedNum.setText(packetsRecieved + " packets");
    }

    /** Increases the number of flits that have been received*/
    public void addFlitReceived(int flits){
        flitsRecieved = flitsRecieved + flits;
        flitsRecievedNum.setText(flitsRecieved + " flits");
    }

    /** Updates the average latency of packets as they travel through the the network*/
    public void updateLatency(int inputCycle, Flit inputFlit){
        int latency = inputCycle - inputFlit.getCycleCreated();
        totalLatency = totalLatency + latency;
        totalSamples++;

        avgLatency = totalLatency / totalSamples;
        avgLatencyNum.setText(String.format("%.2f", avgLatency) + " cycles");
    }
}