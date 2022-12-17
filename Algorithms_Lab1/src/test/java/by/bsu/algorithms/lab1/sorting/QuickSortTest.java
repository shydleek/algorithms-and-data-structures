package by.bsu.algorithms.lab1.sorting;

import by.bsu.algorithms.util.RandomUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class QuickSortTest {

    @Test
    public void sortTestWithRandomData() {
        Integer[] src = RandomUtils.getIntegerArray(0, 2_000_000, 20_000_000);
        Integer[] copy = Arrays.copyOf(src, src.length);
        Arrays.sort(copy);
        QuickSort.sort(src, 7, 0, src.length, Integer::compareTo);
        int result = Arrays.compare(src, copy);
        int expected = 0;
        Assert.assertEquals(expected, result);
    }
}