package edu.gapo.daa;

import java.util.Arrays;

public final class MergeSort {
    private static final int CUTOFF = 16;

    public static void sort(int[] a, Metrics m) {
        if (a == null || a.length <= 1) return;
        int[] buf = new int[a.length];
        sort(a, buf, 0, a.length - 1, m);
    }

    private static void sort(int[] a, int[] buf, int l, int r, Metrics m) {
        if (r - l + 1 <= CUTOFF) { insertion(a, l, r, m); return; }
        int mIdx = (l + r) >>> 1;
        if (m != null) m.enter();
        sort(a, buf, l, mIdx, m);
        sort(a, buf, mIdx + 1, r, m);
        if (m != null) m.exit();

        // already in order?
        if (lessEq(a[mIdx], a[mIdx + 1], m)) return;
        merge(a, buf, l, mIdx, r, m);
    }

    private static boolean lessEq(int x, int y, Metrics mt) {
        if (mt != null) mt.comps++;
        return x <= y;
    }

    private static void merge(int[] a, int[] buf, int l, int m, int r, Metrics mt) {
        // copy to buffer
        System.arraycopy(a, l, buf, l, r - l + 1);
        if (mt != null) mt.moves += (r - l + 1);
        int i = l, j = m + 1, k = l;
        while (i <= m && j <= r) {
            if (lessEq(buf[i], buf[j], mt)) a[k++] = buf[i++];
            else a[k++] = buf[j++];
            if (mt != null) mt.moves++;
        }
        while (i <= m) { a[k++] = buf[i++]; if (mt != null) mt.moves++; }
        while (j <= r) { a[k++] = buf[j++]; if (mt != null) mt.moves++; }
    }

    private static void insertion(int[] a, int l, int r, Metrics m) {
        for (int i = l + 1; i <= r; i++) {
            int key = a[i];
            int j = i - 1;
            while (j >= l && greater(a[j], key, m)) {
                a[j + 1] = a[j];
                if (m != null) m.moves++;
                j--;
            }
            a[j + 1] = key;
            if (m != null) m.moves++;
        }
    }

    private static boolean greater(int x, int y, Metrics m) {
        if (m != null) m.comps++;
        return x > y;
    }

    // Convenience for quick check
    public static boolean isSorted(int[] a) {
        for (int i = 1; i < a.length; i++) if (a[i-1] > a[i]) return false;
        return true;
    }
}