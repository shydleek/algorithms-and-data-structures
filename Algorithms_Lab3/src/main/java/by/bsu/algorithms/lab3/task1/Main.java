package by.bsu.algorithms.lab3.task1;

import by.bsu.algorithms.lab3.task1.algorithm.BipartiteGraph;
import by.bsu.algorithms.lab3.task1.algorithm.BreadthFirstSearch;
import by.bsu.algorithms.lab3.task1.algorithm.ConnectedComponents;
import by.bsu.algorithms.lab3.task1.algorithm.EulerCycle;
import by.bsu.algorithms.lab3.task1.entity.Graph;
import by.bsu.algorithms.lab3.task1.entity.Vertex;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
//        findConnectedComponents();
//        breadthFirstSearch();
        eulerCycle();
//        bipartiteGraph();
    }

    public static void findConnectedComponents() {
        int vertexCount = 7;
        List<Vertex> vertices = new ArrayList<>();
        for (int i = 0; i < vertexCount; i++) {
            vertices.add(new Vertex(i));
        }
        Graph graph = new Graph(vertices);
        graph.addAdjacentVertices(vertices.get(0), 1, 2);
        graph.addAdjacentVertices(vertices.get(1), 0, 2);
        graph.addAdjacentVertices(vertices.get(2), 0, 1);
        graph.addAdjacentVertices(vertices.get(3), 5);
        graph.addAdjacentVertices(vertices.get(4), 5);
        graph.addAdjacentVertices(vertices.get(5), 3, 4);
        List<Graph> components = ConnectedComponents.findConnectedComponents(graph);
        components.forEach(System.out::println);
    }

    public static void breadthFirstSearch() {
        int vertexCount = 6;
        List<Vertex> vertices = new ArrayList<>();
        for (int i = 0; i < vertexCount; i++) {
            vertices.add(new Vertex(i));
        }
        Graph graph = new Graph(vertices);
        graph.addAdjacentVertices(vertices.get(0), 1, 2);
        graph.addAdjacentVertices(vertices.get(1), 0, 2, 3);
        graph.addAdjacentVertices(vertices.get(2), 0, 1, 3, 4);
        graph.addAdjacentVertices(vertices.get(3), 1, 2, 5);
        graph.addAdjacentVertices(vertices.get(4), 2, 4);
        graph.addAdjacentVertices(vertices.get(5), 3, 4);
        BreadthFirstSearch.doSearch(graph,0);
        graph.printVerticesWithMarks();
    }

    public static void eulerCycle() {
        int vertexCount = 8;
        List<Vertex> vertices = new ArrayList<>();
        for (int i = 0; i < vertexCount; i++) {
            vertices.add(new Vertex(i));
        }
        Graph graph = new Graph(vertices);
        graph.addAdjacentVertices(vertices.get(0), 1, 2);
        graph.addAdjacentVertices(vertices.get(1), 0, 2, 7, 6);
        graph.addAdjacentVertices(vertices.get(2), 0, 1, 3, 4);
        graph.addAdjacentVertices(vertices.get(3), 2, 4);
        graph.addAdjacentVertices(vertices.get(4), 2, 3, 6, 5);
        graph.addAdjacentVertices(vertices.get(5), 4, 6);
        graph.addAdjacentVertices(vertices.get(6), 1, 7, 4, 5);
        graph.addAdjacentVertices(vertices.get(7), 1, 6);
        System.out.println(EulerCycle.findSimpleLoops(graph));
        System.out.println(EulerCycle.findEulerCycle(graph));
    }

    public static void bipartiteGraph() {
        int vertexCount = 4;
        List<Vertex> vertices = new ArrayList<>();
        for (int i = 0; i < vertexCount; i++) {
            vertices.add(new Vertex(i));
        }
        Graph graph = new Graph(vertices);
        graph.addAdjacentVertices(vertices.get(0), 1, 3);
        graph.addAdjacentVertices(vertices.get(1), 0, 2);
        graph.addAdjacentVertices(vertices.get(2), 1, 3);
        graph.addAdjacentVertices(vertices.get(3), 0, 2);
        System.out.println(BipartiteGraph.isBipartiteGraph(graph));
        System.out.println(BipartiteGraph.splitIntoParts(graph));
    }
}