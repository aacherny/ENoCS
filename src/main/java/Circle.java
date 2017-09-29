package main.java;

import javax.swing.*;
import java.awt.*;

import static java.lang.Math.sqrt;

public class Circle extends JPanel {
    private int x = 25;
    private int y = 25;
    private int direction;
    private int northx = x;
    private int northy = y + 25;
    private int eastx = x + 25;
    private int easty = y;
    private int southx = x;
    private int southy = y - 25;
    private int westx = x - 25;
    private int westy = y;
    private int nodes;

    Circle(int nodes){
        this.nodes = nodes;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void paintComponent(Graphics c){
        super.paintComponent(c);

        c.setColor(Color.BLACK);

        /**Below draws the lines that will connect the
         * grid of circles
         */



        /**
         * Below draws a square of circles
         */
        for (int i = 0; i < sqrt(this.nodes); i++){
            for (int j = 0; j < sqrt(this.nodes); j++){
                setX(this.x + 50);
                c.drawOval(this.x, this.y, 25, 25);
            }
            setX(this.x = 25);
            setY(this.y + 50);
        }


    }
}
