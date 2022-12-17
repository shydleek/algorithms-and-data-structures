package by.bsu.algorithms.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class RandomUtils {
    private static Random RANDOM = new Random();

    private RandomUtils() {}

    public static List<Integer> getIntList(int min, int max, int count) {
        checkMinMaxCount(min, max, count, Integer::compare);
        ArrayList<Integer> integers = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            integers.add(RANDOM.nextInt(min, max + 1));
        }
        return integers;
    }

    public static List<Double> getDoubleList(Double min, Double max, int count) {
        checkMinMaxCount(min, max, count, Double::compare);
        ArrayList<Double> doubles = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            doubles.add(RANDOM.nextDouble(min, max + 1));
        }
        return doubles;
    }

    public static Integer[] getIntegerArray(int min, int max, int count) {
        checkMinMaxCount(min, max, count, Integer::compare);
        Integer[] integers = new Integer[count];
        for (int i = 0; i < count; i++) {
            integers[i] = RANDOM.nextInt(min, max + 1);
        }
        return integers;
    }

    public static int[] getIntArray(int min, int max, int count) {
        checkMinMaxCount(min, max, count, Integer::compare);
        int[] ints = new int[count];
        for (int i = 0; i < count; i++) {
            ints[i] = RANDOM.nextInt(min, max + 1);
        }
        return ints;
    }

    public static Double[] getDoubleArray(double min, double max, int count) {
        checkMinMaxCount(min, max, count, Double::compare);
        Double[] doubles = new Double[count];
        for (int i = 0; i < count; i++) {
            doubles[i] = RANDOM.nextDouble(min, max + 1);
        }
        return doubles;
    }

    private static <T> void checkMinMaxCount(T min, T max, int count, Comparator<T> comparator) {
        if (comparator.compare(min, max) > 0) {
            throw new IllegalArgumentException("max < min");
        }
        if (count <= 0) {
            throw new IllegalArgumentException("count <= 0");
        }
    }
}