package by.bsu.algorithms.lab3.task4;

public class Main {
    public static void main(String[] args) {
        int[][] adjacentVerticesLists1 = new int[][] {
                {0, 3},
                {6, 2, 5},
                {1, 4, 3},
                {6, 1},
                {6, 5, 4},
                {2, 5},
                {5, 6},
        };
        int[][] adjacentVerticesLists2 = new int[][] {
                {0, 1},
                {2, 3},
                {0, 4, 5},
                {1, 4, 6},
                {2, 4},
                {3, 7},
                {5},
                {7}
        };
        int[][] adjacentVerticesLists3 = new int[][] {
                {0, 1},
                {2, 3},
                {0, 4, 2},
                {1, 4, 6},
                {2, 4},
                {3, 6},
                {1, 3, 2},
                {4}
        };
        HopcroftCarpAlgorithm algorithm = new HopcroftCarpAlgorithm(adjacentVerticesLists3);
        algorithm.doAlgorithm();
    }
}