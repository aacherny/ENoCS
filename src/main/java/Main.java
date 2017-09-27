package main.java;

import javax.swing.UIManager;

public class Main
{
    public static void main(String [ ] args)
    {
        try {   // Changes the LookAndFeel of the panel to Windows/Mac instead of Java
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception x) {}

        Mesh networkMesh = new Mesh(4);

        OuterJFrame outerFrame = new OuterJFrame();
        outerFrame.createOuterJFrame();

    }
}