package main.java;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * Class for the outer window that holds other windows inside it
 */
public class OuterJFrame
{
    private JFrame outerFrame;      // Outer window
    private JPanel panel;           // Needed for toolbar
    private JDesktopPane desktop;   // Needed for Internal JFrames
    private PropertiesWindow propWindow;

    protected Mesh testNetwork;

    public OuterJFrame()
    {
        testNetwork = new Mesh(4);
        outerFrame = new JFrame();
        panel = new JPanel();
        desktop = new JDesktopPane();

        /*
        The next line adds a BoxLayout to outerFrame's content pane
        This allows the panel for the toolbar and the desktoppane
        to play nice together otherwise they will overlap eachother
        */
        outerFrame.getContentPane().setLayout(new BoxLayout(outerFrame.getContentPane(), BoxLayout.Y_AXIS));
        outerFrame.getContentPane().add(panel);
        outerFrame.getContentPane().add(desktop);


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

        JToolBar toolBar = createToolBar();
        panel.add(toolBar);

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
        JMenuItem menuItemNew = new JMenuItem("New");
        menuItemNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        menuFile.add(menuItemNew);

        // 'Open' button under 'File'
        JMenuItem menuItemOpen = new JMenuItem("Open");
        menuItemOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        menuFile.add(menuItemOpen);

        menuFile.addSeparator();

        // 'Save' button under 'File'
        JMenuItem menuItemSave = new JMenuItem("Save");
        menuItemSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        menuFile.add(menuItemSave);

        // 'Save As' button under 'File'
        JMenuItem menuItemSaveAs = new JMenuItem("Save As");
        menuFile.add(menuItemSaveAs);

        menuFile.addSeparator();

        // 'Exit' button under 'File'
        JMenuItem menuItemExit = new JMenuItem("Exit");
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
                    propWindow = new PropertiesWindow(desktop, testNetwork);
                    propWindow.createPropWindow();
                }
            }
        });



        // Build the View menu
        JMenu menuView = new JMenu("View");
        menuBar.add(menuView);

        // 'Toolbar' button under 'View'
        JMenuItem menuItemToolbar = new JCheckBoxMenuItem("Toolbar");
        menuView.add(menuItemToolbar);

        // 'Status Bar' button under 'View'
        JMenuItem menuItemStatusBar = new JCheckBoxMenuItem("Status Bar");
        menuView.add(menuItemStatusBar);

        // 'Topology' button under 'View'
        JMenuItem menuItemTopology = new JCheckBoxMenuItem("Topology");
        menuView.add(menuItemTopology);

        // 'Pipeline' button under 'View'
        JMenuItem menuItemPipeline = new JCheckBoxMenuItem("Pipeline");
        menuView.add(menuItemPipeline);

        // 'Routing' button under 'View'
        JMenuItem menuItemRouting = new JCheckBoxMenuItem("Routing");
        menuView.add(menuItemRouting);

        // 'Statistics' button under 'View'
        JMenuItem menuItemStatistics = new JCheckBoxMenuItem("Statistics");
        menuView.add(menuItemStatistics);

        // 'Network Information' button under 'View'
        JMenuItem menuItemNetworkInfo = new JCheckBoxMenuItem("Network Information");
        menuView.add(menuItemNetworkInfo);

        // 'Flow Control' button under 'View'
        JMenuItem menuItemFlowControl = new JCheckBoxMenuItem("Flow Control");
        menuView.add(menuItemFlowControl);



        // Build the Windows menu
        JMenu menuWindows = new JMenu("Windows");
        menuBar.add(menuWindows);

        // 'Properties' button under 'Windows'
        JMenuItem menuItemNewWindow = new JMenuItem("New Window");
        menuWindows.add(menuItemNewWindow);

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
        menuWindows.add(menuItemCloseAll);

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
     */
    private JToolBar createToolBar()
    {
        JToolBar toolBar = new JToolBar("TEST");

        toolBar.setFloatable(false);

        ImageIcon newFileImage = new ImageIcon(getClass().getResource("images/new.png"));
        JButton newFile = new JButton(newFileImage);
        newFile.setToolTipText("New");
        toolBar.add(newFile);

        ImageIcon openFileImage = new ImageIcon(getClass().getResource("images/open.png"));
        JButton openFile = new JButton(openFileImage);
        openFile.setToolTipText("Open");
        toolBar.add(openFile);

        ImageIcon saveFileImage = new ImageIcon(getClass().getResource("images/save.png"));
        JButton saveFile = new JButton(saveFileImage);
        saveFile.setToolTipText("Save");
        toolBar.add(saveFile);

        ImageIcon printImage = new ImageIcon(getClass().getResource("images/print.png"));
        JButton print = new JButton(printImage);
        print.setToolTipText("Print");
        toolBar.add(print);

        ImageIcon helpImage = new ImageIcon(getClass().getResource("images/help.png"));
        JButton help = new JButton(helpImage);
        help.setToolTipText("Help");
        toolBar.add(help);

        ImageIcon refreshImage = new ImageIcon(getClass().getResource("images/refresh.png"));
        JButton refresh = new JButton(refreshImage);
        refresh.setToolTipText("Refresh");
        toolBar.add(refresh);

        ImageIcon nextCycleImage = new ImageIcon(getClass().getResource("images/play.png"));
        JButton nextCycle = new JButton(nextCycleImage);
        nextCycle.setToolTipText("Next Cycle");
        toolBar.add(nextCycle);

        ImageIcon multiCycleImage = new ImageIcon(getClass().getResource("images/fastforward.png"));
        JButton multiCycle = new JButton(multiCycleImage);
        multiCycle.setToolTipText("Run Multiple Cycles");
        toolBar.add(multiCycle);

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
