package by.bsu.algorithms.lab2.hashtable.future;

public abstract class AbstractHashTable<K, V> implements CustomMap<K, V> {
    protected int initialCapacity = 16;
    protected Pair<K, V>[] table;
    protected int size;

    protected AbstractHashTable() {
        table = (Pair<K, V>[]) new Pair[initialCapacity];
    }

    protected AbstractHashTable(int initialCapacity) {
        table = (Pair<K, V>[]) new Pair[initialCapacity];
    }

    protected abstract int computeHash(K key);

    @Override
    public final boolean isEmpty() {
        return size == 0;
    }

    @Override
    public final int size() {
        return size;
    }
}