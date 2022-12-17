package by.bsu.algorithms.lab3.task3;

import by.bsu.algorithms.lab3.task3.algorithm.KruskalAlgorithm;
import by.bsu.algorithms.lab3.task3.algorithm.PrimAlgorithm;

public class Main {
    public static void main(String[] args) {
        double inf = Double.POSITIVE_INFINITY;
        Double[][] matrix = new Double[][] {
                {inf, 3.0, inf, inf, inf, 5.0, 2.0},
                {3.0, inf, 5.0, 8.0, inf, inf, inf},
                {inf, 5.0, inf, 1.0, inf, inf, 6.0},
                {inf, 8.0, 1.0, inf, 4.0, inf, inf},
                {inf, inf, inf, 4.0, inf, 1.0, 4.0},
                {5.0, inf, inf, inf, 1.0, inf, 2.0},
                {2.0, inf, 6.0, inf, 4.0, 2.0, inf}
        };

        Double[][] matrix2 = new Double[][] {
                {inf, 2.0, inf, inf, inf, inf, inf, inf, 3.0, 3.0, inf, 1.0},
                {2.0, inf, 4.0, inf, inf, inf, inf, inf, inf, inf, 1.0, 4.0},
                {inf, 4.0, inf, 2.0, inf, inf, inf, inf, inf, inf, 2.0, inf},
                {inf, inf, 2.0, inf, 5.0, inf, inf, inf, inf, inf, 3.0, inf},
                {inf, inf, inf, 5.0, inf, 3.0, inf, 1.0, inf, inf, inf, inf},
                {inf, inf, inf, inf, 3.0, inf, 4.0, 2.0, inf, inf, inf, inf},
                {inf, inf, inf, inf, inf, 4.0, inf, 1.0, inf, inf, inf, inf},
                {inf, inf, inf, inf, 1.0, 2.0, 1.0, inf, inf, 4.0, inf, inf},
                {3.0, inf, inf, inf, inf, inf, inf, inf, inf, 2.0, inf, inf},
                {3.0, inf, inf, inf, inf, inf, inf, 4.0, 2.0, inf, 1.0, inf},
                {inf, 1.0, 2.0, 3.0, inf, inf, inf, inf, inf, 1.0, inf, 3.0},
                {1.0, 4.0, inf, inf, inf, inf, inf, inf, inf, inf, 3.0, inf}
        };
        PrimAlgorithm algorithm = new PrimAlgorithm();
        var pGraph = algorithm.buildWeb(matrix2);
        pGraph.getEdges().forEach(System.out::println); // вывод порядка добавления рёбер
        KruskalAlgorithm kruskalAlgorithm = new KruskalAlgorithm();
        var kGraph = kruskalAlgorithm.buildWeb(matrix2);
        System.out.println(pGraph);
        System.out.println(kGraph);
        System.out.println(pGraph.getWeight());
        System.out.println(kGraph.getWeight());
    }
}