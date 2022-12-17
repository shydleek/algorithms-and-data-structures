package by.bsu.algorithms.lab3.task1.algorithm;

import by.bsu.algorithms.lab3.task1.entity.Graph;
import by.bsu.algorithms.lab3.task1.entity.Vertex;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class BreadthFirstSearch {
    public static void doSearch(Graph graph, int startVertexIndex) {
        doSearch(graph.getVertices(), startVertexIndex);
    }

    public static void doSearch(List<Vertex> vertices, int startVertexIndex) {
        PriorityQueue<Vertex> verticesToVisit = new PriorityQueue<>(Comparator.comparingInt(Vertex::getMark));
        List<Vertex> visited = new ArrayList<>();
        Vertex startVertex = vertices.get(startVertexIndex);
        verticesToVisit.add(startVertex);
        startVertex.mark(0);
        while (!verticesToVisit.isEmpty()) {
            Vertex currentVertex = verticesToVisit.poll();
            visited.add(currentVertex);
            currentVertex.getAdjacentVertices()
                    .forEach(v -> {
                        if (!v.isMarked()) {
                            v.mark(currentVertex.getMark() + 1);
                        }
                        if (!visited.contains(v)) {
                            verticesToVisit.add(v);
                        }
                    });
        }
    }

    private BreadthFirstSearch() {}
}