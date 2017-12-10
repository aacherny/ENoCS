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
 */

public class OuterJFrame {
    private JFrame outerFrame;      // Outer window
    private JPanel panel;           // Needed for toolbar
    private JDesktopPane desktop;   // Needed for Internal JFrames
    // The next line declares the frames that are going to be used to control the internal windows
    private TopologyInternalFrame topologyFrame;
    private FlowControlInternalFrame flowControlFrame;
    private StatisticsInternalFrame statisticFrame;
    private PropertiesWindow propWindow;
    private JToolBar toolBar;
    private double packetChance = 1;
    private File saveFile = new File("");

    protected Network network;
    private JTextField multiCycleField;

    protected int cycleNumber;
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

        // TODO add icons to the JMenuItems
        // Build the File menu
        JMenu menuFile = new JMenu("File");
        menuBar.add(menuFile);

        // 'New' button under 'File'
        JMenuItem menuItemNew = new JMenuItem("New", new ImageIcon(getClass().getResource("images/new.png")));
        menuItemNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        menuItemNew.addActionListener(e -> {
            //TODO: Pass something meaningful instead of empty new objects
            topologyFrame = new TopologyInternalFrame(new JPanel());//, openFrameCount++);
            flowControlFrame = new FlowControlInternalFrame(new JTextArea(), openFrameCount++);
            statisticFrame = new StatisticsInternalFrame(openFrameCount++);
            desktop.add(topologyFrame);
            desktop.add(flowControlFrame);
            desktop.add(statisticFrame);
        });
        menuFile.add(menuItemNew);

        // 'Open' button under 'File'
        JMenuItem menuItemOpen = new JMenuItem("Open", new ImageIcon(getClass().getResource("images/open.png")));
        menuItemOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        menuFile.add(menuItemOpen);

        menuFile.addSeparator();

        // 'Save' button under 'File'
        JMenuItem menuItemSave = new JMenuItem("Save", new ImageIcon(getClass().getResource("images/save.png")));
        menuItemSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        menuItemSave.addActionListener(e -> {
            //TODO: figure out why this writes everything on one line
            //TODO: Make the state of the entire simulator recoverable from log
            try {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Save File");
                FileWriter fstream;
                System.out.println(saveFile);
                if (saveFile.getName() != "") {
                    fstream = new FileWriter(saveFile);
                    BufferedWriter out = new BufferedWriter(fstream);
                    out.write("QUICK SAVE TEST");
                    out.close();
                } else {
                    int userSelection = fileChooser.showSaveDialog(outerFrame);
                    if (userSelection == JFileChooser.APPROVE_OPTION) {
                        saveFile = fileChooser.getSelectedFile();
                        fstream = new FileWriter(saveFile);
                        BufferedWriter out = new BufferedWriter(fstream);
                        //UNCOMMENT THE LINE BELOW WHEN FLOW CONTROL TEXT EXISTS
                        //out.write(flowControlText.getText())
                        out.write("SAVE AS TEST");
                        out.close();
                    }
                }
            } catch (Exception saveException) {
                System.err.println("Error: " + saveException.getMessage());
            }
        });
        menuFile.add(menuItemSave);

