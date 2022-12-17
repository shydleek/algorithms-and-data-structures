package by.bsu.algorithms.util;

import java.util.Objects;

public class MatrixValidator {
    public static boolean isSquareMatrixWithPositiveElementsWithoutNulls(Double[][] matrix) {
        if (MatrixUtils.containsNull(matrix) || !MatrixUtils.isSquare(matrix)) {
            return false;
        }
        for (var row : matrix) {
            for (var element : row) {
                if (element < 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isSymmetric(Double[][] matrix) {
        if (!MatrixUtils.isSquare(matrix)) {
            return false;
        }
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (!Objects.equals(matrix[i][j], matrix[j][i])) {
                    return false;
                }
            }
        }
        return true;
    }

    private MatrixValidator() {}
}
