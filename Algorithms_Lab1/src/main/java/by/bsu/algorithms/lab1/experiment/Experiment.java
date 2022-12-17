package by.bsu.algorithms.lab1.experiment;

import by.bsu.algorithms.lab1.sorting.MergeSort;
import by.bsu.algorithms.lab1.sorting.QuickSort;
import by.bsu.algorithms.util.RandomUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Experiment {
    public void testQuickSort(int r, int n, int m, int kTestingRange, Path filePath) throws IOException {
        PrintWriter writer = new PrintWriter(Files.newBufferedWriter(filePath));
        LongSummaryStatistics[] timeStatistics = initTimeStatistic(kTestingRange);
        Integer[] toSort;
        Integer[] currentArrayCopy = new Integer[n];
        for (int i = 0; i < r; i++) {
            toSort = RandomUtils.getIntegerArray(0, m, n);
            writer.println("Before Sort\n");
            writer.println(Arrays.toString(toSort));
            for (int j = 0; j < kTestingRange; j++) {
                writer.println("current k " + j);
                System.arraycopy(toSort, 0, currentArrayCopy, 0, n);
                long startTime = System.currentTimeMillis();
                QuickSort.sort(currentArrayCopy, j, 0, n, Integer::compareTo);
                long resultTime = System.currentTimeMillis() - startTime;
                writer.println(Arrays.toString(currentArrayCopy));
                writer.println("spent time = " + resultTime);
                timeStatistics[j].accept(resultTime);
            }
        }
        printStatistics(timeStatistics);
    }

    public void testMergeSort(int r, int n, int m, int kTestingRange, Path filePath) throws IOException {
        PrintWriter writer = new PrintWriter(Files.newBufferedWriter(filePath));
        LongSummaryStatistics[] timeStatistics = initTimeStatistic(kTestingRange);
        Integer[] toSort;
        Integer[] currentArrayCopy = new Integer[n];
        for (int i = 0; i < r; i++) {
            toSort = RandomUtils.getIntegerArray(0, m, n);
            writer.println("Before Sort\n");
            writer.println(Arrays.toString(toSort));
            for (int j = 0; j < kTestingRange; j++) {
                writer.println("current k " + j);
                System.arraycopy(toSort, 0, currentArrayCopy, 0, n);
                long startTime = System.currentTimeMillis();
                MergeSort.sort(currentArrayCopy, j, 0, n, Integer::compareTo);
                long resultTime = System.currentTimeMillis() - startTime;
                writer.println(Arrays.toString(currentArrayCopy));
                writer.println("spent time = " + resultTime);
                timeStatistics[j].accept(resultTime);
            }
        }
        printStatistics(timeStatistics);
    }

    public void multiThreadExperiment(SortType sortType, int r, int n, int m, int maxK, int threadCount) throws Throwable {
        Integer[][] toSort = generateArrays(r, n, m);
        ArrayList<LongSummaryStatistics> timeStatistics = new ArrayList<>(maxK);

        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

        List<Future<LongSummaryStatistics>> futureResult = new ArrayList<>(threadCount);

        for (int i = 0; i < maxK; i++) {
            futureResult.add(executorService.submit(new CallableSortingTask<>(sortType, toSort, maxK, Integer::compareTo)));
        }

        executorService.shutdown();

        for (var result: futureResult) {
            timeStatistics.add(result.get());
        }

        printStatistics(timeStatistics);
    }

    private LongSummaryStatistics[] initTimeStatistic(int range) {
        LongSummaryStatistics[] timeStatistic = new LongSummaryStatistics[range];
        for (int i = 0; i < timeStatistic.length; i++) {
            timeStatistic[i] = new LongSummaryStatistics();
        }
        return timeStatistic;
    }

    private void printStatistics(LongSummaryStatistics[] statistics) {
        double minAverageTime = Arrays.stream(statistics)
                .mapToDouble(LongSummaryStatistics::getAverage)
                .min()
                .getAsDouble();
        System.out.println("BestTime = " + minAverageTime);
        ArrayList<Integer> bestK = new ArrayList<>();
        for (int i = 0; i < statistics.length; i++) {
            System.out.printf("k=%d time=%f\n", i, statistics[i].getAverage());
            if (minAverageTime == statistics[i].getAverage()) {
                bestK.add(i);
            }
        }
        System.out.println("Best value of k: " + bestK);
    }

    private void printStatistics(List<LongSummaryStatistics> statistics) {
        printStatistics(statistics.toArray(LongSummaryStatistics[]::new));
    }

    private Integer[][] generateArrays(int r, int n, int m) {
        Integer[][] toSort = new Integer[r][n];
        for (int i = 0; i < r; i++) {
            toSort[i] = RandomUtils.getIntegerArray(0, m, n);
        }
        return toSort;
    }
}
