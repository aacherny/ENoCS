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

    private String selectedTopology;
    private int selectedNodes;

    TopologyInternalFrame topologyFrame;
    OuterJFrame OJFrame;

    public PropertiesWindow(JDesktopPane inputJDesktopPane, Network inputNetwork, OuterJFrame inputOuterJFrame)
    {
        desktopPane = inputJDesktopPane;
        network = inputNetwork;
        OJFrame = inputOuterJFrame;

        // General things like window title and size
        propertiesFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
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
        switch(network.getTopology()){  // sets the default value of the dropdown to the value of the object
            default:
                boxTopology.setSelectedIndex(0);
                break;
            case "bus":
                boxTopology.setSelectedIndex(0);
                break;
            case "mesh":
                boxTopology.setSelectedIndex(1);
                break;
            case "torus":
                boxTopology.setSelectedIndex(2);
                break;
            case "butterfly":
                boxTopology.setSelectedIndex(3);
                break;
        }

        networkSettings.add(boxTopology);

        // Nodes label
        JLabel labelNodes = new JLabel("Nodes");

        // Nodes dropdown
        String[] choicesNodes = { "4", "9", "16"};
        final JComboBox<String> boxNodes = new JComboBox<String>(choicesNodes);
        boxNodes.setSelectedItem(network.getNodes());   // Sets the default value of the JComboBox
        switch(network.getNodes()){  // sets the default value of the dropdown to the value of the object
        default:
            boxNodes.setSelectedIndex(0);
            break;
        case 4:
            boxNodes.setSelectedIndex(0);
            break;
        case 9:
            boxNodes.setSelectedIndex(1);
            break;
        case 16:
            boxNodes.setSelectedIndex(2);
            break;
        }

        networkSettings.add(boxNodes);

        topologyPanel.add(labelTopology);
        topologyPanel.add(boxTopology);

        nodesPanel.add(labelNodes);
        nodesPanel.add(boxNodes);

        // OK button at the bottom
        JButton okButton = new JButton("OK");
        networkSettings.add(okButton);

        // What happens when the OK button is clicked
        okButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                selectedTopology = boxTopology.getSelectedItem().toString().toLowerCase();
                selectedNodes = Integer.parseInt(boxNodes.getSelectedItem().toString());

                if(topologyFrame != null)   // Creates a new topology display every time it's opened
                {
                    topologyFrame.dispose();
                }

                switch(selectedTopology) {  // sets the default value of the dropdown to the value of the object
                default:
                    network = new Mesh(selectedNodes, desktopPane);
                    break;
                case "mesh":
                    network = new Mesh(selectedNodes, desktopPane);
                    break;
                case "bus":
                    network = new Bus(selectedNodes, desktopPane);
                    break;
                }

                // Updates the parent class that "network" is now different
                OJFrame.updateNetwork(network);

                topologyFrame = new TopologyInternalFrame(network.drawTopology());

                topologyFrame.setVisible(true);

                desktopPane.add(topologyFrame);
            }
        });
    }
}
