package edu.gapo.daa;

import java.io.*;
import java.util.*;

public final class Main {
    public static void main(String[] args) throws Exception {
        Map<String, String> arg = parseArgs(args);
        String algo = arg.getOrDefault("--algo", "mergesort").toLowerCase();
        int n = Integer.parseInt(arg.getOrDefault("--n", "100000"));
        int trials = Integer.parseInt(arg.getOrDefault("--trials", "1"));
        String csv = arg.getOrDefault("--csv", "out.csv");

        try (PrintWriter pw = new PrintWriter(new FileWriter(csv, true))) {
            // header if empty
            File f = new File(csv);
            if (f.length() == 0) pw.println("algo,n,time_ns,depth,comparisons,moves");

            for (int t = 0; t < trials; t++) {
                int[] a = randomArray(n, 1234 + t);
                Metrics m = new Metrics();
                long start = System.nanoTime();
                switch (algo) {
                    case "mergesort" -> MergeSort.sort(a, m);
                    case "quicksort" -> QuickSort.sort(a, m);
                    case "select" -> {
                        int k = n / 2;
                        Select.select(a, k, m);
                    }
                    case "closest" -> {
                        Point[] pts = randomPoints(n, 1234 + t);
                        ClosestPair.solve(pts, m);
                    }
                    default -> throw new IllegalArgumentException("Unknown --algo: " + algo);
                }

                long time = System.nanoTime() - start;
                pw.printf(Locale.US, "%s,%d,%d,%d,%d,%d%n", algo, n, time, m.maxDepth, m.comps, m.moves);
            }
        }
    }

    private static Map<String, String> parseArgs(String[] a) {
        Map<String, String> m = new HashMap<>();
        for (int i = 0; i < a.length; i++) {
            if (a[i].startsWith("--")) {
                String k = a[i];
                String v = (i + 1 < a.length && !a[i+1].startsWith("--")) ? a[++i] : "true";
                m.put(k, v);
            }
        }
        return m;
    }

    private static int[] randomArray(int n, int seed) {
        Random rnd = new Random(seed);
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = rnd.nextInt();
        return a;
    }

    private static Point[] randomPoints(int n, int seed) {
        Random rnd = new Random(seed);
        Point[] pts = new Point[n];
        for (int i = 0; i < n; i++) pts[i] = new Point(rnd.nextDouble()*1e6, rnd.nextDouble()*1e6);
        return pts;
    }
}