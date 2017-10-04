package main.java;

import javafx.scene.shape.Circle;

public class Node {
    private int nodeNum;    //An integer to keep track of the nodes
    private int x;
    private int y;
    //The below values map the points for the lines to connect the topological nodes
    private int northX;
    private int northY;
    private int eastX;
    private int eastY;
    private int southX;
    private int southY;
    private int westX;
    private int westY;

    //TODO: figure out how to incorporate virtual channels, buffers, and the rest of the stuff that should go here

    public Node(int nodeNumber, int centerX, int centerY){
        nodeNum = nodeNumber;
        x = centerX;
        y = centerY;
        northX = centerX;
        northY = centerY + 25;  //This offset number must match the value for the height and width of the circle
        eastX = centerX + 25;
        eastY = centerY;
        southX = centerX;
        southY = centerY - 25;
        westX = centerX - 25;
        westY = centerY;
    }

    public int getNodeNum(){
        return nodeNum;
    }

    public int getX(){ return x; }

    public int getY(){ return y; }

    public int getNorthX(){
        return northX;
    }

    public int getNorthY() {
        return northY;
    }

    public int getEastX(){
        return eastX;
    }

    public int getEastY(){
        return eastY;
    }

    public int getSouthX(){
        return southX;
    }

    public int getSouthY(){
        return southY;
    }

    public int getWestX(){
        return westX;
    }

    public int getWestY(){
        return westY;
    }
}
