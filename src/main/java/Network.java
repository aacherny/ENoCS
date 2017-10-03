package main.java;

public class Network
{
    private String topology;
    protected int nodes;

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

    public int getNodes()
    {
        return nodes;
    }
}
