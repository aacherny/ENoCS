package main.java;

import javax.swing.*;
import java.awt.*;

public class PropertiesWindow
{
    private JFrame propertiesFrame = new JFrame();   // Main window that holds all of the tabs

    public PropertiesWindow()
    {
        // General things like window title and size
        propertiesFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        propertiesFrame.setTitle("Properties");
        propertiesFrame.setSize(500, 350);
        propertiesFrame.setLocationRelativeTo(null);
    }

    /**
     * Creates the format of the Properties window and its tabs
     */
    public void createPropWindow()
    {
        JTabbedPane tabbedPane = new JTabbedPane(); // Creates tabbed pane

        // Creates tabs and adds them to the tabbed pane
        JPanel runtimeOptions = new JPanel(new BorderLayout());
        tabbedPane.addTab("Runtime Options", runtimeOptions);

        JPanel networkSettings = new JPanel(new BorderLayout());
        tabbedPane.addTab("Network Settings", networkSettings);

        JPanel routerMA = new JPanel(new BorderLayout());
        tabbedPane.addTab("Router MicroArchitecture", routerMA);

        JPanel statWindow = new JPanel(new BorderLayout());
        tabbedPane.addTab("Stat Window", statWindow);

        JPanel misc = new JPanel(new BorderLayout());
        tabbedPane.addTab("Misc", misc);


        // Adds the tabbed pane to the Properties window
        propertiesFrame.add(tabbedPane);

        // Makes the window visible
        propertiesFrame.setVisible(true);
    }
}
