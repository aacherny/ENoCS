package main.java;


import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * Class for the outer window that holds other windows inside it
 */
public class OuterJFrame
{
    private JFrame outerFrame;      // Outer window
    private JPanel panel;           // Needed for toolbar
    private JDesktopPane desktop;   // Needed for Internal JFrames
    // The next line declares the frames that are going to be used to control the internal windows
    // Is there any reason not to define them now?
    private JInternalFrame topologyFrame, flowControlFrame, statisticFrame;
    private PropertiesWindow propWindow;
    private JToolBar toolBar;
    private File saveFile = new File("");

    protected Network network;

    protected int cycleNumber;
    public static int openFrameCount;

    public OuterJFrame()
    {
        network = new Mesh( 4);
        outerFrame = new JFrame();
        panel = new JPanel(new BorderLayout());
        desktop = new JDesktopPane();
        openFrameCount = 0;
        cycleNumber = 0;

        /*
        The next line adds a BorderLayout to outerFrame's content pane
        This allows the panel for the toolbar and the desktoppane
        to play nice together otherwise they will overlap eachother
        */
        outerFrame.getContentPane().setLayout(new BorderLayout());
        outerFrame.getContentPane().add(panel, BorderLayout.PAGE_START);
        outerFrame.getContentPane().add(desktop, BorderLayout.CENTER);


        // General things like window title and size
        outerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        outerFrame.setTitle("ENoCS Simulator");
        outerFrame.setSize(1200, 800);
        outerFrame.setLocationRelativeTo(null);
    }

    /**
     * Creates the JFrame that holds all of the windows inside it and makes it visible
     */
    public void createOuterJFrame()
    {
        // Creates the menu bar and adds it to the window
        JMenuBar menuBar = createMenuBar();
        outerFrame.setJMenuBar(menuBar);

        //Creates the toolbar and adds it to the window on the left side
        JToolBar toolBar = createToolBar();
        panel.add(toolBar, BorderLayout.WEST);

        // Makes the window visible
        outerFrame.setVisible(true);
    }

