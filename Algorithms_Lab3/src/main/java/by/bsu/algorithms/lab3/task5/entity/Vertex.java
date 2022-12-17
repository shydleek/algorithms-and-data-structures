package by.bsu.algorithms.lab3.task5.entity;

public class Vertex {
    private final int index;

    public Vertex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vertex vertex = (Vertex) o;

        return index == vertex.index;
    }

    @Override
    public int hashCode() {
        return index;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Vertex{");
        sb.append("index=").append(index);
        sb.append('}');
        return sb.toString();
    }
}
