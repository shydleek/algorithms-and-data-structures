package by.bsu.algorithms.lab1;

import by.bsu.algorithms.lab1.experiment.Experiment;
import by.bsu.algorithms.lab1.experiment.SortType;
import by.bsu.algorithms.lab1.sorting.InsertionSort;
import by.bsu.algorithms.util.RandomUtils;

import java.nio.file.Path;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        Experiment experiment = new Experiment();
        long start = System.currentTimeMillis();

        try {
            experiment.multiThreadExperiment(SortType.MERGE_SORT, 10, 1_000_000, 10000, 10, 6);
            experiment.testMergeSort(20, 1_000_000, 10000, 10, Path.of("data/result.txt"));
        } catch (Throwable e) {
            e.printStackTrace();
        }
        System.out.println("Experiment Time " + (System.currentTimeMillis() - start));
    }
}