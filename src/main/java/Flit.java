package main.java;

import java.awt.*;

public class Flit {

    private int index; // the position of the flit inside of a packet, 0 = single flit packet, 1-4 = four flit packet
    private int locationX;
    private int locationY;
    private int destinationX;
    private int destinationY;
    private Color color;

    Flit(int inputIndex, int locX, int locY, int destX, int destY, Color inputColor) {
        index = inputIndex;
        locationX = locX;
        locationY = locY;
        destinationX = destX;
        destinationY = destY;
        color = inputColor;
    }

    public void setLocationX(int locX) {
        locationX = locX;
    }

    public void setLocationY(int setY) {
        locationY = setY;
    }

    public int getIndex() {
        return index;
    }

    public Color getColor() {
        return color;
    }

    public int getLocationX() {
        return locationX;
    }

    public int getLocationY() {
        return locationY;
    }

    public int getDestinationX() {
        return destinationX;
    }

    public int getDestinationY() {
        return destinationY;
    }
}
