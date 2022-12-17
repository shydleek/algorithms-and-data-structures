package by.bsu.algorithms.lab3.task2;

public class Main {
    public static void main(String[] args) {
        Double[][] matrix = new Double[][] {
                {Double.POSITIVE_INFINITY, 9.0, 4.0, 7.0, 1.0},
                {4.0, Double.POSITIVE_INFINITY, 8.0, 2.0, 1.0},
                {1.0, 3.0, Double.POSITIVE_INFINITY, 5.0, 4.0},
                {3.0, 2.0, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, 6.0},
                {4., 7.0, 9.0, 0.0, Double.POSITIVE_INFINITY}
        };
        Solution solution1 = new Solution(SolutionType.FLOYD);
        System.out.println("Answer " + solution1.solve(matrix));
        Solution solution2 = new Solution(SolutionType.DIJKSTRA);
        System.out.println("Answer " + solution2.solve(matrix));
    }
}