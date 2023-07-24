package com.Hashing;

import java.util.ArrayList;

public class Node implements INode {
    private String physicalIp;
    private String port;
    private short weight;

    public Node(String physicalIp, String port) {
        setPhysicalIp(physicalIp);
        setPort(port);
        setWeight((short) 1);
    }

    public Node(String physicalIp, String port, short weight) {
        setPhysicalIp(physicalIp);
        setPort(port);
        setWeight(weight);
    }

    public void setPhysicalIp(String physicalIp) {
        this.physicalIp = physicalIp;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setWeight(short weight) {
        this.weight = weight;
    }

    @Override
    public String getIp() {
        return physicalIp;
    }

    @Override
    public String getPort() {
        return port;
    }

    @Override
    public String getKey() {
        return physicalIp+":"+port;
    }

    @Override
    public short getWeight() {
        return weight;
    }

}
