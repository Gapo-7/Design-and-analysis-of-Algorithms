package edu.gapo.daa;

public final class Select {

    public static int select(int[] a, int k, Metrics m) {
        if (k < 0 || k >= a.length) throw new IllegalArgumentException("k out of range");
        return select(a, 0, a.length - 1, k, m);
    }

    private static int select(int[] a, int l, int r, int k, Metrics mt) {
        while (true) {
            if (l == r) return a[l];
            int pivotIdx = medianOfMedians(a, l, r, mt);
            int p = partitionAround(a, l, r, pivotIdx, mt);
            int rank = p - l;
            if (k == rank) return a[p];
            else if (k < rank) { r = p - 1; }
            else { k -= rank + 1; l = p + 1; }
        }
    }

    private static int partitionAround(int[] a, int l, int r, int pivotIdx, Metrics mt) {
        swap(a, l, pivotIdx, mt);
        int pivot = a[l];
        int i = l + 1, j = r;
        while (true) {
            while (i <= r && le(a[i], pivot, mt)) i++;
            while (j >= l + 1 && le(pivot, a[j], mt)) j--;
            if (i >= j) break;
            swap(a, i++, j--, mt);
        }
        swap(a, l, j, mt);
        return j;
    }

    private static int medianOfMedians(int[] a, int l, int r, Metrics mt) {
        int n = r - l + 1;
        if (n <= 5) {
            insertion(a, l, r, mt);
            return (l + r) >>> 1;
        }
        int numGroups = (n + 4) / 5;
        for (int g = 0; g < numGroups; g++) {
            int start = l + g * 5;
            int end = Math.min(start + 4, r);
            insertion(a, start, end, mt);
            int medianIdx = (start + end) >>> 1;
            swap(a, l + g, medianIdx, mt); // compact medians to the front
        }
        int midOfMedians = l + (numGroups - 1) / 2;
        return selectIndex(a, l, l + numGroups - 1, midOfMedians, mt);
    }

    // selectIndex returns the index of the k-th element in a[l..r]
    private static int selectIndex(int[] a, int l, int r, int targetIdx, Metrics mt) {
        while (true) {
            if (l == r) return l;
            int pivotIdx = (l + r) >>> 1; // simple pivot for inner select of medians
            int p = partitionAround(a, l, r, pivotIdx, mt);
            if (targetIdx == p) return p;
            else if (targetIdx < p) r = p - 1;
            else l = p + 1;
        }
    }

    private static void insertion(int[] a, int l, int r, Metrics m) {
        for (int i = l + 1; i <= r; i++) {
            int key = a[i];
            int j = i - 1;
            while (j >= l && gt(a[j], key, m)) { a[j + 1] = a[j]; if (m != null) m.moves++; j--; }
            a[j + 1] = key; if (m != null) m.moves++;
        }
    }

    private static boolean le(int x, int y, Metrics m) { if (m != null) m.comps++; return x <= y; }
    private static boolean gt(int x, int y, Metrics m) { if (m != null) m.comps++; return x > y; }
    private static void swap(int[] a, int i, int j, Metrics m) { int t=a[i]; a[i]=a[j]; a[j]=t; if (m!=null) m.moves+=3; }
}