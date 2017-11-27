package main.java;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class RouterDiagram extends JInternalFrame
{
    static final int xOffset = 25, yOffset = 25;

    private Router currentRouter;

    JPanel routerPanel = new JPanel();

    private JPanel diagram;

    public RouterDiagram(Router inputRouter)
    {
        super("Router " + inputRouter.getRouterNumber(),
                true, //resizable
                true, //closable
                true, //maximizable
                true);//iconifiable

        currentRouter = inputRouter;

        //setSize(500, 350);
        setLayout(new BorderLayout());

        //Set the window's location.
        setLocation(xOffset, yOffset);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        //diagram = addDiagram();
        //add(diagram, BorderLayout.CENTER);



        pack();
    }

    public JPanel updateDiagram()
    {
        JPanel routerPanel = new JPanel();

//        ColoredRectangle rect = new ColoredRectangle();
//        rect.setLocation(20, 50);
//        routerPanel.add(rect);

        routerPanel.add(addDiagram());



        return routerPanel;
    }

    public JPanel addDiagram()
    {
        routerPanel = new JPanel();
        routerPanel.setLayout(null);

        try {
            BufferedImage diagram = ImageIO.read(this.getClass().getResource("/main/resources/routerDiagram.png"));
            JLabel diagramLabel = new JLabel(new ImageIcon(diagram));

            // Sets the size of the container that holds the diagram
            routerPanel.setPreferredSize(new Dimension(diagram.getWidth(), diagram.getHeight()));

            // Sets the size of the JLabel to be the same as the image
            diagramLabel.setSize(diagram.getWidth(), diagram.getHeight());

            addChannels(routerPanel);

            routerPanel.add(diagramLabel);

        } catch (IOException ex) {
            System.out.println("EXCEPTION: " + ex);
        }

        return routerPanel;
    }

    public void addRectangle(ColoredRectangle rectangle)
    {
        routerPanel.add(rectangle, 0);
    }

    /**
     * Adds a channel for every router that neighbors the current one, and adds a channel for home
     * @param panelInput    Is the panel that the channels will be put on
     */
    public void addChannels(JPanel panelInput)
    {
        int channelIndex = 0;

        addChannel(panelInput, channelIndex++, "Home");

        if(currentRouter.getRouterNorth() != -1) {
            addChannel(panelInput, channelIndex++, "North");
        }
        if (currentRouter.getRouterSouth() != -1) {
            addChannel(panelInput, channelIndex++, "South");
        }
        if (currentRouter.getRouterEast() != -1) {
            addChannel(panelInput, channelIndex++, "East");
        }
        if (currentRouter.getRouterWest() != -1) {
            addChannel(panelInput, channelIndex++, "West");
        }
    }

    /**
     * Adds an input channel with 16 slots, and an output channel with 1 slot
     * @param panelInput The panel that it will be added to
     * @param position  The vertical position in the diagram it's in, 0 being at the very top
     * @param label The label that's above the input channel
     */
    private void addChannel(JPanel panelInput, int position, String label)
    {
        int locationY = position * 50;

        try {
            BufferedImage inputNode = ImageIO.read(this.getClass().getResource("/main/resources/inputNode.png"));
            BufferedImage ouputNode = ImageIO.read(this.getClass().getResource("/main/resources/outputNode.png"));

            JLabel channelLabel = new JLabel();
            channelLabel.setText(label);
            JLabel inputLabel = new JLabel(new ImageIcon(inputNode));
            JLabel outputLabel = new JLabel(new ImageIcon(ouputNode));


            channelLabel.setFont(new Font("Arial",Font.PLAIN,12));
            inputLabel.setSize(new Dimension(inputNode.getWidth(), inputNode.getHeight()));
            outputLabel.setSize(new Dimension(ouputNode.getWidth(), ouputNode.getHeight()));

            channelLabel.setBounds(20, 17 + locationY, 200, 50);
            inputLabel.setLocation(20, 50 + locationY);
            outputLabel.setLocation(515 , 50 + locationY);

            panelInput.add(channelLabel);
            panelInput.add(inputLabel);
            panelInput.add(outputLabel);
        } catch (IOException ex) {
            System.out.println("EXCEPTION: " + ex);
        }
    }
}
