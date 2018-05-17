package package1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RoutingAlgorithm {

    private List<Node> nodes;
    private List<Edge> edges;

    private Set<Node> settledNodes;
    private Set<Node> unSettledNodes;

    private Map<Node, Node> predecessors;
    private Map<Node, Integer> distance;

    public RoutingAlgorithm(Graph graph) {
        this.nodes = new ArrayList<>(graph.getNodes());
        this.edges = new ArrayList<>(graph.getEdges());
    }

    public void execute(Node source) {
        settledNodes = new HashSet<Node>();
        unSettledNodes = new HashSet<Node>();

        distance = new HashMap<Node, Integer>();
        predecessors = new HashMap<Node, Node>();

        distance.put(source, 0);

        unSettledNodes.add(source);
        while (unSettledNodes.size() > 0) {
            Node node = getMinimum(unSettledNodes);
            settledNodes.add(node);
            unSettledNodes.remove(node);
            findMinimalDistances(node);
        }
    }

    private Node getMinimum(Set<Node> nodes) {
        Node minimum = null;
        for (Node node : nodes) {
            if (minimum == null) {
                minimum = node;
            } else if (getShortestDistance(node) < getShortestDistance(minimum)) {
                minimum = node;
            }
        }
        return minimum;
    }

    private void findMinimalDistances(Node node) {
        List<Node> adjacentNodes = getNeighbors(node);
        for (Node target : adjacentNodes) {
            if (getShortestDistance(target) > getShortestDistance(node) + getDistance(node, target)) {
                distance.put(target, getShortestDistance(node) + getDistance(node, target));
                predecessors.put(target, node);
                unSettledNodes.add(target);
            }
        }
    }

    private List<Node> getNeighbors(Node node) {
        List<Node> neighbors = new ArrayList<Node>();
        for (Edge edge : edges) {
            if (edge.getSource().equals(node) && !isSettled(edge.getDestination())) {
                neighbors.add(edge.getDestination());
            }
        }
        return neighbors;
    }

    private int getShortestDistance(Node target) {
        Integer d = distance.get(target);
        if (d == null) {
            return Integer.MAX_VALUE;
        } else {
            return d;
        }
    }

    private int getDistance(Node node, Node target) {
        for (Edge edge : edges) {
            if (edge.getSource().equals(node) && edge.getDestination().equals(target)) {
                return edge.getWeight();
            }
        }
        throw new RuntimeException("Should not happen");
    }

    private boolean isSettled(Node node) {
        return settledNodes.contains(node);
    }

    public LinkedList<Node> getPath(Node target) {
        LinkedList<Node> path = new LinkedList<>();
        Node step = target;
        if (predecessors.get(step) == null) {
            return null;
        }
        path.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);
        }
        Collections.reverse(path);
        return path;
    }

    public int startTransmission(Message message) {
        Node source = message.getSource();
        Node destination = message.getDestination();

        int sourceIndex = nodes.indexOf(source);
        int destinationIndex = nodes.indexOf(destination);

        LinkedList<Node> path;

        // check for order
        if (sourceIndex < destinationIndex) {
            this.execute(source);
            path = this.getPath(destination);
            if (path == null) {
                System.out.println(source.toString() + ": No path found, could not reach destination(" + destination.toString() + ")");
            } else {
                forward(path);
            }
        } else {
            // destination comes first in linked list
            this.execute(destination);
            path = this.getPath(source);
            if (path == null) {
                System.out.println(source.toString() + ": No path found, could not reach destination(" + destination.toString() + ")");
            } else {
                Collections.reverse(path);
                forward(path);
            }
        }
        return getPathCost(path);
    }

    private void forward(LinkedList<Node> path) {
        for (int i = 0; i < path.size(); i++) {
            if (i + 1 >= path.size()) {
                break;
            }
            path.get(i + 1).setMessage(path.get(i).getMessage());
        }
    }

    private int getPathCost(LinkedList<Node> path) {
        if (path == null) {
            return 0;
        }

        int unitCost = 1; //1mw
        int numberOfTransmissions = path.size() - 1;
        int totalCost = numberOfTransmissions * unitCost;
        return totalCost;
    }

}
