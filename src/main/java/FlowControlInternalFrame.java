package main.java;

import javax.swing.*;

public class FlowControlInternalFrame extends JInternalFrame{
    public static int openFrameCount = 0;
    static final int xOffset = 50, yOffset = 50;
    private JTextArea textArea;

    public FlowControlInternalFrame(JTextArea textAreaIn, int openFrames){
        super(
                "Network Flow Control",
                true,
                true,
                true,
                false
        );
        openFrameCount = openFrames;
        textArea = textAreaIn;
        setSize(500, 350);
        setLocation(xOffset*openFrameCount, yOffset*openFrameCount);
        textArea.append("THIS IS A STRING FORM THE TEXTAREA WITHIN THE FLOWCONTROLINTERNALFRAME");
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
