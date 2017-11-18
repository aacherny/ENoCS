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

        add(addDiagram(), BorderLayout.CENTER);

        pack();
        setVisible(true);
    }

    public JPanel addDiagram()
    {
        JPanel routerPanel = new JPanel();
        routerPanel.setLayout(null);
        routerPanel.setPreferredSize(new Dimension(500, 500));

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

    public void addChannels(JPanel panelInput)
    {
        /*
        JLabel channelPanel = new JLabel();
        channelPanel.setLayout(null);
        channelPanel.setBackground(new Color(0,0,0,0));
        channelPanel.setBounds(20, 20, 50, 50);

        Button button = new Button("Yes");
        button.setMaximumSize(new Dimension(25, 25));
        button.setLocation(30, 30);
        channelPanel.add(button);
        */

        if(currentRouter.getRouterNorth() != -1)
        {
            try {
                BufferedImage inputNode = ImageIO.read(this.getClass().getResource("/main/resources/inputNode.png"));
                BufferedImage ouputNode = ImageIO.read(this.getClass().getResource("/main/resources/outputNode.png"));

                JLabel inputLabel = new JLabel(new ImageIcon(inputNode));
                JLabel outputLabel = new JLabel(new ImageIcon(ouputNode));

                inputLabel.setSize(new Dimension(inputNode.getWidth(), inputNode.getHeight()));
                outputLabel.setSize(new Dimension(ouputNode.getWidth(), ouputNode.getHeight()));

                inputLabel.setLocation(20, 50);
                outputLabel.setLocation(515, 50);

                panelInput.add(inputLabel);
                panelInput.add(outputLabel);
            } catch (IOException ex) {
                System.out.println("EXCEPTION: " + ex);
            }
        }
    }
}
