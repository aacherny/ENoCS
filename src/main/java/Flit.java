package main.java;

public class Flit {

    private int locationX;
    private int locationY;
    private int destinationX;
    private int destinationY;

    Flit(int locX, int locY, int destX, int destY) {
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
