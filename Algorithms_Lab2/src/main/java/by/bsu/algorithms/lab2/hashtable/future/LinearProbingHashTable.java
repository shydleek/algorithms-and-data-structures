package by.bsu.algorithms.lab2.hashtable.future;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class LinearProbingHashTable<K, V> {
    private static final int INITIAL_SIZE = 16;
    private int size = 0;
    private Pair<K, V>[] table;

    public LinearProbingHashTable() {
        table = (Pair<K, V>[]) new Pair[INITIAL_SIZE];
    }

    public LinearProbingHashTable(int initialCapacity) {
        table = (Pair<K, V>[]) new Pair[initialCapacity];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public V put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Key can't be null");
        }
        if (itsTimeToExtend()) {
            extendTable();
        }
        int hash = computeHash(key);
        Pair<K, V> firstElementInChain = table[hash];
        if (firstElementInChain == null) {
            table[hash] = (Pair<K, V>) new Pair(key, value);
            size++;
            return null;
        } else {
            if (firstElementInChain.key.equals(key)) {
                V oldValue = firstElementInChain.value;
                table[hash].value = value;
                return oldValue;
            } else {
                return resolveCollision(hash, key, value);
            }
        }
    }

    public V get(K key) {
        int keyHash = computeHash(key);
        for (int i = keyHash; i < table.length; i++) {
            if (table[i] == null) {
                return null;
            }
            if (table[i].key.equals(key)) {
                return table[i].value;
            }
        }
        for (int i = 0; i < keyHash; i++) {
            if (table[i] == null) {
                return null;
            }
            if (table[i].key.equals(key)) {
                return table[i].value;
            }
        }
        return null;
    }

    public void clear() {
        size = 0;
        for (int i = 0; i < table.length; i++) {
            table[i] = null;
        }
    }

    public Set<K> keySet() {
        return Arrays.stream(table)
                .filter(Objects::nonNull)
                .map(pair -> pair.key)
                .collect(Collectors.toSet());
    }


    public static class Pair<K, V> implements Map.Entry<K, V> {
        private K key;
        private V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V newValue) {
            V oldValue = value;
            value = newValue;
            return oldValue;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Pair<?, ?> pair = (Pair<?, ?>) o;

            if (key != null ? !key.equals(pair.key) : pair.key != null) return false;
            return value != null ? value.equals(pair.value) : pair.value == null;
        }

        @Override
        public int hashCode() {
            int result = key != null ? key.hashCode() : 0;
            result = 31 * result + (value != null ? value.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return key + "=" + value;
        }
    }

    private int computeHash(K key) {
        double a = (Math.sqrt(5) - 1) / 2;
        return (int) (table.length * ((key.hashCode() * a) - Math.floor(key.hashCode() * a)));
    }

    private V resolveCollision(int hash, K key, V value) {
        for (int i = hash; i < table.length; i++) {
            if (table[i] == null) {
                table[i] = new Pair<>(key, value);
                size++;
                return null;
            }
            else if (table[i].key.equals(key)) {
                V oldValue = table[i].value;
                table[i].value = value;
                return oldValue;
            }
        }
        for (int i = 0; i < hash; i++) {
            if (table[i] == null) {
                table[i] = new Pair<>(key, value);
                size++;
                return null;
            }
            else if (table[i].key.equals(key)) {
                V oldValue = table[i].value;
                table[i].value = value;
                return oldValue;
            }
        }
        return null;
    }

    private boolean itsTimeToExtend() {
        return computeOverflowCoefficient() > 0.75;
    }

    public Set<Pair<K, V>> pairSet() {
        return Arrays.stream(table)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

    }

    private void extendTable() {
        Set<Pair<K, V>> pairs = pairSet();
        size = 0;
        table = (Pair<K, V>[]) new Pair[table.length * 2];
        for (var pair : pairs) {
            put(pair.key, pair.value);
        }
    }

    private double computeOverflowCoefficient() {
        return (double) size / table.length;
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(", ", "{", "}");
        Arrays.stream(table)
                .filter(Objects::nonNull)
                .forEach(pair -> joiner.add(pair.toString()));
        return joiner.toString();
    }
}