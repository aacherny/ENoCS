package main.java;

public class Flit {

    private int index; // the position of the flit inside of a packet, 0 = single flit packet, 1-4 = four flit packet
    private int locationX;
    private int locationY;
    private int destinationX;
    private int destinationY;

    Flit(int inputIndex, int locX, int locY, int destX, int destY) {
        index = inputIndex;
        locationX = locX;
        locationY = locY;
        destinationX = destX;
        destinationY = destY;
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
