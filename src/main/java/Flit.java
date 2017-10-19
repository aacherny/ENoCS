package main.java;

public class Flit {

    private int locationX;
    private int locationY;

    Flit(int locX, int locY) {
        locationX = locX;
        locationY = locY;
    }

    public void setX(int locX) {
        locationX = locX;
    }

    public void setY(int setY) {
        locationY = setY;
    }

    public int getX() {
        return locationX;
    }

    public int getY() {
        return locationY;
    }
}
