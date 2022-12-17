package by.bsu.algorithms.lab2.tree;

import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        BinarySearchTree tree = new BinarySearchTree();
        tree.insert(5);
        tree.insert(9);
        tree.insert(2);
        tree.insert(6);
        tree.insert(8);

        tree.printLevelNodeRepresentation();
    }
}