package org.example;

import java.util.ArrayList;
import java.util.List;

public class OfflinePacking {
    public static List<Bin> offlinePacking(List<PackingItem> items, int binCapacity) {
        List<Bin> bins = new ArrayList<>();

        for (PackingItem item : items) {
            Bin bestBin = null;
            int bestRemainingCapacity = Integer.MAX_VALUE;

            for (Bin bin : bins) {
                int remainingCapacity = bin.remainingCapacity();
                if (remainingCapacity >= item.weight && remainingCapacity < bestRemainingCapacity) {
                    bestBin = bin;
                    bestRemainingCapacity = remainingCapacity;
                }
            }

            if (bestBin != null) {
                bestBin.items.add(item);
            } else {
                Bin newBin = new Bin(bins.size() + 1, binCapacity);
                newBin.items.add(item);
                bins.add(newBin);
            }
        }

        return bins;
    }

    public static void main(String[] args) {
        List<PackingItem> items = List.of(
                new PackingItem(1, 4),
                new PackingItem(2, 6),
                new PackingItem(3, 3),
                new PackingItem(4, 1),
                new PackingItem(5, 7),
                new PackingItem(6, 5),
                new PackingItem(7, 4),
                new PackingItem(8, 9)
        );

        int binCapacity = 10;

        List<Bin> packedBins = offlinePacking(items, binCapacity);

        for (Bin bin : packedBins) {
            System.out.println(bin.id);
            for (PackingItem item : bin.items) {
                System.out.println("  Item " + item.id + ", Weight: " + item.weight);
            }
            System.out.println("Remaining capacity: " + bin.remainingCapacity());
            System.out.println();
        }
    }
}

//1
//  Item 1, Weight: 4
//  Item 2, Weight: 6
//Remaining capacity: 0
//
//2
//  Item 3, Weight: 3
//  Item 4, Weight: 1
//  Item 6, Weight: 5
//Remaining capacity: 1
//
//3
//  Item 5, Weight: 7
//Remaining capacity: 3
//
//4
//  Item 7, Weight: 4
//Remaining capacity: 6
//
//5
//  Item 8, Weight: 9
//Remaining capacity: 1