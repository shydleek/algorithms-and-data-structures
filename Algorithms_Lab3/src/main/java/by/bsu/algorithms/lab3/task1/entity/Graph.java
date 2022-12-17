package by.bsu.algorithms.lab3.task1.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class Graph {
    private List<Vertex> vertices;

    public Graph(List<Vertex> vertices) {
        this.vertices = new ArrayList<>(vertices);
    }

    public void addAdjacentVertices(Vertex vertex, Integer...indexes) {
        vertex.addAdjacentVertices(Arrays.stream(indexes)
                .map(i -> vertices.get(i))
                .toList());
    }

    public void resetMarking() {
        vertices.forEach(Vertex::resetMarking);
    }

    public void printVerticesWithMarks() {
        vertices.forEach(v -> System.out.printf("index=%d, mark=%d\n", v.getIndex(), v.getMark()));
    }
}
