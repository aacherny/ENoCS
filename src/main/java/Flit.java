package main.java;

import java.awt.*;

/**
 * Flit object that's part of a packet, being passed around between routers until it gets to its destination
 *
 * @author Alex Cherny
 */
public class Flit {

    private int index; // the position of the flit inside of a packet, 0 = single flit packet, 1-4 = four flit packet
    private int locationX;
    private int locationY;
    private int destinationX;
    private int destinationY;
    private Color color;
    private int cycleCreated;

    /**
     * Creates a flit object
     * @param inputIndex        The index of the flit within the packet
     * @param locX              The X location of the flit
     * @param locY              The Y location of the flit
     * @param destX             The X destination of the flit
     * @param destY             The Y destination of the flit
     * @param inputColor        The color of the flit
     * @param inputCycleCreated The cycle number that the flit was created
     */
    Flit(int inputIndex, int locX, int locY, int destX, int destY, Color inputColor, int inputCycleCreated) {
        index = inputIndex;
        locationX = locX;
        locationY = locY;
        destinationX = destX;
        destinationY = destY;
        color = inputColor;
        cycleCreated = inputCycleCreated;
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

    public int getPacketIndex() {
        return index;
    }

    public int getCycleCreated(){
        return cycleCreated;
    }
}
