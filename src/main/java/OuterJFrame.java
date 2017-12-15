package main.java;


import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.print.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * Class for the outer window that holds other windows inside it
 *
 * @author Alex Cherny
 */

public class OuterJFrame {
    private JFrame outerFrame;      // Outer window
    private JPanel panel;           // Needed for toolbar
    private JDesktopPane desktop;   // Needed for Internal JFrames
    // The next line declares the frames that are going to be used to control the internal windows
    private TopologyInternalFrame topologyFrame;
    private PropertiesWindow propWindow;
    private JToolBar toolBar;
    private double packetChance = 1;
    private File saveFile = new File("");

    protected Network network;
    private JTextField multiCycleField;

    protected int cycleNumber;
    private int injectType;
    public int openFrameCount;

    public OuterJFrame() {
        outerFrame = new JFrame();
        desktop = new JDesktopPane();   // DesktopPane that will hold all of the windows
        desktop.setLayout(new FlowLayout());

        network = new Mesh(4, desktop, this);    // Creates a default network to work with

        panel = new JPanel(new BorderLayout());
        openFrameCount = 0;
        cycleNumber = 0;

        // General things like window title and size
        outerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        outerFrame.setTitle("ENoCS Simulator");
        outerFrame.setSize(1200, 800);
        outerFrame.setLocationRelativeTo(null);
        outerFrame.setLayout(new BorderLayout());

        outerFrame.add(desktop);
    }

    /**
     * Creates the JFrame that holds all of the windows inside it and makes it visible
     */
    public void createOuterJFrame() {
        // Creates the menu bar and adds it to the window
        JMenuBar menuBar = createMenuBar();
        outerFrame.setJMenuBar(menuBar);

        // Creates the toolbar and adds it to the window
        toolBar = createToolBar();
        //outerFrame.getContentPane().add(toolBar, BorderLayout.NORTH);
        outerFrame.add(toolBar, BorderLayout.PAGE_START);

        // Makes the window visible
        outerFrame.setVisible(true);
    }

    /**
     * Creates the menu bar in the outer window, adding dropdown menus like
     * 'File' and 'Edit'
     */
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();  // Menu bar within the window

        // Build the File menu
        JMenu menuEdit = new JMenu("Edit");
        menuBar.add(menuEdit);

        // 'Properties' button under 'Edit'
        JMenuItem menuItemProperties = new JMenuItem("Properties");
        menuEdit.add(menuItemProperties);
        menuItemProperties.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                if (propWindow != null) {    // Opens the window with all of the existing variables if it's been opened before
                    propWindow.createPropWindow();
                } else {
                    propWindow = new PropertiesWindow(desktop, network, getOuterJFrame());
                    propWindow.createPropWindow();
                }
            }
        });

        return menuBar;
    }

    /**
     * Creates the toolbar with the ability to start a new simulation and advance the
     * cycles in the current simulation.
     *
     * @return JToolBar
     */
    private JToolBar createToolBar() {
        JToolBar toolBar = new JToolBar();
        toolBar.setBounds(0, 0, 9999, 20);
        toolBar.setFloatable(false);

        JLabel cycleLabel = new JLabel("Cycle number: ");

        ImageIcon refreshImage = new ImageIcon(getClass().getResource("images/refresh.png"));
        JButton refresh = new JButton(refreshImage);
        refresh.setToolTipText("Restart");
        refresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cycleNumber = 0;
                cycleLabel.setText("Cycle number: " + cycleNumber);

                network.newCycle();
            }
        });
        toolBar.add(refresh);

        ImageIcon nextCycleImage = new ImageIcon(getClass().getResource("images/play.png"));
        JButton nextCycle = new JButton(nextCycleImage);
        nextCycle.setToolTipText("Next Cycle");
        nextCycle.addActionListener(e -> {
            if (cycleNumber == 0) {

            } else {

            }
            cycleNumber++;
            cycleLabel.setText("Cycle number: " + cycleNumber);

            network.nextCycle();
            desktop.validate();
            desktop.repaint();
        });
        toolBar.add(nextCycle);

        multiCycleField = new JTextField("5", 5);
        multiCycleField.setMaximumSize(new Dimension(48, 24));
        toolBar.add(multiCycleField);

        ImageIcon multiCycleImage = new ImageIcon(getClass().getResource("images/fastforward.png"));
        JButton multiCycle = new JButton(multiCycleImage);
        multiCycle.setToolTipText("Run Multiple Cycles");
        multiCycle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                for (int i = 0; i < Integer.parseInt(multiCycleField.getText()); i++) {
                    cycleNumber++;
                    cycleLabel.setText("Cycle number: " + cycleNumber);

                    network.nextCycle();
                }
            }
        });
        toolBar.add(multiCycle);

        toolBar.add(cycleLabel);

        return toolBar;
    }


    public void setPacketChance(double inputPacketChance) {
        packetChance = inputPacketChance / 100;
    }


    /**
     * Returns the desktop pane of the JPanel
     *
     * @return {JDesktopPane}
     */
    public JDesktopPane getDesktopPane() {
        return desktop;
    }

    public int getCycleNumber(){
        return cycleNumber;
    }

    public OuterJFrame getOuterJFrame() {
        return this;
    }

    public double getPacketChance() {
        return packetChance;
    }

    public void updateNetwork(Network inputNetwork) {
        network = inputNetwork;
    }

    public int getCyclePerStep(){
        return Integer.parseInt(multiCycleField.getText());
    }

    public void setCyclePerStep(int cycles){
        multiCycleField.setText(cycles+"");
    }

    public int getInjectType(){
        return injectType;
    }

    public void setInjectType(int inputInjectType){
        injectType = inputInjectType;
    }
}
