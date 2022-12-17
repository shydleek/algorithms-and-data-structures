package by.bsu.algorithms.util;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.StringJoiner;

public class MatrixUtils {
    public static <T> void printMatrix(T[][] matrix) {
        StringJoiner joiner = new StringJoiner("\n", "{", "}");
        for (T[] ts : matrix) {
            joiner.add(Arrays.toString(ts));
        }
        System.out.println(joiner);
    }

    public static <T> boolean isSquare(T[][] matrix) {
        int size = matrix.length;
        int i = 0;
        while (i < matrix.length) {
            if (matrix[i].length != size) {
                return false;
            }
            i++;
        }
        return true;
    }

    public static <T> T[][] copyMatrix(T[][] matrix) {
        T[][] copy = (T[][]) Array.newInstance(matrix.getClass().getComponentType(), matrix.length);
        for (int i = 0; i < copy.length; i++) {
            copy[i] = Arrays.copyOf(matrix[i], matrix[i].length);
        }
        return copy;
    }

    public static <T> boolean containsNull(T[][] matrix) {
        if (matrix == null) {
            return true;
        }
        int i = 0;
        while (i < matrix.length) {
            if (matrix[i] == null) {
                return true;
            }
            int j = 0;
            while (j < matrix[i].length) {
                if (matrix[i][j] == null) {
                    return true;
                }
                j++;
            }
            i++;
        }
        return false;
    }

    private MatrixUtils() {}
}