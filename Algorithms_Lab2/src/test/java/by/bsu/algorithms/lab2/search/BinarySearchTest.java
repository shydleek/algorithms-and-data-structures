package by.bsu.algorithms.lab2.search;

import by.bsu.algorithms.util.RandomUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

public class BinarySearchTest {
    @Test
    public void searchTest() {
        Integer[] array;
        Random random = new Random();
        int repeatCount = 100000;
        int value;
        for (int i = 0; i < repeatCount; i++) {
            array = RandomUtils.getIntegerArray(-50, 50, 1000);
            Arrays.sort(array);
            value = random.nextInt(-50, 50);
            int actual = BinarySearch.search(array, value);
            int expected = Arrays.binarySearch(array, value);
            if (expected > 0 && actual > 0) {
                Assert.assertEquals(array[actual], array[expected]);
            }
        }
    }
}