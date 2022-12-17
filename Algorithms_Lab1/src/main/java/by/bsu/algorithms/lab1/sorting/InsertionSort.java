package by.bsu.algorithms.lab1.sorting;

import java.util.Comparator;
import java.util.List;

public class InsertionSort {
    public static void sort(List<Integer> list) {
        for (int i = 1; i < list.size(); i++) {
            var value = list.get(i);
            int j = i - 1;
            while (j >= 0 && value < list.get(j)) {
                list.set(j + 1, list.get(j));
                j--;
            }
            list.set(j + 1, value);
        }
    }

    public static <T> void sort(T[] array, int start, int end, Comparator<T> comparator) {
        for (int i = start; i < end; i++) {
            var value = array[i];
            int j = i - 1;
            while (j >= start && comparator.compare(value, array[j]) < 0) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = value;
        }
    }

    public static int sortOperationCount(int[] array, int start, int end) {

        int i = start;
        int operationCount = 1;
        while (true) {
            operationCount++;
            if (i >= end) {
                break;
            }
            int value = array[i];
            int j = i - 1;
            operationCount += 4;

            while (true) {
                operationCount++;
                if (j < start) {
                    break;
                }
                operationCount += 2;
                if (value >= array[j]) {
                    break;
                }
                array[j + 1] = array[j];
                j--;
                operationCount += 6;
            }
            array[j + 1] = value;
            operationCount += 3;

            i++;
            operationCount += 2;
        }
        return operationCount;
    }

    public static <T> void sort(List<T> list, Comparator<T> comparator) {
        for (int i = 1; i < list.size(); i++) {
            var value = list.get(i);
            int j = i - 1;
            while (j >= 0 && comparator.compare(value, list.get(j)) < 1) {
                list.set(j + 1, list.get(j));
                j--;
            }
            list.set(j + 1, value);
        }
    }

    private InsertionSort() {
    }
}
