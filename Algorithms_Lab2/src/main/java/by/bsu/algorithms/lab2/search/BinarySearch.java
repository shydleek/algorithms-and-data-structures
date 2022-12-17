package by.bsu.algorithms.lab2.search;

public class BinarySearch {
    public static int search(Integer[] array, int searchingElement) {
        int mid;
        int left = 0;
        int right = array.length - 1;
        while (left <= right) {
            mid = (left + right) / 2;
            if (array[mid] == searchingElement) {
                return mid;
            } else if (array[mid] > searchingElement) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return -1;
    }

    public static int comparisonOperationCountSearch(Integer[] array, int searchingElement) {
        int mid;
        int left = 0;
        int right = array.length - 1;
        int comparisonOperationCount = 0;

        while (true) {
            comparisonOperationCount++;
            if (left > right) {
                break;
            }

            mid = (left + right) / 2;
            comparisonOperationCount += 2;
            if (array[mid] == searchingElement) {
                comparisonOperationCount--;
                System.out.println("Количество операций сравнения: " + comparisonOperationCount);
                return mid;
            } else if (array[mid] > searchingElement) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        System.out.println("Количество операций сравнения: " + comparisonOperationCount);
        return -1;
    }
}