        // 'Save As' button under 'File'
        JMenuItem menuItemSaveAs = new JMenuItem("Save As");
        menuItemSaveAs.addActionListener(e -> {
            //TODO: figure out why this writes everything on one line
            //TODO: Make the state of the entire simulator recoverable from log
            try {
                JFileChooser fileChooser = new JFileChooser();
                int userSelection = fileChooser.showSaveDialog(outerFrame);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    saveFile = fileChooser.getSelectedFile();
                    FileWriter fstream = new FileWriter(saveFile);
                    BufferedWriter out = new BufferedWriter(fstream);
                    //UNCOMMENT THE LINE BELOW WHEN FLOW CONTROL TEXT EXISTS
                    //out.write(flowControlText.getText())
                    out.write("SAVE AS TEST");
                    out.close();
                }
            } catch (Exception saveException) {
                System.err.println("Error: " + saveException.getMessage());
            }
        });
        menuFile.add(menuItemSaveAs);

        menuFile.addSeparator();

        // 'Exit' button under 'File'
        JMenuItem menuItemExit = new JMenuItem("Exit");
        menuItemExit.addActionListener(e -> {
            topologyFrame.dispose();
            flowControlFrame.dispose();
            statisticFrame.dispose();
        });
        menuFile.add(menuItemExit);


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

        ImageIcon newFileImage = new ImageIcon(getClass().getResource("images/new.png"));
        JButton newFile = new JButton(newFileImage);
        newFile.setToolTipText("New");
        newFile.addActionListener(e -> {
            //This is the start for the cascade
            //TODO: find a way to decrement openFrameCount when an Internal frame is closed or find a workaround
            topologyFrame = new TopologyInternalFrame(new JPanel());//, openFrameCount++);
            flowControlFrame = new FlowControlInternalFrame(new JTextArea(), openFrameCount++);
            statisticFrame = new StatisticsInternalFrame(openFrameCount++);
            desktop.add(topologyFrame);
            desktop.add(flowControlFrame);
            desktop.add(statisticFrame);
        });
        toolBar.add(newFile);

        //TODO: Comeback to this and fix it
        ImageIcon openFileImage = new ImageIcon(getClass().getResource("images/open.png"));
        JButton openFileButton = new JButton(openFileImage);
        openFileButton.setToolTipText("Open");
        openFileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Open File");
            int userSelection = fileChooser.showOpenDialog(outerFrame);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                saveFile = fileChooser.getSelectedFile();
            }
        });
        toolBar.add(openFileButton);

        ImageIcon saveFileImage = new ImageIcon(getClass().getResource("images/save.png"));
        JButton saveFileButton = new JButton(saveFileImage);
        saveFileButton.setToolTipText("Save");
        saveFileButton.addActionListener(e -> {
            //TODO: figure out why this writes everything on one line
            //TODO: Make the state of the entire simulator recoverable from log
            try {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Save File");
                FileWriter fstream;
                System.out.println(saveFile);
                if (saveFile.getName() != "") {
                    fstream = new FileWriter(saveFile);
                    BufferedWriter out = new BufferedWriter(fstream);
                    out.write("QUICK SAVE TEST");
                    out.close();
                } else {
                    int userSelection = fileChooser.showSaveDialog(outerFrame);
                    if (userSelection == JFileChooser.APPROVE_OPTION) {
                        saveFile = fileChooser.getSelectedFile();
                        fstream = new FileWriter(saveFile);
                        BufferedWriter out = new BufferedWriter(fstream);
                        //UNCOMMENT THE LINE BELOW WHEN FLOW CONTROL TEXT EXISTS
                        //out.write(flowControlText.getText())
                        out.write("SAVE AS TEST");
                        out.close();
                    }
                }
            } catch (Exception saveException) {
                System.err.println("Error: " + saveException.getMessage());
            }
        });
        toolBar.add(saveFileButton);

        //TODO: Figure out if the content windows can be initialized at the start of the program
        ImageIcon printImage = new ImageIcon(getClass().getResource("images/print.png"));
        JButton printButton = new JButton(printImage);
        printButton.setToolTipText("Print");
        printButton.addActionListener(e -> {
            //This next line creates a new j ob for the printer
            PrinterJob printerJob = PrinterJob.getPrinterJob();
            //Below is where you define what you are printing a nd what is os going to look like
            printerJob.setPrintable((graphics, pageFormat, pageIndex) -> {
                if (pageIndex != 0) {
                    return Printable.NO_SUCH_PAGE;
                }
                Graphics2D graphics2D = (Graphics2D) graphics;
                graphics2D.setFont(new Font("Serif", Font.PLAIN, 12));
                graphics2D.setPaint(Color.BLACK);
                //This is where you pass it what is going to be printed
                //TODO: fix the statistic frame saving
//                graphics2D.drawString(flowControlFrame.getText() + statisticFrame.getText(), 144, 144);

                return Printable.PAGE_EXISTS;
            });

            if (printerJob.printDialog()) {
                try {
                    printerJob.print();
                } catch (PrinterException printerException) {
                    System.out.println(printerException);
                }
            }
        });
        toolBar.add(printButton);

        ImageIcon helpImage = new ImageIcon(getClass().getResource("images/help.png"));
        JButton help = new JButton(helpImage);
        help.setToolTipText("Help");
        help.addActionListener(e -> {
            //TODO: make this actually helpful
            JFrame helpWindow = new JFrame();
            helpWindow.setSize(400, 300);
            helpWindow.setLocation(400, 400);
            helpWindow.setVisible(true);
        });
        toolBar.add(help);

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
}
