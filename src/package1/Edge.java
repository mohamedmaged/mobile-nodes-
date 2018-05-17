package package1;

public class Edge {

    private String id;
    private Node source;
    private Node destination;
    private int weight; // weight=cost/message: 1mw

    public Edge(String id, Node source, Node destination, int weight) {
        this.id = id;
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    public String getId() {
        return this.id;
    }

    public Node getSource() {
        return this.source;
    }

    public Node getDestination() {
        return this.destination;
    }

    public int getWeight() {
        return this.weight;
    }

    @Override
    public String toString() {
        return source + "-" + destination;
    }

}
