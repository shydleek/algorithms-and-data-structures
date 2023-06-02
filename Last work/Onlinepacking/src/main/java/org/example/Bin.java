package org.example;

import java.util.ArrayList;
import java.util.List;

public class Bin {
    int id;
    int capacity;
    List<PackingItem> items;

    Bin(int id, int capacity) {
        this.id = id;
        this.capacity = capacity;
        this.items = new ArrayList<>();
    }

    int remainingCapacity() {
        int totalWeight = 0;
        for (PackingItem item : items) {
            totalWeight += item.weight;
        }
        return capacity - totalWeight;
    }
}