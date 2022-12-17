package by.bsu.algorithms.lab1.experiment;

import by.bsu.algorithms.lab1.sorting.MergeSort;
import by.bsu.algorithms.lab1.sorting.QuickSort;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LongSummaryStatistics;
import java.util.concurrent.Callable;

public class CallableSortingTask<T> implements Callable<LongSummaryStatistics> {
    private SortType sortType;
    private LongSummaryStatistics statistics;
    private T[][] toSort;
    Comparator<T> comparator;
    int maxK;

    {
        statistics = new LongSummaryStatistics();
    }

    public CallableSortingTask(SortType sortType,
                               T[][] toSort, int maxK, Comparator<T> comparator) {
        super();
        this.sortType = sortType;
        this.toSort = toSort;
        this.maxK = maxK;
        this.comparator = comparator;
    }

    @Override
    public LongSummaryStatistics call() throws Exception {
        int n = toSort[0].length;
        T[] currentArrayCopy;

        switch (sortType) {
            case MERGE_SORT -> {
                for (int i = 0; i < toSort.length; i++) {
                    for (int j = 0; j < maxK; j++) {
                        currentArrayCopy = Arrays.copyOf(toSort[i], n);
                        long startTime = System.currentTimeMillis();
                        MergeSort.sort(currentArrayCopy, j, 0, n, comparator);
                        long resultTime = System.currentTimeMillis() - startTime;
                        statistics.accept(resultTime);
                    }
                }
            }
            case QUICK_SORT -> {
                for (int i = 0; i < toSort.length; i++) {
                    for (int j = 0; j < maxK; j++) {
                        currentArrayCopy = Arrays.copyOf(toSort[i], n);
                        long startTime = System.currentTimeMillis();
                        QuickSort.sort(currentArrayCopy, j, 0, n, comparator);
                        long resultTime = System.currentTimeMillis() - startTime;
                        statistics.accept(resultTime);
                    }
                }
            }
        }
        return statistics;
    }
}