package main.java;

import javax.swing.*;
import java.awt.*;

public class ColoredRectangle extends JPanel {

    private Color color;
    private int RECT_X = 0;
    private int RECT_Y = 0;
    private int RECT_WIDTH = 8;
    private int RECT_HEIGHT = 19;


    public ColoredRectangle(Color inputColor, int inputRectX, int inputRectY){
        color = inputColor;

        if(0 <= inputRectX && inputRectX <= 15) {
            RECT_Y = 53 + inputRectY * 50;
            RECT_X = 23 + inputRectX * 11;
        } else if (inputRectX == 16) {
            RECT_Y = 41;
            RECT_X = 252;
            RECT_WIDTH = 15;
            RECT_HEIGHT = 248;
        } else if (inputRectX == 17) {
            RECT_Y = 40;
            RECT_X = 326;
            RECT_WIDTH = 15;
            RECT_HEIGHT = 248;
        } else if (inputRectX == 18) {
            RECT_Y = 40;
            RECT_X = 400;
            RECT_WIDTH = 15;
            RECT_HEIGHT = 248;
        } else if (inputRectX == 19) {
            RECT_Y = 40;
            RECT_X = 474;
            RECT_WIDTH = 15;
            RECT_HEIGHT = 248;
        }

        setLocation(RECT_X, RECT_Y);
        setSize(RECT_WIDTH, RECT_HEIGHT);
        setBackground(color);
    }
}