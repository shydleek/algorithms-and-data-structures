package org.example;

import java.util.Arrays;
import java.util.Random;

public class TwoOptAlgorithm {
    public static int[][] generateRandomGraph(int size, int minValue, int maxValue) {
        int[][] graph = new int[size][size];
        Random random = new Random();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i != j) {
                    graph[i][j] = random.nextInt(maxValue - minValue + 1) + minValue;
                }
            }
        }

        return graph;
    }

    public static int[] generateRandomPath(int size) {
        int[] path = new int[size];
        for (int i = 0; i < size; i++) {
            path[i] = i;
        }

        Random random = new Random();
        for (int i = size - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = path[i];
            path[i] = path[j];
            path[j] = temp;
        }

        return path;
    }

    public static int[] twoOpt(int[][] graph, int[] path) {
        int[] bestPath = Arrays.copyOf(path, path.length);
        boolean improved = true;

        while (improved) {
            improved = false;

            for (int i = 1; i < path.length - 2; i++) {
                for (int j = i + 1; j < path.length; j++) {
                    if (j - i == 1) {
                        continue;
                    }

                    int[] newPath = Arrays.copyOf(path, path.length);
                    reversePathSegment(newPath, i, j);

                    if (calculatePathLength(graph, newPath) < calculatePathLength(graph, bestPath)) {
                        bestPath = Arrays.copyOf(newPath, newPath.length);
                        improved = true;
                    }
                }
            }

            path = Arrays.copyOf(bestPath, bestPath.length);
        }

        return bestPath;
    }

    public static int calculatePathLength(int[][] graph, int[] path) {
        int length = 0;

        for (int i = 0; i < path.length - 1; i++) {
            length += graph[path[i]][path[i + 1]];
        }

        return length;
    }

    public static void reversePathSegment(int[] path, int start, int end) {
        while (start < end) {
            int temp = path[start];
            path[start] = path[end];
            path[end] = temp;
            start++;
            end--;
        }
    }

    public static void main(String[] args) {
        int size = 5;
        int minValue = 1;
        int maxValue = 10;

        int[][] graph = generateRandomGraph(size, minValue, maxValue);
        int[] path = generateRandomPath(size);

        System.out.println("Graph matrix:");
        for (int[] row : graph) {
            System.out.println(Arrays.toString(row));
        }

        System.out.println("Starting path:");
        System.out.println(Arrays.toString(path));

        int[] optimizedPath = twoOpt(graph, path);

        System.out.println("Optimized path:");
        System.out.println(Arrays.toString(optimizedPath));

        System.out.println("Source path length: " + calculatePathLength(graph, path));
        System.out.println("Optimized path length: " + calculatePathLength(graph, optimizedPath));
    }
}

//Graph matrix:
//[0, 9, 8, 4, 3]
//[3, 0, 3, 4, 7]
//[2, 2, 0, 2, 10]
//[1, 2, 10, 0, 5]
//[3, 6, 10, 3, 0]
//Starting path:
//[2, 0, 3, 4, 1]
//Optimized path:
//[2, 1, 4, 3, 0]
//Source path length: 17
//Optimized path length: 13