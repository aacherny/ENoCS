package main.java;

import javax.swing.*;

public class FlowControlInternalFrame extends JInternalFrame{
    public static int openFrameCount = 0;
    static final int xOffset = 25, yOffset = 25;
    public FlowControlInternalFrame(JTextPane textPane){
        super(
                "Network Flow Control",
                true,
                true,
                true,
                false
        );
        ++openFrameCount;
        setSize(500, 350);
        setLocation(xOffset*openFrameCount, yOffset*openFrameCount);

        add(textPane);

        pack();
        setVisible(true);
    }

    public static int getxOffset() {
        return xOffset;
    }

    public static int getyOffset() {
        return yOffset;
    }
}
