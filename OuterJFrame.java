package main.java;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * Class for the outer window that holds other windows inside it
 */
public class OuterJFrame
{
    private JFrame outerFrame = new JFrame();   // Outer window

    public OuterJFrame()
    {
        // General things like window title and size
        outerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        outerFrame.setTitle("ENoCS Simulator");
        outerFrame.setSize(500, 350);
        outerFrame.setLocationRelativeTo(null);

        // Creates the menu bar and adds it to the window
        JMenuBar menuBar = createMenuBar();
        outerFrame.setJMenuBar(menuBar);

        // Makes the window visible
        outerFrame.setVisible(true);
    }

    /**
     * Creates the menu bar in the outer window, adding dropdown menus like
     * 'File' and 'Edit'
     */
    public JMenuBar createMenuBar()
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

        // 'Properties' button under 'Windows'
        JMenuItem menuItemContents = new JMenuItem("Contents");
        menuHelp.add(menuItemContents);

        menuHelp.addSeparator();

        // 'Properties' button under 'Windows'
        JMenuItem menuItemAbout = new JMenuItem("About");
        menuHelp.add(menuItemAbout);

        return menuBar;
    }
}