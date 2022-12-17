package by.bsu.algorithms.lab1.sorting;

import java.util.Arrays;
import java.util.Comparator;

public class MergeSort {
    public static <T> void sort(T[] array, int k, int left, int right, Comparator<T> comparator) {
        if (left >= right) {
            throw new IllegalArgumentException("left >= right");
        }
        int size = right - left;
        if (size <= k) {
            InsertionSort.sort(array, left, right, comparator);
        }
        if (size > 1) {
            int mid = (right + left) / 2;
            sort(array, k, left, mid, comparator);
            sort(array, k, mid, right, comparator);
            merge(array, left, mid, right, comparator);
        }
    }

    private static <T> void merge(T[] array, int left, int mid, int right, Comparator<T> comparator) {
        int resultSize = right - left;
        int leftIndex = left;
        int rightIndex = mid;
        T[] result = Arrays.copyOfRange(array, left, right);
        for (int i = 0; i < resultSize; i++) {
            if (rightIndex < right) {
                if (leftIndex < mid) {
                    if (comparator.compare(array[leftIndex], array[rightIndex]) > 0) {
                        result[i] = array[rightIndex];
                        rightIndex++;
                    }
                    else {
                        result[i] = array[leftIndex];
                        leftIndex++;
                    }
                }
                else {
                    result[i] = array[rightIndex];
                    rightIndex++;
                }
            }
            else {
                result[i] = array[leftIndex];
                leftIndex++;
            }
        }
        System.arraycopy(result, 0, array, left, resultSize);
    }

    private MergeSort() {}
}
