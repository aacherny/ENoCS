package main.java;

public class Packet {
    private int destination;    //Placeholder until I can figure out how to correctly represent the destination
    private int packetID;       //Every packet gets a unique identifier
    private int size;           //Every packet is either 1 or 4 flits long

    public Packet(int dest, int id, int s){
        destination = dest;
        packetID = id;
        size = s;

        for(int i = 0; i < size; i++){
            new Flit();
        }
    }


}
