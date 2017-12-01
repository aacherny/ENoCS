package main.java;

public class Flit {

    private int locationX;
    private int locationY;
    private int destinationX;
    private int destinationY;
    private int type; //0 = header, 1 = body, 2 = tail, 3 = head and tail
    private int packetID; //What packet does the flit belong to

    Flit(int locX, int locY, int destX, int destY, int t, int pID) {
        locationX = locX;
        locationY = locY;
        destinationX = destX;
        destinationY = destY;
        type = t;
        packetID = pID;
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
