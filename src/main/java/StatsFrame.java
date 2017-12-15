package main.java;

import javax.swing.*;
import java.awt.*;

public class StatsFrame extends JInternalFrame {

    private double avgLatency = 0;
    private int flitsCreated = 0;
    private int flitsRecieved = 0;

    private JLabel avgLatencyNum;
    private JLabel flitsCreatedNum;
    private JLabel flitsRecievedNum;



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

    private void initTextWindow(){
        JPanel avgLatencyContainer = new JPanel(new GridLayout(0, 2));
        JLabel avgLatencyLabel = new JLabel("Average latency per packet:");
        JLabel avgLatencyNum =  new JLabel();
        avgLatencyContainer.add(avgLatencyLabel);
        avgLatencyContainer.add(avgLatencyNum);
        getContentPane().add(avgLatencyContainer);

        JPanel flitsCreatedContainer = new JPanel(new GridLayout(0, 2));
        JLabel flitsCreatedLabel = new JLabel("Flits generated:");
        flitsCreatedNum =  new JLabel();
        flitsCreatedContainer.add(flitsCreatedLabel);
        flitsCreatedContainer.add(flitsCreatedNum);
        getContentPane().add(flitsCreatedContainer);

        JPanel flitsRecievedContainer = new JPanel(new GridLayout(0, 2));
        JLabel flitsRecievedLabel = new JLabel("Flits received:");
        flitsRecievedNum =  new JLabel();
        flitsRecievedContainer.add(flitsRecievedLabel);
        flitsRecievedContainer.add(flitsRecievedNum);
        getContentPane().add(flitsRecievedContainer);
    }

    public void addFlitCreated(int flits){
        flitsCreated = flitsCreated + flits;
        flitsCreatedNum.setText(flitsCreated+"");
    }

    public void addFlitReceived(int flits){
        flitsRecieved = flitsRecieved + flits;
        flitsRecievedNum.setText(flitsRecieved+"");
    }

    private void updateLatency(){
    }
}