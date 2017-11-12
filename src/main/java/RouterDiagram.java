package main.java;

import javax.imageio.ImageIO;
import javax.swing.*;
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

        setSize(500, 350);

        //Set the window's location.
        setLocation(xOffset, yOffset);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        add(addDiagram());

        pack();
        setVisible(true);
    }

    public JPanel addDiagram()
    {
        JPanel routerPanel = new JPanel();

        try {
            BufferedImage image = ImageIO.read(this.getClass().getResource("/main/resources/routerDiagram.png"));
            JLabel picLabel = new JLabel(new ImageIcon(image));
            routerPanel.add(picLabel);
        } catch (IOException ex) {
            System.out.println("EXCEPTION: " + ex);
        }

        return routerPanel;
    }
}
