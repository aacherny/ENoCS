package main.java;

import javax.swing.*;
import javax.swing.border.EtchedBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * Class for the outer window that holds other windows inside it
 */
public class OuterJFrame
{
    private JFrame outerFrame;   // Outer window
    private JDesktopPane desktop;
    private PropertiesWindow propWindow;
    private JToolBar toolBar;

    protected Network network;

    protected int cycleNumber;

    public OuterJFrame()
    {
        network = new Mesh( 4, desktop);    // Creates a default network to work with
        outerFrame = new JFrame();
        desktop = new JDesktopPane();   // DesktopPane that will hold all of the windows
        desktop.setLayout(new GridBagLayout());

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
    public void createOuterJFrame()
    {
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

    private JToolBar createToolBar()
    {
        JLabel cycleLabel = new JLabel("Cycle number: ");

        JToolBar newToolBar = new JToolBar();
        newToolBar.setBounds(0, 0, 9999, 20);
        newToolBar.setFloatable( false);

        JButton nextClockCycle = new JButton("Next Cycle");
        nextClockCycle.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                cycleNumber++;
                cycleLabel.setText("Cycle number: " + cycleNumber);

                network.nextCycle();
            }
        });

        JButton newCycle = new JButton("Restart");
        newCycle.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                cycleNumber = 0;
                cycleLabel.setText("Cycle number: " + cycleNumber);

                network.newCycle();
            }
        });

        newToolBar.add(nextClockCycle);
        newToolBar.add(newCycle);
        newToolBar.add(cycleLabel);

        return newToolBar;
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
                    propWindow = new PropertiesWindow(desktop, network);
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
     * Returns the desktop pane of the JPanel
     * @return {JDesktopPane}
     */
    public JDesktopPane getDesktopPane()
    {
        return desktop;
    }
}
