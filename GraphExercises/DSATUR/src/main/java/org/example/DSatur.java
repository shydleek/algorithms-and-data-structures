package org.example;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class DSatur {
    public static int[][] generateRandomGraph(int size, int minValue, int maxValue, boolean replaceNonZeros) {
        int[][] graph = new int[size][size];
        Random random = new Random();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i != j) {
                    graph[i][j] = random.nextInt(maxValue - minValue + 1) + minValue;
                    if (replaceNonZeros && graph[i][j] != 0) {
                        graph[i][j] = 1;
                    }
                }
            }
        }

        return graph;
    }

    public static int[] dsatur(int[][] graph) {
        int numVertices = graph.length;
        int[] colors = new int[numVertices];
        Arrays.fill(colors, -1);

        int[] saturation = new int[numVertices];
        int[] degree = new int[numVertices];
        for (int i = 0; i < numVertices; i++) {
            degree[i] = Arrays.stream(graph[i]).sum();
        }

        int maxDegreeVertex = Arrays.stream(degree).max().getAsInt();
        colors[maxDegreeVertex] = 0;

        for (int neighbor = 0; neighbor < numVertices; neighbor++) {
            if (graph[maxDegreeVertex][neighbor] != 0) {
                saturation[neighbor] += 1;
            }
        }

        for (int step = 1; step < numVertices; step++) {
            int maxSaturationVertex = findMaxSaturationVertex(saturation, colors);

            Set<Integer> availableColors = new HashSet<>();
            for (int neighbor = 0; neighbor < numVertices; neighbor++) {
                if (graph[maxSaturationVertex][neighbor] != 0 && colors[neighbor] != -1) {
                    availableColors.add(colors[neighbor]);
                }
            }

            int color = findMinAvailableColor(availableColors);

            colors[maxSaturationVertex] = color;

            for (int neighbor = 0; neighbor < numVertices; neighbor++) {
                if (graph[maxSaturationVertex][neighbor] != 0) {
                    saturation[neighbor] += 1;
                }
            }
        }

        return colors;
    }

    private static int findMaxSaturationVertex(int[] saturation, int[] colors) {
        int maxSaturationVertex = -1;
        int maxSaturation = -1;
        for (int vertex = 0; vertex < saturation.length; vertex++) {
            if (colors[vertex] == -1 && saturation[vertex] > maxSaturation) {
                maxSaturationVertex = vertex;
                maxSaturation = saturation[vertex];
            }
        }
        return maxSaturationVertex;
    }

    private static int findMinAvailableColor(Set<Integer> availableColors) {
        int color = 0;
        while (availableColors.contains(color)) {
            color++;
        }
        return color;
    }

    public static void main(String[] args) {
        int size = 7;
        int minValue = 0;
        int maxValue = 2;
        boolean replaceNonZeros = true;

        int[][] graph = generateRandomGraph(size, minValue, maxValue, replaceNonZeros);
        System.out.println("Graph Adjacency Matrix:");
        for (int[] row : graph) {
            System.out.println(Arrays.toString(row));
        }

        int[] colors = dsatur(graph);
        System.out.println("Graph vertex coloring:");
        for (int v = 0; v < colors.length; v++) {
            System.out.println("Vertex " + v + " has color " + colors[v] + " and degree " + Arrays.stream(graph[v]).sum());
        }
    }
}

//Graph Adjacency Matrix:
//[0, 1, 1, 1, 1, 1, 1]
//[0, 0, 0, 0, 0, 0, 0]
//[1, 0, 0, 0, 1, 0, 1]
//[1, 1, 0, 0, 1, 1, 0]
//[1, 1, 1, 1, 0, 1, 1]
//[1, 1, 1, 1, 1, 0, 1]
//[1, 0, 0, 0, 1, 1, 0]
//Graph vertex coloring:
//Vertex 0 has color 1 and degree 6
//Vertex 1 has color 0 and degree 0
//Vertex 2 has color 3 and degree 3
//Vertex 3 has color 4 and degree 4
//Vertex 4 has color 2 and degree 6
//Vertex 5 has color 3 and degree 6
//Vertex 6 has color 0 and degree 3