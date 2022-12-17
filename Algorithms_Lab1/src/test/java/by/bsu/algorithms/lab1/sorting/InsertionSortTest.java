package by.bsu.algorithms.lab1.sorting;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class InsertionSortTest {
    public static List<Integer> data;

    @Before
    public void fillData() {
        data = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 100_000; i++) {
            data.add(random.nextInt(500));
        }
    }

    @Test
    public void sortTest() {
        var copy = new ArrayList<>(data);
        InsertionSort.sort(data);
        copy.sort(Integer::compareTo);
        int number = Arrays.compare(copy.toArray(Integer[]::new), data.toArray(Integer[]::new));
        Assert.assertEquals(0, number);
    }
}