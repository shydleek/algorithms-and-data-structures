package by.bsu.algorithms.lab2.hashtable.experiment;

import java.util.Random;

public class Experiment {
    private static final double KNUTH_CONST = 0.61803399;

    public void doExperiment(int setCount, int keyCount, int maxKeyValue, int hashTableLength) {
        Random random = new Random();
        long bestCollisionCount = Long.MAX_VALUE;
        double bestConstant = 0.0;
        for (int i = 0; i < setCount; i++) {
            double randomConstant = Math.random();
            ExperimentHashTable<Integer, String> tableWithKnuthConstant = new ExperimentHashTable<>(hashTableLength, KNUTH_CONST);
            ExperimentHashTable<Integer, String> tableWithRandomConstant = new ExperimentHashTable<>(hashTableLength, randomConstant);
            for (int j = 0; j < keyCount; j++) {
                int currentKey = random.nextInt(1, maxKeyValue);
                tableWithKnuthConstant.put(currentKey, "");
                tableWithRandomConstant.put(currentKey, "");
            }
            long collisionCount = tableWithRandomConstant.getCollisionCount();
            System.out.println("Количество коллизий с костантой Кнута - " + tableWithKnuthConstant.getCollisionCount());
            System.out.println(String.format("Количество коллизий с костантой %f - %d", randomConstant, collisionCount));
            if (collisionCount < bestCollisionCount) {
                bestCollisionCount = collisionCount;
                bestConstant = randomConstant;
            }
        }
        System.out.println("Лучшая констаниа " + bestConstant);
    }
}

