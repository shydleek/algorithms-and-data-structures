package by.bsu.algorithms.lab3.task1.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

@Getter
@Setter
public class Vertex {
    private final int index;
    @Setter(AccessLevel.NONE)
    private int mark;
    @Setter(AccessLevel.NONE)
    private Vertex marker;
    @Setter(AccessLevel.NONE)
    private boolean isMarked;
    private Set<Vertex> adjacentVertices;

    public Vertex(int index, int mark) {
        this(index);
        this.mark = mark;
    }

    public Vertex(int index) {
        this.index = index;
        adjacentVertices = new HashSet<>();
    }

    public void mark(int mark) {
        this.mark = mark;
        isMarked = true;
    }

    public void mark(int mark, Vertex marker) {
        this.mark = mark;
        this.marker = marker;
        isMarked = true;
    }

    public void addAdjacentVertices(Vertex...vertices) {
        adjacentVertices.addAll(List.of(vertices));
    }

    public void addAdjacentVertices(Collection<Vertex> vertices) {
        adjacentVertices.addAll(vertices);
    }

    public boolean remove(Vertex o) {
        return adjacentVertices.remove(o);
    }

    public Set<Vertex> getAdjacentVertices() {
        return new HashSet<>(adjacentVertices);
    }

    public void setAdjacentVertices(Set<Vertex> adjacentVertices) {
        this.adjacentVertices = new HashSet<>(adjacentVertices);
    }

    public void resetMarking() {
        marker = null;
        mark = 0;
        isMarked = false;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vertex vertex = (Vertex) o;

        if (index != vertex.index) return false;
        if (mark != vertex.mark) return false;
        if (isMarked != vertex.isMarked) return false;
        if (marker != null ? !marker.equals(vertex.marker) : vertex.marker != null) return false;
        return adjacentVertices.containsAll(vertex.adjacentVertices);
    }

    @Override
    public int hashCode() {
        int result = index;
        result = 31 * result + mark;
        for (var vertex: adjacentVertices) {
            result = 31 * result + vertex.index;
            result = 31 * result + vertex.mark;
            result = 31 * result + Boolean.hashCode(vertex.isMarked);
        }
        result = 31 * result + (marker != null ? marker.index : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Vertex{");
        sb.append("index=").append(index);
        sb.append(", mark=").append(mark);
        sb.append(", adjacentVertices=");
        StringJoiner joiner = new StringJoiner(", ", "{", "}");
        adjacentVertices.forEach(v -> joiner.add(Integer.toString(v.index)));
        sb.append(joiner);
        sb.append('}');
        return sb.toString();
    }
}