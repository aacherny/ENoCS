package main.java;

import javafx.scene.control.CheckMenuItem;

import javax.imageio.plugins.jpeg.JPEGHuffmanTable;
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

    public PropertiesWindow(JDesktopPane inputJDesktopPane, Network inputNetwork)
    {
        desktopPane = inputJDesktopPane;
        network = inputNetwork;

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

        JPanel runtimeOptions = new JPanel();
        tabbedPane.addTab("Runtime Options", runtimeOptions);
        createRuntimeOptionsPanel(runtimeOptions);


        // 'Network Settings' panel
        JPanel networkSettings = new JPanel();
//        JPanel networkSettings = new JPanel(new GridLayout(9, 2));
        tabbedPane.addTab("Network Settings", networkSettings);
        createNetworkSettingsPanel(networkSettings);    // creates the content in the 'Network Settings' tab


        // 'Router MicroArchitecture' panel
        JPanel routerMA = new JPanel();
        tabbedPane.addTab("Router MicroArchitecture", routerMA);
        createRouterMAPanel(routerMA);


        // 'Stat Window' panel
        JPanel statWindow = new JPanel();
        tabbedPane.addTab("Stat Window", statWindow);
        createStatWindow(statWindow);


        // 'Misc' panel
        JPanel misc = new JPanel(new BorderLayout());
        tabbedPane.addTab("Misc", misc);


        // Adds the tabbed pane to the Properties window
        propertiesFrame.add(tabbedPane);

        // Makes the window visible
        propertiesFrame.setVisible(true);
    }

    /**
     * Creates the panel under the Runtime Options tab
     * @param runtimeOptions
     */
    private void createRuntimeOptionsPanel(JPanel runtimeOptions)
    {
        //The line below set panel to center things and cascade them vertically
        runtimeOptions.setLayout(new BoxLayout(runtimeOptions, BoxLayout.Y_AXIS));

        /**
         * the design of this panel is to create a panel that is divided into two sections. The left section holds
         * the label for the parameter that is going to be changed. The right side holds the parameter that
         * is getting changed.
         */
        JPanel ccPerStepPanel = new JPanel(new GridLayout(1,2));

        JLabel ccPerStepLabel = new JLabel("Clock Cycles per step");
        ccPerStepPanel.add(ccPerStepLabel);

        JTextField ccPerStepField = new JTextField("3");
        ccPerStepPanel.add(ccPerStepField);

        JPanel hotspotRatePanel = new JPanel(new GridLayout(1,2));

        JLabel hotspotRateLabel = new JLabel("Hotspot Traffic Rate");
        hotspotRatePanel.add(hotspotRateLabel);

        JTextField hotSpotRateField = new JTextField("100");
        hotspotRatePanel.add(hotSpotRateField);


        JPanel injectionTypePanel = new JPanel(new GridLayout(1, 2));

        JLabel injectionTypeLabel = new JLabel("Injection Type");
        injectionTypePanel.add(injectionTypeLabel);

        JComboBox<String> injectionTypeBox = new JComboBox<>();
        injectionTypeBox.addItem("By Flit");
        injectionTypeBox.addItem("By Packet");
        injectionTypePanel.add(injectionTypeBox);


        JPanel hotspotNodePanel = new JPanel(new GridLayout(1, 2));

        JLabel hotspotNodeLabel = new JLabel("Hotspot Node");
        hotspotNodePanel.add(hotspotNodeLabel);

        JTextField hotspotNodeField = new JTextField("0");
        hotspotNodePanel.add(hotspotNodeField);


        JPanel injectionRatePanel = new JPanel(new GridLayout(1, 2));

        JLabel injectionRateLabel = new JLabel("Packet/Flit Injection Rate");
        injectionRatePanel.add(injectionRateLabel);

        JTextField injectionRateField = new JTextField("10");
        injectionRatePanel.add(injectionRateField);


        JPanel trafficPatternPanel = new JPanel(new GridLayout(1, 2));

        JLabel trafficPatternLabel = new JLabel("Traffic Pattern");
        trafficPatternPanel.add(trafficPatternLabel);

        JComboBox<String> trafficPatternBox = new JComboBox<>();
        trafficPatternBox.addItem("Unified Random");
        trafficPatternBox.addItem("Tornado");
        trafficPatternBox.addItem("Hotspot");
        trafficPatternPanel.add(trafficPatternBox);

        JPanel packetGenPanel = new JPanel();

        JLabel packetGenLabel = new JLabel("Packet Generation Options");
        packetGenPanel.add(packetGenLabel);

        JComboBox<String> packetGenBox = new JComboBox<>();
        packetGenBox.addItem("Drop packets generated at nodes with full input buffer");
        packetGenBox.addItem("Do not generate packets at nodes with full input buffer");
        packetGenBox.addItem("Unlimited buffer space at nodes for generated packets");
        packetGenPanel.add(packetGenBox);

        JPanel okayCancelPanel = new JPanel();

        JButton okay = new JButton("OK");
        okayCancelPanel.add(okay);

        JButton cancel = new JButton("Cancel");
        okayCancelPanel.add(cancel);

        runtimeOptions.add(ccPerStepPanel);
        runtimeOptions.add(hotspotRatePanel);
        runtimeOptions.add(injectionTypePanel);
        runtimeOptions.add(hotspotNodePanel);
        runtimeOptions.add(injectionRatePanel);
        runtimeOptions.add(trafficPatternPanel);
        runtimeOptions.add(packetGenPanel);
//        runtimeOptions.add(packetGenLabel);
//        runtimeOptions.add(packetGenBox);
        runtimeOptions.add(okayCancelPanel);

    }

    /**
     * Creates the panel under the 'Network Settings' tab
     * @param networkSettings
     */
    private void createNetworkSettingsPanel(JPanel networkSettings)
    {
        networkSettings.setLayout(new BoxLayout(networkSettings, BoxLayout.Y_AXIS));
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


        JPanel routingPanel = new JPanel(new GridLayout(1, 2));

        JLabel routingLabel = new JLabel();
        routingPanel.add(routingLabel);

        JComboBox<String> routingCombo = new JComboBox<>();
        routingCombo.addItem("X-Y Routing");
        routingCombo.addItem("Adaptive Routing");
        routingPanel.add(routingCombo);

        JPanel linkLatencyPanel = new JPanel(new GridLayout(1, 2));

        JLabel linkLatencyLabel = new JLabel("Link Latency");
        linkLatencyPanel.add(linkLatencyLabel);

        JTextField linkLatencyField = new JTextField("0");
        linkLatencyPanel.add(linkLatencyField);

        JPanel nodeSizePanel = new JPanel(new GridLayout(1, 2));

        JLabel nodeSizeLabel = new JLabel("Node Size");
        nodeSizePanel.add(nodeSizeLabel);

        JTextField nodeSizeField = new JTextField("20");
        nodeSizePanel.add(nodeSizeField);

        JPanel linkSizePanel = new JPanel(new GridLayout(1, 2));

        JLabel linkSizeLabel = new JLabel("Link Size");
        linkSizePanel.add(linkSizeLabel);

        JTextField linkSizeField = new JTextField("30");
        linkSizePanel.add(linkSizeField);

        JPanel phitSizePanel = new JPanel(new GridLayout(1, 2));

        JLabel phitSizeLabel = new JLabel("Phit Size (in bits)");
        phitSizePanel.add(phitSizeLabel);

        JTextField phitSizeField = new JTextField("40");
        phitSizePanel.add(phitSizeField);

        JPanel flitSizePanel = new JPanel(new GridLayout(1, 2));

        JLabel flitSizeLabel = new JLabel("Flit Size (in bits)");
        flitSizePanel.add(flitSizeLabel);

        JTextField flitSizeField = new JTextField("40");
        flitSizePanel.add(flitSizeField);

        JPanel packetSizePanel = new JPanel(new GridLayout(1, 2));

        JLabel packetSizeLabel = new JLabel("Packet Size (in bits)");
        packetSizePanel.add(packetSizeLabel);

        JTextField packetSizeField = new JTextField("80");
        packetSizePanel.add(packetSizeField);

        networkSettings.add(linkLatencyPanel);
        networkSettings.add(nodeSizePanel);
        networkSettings.add(linkSizePanel);
        networkSettings.add(phitSizePanel);
        networkSettings.add(flitSizePanel);
        networkSettings.add(packetSizePanel);

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
                    network = new Mesh(selectedNodes);
                    break;
                case "mesh":
                    network = new Mesh(selectedNodes);
                    break;
                case "bus":
                    network = new Bus(selectedNodes);
                    break;
                }

