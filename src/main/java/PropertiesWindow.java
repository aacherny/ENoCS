package main.java;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Properties window that receives network changes from the user and save/apply the changes to the network
 *
 * @author Alex Cherny
 */
public class PropertiesWindow {
    private JFrame propertiesFrame = new JFrame();   // Main window that holds all of the tabs
    private JDesktopPane desktopPane;

    public Network network;

    private JPanel runtimeOptions;
    //    private JPanel networkSettings;
    private JPanel routerMA;
    private JPanel statWindow;

    private String selectedTopology;
    private int selectedNodes;
    private double selectedInjection;

    TopologyInternalFrame topologyFrame;
    OuterJFrame OJFrame;

    /**
     * Creates the properties window
     * @param inputJDesktopPane The desktop that holds different windows
     * @param inputNetwork      The network of routers
     * @param inputOuterJFrame  The JFrame that holds the desktop and other network information
     */
    public PropertiesWindow(JDesktopPane inputJDesktopPane, Network inputNetwork, OuterJFrame inputOuterJFrame) {
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
    public void createPropWindow() {
        JTabbedPane tabbedPane = new JTabbedPane(); // Creates tabbed pane

        // Creates tabs and adds them to the tabbed pane
        // 'Runtime Options' panel
        runtimeOptions = createRuntimeOptionsPanel();
        tabbedPane.addTab("Runtime Options", runtimeOptions);


        // 'Network Settings' panel
        JPanel networkSettings = new JPanel(new BorderLayout());
        tabbedPane.addTab("Network Settings", networkSettings);
        createNetworkSettingsPanel(networkSettings);    // creates the content in the 'Network Settings' tab


        // 'Router MicroArchitecture' panel
        routerMA = createRouterMAPanel();
        tabbedPane.addTab("Router MicroArchitecture", routerMA);


        // Adds the tabbed pane to the Properties window
        propertiesFrame.add(tabbedPane);

        // Makes the window visible
        propertiesFrame.setVisible(true);
    }

    /**
     * Creates the panel under the 'Network Settings' tab
     *
     * @param networkSettings
     */
    private void createNetworkSettingsPanel(JPanel networkSettings) {
        // Panel for the topology
        JPanel borderPanel = new JPanel(new GridLayout(9, 2));
        networkSettings.add(borderPanel, BorderLayout.CENTER);

        JPanel topologyPanel = new JPanel(new GridLayout(1, 2)); // 2 rows 1 column
        borderPanel.add(topologyPanel);


        // Panel for the nodes
        JPanel nodesPanel = new JPanel(new GridLayout(1, 2)); // 2 rows 1 column
        borderPanel.add(nodesPanel);

        // Topology label
        JLabel labelTopology = new JLabel("Topology");

        // Topology dropdown
        String[] choicesTopology = {"Bus", "Mesh", "Torus"};
        final JComboBox<String> boxTopology = new JComboBox<String>(choicesTopology);
        switch (network.getTopology()) {  // sets the default value of the dropdown to the value of the object
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
        }

        borderPanel.add(boxTopology);

        // Nodes label
        JLabel labelNodes = new JLabel("Nodes");

        // Nodes dropdown
        String[] choicesNodes = {"4", "9", "16"};
        final JComboBox<String> boxNodes = new JComboBox<String>(choicesNodes);
        boxNodes.setSelectedItem(network.getNodes());   // Sets the default value of the JComboBox
        switch (network.getNodes()) {  // sets the default value of the dropdown to the value of the object
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

        borderPanel.add(boxNodes);

        topologyPanel.add(labelTopology);
        topologyPanel.add(boxTopology);

        nodesPanel.add(labelNodes);
        nodesPanel.add(boxNodes);

        JPanel okayCancelPanel = new JPanel();
        //Below adds the okay and cancel buttons
        JButton networkOkayButton = new JButton("Okay");

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> {
            propertiesFrame.dispose();
        });
        okayCancelPanel.add(networkOkayButton);
        okayCancelPanel.add(cancelButton);

        borderPanel.add(okayCancelPanel, BorderLayout.SOUTH);


        // What happens when the OK button is clicked
        networkOkayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedTopology = boxTopology.getSelectedItem().toString().toLowerCase();
                selectedNodes = Integer.parseInt(boxNodes.getSelectedItem().toString());

                if (topologyFrame != null)   // Creates a new topology display every time it's opened
                {
                    topologyFrame.dispose();
                }

                network.removeTextWindow();

                // Updates the parent class that "network" is now different
                OJFrame.updateNetwork(network);

                switch (selectedTopology) {  // sets the default value of the dropdown to the value of the object
                    default:
                        network = new Mesh(selectedNodes, desktopPane, OJFrame);
                        break;
                    case "mesh":
                        network = new Mesh(selectedNodes, desktopPane, OJFrame);
                        break;
                    case "bus":
                        network = new Bus(selectedNodes, desktopPane, OJFrame);
                        break;
                    case "torus":
                        network = new Torus(selectedNodes, desktopPane, OJFrame);
                        break;
                }

                // Updates the parent class that "network" is different again
                OJFrame.updateNetwork(network);

                topologyFrame = new TopologyInternalFrame(network.drawTopology());

                desktopPane.add(topologyFrame); // Adds the topology jframe to the desktop

                propertiesFrame.dispose();
            }
        });
    }

    /**
     * Creates the JPanel for Runtime Options
     * @return
     */
    private JPanel createRuntimeOptionsPanel() {
        //The below panel is for the proper positioning of the okay and cancel buttons
        JPanel borderLayout = new JPanel(new BorderLayout());

        //The JPanel below is where the rest of the options for this panel will go
        JPanel runtimeOptions = new JPanel();

        //Adding content to the Runtime Options JPanel
        //Below contains the CC per step
        JPanel CCPerStepPanel = new JPanel();
        JLabel CCPerStepLabel = new JLabel("Clock Cycles Per Step");
        JTextField CCPerStepField = new JTextField(OJFrame.getCyclePerStep()+"", 3);
        CCPerStepPanel.add(CCPerStepLabel);
        CCPerStepPanel.add(CCPerStepField);

        //Below contains the injection rate percent and the traffic pattern
        JPanel injectionRatePanel = new JPanel();
        JLabel injectionRateLabel = new JLabel("Injection Rate");

        String[] injectionRateField = {"0", "10", "20", "30", "40", "50", "60", "70", "80", "90", "100"};
        final JComboBox<String> boxInjection = new JComboBox<String>(injectionRateField);
        boxInjection.setSelectedIndex(10);

        JLabel injectionRatePercentLabel = new JLabel("%");
        injectionRatePanel.add(injectionRateLabel);
        injectionRatePanel.add(boxInjection);
        injectionRatePanel.add(injectionRatePercentLabel);

        //Below adds the okay and cancel buttons
        JPanel okayCancelPanel = new JPanel();
        JButton okayButton = new JButton("Okay");
        okayButton.addActionListener(e -> {
            selectedInjection = Integer.parseInt(boxInjection.getSelectedItem().toString());
            int selectedCyclesPerStep = Integer.parseInt(CCPerStepField.getText());
            OJFrame.setPacketChance(selectedInjection);
            OJFrame.setCyclePerStep(selectedCyclesPerStep);
            network.setPacketChance(selectedInjection);

            propertiesFrame.dispose();
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> {
            propertiesFrame.dispose();
        });
        okayCancelPanel.add(okayButton);
        okayCancelPanel.add(cancelButton);

        //Adding Everything above to the RuntimeOptions Panel
        runtimeOptions.add(CCPerStepPanel);
        runtimeOptions.add(injectionRatePanel);
        borderLayout.add(runtimeOptions, BorderLayout.CENTER);
        borderLayout.add(okayCancelPanel, BorderLayout.SOUTH);

        return borderLayout;
    }

    /**
     * Creates the JPanel for Router Microarchitecture options
     */
    private JPanel createRouterMAPanel() {
        //The below panel is for the proper positioning of the okay and cancel buttons
        JPanel borderLayout = new JPanel(new BorderLayout());

        //The below panel is where all of he options for the routerMA panel will go
        JPanel routerMAPanel = new JPanel();

        //Pipeline Stages Options
        JPanel pipelineStagesPanel = new JPanel();
        JLabel pipelineStagesLabel = new JLabel("Number of Pipeline Stages");
        JSlider pipelineStagesSlider = new JSlider(0, 4, network.getPipelineStages());
        pipelineStagesSlider.setMajorTickSpacing(1);
        pipelineStagesSlider.setMinorTickSpacing(1);
        pipelineStagesSlider.setSnapToTicks(true);
        pipelineStagesSlider.setPaintTicks(true);
        pipelineStagesSlider.setPaintLabels(true);
        pipelineStagesPanel.add(pipelineStagesLabel);
        pipelineStagesPanel.add(pipelineStagesSlider);

        //Okay and Cancel buttons
        JPanel okayCancelPanel = new JPanel();
        JButton okayButton = new JButton("Okay");
        okayButton.addActionListener(e -> {
            network.setPipelineStages(pipelineStagesSlider.getValue());

            propertiesFrame.dispose();
        });
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> {
            propertiesFrame.dispose();
        });
        okayCancelPanel.add(okayButton);
        okayCancelPanel.add(cancelButton);

        routerMAPanel.add(pipelineStagesPanel);
        borderLayout.add(routerMAPanel, BorderLayout.CENTER);
        borderLayout.add(okayCancelPanel, BorderLayout.SOUTH);

        return borderLayout;
    }
}
