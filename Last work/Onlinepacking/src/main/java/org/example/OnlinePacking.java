package org.example;

import java.util.ArrayList;
import java.util.List;

public class OnlinePacking {
    public static List<Bin> onlinePackItems(List<PackingItem> items, int binCapacity) {
        List<Bin> bins = new ArrayList<>();
        bins.add(new Bin(1, binCapacity));

        for (PackingItem item : items) {
            boolean packed = false;

            for (Bin bin : bins) {
                // Search the empty areas for sufficient space to pack
                if (bin.remainingCapacity() >= item.weight) {
                    // Pack item in empty area
                    bin.items.add(item);
                    packed = true;
                    break;
                }
            }

            if (!packed) {
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
                new PackingItem(2, 5),
                new PackingItem(3, 6),
                new PackingItem(4, 1),
                new PackingItem(5, 2),
                new PackingItem(6, 5),
                new PackingItem(7, 7),
                new PackingItem(8, 4)
        );

        int binCapacity = 10;

        List<Bin> packedBins = onlinePackItems(items, binCapacity);

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
//  Item 2, Weight: 5
//  Item 4, Weight: 1
//Remaining capacity: 0
//
//2
//  Item 3, Weight: 6
//  Item 5, Weight: 2
//Remaining capacity: 2
//
//3
//  Item 6, Weight: 5
//  Item 8, Weight: 4
//Remaining capacity: 1
//
//4
//  Item 7, Weight: 7
//Remaining capacity: 3