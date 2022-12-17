package by.bsu.algorithms.lab2.search;

import by.bsu.algorithms.util.RandomUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) { // FixMe интерполяционный поиск (критерии остановки)
        Random random = new Random();

        int bound = 200_000;
        int amount = 10_000_000;
        int toSearch = random.nextInt(-bound, bound);

        Integer[] array = RandomUtils.getIntegerArray(-bound, bound, amount);
        Arrays.sort(array);
        System.out.println("src " + Arrays.toString(array));
        System.out.println("toSearch");
        System.out.println("present " + List.of(array).contains(toSearch));
        System.out.println("=====================================================================");
        System.out.println("interpolation");
        int interpolationAnswer = InterpolationSearch.comparisonOperationCountSearch(array, toSearch);
        System.out.println("=====================================================================");
        System.out.println("binary");
        int binaryAnswer = BinarySearch.comparisonOperationCountSearch(array, toSearch);
        System.out.println("=====================================================================");
        boolean isCorrect;
        if (interpolationAnswer != -1 ) {
            isCorrect = array[interpolationAnswer].equals(array[binaryAnswer]);
        }
        else {
            isCorrect = interpolationAnswer == binaryAnswer;
        }
        if (isCorrect) {
            System.out.println("Correct");
        }
        else {
            System.out.println("Incorrect");
            System.out.println("Binary " + binaryAnswer);
            System.out.println("Interpolation " + interpolationAnswer);
        }
    }
}