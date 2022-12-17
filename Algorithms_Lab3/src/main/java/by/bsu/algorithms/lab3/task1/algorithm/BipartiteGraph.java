package by.bsu.algorithms.lab3.task1.algorithm;

import by.bsu.algorithms.lab3.task1.entity.Graph;
import by.bsu.algorithms.lab3.task1.entity.Vertex;
import by.bsu.algorithms.util.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class BipartiteGraph {
    public static boolean isBipartiteGraph(Graph graph) {
        PriorityQueue<Vertex> verticesToVisit = new PriorityQueue<>(Comparator.comparingInt(Vertex::getMark));
        List<Vertex> visited = new ArrayList<>();
        Vertex startVertex = graph.getVertices().get(0);
        verticesToVisit.add(startVertex);
        startVertex.mark(0);
        while (!verticesToVisit.isEmpty()) {
            Vertex currentVertex = verticesToVisit.poll();
            visited.add(currentVertex);
            for (Vertex v : currentVertex.getAdjacentVertices()) {
                if (!v.isMarked()) {
                    v.mark(currentVertex.getMark() + 1);
                }
                else if (!visited.contains(v) && (currentVertex.getMark() - v.getMark()) == 0) { // цикл нечётной длины
                    graph.resetMarking();
                    return false;
                }
                if (!visited.contains(v)) {
                    verticesToVisit.add(v);
                }
            }
        }
        graph.resetMarking();
        return true;
    }

    public static Pair<List<Integer>, List<Integer>> splitIntoParts(Graph graph) {
        if (!isBipartiteGraph(graph)) {
            throw new IllegalArgumentException("This function is applicable only for Bipartite graph");
        }
        BreadthFirstSearch.doSearch(graph, 0);
        List<Integer> firstPart = graph.getVertices()
                .stream()
                .filter(v -> v.getMark() % 2 == 0)
                .map(Vertex::getIndex)
                .toList();
        List<Integer> secondPart = graph.getVertices()
                .stream()
                .filter(v -> v.getMark() % 2 == 1)
                .map(Vertex::getIndex)
                .toList();
        return new Pair<>(firstPart, secondPart);
    }

    private BipartiteGraph() {}
}