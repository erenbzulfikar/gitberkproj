package ngordnet.main;

import java.util.*;

public class Graph<T> {

    private HashMap graph = new HashMap<Node, TreeMap<Integer, String>>();
    private HashMap graphStr = new HashMap<String, ArrayList<Node>>();
    private HashMap graphID = new HashMap<Integer, ArrayList<Node>>();

    protected class Node {
        //int key;
        Integer id;
        String value;

        ArrayList<Node> children;

        private Node(int k, String v) {
            children = new ArrayList<Node>();
            id = k;
            value = v;
        }


    }

    public void Graph() { //constructor
        this.graph = new HashMap<Node, TreeMap<Integer, String>>();
        this.graphStr = new HashMap<String, ArrayList<Node>>();
        this.graphID = new HashMap<Integer, ArrayList<Node>>();
    }

    public void printChildList(ArrayList<Node> list) {
        for (Node node : list) {
            System.out.println(node.value);
        }
    }

    public ArrayList<Node> getNodesbyID(int ID) {
        return (ArrayList<Node>) this.graphID.get(ID);
    }

    public HashSet<String> findAllChildren(String key) {
        ArrayList<Node> words = (ArrayList<Node>) this.graphStr.get(key);
        HashSet<String> children = new HashSet<>();
        if (words != null) {
            for (Node word : words) {
                DFS(word, children);

            }
        }
        return children;
    }

    public void DFS(Node node, HashSet<String> list) {
        ArrayList<Node> synonyms = getNodesbyID(node.id);
        for (Node synonym : synonyms) {
            list.add(synonym.value);
        }
        for (Node child : node.children) {
            DFS(child, list);
        }

    }

    public void createNode(int ID, String word) {
        Node node = new Node(ID, word);
        if (nodesExists(word)) {
            ArrayList<Node> nodes = (ArrayList<Node>) graphStr.get(word);
            nodes.add(node);
        } else {
            ArrayList<Node> lst = new ArrayList<>();
            lst.add(node);
            graphStr.put(word, lst);
        }

        if (graphID.containsKey(ID)) {
            ArrayList<Node> list = (ArrayList<Node>) graphID.get(ID);
            list.add(node);
        } else {
            ArrayList<Node> lst = new ArrayList<>();
            lst.add(node);
            graphID.put(ID, lst);
        }
    }

    public boolean nodesExists(String key) {
        return graphStr.containsKey(key);
    }

    public ArrayList<Node> getNodes(String word) {
        return (ArrayList<Node>) graphStr.get(word);
    }

    public void printNode(String word) {
        if (nodesExists(word)) {
            ArrayList<Node> nodes = getNodes(word);
            for (Node node : nodes) {
                printNode(node);
            }
        }
    }

    public void printNode(Node node) {
        System.out.println();
        System.out.println("Name: " + node.value);
        System.out.println("IDs: " + node.id);
        //for (Integer id: node.idList) {
        //System.out.print(id + " ");
        //}
        /*System.out.println("Children: ");
        for (Node child: node.children) {
            System.out.print(child. + " ");
        }*/
        System.out.println();

    }


    public void addEdge2(T source, T destination) {
        LinkedList<T> list = new LinkedList<>();
        if (!graph.containsKey(source)) { //creates source if doesnt exist
            createVertex(source);
        }
        if (graph.containsKey(source) && graph.containsKey(destination)) {//connects existing source with destination if both exist
            LinkedList<T> l = (LinkedList<T>) graph.get(source);
            l.addLast(destination);
        }
        if (graph.containsKey(source) && !graph.containsKey(destination)) { //creates destination and connects source with destination
            createVertex(destination);
            LinkedList<T> ll = (LinkedList<T>) graph.get(source);
            ll.addLast(destination);
        }

    }

    public void createVertex(T vertex) {
        graph.put(vertex, new LinkedList<T>());

    }


}
