package by.bsu.algorithms.lab1.sorting;

import by.bsu.algorithms.util.RandomUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class MergeSortTest {
    @Test
    public void sortTestWithRandomData() {
        Integer[] src = RandomUtils.getIntegerArray(0, 100_000, 20_000_000);
        Integer[] copy = Arrays.copyOf(src, src.length);
        Arrays.sort(copy);
        MergeSort.sort(src, 0, 0, src.length, Integer::compareTo);
        int number = Arrays.compare(copy, src);
        Assert.assertEquals(0, number);
    }
}

