package main.java;

import javax.swing.*;

public class StatisticsInternalFrame extends JInternalFrame{

    public static int openFrameCount = 0;
    static final int xOffset = 25, yOffset = 25;

    StatisticsInternalFrame(JTextPane textPane){
        super(
                "Statistics",
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
}
