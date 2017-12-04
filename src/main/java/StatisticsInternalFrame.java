package main.java;

import javax.swing.*;
import java.awt.*;

public class StatisticsInternalFrame extends JInternalFrame{

    public static int openFrameCount = 0;
    static final int xOffset = 50, yOffset = 50;
    private JTextArea textArea;

    StatisticsInternalFrame(JTextArea textAreaIn, int openFrames){
        super(
                "Statistics",
                true,
                true,
                true,
                false
        );

        openFrameCount = openFrames;
        textArea = textAreaIn;
        setSize(500, 350);
        setLocation(xOffset*openFrameCount, yOffset*openFrameCount);
        textArea.append("this is a test string from the textArea within the statisticinternalframe");
        add(textArea);
        pack();
        setVisible(true);
    }

    public void resetLocationCascade(int openFrameCount){
        setLocation(xOffset*openFrameCount, yOffset*openFrameCount);
    }

    public void resetLocationTile(int winPosX, int winPosY){
        setLocation(winPosX, winPosY);
    }

    public void append(String textOut){
        textArea.append(textOut);
    }

    public String getText(){
        return textArea.getText();
    }
}