//                topologyFrame = new TopologyInternalFrame(network.drawTopology());

                topologyFrame.setVisible(true);

                desktopPane.add(topologyFrame);
            }
        });
    }

    private void createRouterMAPanel(JPanel routerMA)
    {
        routerMA.setLayout(new BoxLayout(routerMA, BoxLayout.Y_AXIS));

        JPanel topPanel = new JPanel(new GridLayout(4, 2));
        JPanel sliderPanel = new JPanel(new GridLayout(3, 1));

        /**
         * This panel is divided into a top panel and a bottom panel. The bottom panel contains
         * the slider and the Credit based flow control checkbox. The top panel contains
         * everything else.
         */

        JLabel bufferDesignLabel = new JLabel("Buffer Design");
        topPanel.add(bufferDesignLabel);

        JComboBox<String> bufferDesignBox = new JComboBox<>();
        bufferDesignBox.addItem("1 buffer w/ 0 VCs per Node");
        bufferDesignBox.addItem("1 buffer w/ 2 VCs per Node");
        bufferDesignBox.addItem("1 buffer w/ 3 VCs per Node");
        bufferDesignBox.addItem("#buffers = #inputs w/ 0 VCs per Node");
        bufferDesignBox.addItem("#buffers = #inputs w/ 2 VCs per Node");
        bufferDesignBox.addItem("#buffers = #inputs w/ 3 VCs per Node");
        bufferDesignBox.addItem("Bufferless");
        topPanel.add(bufferDesignBox);

        JLabel bufferSizeLabel = new JLabel("Buffer Size");
        topPanel.add(bufferSizeLabel);

        JTextField bufferSizeField = new JTextField("4");
        topPanel.add(bufferSizeField);

        JLabel flowControlLabel = new JLabel("Flow Control");
        topPanel.add(flowControlLabel);

        JComboBox<String> flowControlBox = new JComboBox<>();
        flowControlBox.addItem("Round Robin");
        flowControlBox.addItem("Priority");
        flowControlBox.addItem("Wormhole");
        topPanel.add(flowControlBox);

        JLabel pipelineTypeLabel = new JLabel("Pipeline Type");
        topPanel.add(pipelineTypeLabel);

        JComboBox<String> pipelineTypeBox = new JComboBox<>();
        pipelineTypeBox.addItem("Fixed Pipeline");
        pipelineTypeBox.addItem("Flexible Pipeline");
        topPanel.add(pipelineTypeBox);

        JLabel pipelineStageLabel = new JLabel("Number of Pipeline Stages");
        sliderPanel.add(pipelineStageLabel);

        JSlider pipelineStageSlider = new JSlider(JSlider.HORIZONTAL,0, 5, 4);
        pipelineStageSlider.setMajorTickSpacing(1);
        pipelineStageSlider.setPaintTicks(true);
        pipelineStageSlider.setPaintLabels(true);
        sliderPanel.add(pipelineStageSlider);

        JCheckBox creditBasedCheck = new JCheckBox("Turn On Credit Based Flow Control");
        creditBasedCheck.setSelected(true);
        sliderPanel.add(creditBasedCheck);

        JPanel okayCancel = new JPanel();

        JButton okay = new JButton("Okay");
        okayCancel.add(okay);

        JButton cancel = new JButton("Cancel");
        okayCancel.add(cancel);

        routerMA.add(topPanel);
        routerMA.add(sliderPanel);
        routerMA.add(okayCancel);

    }

    private void createStatWindow(JPanel statWindow){
        statWindow.setLayout(new BoxLayout(statWindow, BoxLayout.Y_AXIS));

        JLabel latencyStatsLabel = new JLabel("Latency Statistics");
        statWindow.add(latencyStatsLabel);

        JPanel latencyStatsCheckPanel = new JPanel(new FlowLayout());

        JCheckBox clockCycles = new JCheckBox("Clock Cycles");
        clockCycles.setSelected(true);
        latencyStatsCheckPanel.add(clockCycles);

        JCheckBox hops = new JCheckBox("Hops");
        hops.setSelected(true);
        latencyStatsCheckPanel.add(hops);

        statWindow.add(latencyStatsCheckPanel);

        JLabel otherStatsLabel = new JLabel("Other Statistics");
        statWindow.add(otherStatsLabel);

        JPanel otherStatsPanel = new JPanel(new FlowLayout());

        JCheckBox bandwidth = new JCheckBox("Bandwidth");
        otherStatsPanel.add(bandwidth);

        JCheckBox throughput = new JCheckBox("Throughput");
        otherStatsPanel.add(throughput);

        JCheckBox droppedFlits = new JCheckBox("Dropped Flits");
        droppedFlits.setSelected(true);
        otherStatsPanel.add(droppedFlits);

        JCheckBox area = new JCheckBox("Area");
        otherStatsPanel.add(area);

        JCheckBox power = new JCheckBox("Power");
        otherStatsPanel.add(power);

        statWindow.add(otherStatsPanel);

        JPanel okayCancelPanel = new JPanel();

        JButton okay = new JButton("Okay");
        okayCancelPanel.add(okay);

        JButton cancel = new JButton("Cancel");
        okayCancelPanel.add(cancel);

        statWindow.add(okayCancelPanel);

    }
}
