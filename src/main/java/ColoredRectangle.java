package main.java;

import javax.swing.*;
import java.awt.*;

public class ColoredRectangle extends JPanel {

    private Color color;
    private int RECT_X = 0;
    private int RECT_Y = 0;
    private static final int RECT_WIDTH = 30;
    private static final int RECT_HEIGHT = 30;


    public ColoredRectangle(Color inputColor, int inputRectX, int inputRectY){
        color = inputColor;
        RECT_X = inputRectX;
        RECT_Y = inputRectY;

        setLocation(RECT_X, RECT_Y);
        setSize(RECT_WIDTH, RECT_HEIGHT);
        setBackground(Color.RED);
    }
}