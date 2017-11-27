package main.java;

import javax.swing.*;
import java.awt.*;

public class ColoredRectangle extends JPanel {

    private Color color;
    private int RECT_X = 0;
    private int RECT_Y = 0;
    private static final int RECT_WIDTH = 8;
    private static final int RECT_HEIGHT = 19;


    public ColoredRectangle(Color inputColor, int inputRectX, int inputRectY){
        color = inputColor;
        RECT_X = 23 + inputRectX * 11;
        RECT_Y = 53 + inputRectY * 50;

        setLocation(RECT_X, RECT_Y);
        setSize(RECT_WIDTH, RECT_HEIGHT);
        setBackground(color);
    }
}