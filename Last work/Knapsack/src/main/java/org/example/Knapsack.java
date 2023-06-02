package org.example;

import java.util.Arrays;


public class Knapsack {
    public static int knapsack(KnapsackItem[] items, int capacity) {
        int n = items.length;
        int[][] dp = new int[n + 1][capacity + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= capacity; j++) {
                if (items[i - 1].weight > j) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    dp[i][j] = Math.max(
                            dp[i - 1][j],
                            dp[i - 1][j - items[i - 1].weight] + items[i - 1].cost
                    );
                }
            }
        }

        for (int[] row : dp) {
            System.out.println(Arrays.toString(row));
        }

        return dp[n][capacity];
    }

    public static void main(String[] args) {
        KnapsackItem[] items = {
                new KnapsackItem(3, 23),
                new KnapsackItem(2, 45),
                new KnapsackItem(2, 24)
        };
        int capacity = 5;

        int maxTotalValue = knapsack(items, capacity);
        System.out.println("Max total value: " + maxTotalValue);
    }
}

//[0, 0, 0, 0, 0, 0]
//[0, 0, 0, 23, 23, 23]
//[0, 0, 45, 45, 45, 68]
//[0, 0, 45, 45, 69, 69]
//Max total value: 69
