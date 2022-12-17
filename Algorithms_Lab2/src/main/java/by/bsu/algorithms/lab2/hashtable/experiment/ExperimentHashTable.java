package by.bsu.algorithms.lab2.hashtable.experiment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

public class ExperimentHashTable<K, V> {
    private int size = 0;
    private double constantForMultiplicationHash;
    private Pair<K, V>[] table;

    public ExperimentHashTable(int initialCapacity, double constantForMultiplicationHash) {
        table = (Pair<K, V>[]) new Pair[initialCapacity];
        this.constantForMultiplicationHash = constantForMultiplicationHash;
    }

    public long getCollisionCount() {
        return Arrays.stream(table)
                .filter(Objects::nonNull)
                .flatMap(pair -> getWholeChainWithoutFirstElement(computeHash(pair.key)).stream())
                .count();
    }

    public int size() {
        return size;
    }

    public V get(K key) {
        List<Pair<K, V>> chain = getWholeChain(computeHash(key));
        for (var pair : chain) {
            if (pair.key.equals(key)) {
                return pair.value;
            }
        }
        return null;
    }

    public V put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Key can't be null");
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

    public V remove(K key) {
        int keyHash = computeHash(key);
        if (table[keyHash] == null) {
            return null;
        } else {
            Pair<K, V> currentPair = table[keyHash];
            if (currentPair.key.equals(key)) {
                table[keyHash] = currentPair.next;
                size--;
                return currentPair.value;
            }
            while (true) {
                if (currentPair.next == null) {
                    return null;
                }
                if (currentPair.next.key.equals(key)) {
                    V toReturn = currentPair.next.value;
                    currentPair.next = currentPair.next.next;
                    size--;
                    return toReturn;
                }
                currentPair = currentPair.next;
            }
        }
    }

    public static class Pair<K, V> implements Map.Entry<K, V> {

        private K key;
        private V value;
        private Pair<K, V> next;

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

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(", ", "{", "}");
        Arrays.stream(table)
                .filter(Objects::nonNull)
                .forEach(p -> {
                    while (true) {
                        joiner.add(p.toString());
                        p = p.next;
                        if (p == null) {
                            break;
                        }
                    }
                });
        return joiner.toString();
    }

    private int computeHash(K key) {
        double a = Math.abs(constantForMultiplicationHash);
        return (int) (table.length * ((key.hashCode() * a) - Math.floor(key.hashCode() * a)));
    }

    private V resolveCollision(int startIndex, K key, V value) {
        Pair<K, V> oldElement = table[startIndex];
        Pair<K, V> currentElementInChain = oldElement;
        while (true) {
            Pair<K, V> nextElementInChain = currentElementInChain.next;
            if (nextElementInChain != null) {
                if (nextElementInChain.key.equals(key)) {
                    V oldValue = nextElementInChain.value;
                    nextElementInChain.value = value;
                    return oldValue;
                }
            } else {
                currentElementInChain.next = new Pair<>(key, value);
                size++;
                return null;
            }
            currentElementInChain = currentElementInChain.next;
        }
    }

    private List<Pair<K, V>> getWholeChain(int hash) {
        ArrayList<Pair<K, V>> chain = new ArrayList<>();
        Pair<K, V> firstElement = table[hash];
        if (firstElement == null) {
            return chain;
        }
        chain.add(firstElement);
        while (true) {
            if (firstElement.next != null) {
                chain.add(firstElement.next);
            } else {
                return chain;
            }
            firstElement = firstElement.next;
        }
    }

    private List<Pair<K, V>> getWholeChainWithoutFirstElement(int hash) {
        ArrayList<Pair<K, V>> chain = new ArrayList<>();
        Pair<K, V> firstElement = table[hash];
        if (firstElement == null) {
            return chain;
        }
        while (true) {
            if (firstElement.next != null) {
                chain.add(firstElement.next);
            } else {
                return chain;
            }
            firstElement = firstElement.next;
        }
    }
}
