package by.bsu.algorithms.lab3.task2;

import lombok.Getter;
import lombok.NonNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

@Getter
public class Vertex {
    private final String name;
    private final Map<Vertex, Double> environment = new HashMap<>();

    public Vertex(String name) {
        this.name = name;
    }

    public Vertex(String name, Map<Vertex, Double> environment) {
        this.name = name;
        this.environment.putAll(environment);
    }

    public Map<Vertex, Double> getEnvironment() {
        return Collections.unmodifiableMap(environment);
    }

    public void addAdjacentVertex(@NonNull Vertex vertex, double weight) {
        if (vertex.equals(this)) {
            throw new IllegalArgumentException("vertex not adjacent to itself");
        }
        environment.put(vertex, weight);
    }
    public void addAdjacentVertex(@NonNull Vertex vertex) {
        addAdjacentVertex(vertex, 0);
    }

    public int getVertexDegree() {
        return environment.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vertex vertex = (Vertex) o;

        if (name != null ? !name.equals(vertex.name) : vertex.name != null) return false;
        var selfEnvironment = environment.entrySet();
        var otherEnvironment = vertex.environment.entrySet();
        if (selfEnvironment.size() != otherEnvironment.size()) return false;
        return selfEnvironment.containsAll(otherEnvironment);
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = name != null ? name.hashCode() : 1;
        for (Map.Entry<Vertex, Double> vertexAndWeight: environment.entrySet()) {
            result = result * prime + vertexAndWeight.getKey().name.hashCode();
            result = result * prime + vertexAndWeight.getValue().hashCode();
        }
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Vertex{");
        sb.append("name='").append(name).append('\'');
        sb.append(", environment=");
        StringJoiner joiner = new StringJoiner(", ", "{", "}");
        for (Map.Entry<Vertex, Double> vertexAndWeight: environment.entrySet()) {
            String currentEdge = String.format("(%s,%s)=%s", name, vertexAndWeight.getKey().name, vertexAndWeight.getValue());
            joiner.add(currentEdge);
        }
        sb.append(joiner);
        sb.append("}");
        return sb.toString();
    }
}