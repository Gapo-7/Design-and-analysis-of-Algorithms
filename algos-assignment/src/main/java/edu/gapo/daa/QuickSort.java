package edu.gapo.daa;

import java.util.concurrent.ThreadLocalRandom;

public final class QuickSort {

    public static void sort(int[] a, Metrics m) {
        if (a == null || a.length <= 1) return;
        sort(a, 0, a.length - 1, m);
    }

    private static void sort(int[] a, int l, int r, Metrics mt) {
        while (l < r) {
            int p = randomizedPartition(a, l, r, mt);
            int leftSize = p - l;
            int rightSize = r - p;
            // recurse on smaller side for bounded depth
            if (leftSize < rightSize) {
                if (mt != null) mt.enter();
                sort(a, l, p - 1, mt);
                if (mt != null) mt.exit();
                l = p + 1; // iterate on larger
            } else {
                if (mt != null) mt.enter();
                sort(a, p + 1, r, mt);
                if (mt != null) mt.exit();
                r = p - 1;
            }
        }
    }

    private static int randomizedPartition(int[] a, int l, int r, Metrics mt) {
        int pivotIndex = ThreadLocalRandom.current().nextInt(l, r + 1);
        swap(a, l, pivotIndex, mt);
        int pivot = a[l];
        int i = l + 1, j = r;
        while (true) {
            while (i <= r && less(a[i], pivot, mt)) i++;
            while (j >= l + 1 && less(pivot, a[j], mt)) j--;
            if (i >= j) break;
            swap(a, i++, j--, mt);
        }
        swap(a, l, j, mt);
        return j;
    }

    private static boolean less(int x, int y, Metrics mt) {
        if (mt != null) mt.comps++;
        return x < y;
    }
    private static void swap(int[] a, int i, int j, Metrics mt) {
        int t = a[i]; a[i] = a[j]; a[j] = t;
        if (mt != null) mt.moves += 3;
    }
}