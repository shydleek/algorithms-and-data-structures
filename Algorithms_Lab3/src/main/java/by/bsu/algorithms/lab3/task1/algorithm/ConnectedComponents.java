package by.bsu.algorithms.lab3.task1.algorithm;

import by.bsu.algorithms.lab3.task1.entity.Graph;
import by.bsu.algorithms.lab3.task1.entity.Vertex;

import java.util.ArrayList;
import java.util.List;

public class ConnectedComponents {
    public static List<Graph> findConnectedComponents(Graph graph) {
        List<Graph> connectedComponents = new ArrayList<>();
        List<Vertex> vertices = new ArrayList<>(graph.getVertices());
        while (!vertices.isEmpty()) {
            BreadthFirstSearch.doSearch(vertices, 0);
            List<Vertex> visited = vertices.stream()
                    .filter(Vertex::isMarked)
                    .toList();
            vertices.removeAll(visited);
            connectedComponents.add(new Graph(visited));
        }
        graph.resetMarking();
        return connectedComponents;
    }

    private ConnectedComponents() {}
}
