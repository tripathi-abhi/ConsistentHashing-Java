package com.Hashing;

import java.util.*;

public class ContinuumTable<T extends INode> {

    private Map<String,ArrayList<String>> instanceMap = new HashMap<>();
    private SortedMap<Long,T> ring = new TreeMap<>();
    private IHash hashFunc;

    ContinuumTable(List<T> nodes){
        this(nodes, "MD5");
    }

    ContinuumTable(List<T> nodes, String hashType){
        setHashFunc(new HashService(hashType));
        initRing(nodes);
    }

    private void initRing(List<T> nodes){
        if(Objects.isNull(nodes))
            throw new IllegalArgumentException("No server nodes provided!");

        for(var physicalNode:nodes ) {
            addVirtualNodes(physicalNode);
        }
    }

    private void addVirtualNodes(T physicalNode){
        assert physicalNode != null;
        ArrayList<String> virtualInstanceIDs = new ArrayList<>();

        for(int i=0;i<200*physicalNode.getWeight();i++) {
            String instanceID = UUID.randomUUID().toString();
            virtualInstanceIDs.add(instanceID);
            String virtualInstanceKey = physicalNode.getKey()+"_"+instanceID;
            long hashed = hashFunc.doHash(virtualInstanceKey);
            if (ring.containsKey(hashed))
                throw new IllegalStateException("Duplicate server keys!");

            ring.put(hashed, physicalNode);
        }

        instanceMap.put(physicalNode.getKey(),virtualInstanceIDs);
    }

    public void removeNode(T physicalNode){
        assert physicalNode != null;

        for(var virtualInstanceID:instanceMap.get(physicalNode.getKey())) {
            String virtualInstanceKey = physicalNode.getKey()+"_"+virtualInstanceID;
            long hashed = hashFunc.doHash(virtualInstanceKey);
            if (!ring.containsKey(hashed))
                throw new IllegalArgumentException("Continuum has no such node!");
            ring.remove(hashed);
        }
    }

    public INode getNode(String key){
        assert key != null;

        long hashed = hashFunc.doHash(key);
        var tailMap = ring.tailMap(hashed);

        long hashVal = tailMap.isEmpty() ? ring.firstKey() : tailMap.firstKey();
        INode node = ring.get(hashVal);
        return node;
    }

    public void setHashFunc(IHash hashFunc) {
        this.hashFunc = hashFunc;
    }
}
