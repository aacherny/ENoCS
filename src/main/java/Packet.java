package main.java;

public class Packet {

    private int destinationX;    //Placeholder until I can figure out how to correctly represent the destination
    private int destinationY;
    private int packetID;       //Every packet gets a unique identifier
    private int size;           //Every packet is either 1 or 4 flits long

    public Packet(int destX, int destY, int id, int s){
        destinationX = destX;
        destinationY = destY;
        packetID = id;
        size = s;

        for(int i = 0; i < size; i++){
            //new Flit();
        }
    }


}
