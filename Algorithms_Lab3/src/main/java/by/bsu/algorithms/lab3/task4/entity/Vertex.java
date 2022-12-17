package by.bsu.algorithms.lab3.task4.entity;

public class Vertex {
    private final int index;
    private Vertex marker;
    private int mark;
    private Vertex link;

    public Vertex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public Vertex getLink() {
        return link;
    }

    public void setLink(Vertex link) {
        this.link = link;
    }

    public void mark(int mark, Vertex marker) {
        this.mark = mark;
        this.marker = marker;
    }

    public boolean isMarked() {
        return marker != null;
    }

    public void resetMarking() {
        marker = null;
        mark = 0;
    }

    public int getMark() {
        return mark;
    }

    public Vertex getMarker() {
        return marker;
    }

    public boolean isFree() {
        return link == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vertex vertex = (Vertex) o;

        if (index != vertex.index) return false;
        if (marker != null ? marker.index != vertex.marker.index : vertex.marker != null) return false;
        if (mark != vertex.mark) return false;
        return link != null ? link.index == vertex.link.index : vertex.link == null;
    }

    @Override
    public int hashCode() {
        int result = index;
        result = 31 * result + (marker != null ? marker.index : 0);
        result = 31 * result + (link != null ? link.index : 0);
        result = 31 * result + mark;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Vertex{");
        sb.append("index=").append(index);
        sb.append(", link=").append(link);
        sb.append('}');
        return sb.toString();
    }
}