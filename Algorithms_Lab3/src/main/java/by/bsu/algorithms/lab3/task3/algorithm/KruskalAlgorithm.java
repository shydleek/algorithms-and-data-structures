package by.bsu.algorithms.lab3.task3.algorithm;

import by.bsu.algorithms.lab3.task3.entity.Edge;
import by.bsu.algorithms.lab3.task3.entity.Graph;
import by.bsu.algorithms.lab3.task3.entity.Vertex;
import by.bsu.algorithms.util.MatrixValidator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class KruskalAlgorithm {
    public Graph buildWeb(Double[][] matrix) {
        if (!MatrixValidator.isSquareMatrixWithPositiveElementsWithoutNulls(matrix) || !MatrixValidator.isSymmetric(matrix)) {  // является ли матрица квадратной и все ли элементы матрицы - положительные
            throw new IllegalArgumentException("Invalid matrix. Only square symmetric matrices with positive elements are allowed");
        }
        Vertex[] vertices = new Vertex[matrix.length]; // инициализация вершин графа
        for (int i = 0; i < vertices.length; i++) {
            vertices[i] = new Vertex(i);
        }
        PriorityQueue<Edge> edges = parseMatrix(matrix, vertices); // рёбра отсортированные по весу
        List<Graph> trees = new ArrayList<>();
        for (int i = 0; i < matrix.length; i++) { // инициализация начальных деревьев
            Graph graph = new Graph();
            graph.addVertex(vertices[i]);
            trees.add(graph);
        }
        while (trees.size() != 1) {
            Edge edge = edges.poll(); // извлечение ребра с минимальным весом
            int firstIndex = -1;
            int secondIndex = -1;
            for (int i = 0; i < trees.size(); i++) {
                Graph currentTree = trees.get(i);
                if (currentTree.contains(edge.getFirstVertex())) {
                    firstIndex = i;
                }
                if (currentTree.contains(edge.getSecondVertex())) {
                    secondIndex = i;
                }
                if (firstIndex != -1 && secondIndex != -1) { // деревья, которые содержат вершины нового ребра найдены
                    break;
                }
            }
            if (firstIndex != secondIndex) { // новое ребро не образует цикл
                Graph firstTree = trees.get(firstIndex);
                firstTree.addEdge(edge);
                firstTree.addAll(trees.get(secondIndex).getEdges()); // объединение деревьев
                trees.remove(secondIndex); // удаление слитого дерева
            }
            // 1) Существует граф, который содержит обе вершины => просто пропускаем данное ребро
            // 2) Существует два разных графа, которые содержат по 1 вершине => производим слияние графов, добавляем текущее ребро, удаляем 1 из графов
        }
        return trees.get(0);
    }

    private PriorityQueue<Edge> parseMatrix(Double[][] matrix, Vertex[] vertices) { // получение очереди, с отсортированными по весу рёбрами
        PriorityQueue<Edge> queue = new PriorityQueue<>(Comparator.comparingDouble(Edge::getWeight));
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (i <= j) {
                    queue.add(new Edge(vertices[i], vertices[j], matrix[i][j]));
                }
            }
        }
        return queue;
    }
}