package main.java;

import javax.swing.UIManager;

/**
 * ENOCS is a network-on-chip network simulator showing the routes of packets as they travel through bus, mesh, and torus networks.
 *
 * @author  Alex Cherny and Sean Copp
 * @version 1.0.0
 * @since   2017-09-25
 */
public class Main
{
    public static void main(String [ ] args)
    {
        try {   // Changes the LookAndFeel of the panel to Windows/Mac instead of Java
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception x) {}

        OuterJFrame outerFrame = new OuterJFrame();
        outerFrame.createOuterJFrame();

    }
}