package main.java;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PropertiesWindow
{
    private JFrame propertiesFrame = new JFrame();   // Main window that holds all of the tabs
    private JDesktopPane desktopPane;

    public Network network;

    private FlowControlInternalFrame fc;

    private JPanel runtimeOptions;
//    private JPanel networkSettings;
    private JPanel routerMA;
    private JPanel statWindow;

    private String selectedTopology;
    private int selectedNodes;

    //Setting for the properties window runtime options frame
    public int ccPerStep = 3;
    public String injectionType = "By Flit";
    public int injectionRate = 10;
    public int hotspotTrafficRate = 100;
    public int hotspotNode = 0;
    public String trafficPattern = "Uniform Random";
    public String packetGeneration = "Drop packets generated at nodes with full input buffer";
    //Settings for the properties window Network Settings frame
    //TODO: remember to add the rest of the options to the network settings frame
    public String topology = "Mesh";
    public int nodes = 4;
    //Settings for the properties window routerMA frame
    public String bufferDesign = "1 buffer w/ 0 VCs per Node";
    public int bufferSize = 4;
    public String flowControl = "RoundRobin";
    public String pipelineType = "Fixed Pipeline";
    public int pipelineStages = 4;
    public boolean creditBased = true;
    //Settings for the properties window State frame
    public boolean showClockCycles = true;
    public boolean showHops = true;
    public boolean showBandwidth = false;
    public boolean showThroughput = false;
    public boolean showDroppedFlits = true;
    public boolean showArea = false;
    public boolean showPower = false;

    //Runtime Options
    private JTextField CCPerStepField;
    private JTextField hotspotTrafficRateField;
    private JComboBox<String> injectionTypeBox;
    private JTextField hotspotNodeField;
    private JTextField injectionRateField;
    private JComboBox<String> trafficPatternBox;
    private JComboBox<String> packetGenerationBox;

    //RouterMA Options
    private JComboBox<String> bufferDesignBox;
    private JTextField bufferSizeField;
    private JComboBox<String> flowControlBox;
    private JComboBox<String> pipelineTypeBox;
    private JSlider pipelineStagesSlider;
    private JCheckBox creditBasedCheck;

    //Statistic Options
    private JCheckBox clockCyclesCheck;
    private JCheckBox hopsCheck;
    private JCheckBox bandwidthCheck;
    private JCheckBox throughputCheck;
    private JCheckBox droppedFlitsCheck;
    private JCheckBox areaCheck;
    private JCheckBox powerCheck;

    TopologyInternalFrame topologyFrame;
    OuterJFrame OJFrame;

    public PropertiesWindow(JDesktopPane inputJDesktopPane, Network inputNetwork, OuterJFrame inputOuterJFrame, FlowControlInternalFrame flowControl)
    {
        desktopPane = inputJDesktopPane;
        network = inputNetwork;
        OJFrame = inputOuterJFrame;

        fc = flowControl;

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
        runtimeOptions = createRuntimeOptionsPanel();
        tabbedPane.addTab("Runtime Options", runtimeOptions);



        // 'Network Settings' panel
        JPanel networkSettings = new JPanel(new GridLayout(9, 2));
        tabbedPane.addTab("Network Settings", networkSettings);
        createNetworkSettingsPanel(networkSettings);    // creates the content in the 'Network Settings' tab


        // 'Router MicroArchitecture' panel
        routerMA = createRouterMAPanel();
        tabbedPane.addTab("Router MicroArchitecture", routerMA);


        // 'Stat Window' panel
        statWindow = createStatWindowPanel();
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
                    network = new Mesh(selectedNodes, desktopPane, fc);
                    break;
                case "mesh":
                    network = new Mesh(selectedNodes, desktopPane, fc);
                    break;
                case "bus":
                    network = new Bus(selectedNodes, desktopPane);
                    break;
                }

                // Updates the parent class that "network" is now different
                OJFrame.updateNetwork(network);

                //TODO: Come and edit openFrame as needed
                topologyFrame = new TopologyInternalFrame(network.drawTopology());

                topologyFrame.setVisible(true);

                desktopPane.add(topologyFrame);

                propertiesFrame.dispose();
            }
        });
    }

    private JPanel createRuntimeOptionsPanel(){
        //The below panel is for the proper positioning of the okay and cancel buttons
        JPanel borderLayout = new JPanel(new BorderLayout());

        //The JPanel below is where the rest of the options for this panel will go
        JPanel runtimeOptions = new JPanel();

        //Adding content to the Runtime Options JPanel
        //Below contains the CC per step
        JPanel CCPerStepPanel = new JPanel();
        JLabel CCPerStepLabel = new JLabel("Clock Cycles Per Step");
        CCPerStepField = new JTextField("3", 3);
        CCPerStepPanel.add(CCPerStepLabel);
        CCPerStepPanel.add(CCPerStepField);

        //Below contains the hotspot traffic rate options
        JPanel hotspotTrafficRatePanel = new JPanel();
        JLabel hotspotTrafficRateLabel = new JLabel("Hotspot TrafficRate");
        hotspotTrafficRateField = new JTextField("100", 3);
        JLabel hotspotPercentLabel = new JLabel("%");
        hotspotTrafficRatePanel.add(hotspotTrafficRateLabel);
        hotspotTrafficRatePanel.add(hotspotTrafficRateField);
        hotspotTrafficRatePanel.add(hotspotPercentLabel);

        //Below contains the injection type
        JPanel injectionTypePanel = new JPanel();
        JLabel injectionTypeLabel = new JLabel("Injection Type");
        injectionTypeBox = new JComboBox<>();
        injectionTypeBox.addItem("By Flit");
        injectionTypeBox.addItem("By Packet");
        injectionTypePanel.add(injectionTypeLabel);
        injectionTypePanel.add(injectionTypeBox);

        //Below contains the option to change the number of hotspot nodes
        JPanel hotspotNodePanel = new JPanel();
        JLabel hotspotNodeLabel = new JLabel("Hotspot Node");
        hotspotNodeField = new JTextField("0",3);
        hotspotNodePanel.add(hotspotNodeLabel);
        hotspotNodePanel.add(hotspotNodeField);

        //Below contains the injection rate percent and the traffic pattern
        JPanel injectionRatePanel = new JPanel();
        JLabel injectionRateLabel = new JLabel("Injection Rate");
        injectionRateField = new JTextField("10", 3);
        JLabel injectionRatePercentLabel = new JLabel("%");
        injectionRatePanel.add(injectionRateLabel);
        injectionRatePanel.add(injectionRateField);
        injectionRatePanel.add(injectionRatePercentLabel);

        //Below is the traffic pattern option
        JPanel trafficPatternPanel = new JPanel();
        JLabel trafficPatternLabel = new JLabel("Traffic Pattern");
        trafficPatternBox = new JComboBox<>();
        trafficPatternBox.addItem("Uniform Random");
        trafficPatternBox.addItem("Tornado");
        trafficPatternBox.addItem("Hotspot");
        trafficPatternPanel.add(trafficPatternLabel);
        trafficPatternPanel.add(trafficPatternBox);

        //Below will contain the packet generation options
        JPanel packetGenerationPanel = new JPanel();
        JLabel packetGenerationLabel = new JLabel("Packet Generation");
        packetGenerationBox = new JComboBox<>();
        packetGenerationBox.addItem("Drop packets generated at nodes with full input buffer");
        packetGenerationBox.addItem("Do not generate packets at nodes with full input buffer");
        packetGenerationBox.addItem("Unlimited buffer space at nodes for generated packets");
        packetGenerationPanel.add(packetGenerationLabel);
        packetGenerationPanel.add(packetGenerationBox);

        //Below adds the okay and cancel buttons
        JPanel okayCancelPanel = new JPanel();
        JButton okayButton = new JButton("Okay");
        okayButton.addActionListener(e -> {
            setProperties();
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
        runtimeOptions.add(injectionTypePanel);
        runtimeOptions.add(injectionRatePanel);
        runtimeOptions.add(hotspotTrafficRatePanel);
        runtimeOptions.add(hotspotNodePanel);
        runtimeOptions.add(trafficPatternPanel);
        runtimeOptions.add(packetGenerationPanel);
        borderLayout.add(runtimeOptions, BorderLayout.CENTER);
        borderLayout.add(okayCancelPanel, BorderLayout.SOUTH);

        return borderLayout;
    }

    private JPanel createRouterMAPanel(){
        //The below panel is for the proper positioning of the okay and cancel buttons
        JPanel borderLayout = new JPanel(new BorderLayout());

        //The below panel is where all of he options for the routerMA panel will go
        JPanel routerMAPanel = new JPanel();

        //Buffer design option
        JPanel bufferDesignPanel = new JPanel();
        JLabel bufferDesignLabel = new JLabel("Buffer Design");
        bufferDesignBox = new JComboBox<>();
        bufferDesignBox.addItem("1 buffer w/ 0 VCs per Node");
        bufferDesignBox.addItem("1 buffer w/ 2 VCs per Node");
        bufferDesignBox.addItem("1 buffer w/ 3 VCs per Node");
        bufferDesignBox.addItem("#buffers = #inputs w/ 0 VCs per Node");
        bufferDesignBox.addItem("#buffers = #inputs w/ 2 VCs per Node");
        bufferDesignBox.addItem("#buffers = #inputs w/ 3 VCs per Node");
        bufferDesignBox.addItem("Bufferless");
        bufferDesignPanel.add(bufferDesignLabel);
        bufferDesignPanel.add(bufferDesignBox);

        //Buffer Size option
        JPanel bufferSizePanel = new JPanel();
        JLabel bufferSizeLabel = new JLabel("Buffer Size");
        bufferSizeField = new JTextField("4", 3);
        bufferSizePanel.add(bufferSizeLabel);
        bufferSizePanel.add(bufferSizeField);

        //Flow Control Option
        JPanel flowControlPanel = new JPanel();
        JLabel flowControlLabel = new JLabel("Flow Control");
        flowControlBox = new JComboBox<>();
        flowControlBox.addItem("RoundRobin");
        flowControlBox.addItem("Priority");
        flowControlBox.addItem("Wormhole");
        flowControlPanel.add(flowControlLabel);
        flowControlPanel.add(flowControlBox);

        //Pipeline Type Options
        JPanel pipelineTypePanel = new JPanel();
        JLabel pipelineTypeLabel = new JLabel("Pipeline Type");
        pipelineTypeBox = new JComboBox<>();
        pipelineTypeBox.addItem("Fixed Pipeline");
        pipelineTypeBox.addItem("Flexible Pipeline");
        pipelineTypePanel.add(pipelineTypeLabel);
        pipelineTypePanel.add(pipelineTypeBox);

        //Pipeline Stages Options
        JPanel pipelineStagesPanel = new JPanel();
        JLabel pipelineStagesLabel = new JLabel("Number of Pipeline Stages");
        pipelineStagesSlider = new JSlider(0, 5, 4);
        pipelineStagesSlider.setMajorTickSpacing(1);
        pipelineStagesSlider.setMinorTickSpacing(1);
        pipelineStagesSlider.setSnapToTicks(true);
        pipelineStagesSlider.setPaintTicks(true);
        pipelineStagesSlider.setPaintLabels(true);
        pipelineStagesPanel.add(pipelineStagesLabel);
        pipelineStagesPanel.add(pipelineStagesSlider);

        //Credit Based Flow Control Options
        creditBasedCheck = new JCheckBox("Credit Based Flow Control");
        creditBasedCheck.setSelected(true);

        //Okay and Cancel buttons
        JPanel okayCancelPanel = new JPanel();
        JButton okayButton = new JButton("Okay");
        okayButton.addActionListener(e -> {
            setProperties();
            propertiesFrame.dispose();
        });
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> {
            propertiesFrame.dispose();
        });
        okayCancelPanel.add(okayButton);
        okayCancelPanel.add(cancelButton);

        routerMAPanel.add(bufferDesignPanel);
        routerMAPanel.add(bufferSizePanel);
        routerMAPanel.add(flowControlPanel);
        routerMAPanel.add(pipelineTypePanel);
        routerMAPanel.add(pipelineStagesPanel);
        routerMAPanel.add(creditBasedCheck);
        borderLayout.add(routerMAPanel, BorderLayout.CENTER);
        borderLayout.add(okayCancelPanel, BorderLayout.SOUTH);

        return borderLayout;
    }

    private JPanel createStatWindowPanel(){
        //The below JPanel is used to correctly position the okay and cancel buttons
        JPanel borderLayout = new JPanel(new BorderLayout());

        //The below JPanel is used to hold all of the options for the Stats Window
        JPanel statWindowPanel = new JPanel();
        statWindowPanel.setLayout(new BoxLayout(statWindowPanel, BoxLayout.Y_AXIS));

        JLabel latencyStatsLabel = new JLabel("Latency Statistics");

        JPanel latencyStatsPanel = new JPanel();
        clockCyclesCheck = new JCheckBox("Clock Cycles", true);
        hopsCheck = new JCheckBox("Hops", true);
        latencyStatsPanel.add(clockCyclesCheck);
        latencyStatsPanel.add(hopsCheck);

        JLabel otherStatsLabel = new JLabel("Other Statistics");

        JPanel otherStatsPanel = new JPanel();
        bandwidthCheck = new JCheckBox("Bandwidth", false);
        throughputCheck = new JCheckBox("Throughput", false);
        droppedFlitsCheck = new JCheckBox("Dropped Flits", true);
        areaCheck = new JCheckBox("Area", false);
        powerCheck = new JCheckBox("Power", false);
        otherStatsPanel.add(bandwidthCheck);
        otherStatsPanel.add(throughputCheck);
        otherStatsPanel.add(droppedFlitsCheck);
        otherStatsPanel.add(areaCheck);
        otherStatsPanel.add(powerCheck);

        //Okay and Cancel buttons
        JPanel okayCancelPanel = new JPanel();
        JButton okayButton = new JButton("Okay");
        okayButton.addActionListener(e -> {
            setProperties();
            propertiesFrame.dispose();
        });
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> {
            propertiesFrame.dispose();
        });
        okayCancelPanel.add(okayButton);
        okayCancelPanel.add(cancelButton);

        statWindowPanel.add(latencyStatsLabel);
        statWindowPanel.add(latencyStatsPanel);
        statWindowPanel.add(otherStatsLabel);
        statWindowPanel.add(otherStatsPanel);
        borderLayout.add(statWindowPanel, BorderLayout.CENTER);
        borderLayout.add(okayCancelPanel, BorderLayout.SOUTH);

        return borderLayout;
    }

    private void setProperties() {
//        TODO: Figure out how to pass settings from the properties window the main window
        //Runtime Options
        ccPerStep = Integer.parseInt(CCPerStepField.getText());
        injectionType = (String) injectionTypeBox.getSelectedItem();
        injectionRate = Integer.parseInt(injectionRateField.getText());
        hotspotTrafficRate = Integer.parseInt(hotspotTrafficRateField.getText());
        hotspotNode = Integer.parseInt(hotspotNodeField.getText());
        trafficPattern = (String) trafficPatternBox.getSelectedItem();
        packetGeneration = (String) packetGenerationBox.getSelectedItem();

        //Network Settings
        //Double check that these are right
        topology = selectedTopology;
        nodes = selectedNodes;

        //Router Micro Architecture Options
        bufferDesign = (String) bufferDesignBox.getSelectedItem();
        bufferSize = Integer.parseInt(bufferSizeField.getText());
        flowControl = (String) flowControlBox.getSelectedItem();
        pipelineType = (String) pipelineTypeBox.getSelectedItem();
        pipelineStages = pipelineStagesSlider.getValue();
        creditBased = creditBasedCheck.isSelected();

        //Statistic Options
        showClockCycles = clockCyclesCheck.isSelected();
        showHops = hopsCheck.isSelected();
        showBandwidth = bandwidthCheck.isSelected();
        showThroughput = throughputCheck.isSelected();
        showDroppedFlits = droppedFlitsCheck.isSelected();
        showArea = areaCheck.isSelected();
        showPower = powerCheck.isSelected();
    }

    //Come up with a better name for this
    void okButtonAction(String boxTopology, int boxNodes) {
        selectedTopology = boxTopology;
        selectedNodes = boxNodes;

        if (topologyFrame != null)   // Creates a new topology display every time it's opened
        {
            topologyFrame.dispose();
        }

        switch (selectedTopology) {  // sets the default value of the dropdown to the value of the object
            default:
                network = new Mesh(selectedNodes, desktopPane, fc);
                break;
            case "mesh":
                network = new Mesh(selectedNodes, desktopPane, fc);
                break;
            case "bus":
                network = new Bus(selectedNodes, desktopPane);
                break;
        }

        // Updates the parent class that "network" is now different
        OJFrame.updateNetwork(network);

        //TODO: Come and edit openFrame as needed
        topologyFrame = new TopologyInternalFrame(network.drawTopology());

        topologyFrame.setVisible(true);

        desktopPane.add(topologyFrame);

        propertiesFrame.dispose();
    }
}