    /**
     * Creates the menu bar in the outer window, adding dropdown menus like
     * 'File' and 'Edit'
     */
    private JMenuBar createMenuBar()
    {
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
//            topologyFrame = new TopologyInternalFrame(new JPanel());
            flowControlFrame = new FlowControlInternalFrame(new JTextPane());
            statisticFrame = new StatisticsInternalFrame(new JTextPane());
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
            try{
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Save File");
                FileWriter fstream;
                System.out.println(saveFile);
                if (saveFile.getName() != ""){
                    fstream = new FileWriter(saveFile);
                    BufferedWriter out = new BufferedWriter(fstream);
                    out.write("QUICK SAVE TEST");
                    out.close();
                } else {
                    int userSelection = fileChooser.showSaveDialog(outerFrame);
                    if (userSelection == JFileChooser.APPROVE_OPTION){
                        saveFile = fileChooser.getSelectedFile();
                        fstream = new FileWriter(saveFile);
                        BufferedWriter out = new BufferedWriter(fstream);
                        //UNCOMMENT THE LINE BELOW WHEN FLOW CONTROL TEXT EXISTS
                        //out.write(flowControlText.getText())
                        out.write("SAVE AS TEST");
                        out.close();
                    }
                }
            } catch (Exception saveException){
                System.err.println("Error: " + saveException.getMessage());
            }
        });
        menuFile.add(menuItemSave);

        // 'Save As' button under 'File'
        JMenuItem menuItemSaveAs = new JMenuItem("Save As");
        menuItemSaveAs.addActionListener(e -> {
            //TODO: figure out why this writes everything on one line
            //TODO: Make the state of the entire simulator recoverable from log
            try{
                JFileChooser fileChooser = new JFileChooser();
                int userSelection = fileChooser.showSaveDialog(outerFrame);
                if (userSelection == JFileChooser.APPROVE_OPTION){
                    saveFile = fileChooser.getSelectedFile();
                    FileWriter fstream = new FileWriter(saveFile);
                    BufferedWriter out = new BufferedWriter(fstream);
                    //UNCOMMENT THE LINE BELOW WHEN FLOW CONTROL TEXT EXISTS
                    //out.write(flowControlText.getText())
                    out.write("SAVE AS TEST");
                    out.close();
                }
            } catch (Exception saveException){
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
                if(propWindow != null) {    // Opens the window with all of the existing variables if it's been opened before
                    propWindow.createPropWindow();
                }else{
                    propWindow = new PropertiesWindow(desktop, network);
                    propWindow.createPropWindow();
                }
            }
        });



        // Build the View menu
        JMenu menuView = new JMenu("View");
        menuBar.add(menuView);

        // 'Toolbar' button under 'View'
        JCheckBoxMenuItem menuItemToolbar = new JCheckBoxMenuItem("Toolbar");
        menuItemToolbar.setState(true);
        if (menuItemToolbar.getState()){
            //TODO: Set toolbar on
            //Uncommenting below will break the program because at this point the toolbar is not defined
//            desktop.add(toolBar);
        } else {
            //TODO: set toolbar off
            //Uncommenting below will break the program because at this point the toolbar is not defined
//            desktop.remove(toolBar);
        }
        menuView.add(menuItemToolbar);

        //TODO: add statusbar if a reason for can be thought of
        //This statusbar doesn't exist as of 11/22/17
        // 'Status Bar' button under 'View'
        JCheckBoxMenuItem menuItemStatusBar = new JCheckBoxMenuItem("Status Bar");
        menuItemStatusBar.setState(true);
        if (menuItemStatusBar.getState()){
//            desktop.add(statusBar);
        } else {
//            desktop.remove(statusBar);
        }
        menuView.add(menuItemStatusBar);

        // 'Topology' button under 'View'
        JCheckBoxMenuItem menuItemTopology = new JCheckBoxMenuItem("Topology");
        menuItemTopology.setState(true);
        if (menuItemTopology.getState()){
//            topologyFrame.setVisible(true);
        } else {
//            topologyFrame.setVisible(false);
        }
        menuView.add(menuItemTopology);

        //The Pipeline window doesn't yet exist
        // 'Pipeline' button under 'View'
        JCheckBoxMenuItem menuItemPipeline = new JCheckBoxMenuItem("Pipeline");
        menuItemPipeline.setState(false);
        if (menuItemPipeline.getState()){
//            pipelineFrame.setVisible(true);
        } else {
//            pipelineFrame.setVisible(false);
        }
        menuView.add(menuItemPipeline);

        // 'Routing' button under 'View'
        JCheckBoxMenuItem menuItemRouting = new JCheckBoxMenuItem("Routing");
        menuItemRouting.setState(false);
        if (menuItemRouting.getState()){
//            routingFrame.setVisible(true);
        } else {
//            routingFrame.setVisible(false);
        }
        menuView.add(menuItemRouting);

        //This is in the same state as the topology window
        // 'Statistics' button under 'View'
        JCheckBoxMenuItem menuItemStatistics = new JCheckBoxMenuItem("Statistics");
        menuItemStatistics.setState(true);
        if (menuItemStatistics.getState()){
//            statisticFrame.setVisible(true);
        } else {
//            statisticFrame.setVisible(false);
        }
        menuView.add(menuItemStatistics);

        //NetworkInfoFrame does not exist as of yet
        // 'Network Information' button under 'View'
        JCheckBoxMenuItem menuItemNetworkInfo = new JCheckBoxMenuItem("Network Information");
        menuItemNetworkInfo.setState(false);
        if (menuItemNetworkInfo.getState()){
//            networkInfoFrame.setVisible(true);
        } else {
//            networkInfoFrame.setVisible(false);
        }
        menuView.add(menuItemNetworkInfo);

        //This is in the same state as the statisticFrame and the topologyFrame
        //they need to be defined earlier to work here
        // 'Flow Control' button under 'View'
        JCheckBoxMenuItem menuItemFlowControl = new JCheckBoxMenuItem("Flow Control");
        menuItemFlowControl.setState(true);
        if (menuItemFlowControl.getState()){
//            flowControlFrame.setVisible(true);
        } else {
//            flowControlFrame.setVisible(false);
        }
        menuView.add(menuItemFlowControl);



        // Build the Windows menu
        JMenu menuWindows = new JMenu("Windows");
        menuBar.add(menuWindows);

        //TODO: Does this button have a purpose
        // 'Properties' button under 'Windows'
        JMenuItem menuItemNewWindow = new JMenuItem("New Window");
        menuWindows.add(menuItemNewWindow);

        //TODO: figure out a way to cascade windows (using the xoffset and yoffset?)
        // 'Properties' button under 'Windows'
        JMenuItem menuItemCascade = new JMenuItem("Cascade");
        menuWindows.add(menuItemCascade);

        // 'Properties' button under 'Windows'
        JMenuItem menuItemTileV = new JMenuItem("Tile Vertical");
        menuWindows.add(menuItemTileV);

        // 'Properties' button under 'Windows'
        JMenuItem menuItemTileH = new JMenuItem("Tile Horizontal");
        menuWindows.add(menuItemTileH);

        // 'Properties' button under 'Windows'
        JMenuItem menuItemCloseAll = new JMenuItem("Close All");
        menuItemCloseAll.addActionListener(e -> {
            //TODO: Should this be dispose or setVisibility(false)?
            topologyFrame.dispose();
            flowControlFrame.dispose();
            statisticFrame.dispose();
            //TODO: Add these back in after creating them in the first place
//            networkInfoFrame.dispose();
//            routingFrame.dispose();
        });
        menuWindows.add(menuItemCloseAll);

        //TODO: Come back to this and figure out what it does
        // 'Properties' button under 'Windows'
        JMenuItem menuItemRefresh = new JMenuItem("Refresh");
        menuWindows.add(menuItemRefresh);



        // Build the Help menu
        JMenu menuHelp = new JMenu("Help");
        menuBar.add(menuHelp);

        // 'Contents' button under 'Help'
        JMenuItem menuItemContents = new JMenuItem("Contents");
        menuHelp.add(menuItemContents);

        menuHelp.addSeparator();

        // 'About' button under 'Help'
        JMenuItem menuItemAbout = new JMenuItem("About");
        menuHelp.add(menuItemAbout);

        return menuBar;
    }

    /**
     * Creates the toolbar with the ability to start a new simulation and advance the
     * cycles in the current simulation.
     * @return JToolBar
     */
    private JToolBar createToolBar()
    {
        JToolBar toolBar = new JToolBar();
        toolBar.setBounds(0, 0, 9999, 20);
        toolBar.setFloatable( false);

        ImageIcon newFileImage = new ImageIcon(getClass().getResource("images/new.png"));
        JButton newFile = new JButton(newFileImage);
        newFile.setToolTipText("New");
        newFile.addActionListener(e -> {
            topologyFrame = new TopologyInternalFrame(new JPanel(), openFrameCount++);
            flowControlFrame = new FlowControlInternalFrame(new JTextPane());
            statisticFrame = new StatisticsInternalFrame(new JTextPane());
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
            if (userSelection == JFileChooser.APPROVE_OPTION){
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
            try{
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Save File");
                FileWriter fstream;
                System.out.println(saveFile);
                if (saveFile.getName() != ""){
                    fstream = new FileWriter(saveFile);
                    BufferedWriter out = new BufferedWriter(fstream);
                    out.write("QUICK SAVE TEST");
                    out.close();
                } else {
                    int userSelection = fileChooser.showSaveDialog(outerFrame);
                    if (userSelection == JFileChooser.APPROVE_OPTION){
                        saveFile = fileChooser.getSelectedFile();
                        fstream = new FileWriter(saveFile);
                        BufferedWriter out = new BufferedWriter(fstream);
                        //UNCOMMENT THE LINE BELOW WHEN FLOW CONTROL TEXT EXISTS
                        //out.write(flowControlText.getText())
                        out.write("SAVE AS TEST");
                        out.close();
                    }
                }
            } catch (Exception saveException){
                System.err.println("Error: " + saveException.getMessage());
            }
        });
        toolBar.add(saveFileButton);

        //TODO: Is this necessary?
        ImageIcon printImage = new ImageIcon(getClass().getResource("images/print.png"));
        JButton print = new JButton(printImage);
        print.setToolTipText("Print");
        toolBar.add(print);

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
        refresh.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                cycleNumber = 0;
                cycleLabel.setText("Cycle number: " + cycleNumber);

                // network.newCycle();
            }
        });
        toolBar.add(refresh);

        ImageIcon nextCycleImage = new ImageIcon(getClass().getResource("images/play.png"));
        JButton nextCycle = new JButton(nextCycleImage);
        nextCycle.setToolTipText("Next Cycle");
        nextCycle.addActionListener(e -> {
            cycleNumber++;
            cycleLabel.setText("Cycle number: " + cycleNumber);
            TopologyInternalFrame top1 = new TopologyInternalFrame(new JPanel(), openFrameCount++);
            TopologyInternalFrame top2 = new TopologyInternalFrame(new JPanel(), openFrameCount++);
            desktop.add(top1);
            desktop.add(top2);
            // network.nextCycle();
        });
        toolBar.add(nextCycle);

        JTextField multiCycleField = new JTextField("5");
        toolBar.add(multiCycleField);

        ImageIcon multiCycleImage = new ImageIcon(getClass().getResource("images/fastforward.png"));
        JButton multiCycle = new JButton(multiCycleImage);
        multiCycle.setToolTipText("Run Multiple Cycles");
        multiCycle.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {

                for (int i = 0; i < Integer.parseInt(multiCycleField.getText()); i++){
                    cycleNumber++;
                    cycleLabel.setText("Cycle number: " + cycleNumber);
                }

                // network.nextCycle();
            }
        });
        toolBar.add(multiCycle);

        toolBar.add(cycleLabel);

        return toolBar;
    }


    /**
     * Returns the desktop pane of the JPanel
     * @return {JDesktopPane}
     */
    public JDesktopPane getDesktopPane()
    {
        return desktop;
    }
}
