package main.java;

public class Network
{
    private String topology;
    private int nodes;

    public Network(String inputTopology, int inputNodes)
    {
        // Default values
        topology = inputTopology;
        nodes = inputNodes;
    }

    public String getTopology()
    {
        return topology;
    }
}
