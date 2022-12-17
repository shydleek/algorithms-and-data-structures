package by.bsu.algorithms.lab3.task3.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class Graph {
    private List<Edge> edges;
    private Set<Vertex> vertices;

    public Graph() {
        edges = new ArrayList<>();
        vertices = new HashSet<>();
    }

    public Graph(List<Edge> edges) {
        this.edges = edges;
        vertices = new HashSet<>();
        edges.forEach(this::addEdgeVertices);
    }

    public void addVertex(Vertex vertex) {
        vertices.add(vertex);
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
        addEdgeVertices(edge);
    }

    public double getWeight() {
        return edges.stream()
                .mapToDouble(Edge::getWeight)
                .sum();
    }

    public void addAll(Collection<Edge> edges) {
        edges.forEach(this::addEdge);
    }

    public boolean contains(Vertex vertex) {
        return vertices.contains(vertex);
    }

    public boolean contains(Edge edge) {
        return edges.contains(edge);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Graph that = (Graph) o;

        if (edges != null ? !edges.equals(that.edges) : that.edges != null) return false;
        return vertices != null ? vertices.equals(that.vertices) : that.vertices == null;
    }

    @Override
    public int hashCode() {
        int result = edges != null ? edges.hashCode() : 0;
        result = 31 * result + (vertices != null ? vertices.hashCode() : 0);
        return result;
    }

    private void addEdgeVertices(Edge edge) {
        vertices.add(edge.getFirstVertex());
        vertices.add(edge.getSecondVertex());
    }
}
