package main.java;

import javax.swing.*;

public class FlowControlInternalFrame extends JInternalFrame{
    public static int openFrameCount = 0;
    static final int xOffset = 50, yOffset = 50;
    private JTextArea textArea = new JTextArea("");

    public FlowControlInternalFrame(int openFrames){
        super(
                "Network Flow Control",
                true,
                true,
                true,
                false
        );
        openFrameCount = openFrames;
        setSize(500, 350);
        setLocation(xOffset*openFrameCount, yOffset*openFrameCount);
        textArea.append("Flow Control Test");
        add(textArea);

        pack();
        setVisible(true);
    }

    public static int getxOffset() {
        return xOffset;
    }

    public static int getyOffset() {
        return yOffset;
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
