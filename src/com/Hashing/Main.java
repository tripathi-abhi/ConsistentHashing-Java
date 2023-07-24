package com.Hashing;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException {

        String sellerIdsString = readFileAsString("C:\\Users\\abhit\\IdeaProjects\\LoadBalancer\\Data\\clientKeys.txt");

        String[] clientKeys = sellerIdsString.split(",");

        Map<String,Integer> countMap = new HashMap<>();

        List<Node> nodes = List.of(
                new Node("209.62.36.120","80",(short) 1),
                new Node("70.127.145.251","80",(short) 1),
                new Node("140.254.24.153","80",(short) 1),
                new Node("121.130.138.137","80",(short) 1),
                new Node("209.133.230.16","80",(short) 1)
        );

//        List<Node> nodes = List.of(
//                new Node("70.127.145.251","8080",(short) 1),
//                new Node("70.127.145.251","8081",(short) 1),
//                new Node("70.127.145.251","9091",(short) 1),
//                new Node("70.127.145.251","10800",(short) 1),
//                new Node("70.127.145.251","3000",(short) 1)
//        );

        ContinuumTable<Node> ring = new ContinuumTable<>(nodes, "SHA1");

        for(var clientKey:clientKeys){
            Node node = (Node)ring.getNode(clientKey);
            String nodeKey = node.getKey();
            if(!countMap.containsKey(nodeKey)){
                countMap.put(nodeKey,0);
            } else {
                countMap.put(nodeKey, countMap.get(nodeKey) + 1);
            }
        }

        for(var countSet:countMap.entrySet())
            System.out.printf("%s : %d\n",countSet.getKey(),countSet.getValue());
    }

    public static String readFileAsString(String path) throws IOException {
        String data = new String(Files.readAllBytes(Paths.get(path)));
        return data;
    }
}
