package edu.gapo.daa;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Random;
import java.util.Arrays;

public class MergeSortTest {
    @Test
    public void sortsRandom() {
        int[] a = new Random(1).ints(1000).toArray();
        Metrics m = new Metrics();
        MergeSort.sort(a, m);
        assertTrue(MergeSort.isSorted(a));
        assertTrue(m.maxDepth >= 0);
    }
}