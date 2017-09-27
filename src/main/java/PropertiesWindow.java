package main.java;

import javax.swing.*;
import java.awt.*;

public class PropertiesWindow
{
    private JFrame propertiesFrame = new JFrame();   // Outer window

    private String topology;
    private int nodes;

    public PropertiesWindow()
    {
        // General things like window title and size
        propertiesFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        propertiesFrame.setTitle("Properties");
        propertiesFrame.setSize(500, 350);
        propertiesFrame.setLocationRelativeTo(null);

        // Default values
        topology = "mesh";
        nodes = 4;
    }

    public void createPropWindow()
    {
        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel p = new JPanel(new BorderLayout());

        tabbedPane.addTab("Tab 1", p);




        // Makes the window visible
        propertiesFrame.setVisible(true);
    }
}
