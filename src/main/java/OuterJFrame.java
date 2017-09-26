package main.java;

import javax.swing.JFrame;

/**
 * Class for the outer window that holds other windows inside it
 */
public class OuterJFrame
{
    private JFrame outerFrame = new JFrame();

    public OuterJFrame()
    {
        outerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        outerFrame.setTitle("Example GUI");
        outerFrame.setSize(500, 350);
        outerFrame.setLocationRelativeTo(null);

        outerFrame.setVisible(true);
    }
}
