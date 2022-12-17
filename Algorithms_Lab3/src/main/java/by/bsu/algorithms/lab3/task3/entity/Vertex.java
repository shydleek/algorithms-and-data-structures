package by.bsu.algorithms.lab3.task3.entity;

import lombok.Data;

@Data
public class Vertex {
    private int index;
    private String name;

    public Vertex(int index) {
        this.index = index;
        this.name = Integer.toString(index);
    }

    public Vertex(int index, String name) {
        this.index = index;
        this.name = name;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vertex vertex = (Vertex) o;

        if (index != vertex.index) return false;
        return name.equals(vertex.name);
    }

    @Override
    public int hashCode() {
        int result = index;
        result = 31 * result + name.hashCode();
        return result;
    }
}
