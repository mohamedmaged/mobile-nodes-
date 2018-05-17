package package1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Network {

    private static List<Node> nodes;
    private static List<Edge> edges;

    private static Graph graph;
    private static RoutingAlgorithm algorithm;

    private static Cartesian cartesian;

    public static void main(String[] args) {

        // N: number of nodes
        int N =20;
        System.out.println("For N = " + N + ":");
        execute(N);
    }

    public static void execute(int N) {
        initializeNodes(N);
        initializeEdges(nodes);

        graph = new Graph(nodes, edges);
        algorithm = new RoutingAlgorithm(graph);
        cartesian = new Cartesian(nodes, edges);

        int totalPower = 0;
        Random rand = new Random();
        int numberOfMessages = 1000;
        for (int i = 0; i < numberOfMessages; i++) {
            int sourceIndex = rand.nextInt(nodes.size());
            int destinationIndex = rand.nextInt(nodes.size());
            totalPower += nodes.get(sourceIndex).createMessage("Hello", nodes.get(destinationIndex));
        }
        System.out.println("Total Power =" + totalPower+" mw");
        double averagePower = (double) totalPower / numberOfMessages;
        System.out.println("Average power =" + averagePower +" mw");
    }

    public static RoutingAlgorithm getAlgorithm() {
        return algorithm;
    }

    private static void initializeNodes(int N) {
        nodes = new ArrayList<>();

        Random rand = new Random();
        for (int i = 0; i < N; i++) {
            Node temp = new Node("Node_" + i, rand.nextInt(100), rand.nextInt(100));
            nodes.add(temp);
            System.out.println(temp.getX() + ", " + temp.getY());
        }

    }

    private static void initializeEdges(List<Node> nodes) {
        edges = new ArrayList<>();

        for (int i = 0; i < nodes.size(); i++) {
            Node node1 = nodes.get(i);
            for (int j = i + 1; j < nodes.size(); j++) {
                Node node2 = nodes.get(j);
                //range 20m
                if (node1.distance(node2) <= 20) {
                    Edge edge = new Edge("E1", node1, node2, 1);
                    edges.add(edge);
                }
            }
        }
    }
}
