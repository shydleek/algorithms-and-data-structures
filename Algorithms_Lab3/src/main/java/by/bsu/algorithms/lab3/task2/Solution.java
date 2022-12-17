package by.bsu.algorithms.lab3.task2;

import by.bsu.algorithms.util.MatrixUtils;
import by.bsu.algorithms.util.MatrixValidator;
import by.bsu.algorithms.util.Pair;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class Solution {
    private final SolutionType type;

    public Solution(@NonNull SolutionType type) {
        this.type = type;
    }

    public int solve(Double[][] matrix) {
        if (!MatrixValidator.isSquareMatrixWithPositiveElementsWithoutNulls(matrix)) {
            throw new IllegalArgumentException("Illegal matrix. Only square matrix with positive elements are allowed");
        }
        Double[][] copy = MatrixUtils.copyMatrix(matrix);
        fixMatrix(copy);
        copy = switch (type) {
            case FLOYD -> floydSolution(copy);
            case DIJKSTRA -> dijkstraSolution(copy);
            default -> throw new UnsupportedOperationException("Unknown algorithm");
        };
        System.out.println("Final distances matrix");
        MatrixUtils.printMatrix(copy);
        return findAnswer(copy);
    }

    public Double[][] floydSolution(Double[][] matrix) {
        int size = matrix.length;
        Double[][] oldMatrix = matrix;
        Integer[][] oldRouteMatrix = initRouteMatrix(size);
        System.out.println("d0");
        MatrixUtils.printMatrix(oldMatrix);
        System.out.println("s0");
        MatrixUtils.printMatrix(oldRouteMatrix);
        Double[][] newMatrix;
        Integer[][] newRouteMatrix;

        for (int k = 0; k < size; k++) {
            newMatrix = new Double[size][size];
            newRouteMatrix = new Integer[size][size];
            for (int i = 0; i < size; i++) {
                if (oldMatrix[i][i] < 0) {
                    System.out.println("Обнаружен цикл отрицательной длины");
                }
                for (int j = 0; j < size; j++) {
                    if (i == k || j == k) {
                        newMatrix[i][j] = oldMatrix[i][j];
                        newRouteMatrix[i][j] = oldRouteMatrix[i][j];
                    } else {
                        double currentValue = oldMatrix[i][k] + oldMatrix[k][j];
                        if (currentValue < oldMatrix[i][j]) {
                            newMatrix[i][j] = currentValue;
                            newRouteMatrix[i][j] = oldRouteMatrix[i][k];
                        } else {
                            newMatrix[i][j] = oldMatrix[i][j];
                            newRouteMatrix[i][j] = oldRouteMatrix[i][j];
                        }
                    }
                }
            }
            System.out.println("d" + (k + 1));
            MatrixUtils.printMatrix(newMatrix);
            System.out.println("s" + (k + 1));
            MatrixUtils.printMatrix(newRouteMatrix);
            oldMatrix = MatrixUtils.copyMatrix(newMatrix);
            oldRouteMatrix = MatrixUtils.copyMatrix(newRouteMatrix);
        }
        return oldMatrix;
    }

    public Double[][] dijkstraSolution(Double[][] matrix) {
        Vertex[] vertices = parseMatrix(matrix);
        Double[][] minRouteLengthMatrix = new Double[matrix.length][];
        for (int i = 0; i < vertices.length; i++) {
            minRouteLengthMatrix[i] = dijkstraAlgorithm(vertices, i);
        }
        return minRouteLengthMatrix;
    }

    private Double[] dijkstraAlgorithm(Vertex[] vertices, int vertexIndex) {
        List<Vertex> vertexList = Arrays.asList(vertices);
        ArrayList<Vertex> visited = new ArrayList<>();
        var priorityQueue = new PriorityQueue<Pair<Vertex, Double>>(Comparator.comparingDouble(Pair::getValue));
        priorityQueue.add(new Pair<>(vertices[vertexIndex], 0.0));
        Double[] shortestRoutes = new Double[vertices.length];
        Arrays.fill(shortestRoutes, Double.POSITIVE_INFINITY);
        while (visited.size() != vertices.length) {
            Pair<Vertex, Double> currentVertexWithMinRouteLength = priorityQueue.poll();
            Vertex currentVertex = currentVertexWithMinRouteLength.getKey();
            if (!visited.contains(currentVertex)) {
                visited.add(currentVertex);
                Map<Vertex, Double> currentVertexEnvironment = currentVertex.getEnvironment();
                for (Map.Entry<Vertex, Double> edge : currentVertexEnvironment.entrySet()) {
                    int edgeVertexIndex = vertexList.indexOf(edge.getKey());
                    double minLength = Math.min(shortestRoutes[edgeVertexIndex], currentVertexWithMinRouteLength.getValue() + edge.getValue());
                    shortestRoutes[edgeVertexIndex] = minLength;
                    priorityQueue.add(new Pair<>(edge.getKey(), minLength));
                }
            }
        }
        return shortestRoutes;
    }

    private Vertex[] parseMatrix(Double[][] matrix) {
        Vertex[] vertices = new Vertex[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            vertices[i] = new Vertex(Integer.toString(i));
        }
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (i != j) {
                    vertices[i].addAdjacentVertex(vertices[j], matrix[i][j]);
                }
            }
        }
        return vertices;
    }

    public SolutionType getType() {
        return type;
    }

    private Integer[][] initRouteMatrix(int size) {
        Integer[][] routeMatrix = new Integer[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                routeMatrix[i][j] = j + 1;
            }
        }
        return routeMatrix;
    }

    private void fixMatrix(Double[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (i < j) {
                    if (matrix[i][j] > matrix[j][i]) {
                        matrix[i][j] = matrix[j][i];
                    } else {
                        matrix[j][i] = matrix[i][j];
                    }
                }
            }
        }
    }

    private int findAnswer(Double[][] matrix) {
        double[] longestWays = new double[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            longestWays[i] = Arrays.stream(matrix[i])
                    .max(Double::compareTo)
                    .orElse(Double.POSITIVE_INFINITY);
        }
        int currentAnswer = 0;
        double currentMin = longestWays[0];
        for (int i = 1; i < longestWays.length; i++) {
            if (longestWays[i] < currentMin) {
                currentMin = longestWays[i];
                currentAnswer = i;
            }
        }
        return currentAnswer;
    }
}