package by.bsu.algorithms.util;

public class Counter {
    private int value;

    public Counter() {}

    public Counter(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int addAndGet(int addend) {
        value += addend;
        return value;
    }
}
