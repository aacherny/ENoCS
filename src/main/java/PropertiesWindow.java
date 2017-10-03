package main.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PropertiesWindow
{
    private JFrame propertiesFrame = new JFrame();   // Main window that holds all of the tabs
    private JDesktopPane desktopPane;

    public Network network;

    TopologyInternalFrame topologyFrame;

    public PropertiesWindow(JDesktopPane inputJDesktopPane, Network inputNetwork)
    {
        desktopPane = inputJDesktopPane;
        network = inputNetwork;

        // General things like window title and size
        propertiesFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        propertiesFrame.setTitle("Properties");
        propertiesFrame.setSize(500, 350);
        propertiesFrame.setResizable(false);
        propertiesFrame.setLocationRelativeTo(null);
    }

    /**
     * Creates the format of the Properties window and its tabs
     */
    public void createPropWindow()
    {
        JTabbedPane tabbedPane = new JTabbedPane(); // Creates tabbed pane

        // Creates tabs and adds them to the tabbed pane
        // 'Runtime Options' panel
        JPanel runtimeOptions = new JPanel(new BorderLayout());
        tabbedPane.addTab("Runtime Options", runtimeOptions);


        // 'Network Settings' panel
        JPanel networkSettings = new JPanel(new GridLayout(9, 2));
        tabbedPane.addTab("Network Settings", networkSettings);
        createNetworkSettingsPanel(networkSettings);    // creates the content in the 'Network Settings' tab


        // 'Router MicroArchitecture' panel
        JPanel routerMA = new JPanel(new BorderLayout());
        tabbedPane.addTab("Router MicroArchitecture", routerMA);


        // 'Stat Window' panel
        JPanel statWindow = new JPanel(new BorderLayout());
        tabbedPane.addTab("Stat Window", statWindow);


        // 'Misc' panel
        JPanel misc = new JPanel(new BorderLayout());
        tabbedPane.addTab("Misc", misc);


        // Adds the tabbed pane to the Properties window
        propertiesFrame.add(tabbedPane);

        // Makes the window visible
        propertiesFrame.setVisible(true);
    }

    /**
     * Creates the panel under the 'Network Settings' tab
     * @param networkSettings
     */
    private void createNetworkSettingsPanel(JPanel networkSettings)
    {
        // Panel for the topology
        JPanel topologyPanel = new JPanel(new GridLayout(1, 2)); // 2 rows 1 column
        networkSettings.add(topologyPanel);


        // Panel for the nodes
        JPanel nodesPanel = new JPanel(new GridLayout(1, 2)); // 2 rows 1 column
        networkSettings.add(nodesPanel);

        // Topology label
        JLabel labelTopology = new JLabel("Topology");

        // Topology dropdown
        String[] choicesTopology = { "Bus", "Mesh", "Torus", "Flattened Butterfly"};
        final JComboBox<String> boxTopology = new JComboBox<String>(choicesTopology);
        networkSettings.add(boxTopology);
        boxTopology.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                // Do something when you select a value
            }
        });



        // Nodes label
        JLabel labelNodes = new JLabel("Nodes");

        // Nodes dropdown
        String[] choicesNodes = { "4", "9", "16"};
        final JComboBox<String> boxNodes = new JComboBox<String>(choicesNodes);
        networkSettings.add(boxNodes);
        boxNodes.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                // Do something when you select a value
            }
        });

        topologyPanel.add(labelTopology);
        topologyPanel.add(boxTopology);

        nodesPanel.add(labelNodes);
        nodesPanel.add(boxNodes);

        // OK button at the bottom
        JButton okButton = new JButton("OK");
        networkSettings.add(okButton);

        okButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(topologyFrame != null)
                {
                    topologyFrame.dispose();
                }

                topologyFrame = new TopologyInternalFrame(network);

                topologyFrame.setVisible(true);

                desktopPane.add(topologyFrame);
            }
        });
    }
}
