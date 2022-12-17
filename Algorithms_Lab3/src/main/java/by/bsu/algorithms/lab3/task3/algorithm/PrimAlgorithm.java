package by.bsu.algorithms.lab3.task3.algorithm;

import by.bsu.algorithms.lab3.task3.entity.Edge;
import by.bsu.algorithms.lab3.task3.entity.Graph;
import by.bsu.algorithms.lab3.task3.entity.Vertex;
import by.bsu.algorithms.util.MatrixValidator;
import by.bsu.algorithms.util.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class PrimAlgorithm {
    public Graph buildWeb(Double[][] matrix) {
        if (!MatrixValidator.isSquareMatrixWithPositiveElementsWithoutNulls(matrix) || !MatrixValidator.isSymmetric(matrix)) {  // является ли матрица квадратной и все ли элементы матрицы - положительные
            throw new IllegalArgumentException("Invalid matrix. Only square symmetric matrices with positive elements are allowed");
        }
        Vertex[] vertices = new Vertex[matrix.length];
        for (int i = 0; i < vertices.length; i++) {
            vertices[i] = new Vertex(i);
        }
        PriorityQueue<Pair<Vertex, Double>>[] environments = getVertexEnvironments(matrix, vertices); // массив окружений вершин
        List<Vertex> alreadyIncluded = new ArrayList<>(); // уже включённые вершины
        alreadyIncluded.add(vertices[0]); // стартовая вершина
        List<Edge> treeEdges = new ArrayList<>();
        while (alreadyIncluded.size() != vertices.length) { // пока не все вершины включены
            treeEdges.add(findNextEdge(environments, alreadyIncluded));
        }
        return new Graph(treeEdges);
    }

    private PriorityQueue<Pair<Vertex, Double>>[] getVertexEnvironments(Double[][] matrix, Vertex[] vertices) {
        PriorityQueue<Pair<Vertex, Double>>[] environments = new PriorityQueue[vertices.length];
        for (int i = 0; i < matrix.length; i++) {
            PriorityQueue<Pair<Vertex, Double>> environment = new PriorityQueue<>(Comparator.comparingDouble(Pair::getValue));
            environments[i] = environment;
            for (int j = 0; j < matrix.length; j++) {
                if (i != j && Double.isFinite(matrix[i][j])) {
                    environment.add(new Pair<>(vertices[j], matrix[i][j]));
                }
            }
        }
        return environments;
    }

    private Edge findNextEdge(PriorityQueue<Pair<Vertex, Double>>[] vertexEnvironments, List<Vertex> alreadyIncluded) { // нахождение следующего ребра дерева
        Double minWeight = Double.POSITIVE_INFINITY;
        int firstVertexIndex = -1;
        int i = 0;
        while (i < alreadyIncluded.size()) { // нахождение ребра минимального веса, от одной из включённых вершин к невключенной вершине
            int vertexIndex = alreadyIncluded.get(i).getIndex();
            Pair<Vertex, Double> edge = vertexEnvironments[vertexIndex].peek(); // ребро минимального веса, из окружения вершины с индексом vertexIndex
            if (edge != null) {
                if (alreadyIncluded.contains(edge.getKey())) {
                    vertexEnvironments[vertexIndex].poll(); // извлечение ненужного ребра
                    i--;
                }
                else {
                    double lightestEdgeWeight = edge.getValue();
                    if (lightestEdgeWeight < minWeight) {
                        firstVertexIndex = i;
                        minWeight = lightestEdgeWeight;
                    }
                }
            }
            i++;
        }
        int realIndex = alreadyIncluded.get(firstVertexIndex).getIndex();
        Pair<Vertex, Double> nextEdgeData = vertexEnvironments[realIndex].poll(); // извлечение ребра из очереди
        alreadyIncluded.add(nextEdgeData.getKey());
        return new Edge(alreadyIncluded.get(firstVertexIndex), nextEdgeData.getKey(), nextEdgeData.getValue());
    }
}