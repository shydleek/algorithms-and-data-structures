package by.bsu.algorithms.lab2.hashtable;

import by.bsu.algorithms.lab2.hashtable.experiment.Experiment;
import by.bsu.algorithms.lab2.hashtable.future.DoubleHashingHashTable;
import by.bsu.algorithms.lab2.hashtable.future.LinearProbingHashTable;
import by.bsu.algorithms.lab2.hashtable.future.OverflowChainHashTable;
import com.github.javafaker.Faker;

import java.util.HashMap;
import java.util.Random;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args) {
        Faker faker = Faker.instance();
        Random random = new Random();

        DoubleHashingHashTable<Integer, String> hashTable = new DoubleHashingHashTable<>();
        TreeSet<Integer> keys = new TreeSet<>();

        hashTable.put(1, "Misha");
        hashTable.put(3, "Gosha");
        System.out.println(hashTable.put(1, "Zebra"));
        System.out.println(hashTable.get(1));
    }
}