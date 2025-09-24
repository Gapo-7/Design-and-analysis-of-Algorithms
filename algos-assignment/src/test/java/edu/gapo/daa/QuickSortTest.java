package edu.gapo.daa;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Random;

public class QuickSortTest {
    @Test
    public void sortsRandom() {
        int n = 20000;
        int[] a = new Random(2).ints(n).toArray();
        Metrics m = new Metrics();
        QuickSort.sort(a, m);
        for (int i = 1; i < n; i++) assertTrue(a[i-1] <= a[i]);
        // sanity: depth should be O(log n) on average
        assertTrue(m.maxDepth <= 2 * (int)Math.floor(Math.log(n)/Math.log(2)) + 20);
    }
}