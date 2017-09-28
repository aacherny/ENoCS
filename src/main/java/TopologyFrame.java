package main.java;


import javax.swing.*;

public class TopologyFrame {

    // The below two lines create a closable window inside the outer window
    private JInternalFrame topologyFrame = new JInternalFrame(
            "Network Topology", true, true, true, true);

    public TopologyFrame(){
        topologyFrame.setDefaultCloseOperation(JInternalFrame.EXIT_ON_CLOSE);
        topologyFrame.setSize(250, 175);
        topologyFrame.setLocation(0, 0);
        topologyFrame.moveToFront();


        topologyFrame.setVisible(true);

    }
}
