package heap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * @source code for inspiration: https://algs4.cs.princeton.edu/24pq/IndexMinPQ.java.html
 * and https://algs4.cs.princeton.edu/24pq/MinPQ.java
 */
public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    private ArrayList<T> items;
    private HashMap<T, Double> priorities;
    private HashMap<T, Integer> trackIndex;


    public ArrayHeapMinPQ() {
        items = new ArrayList<>();
        priorities = new HashMap<>();
        trackIndex = new HashMap<>();
        items.add(null);
    }


    /**
     * A helper method for swapping the items at two indices of the array heap.
     */
    private void swap(int a, int b) {
        trackIndex.put(items.get(a), b);
        trackIndex.put(items.get(b), a);
        Collections.swap(items, a, b);
    }

    /**
     * Adds an item with the given priority value.
     * Assumes that item is never null.
     * Runs in O(log N) time (except when resizing).
     * @throws IllegalArgumentException if item is already present in the PQ
     */
    @Override
    public void add(T item, double priority) {
        if (priorities.containsKey(item)) {
            throw new IllegalArgumentException();
        }
        items.add(item);
        priorities.put(item, priority);
        trackIndex.put(item, size());
        swim(items.size() - 1);
    }

    private void swim(int k) {
        while (k > 1 && greater(k / 2, k)) {
            swap(k, k / 2);
            k = k / 2;
        }
    }

    private void sink(int k) {
        while (2 * k <= size()) {
            int j = 2 * k;
            if (j < size() && greater(j, j + 1)) {
                j++;
            }
            if (!greater(k, j)) {
                break;
            }
            swap(k, j);
            k = j;
        }
    }

    private boolean greater(int i, int j) {
        return priorities.get(items.get(i)) > priorities.get(items.get(j));
    }
    /**
     * Returns true if the PQ contains the given item; false otherwise.
     * Runs in O(log N) time.
     */
    @Override
    public boolean contains(T item) {
        return priorities.containsKey(item);
    }

    /**
     * Returns the item with the smallest priority.
     * Runs in O(log N) time.
     * @throws NoSuchElementException if the PQ is empty
     */
    @Override
    public T getSmallest() {
        if (items.isEmpty()) {
            throw new NoSuchElementException();
        }
        return items.get(1);
    }

    /**
     * Removes and returns the item with the smallest priority.
     * Runs in O(log N) time (except when resizing).
     * @throws NoSuchElementException if the PQ is empty
     */
    @Override
    public T removeSmallest() {
        if (items.isEmpty()) {
            throw new NoSuchElementException();
        }
        T small = items.get(1);
        swap(1, size());
        items.remove(size());
        priorities.remove(small);
        sink(1);
        trackIndex.remove(small);
        return small;
    }

    /**
     * Changes the priority of the given item.
     * Runs in O(log N) time.
     * @throws NoSuchElementException if the item is not present in the PQ
     */
    @Override
    public void changePriority(T item, double priority) {
        if (!priorities.containsKey(item)) {
            throw new NoSuchElementException();
        }
        int target = trackIndex.get(item);
        priorities.put(item, priority);
        if (target != -1) {
            swim(target);
            sink(target);
        }
    }

    /**
     * Returns the number of items in the PQ.
     * Runs in O(log N) time.
     */
    @Override
    public int size() {
        return items.size() - 1;
    }
}


