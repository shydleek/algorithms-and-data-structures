package by.bsu.algorithms.lab3.task3.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class Edge {
    private final Vertex firstVertex;
    private final Vertex secondVertex;
    private double weight;

    public Edge(@NonNull Vertex firstVertex, @NonNull Vertex secondVertex, double weight) {
        this.firstVertex = firstVertex;
        this.secondVertex = secondVertex;
        this.weight = weight;
    }

    public Edge(int firstVertex, int secondVertex, double weight) {
        this.firstVertex = new Vertex(firstVertex);
        this.secondVertex = new Vertex(secondVertex);
        this.weight = weight;
    }

    public boolean contains(Vertex vertex) {
        if (vertex == null) {
            return false;
        }
        return vertex.equals(firstVertex) || vertex.equals(secondVertex);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Edge edge = (Edge) o;

        if (Double.compare(edge.weight, weight) != 0) return false;
        if (!firstVertex.equals(edge.firstVertex)) return false;
        return secondVertex.equals(edge.secondVertex);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = firstVertex.hashCode();
        result = 31 * result + secondVertex.hashCode();
        temp = Double.doubleToLongBits(weight);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Edge{");
        sb.append(firstVertex);
        sb.append(", ").append(secondVertex);
        sb.append(", weight=").append(weight);
        sb.append('}');
        return sb.toString();
    }
}