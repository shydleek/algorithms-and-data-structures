package org.example;

import java.util.Arrays;
import java.util.Random;

public class GraphColoring {
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

    public static int[] greedyColoring(int[][] graph) {
        int numVertices = graph.length;
        int[] colors = new int[numVertices];
        Arrays.fill(colors, -1);

        colors[0] = 0;

        for (int v = 1; v < numVertices; v++) {
            boolean[] availableColors = new boolean[numVertices];
            Arrays.fill(availableColors, true);

            for (int neighbor = 0; neighbor < numVertices; neighbor++) {
                if (graph[v][neighbor] != 0 && colors[neighbor] != -1) {
                    availableColors[colors[neighbor]] = false;
                }
            }

            for (int color = 0; color < numVertices; color++) {
                if (availableColors[color]) {
                    colors[v] = color;
                    break;
                }
            }
        }

        return colors;
    }

    public static void main(String[] args) {
        int size = 14;
        int minValue = 0;
        int maxValue = 2;
        boolean replaceNonZeros = true;

        int[][] graph = generateRandomGraph(size, minValue, maxValue, replaceNonZeros);
        System.out.println("Graph Adjacency Matrix:");
        for (int[] row : graph) {
            System.out.println(Arrays.toString(row));
        }

        int[] colors = greedyColoring(graph);
        System.out.println("Graph vertex coloring:");
        for (int v = 0; v < colors.length; v++) {
            System.out.println("Vertex " + v + " has color " + colors[v]);
        }
    }
}

//Graph Adjacency Matrix:
//[0, 1, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1]
//[1, 0, 1, 1, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0]
//[1, 1, 0, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1]
//[1, 1, 0, 0, 1, 0, 0, 1, 1, 1, 1, 0, 0, 0]
//[1, 1, 1, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1, 1]
//[1, 0, 1, 1, 1, 0, 1, 1, 0, 0, 1, 1, 1, 0]
//[1, 0, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 0, 1]
//[0, 0, 0, 0, 1, 0, 1, 0, 0, 1, 1, 1, 0, 0]
//[1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 0]
//[1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0]
//[1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 0, 1, 0]
//[1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1]
//[1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 1, 1, 0, 1]
//[0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 0, 1, 0, 0]
//Graph vertex coloring:
//Vertex 0 has color 0
//Vertex 1 has color 1
//Vertex 2 has color 2
//Vertex 3 has color 2
//Vertex 4 has color 3
//Vertex 5 has color 1
//Vertex 6 has color 3
//Vertex 7 has color 0
//Vertex 8 has color 4
//Vertex 9 has color 4
//Vertex 10 has color 5
//Vertex 11 has color 6
//Vertex 12 has color 1
//Vertex 13 has color 5