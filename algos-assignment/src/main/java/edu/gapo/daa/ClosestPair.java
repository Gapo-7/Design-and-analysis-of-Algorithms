package edu.gapo.daa;

import java.util.Arrays;
import java.util.Comparator;

public final class ClosestPair {

    private static final Comparator<Point> BY_X = (p, q) -> Double.compare(p.x, q.x);
    private static final Comparator<Point> BY_Y = (p, q) -> Double.compare(p.y, q.y);

    public static double solve(Point[] pts, Metrics m) {
        if (pts == null || pts.length < 2) return Double.POSITIVE_INFINITY;
        Point[] px = pts.clone();
        Point[] py = pts.clone();
        Arrays.sort(px, BY_X);
        Arrays.sort(py, BY_Y);
        return rec(px, py, 0, pts.length - 1, m);
    }

    private static double rec(Point[] px, Point[] py, int l, int r, Metrics mt) {
        int n = r - l + 1;
        if (n <= 3) {
            double best = Double.POSITIVE_INFINITY;
            for (int i = l; i <= r; i++) {
                for (int j = i + 1; j <= r; j++) {
                    best = Math.min(best, dist(px[i], px[j], mt));
                }
            }
            return best;
        }
        int mid = (l + r) >>> 1;
        double midX = px[mid].x;

        if (mt != null) mt.enter();
        // Split py into pyl/pyr in O(n) using midX and the px index positions
        Point[] pyl = new Point[(mid - l + 1)];
        Point[] pyr = new Point[(r - mid)];
        int il = 0, irp = 0;
        boolean[] inLeft = new boolean[r - l + 1];
        // map px index
        // Build an index map from Point identity to side is cumbersome; rely on x threshold
        for (Point p : py) {
            if (p.x <= midX && il < pyl.length) pyl[il++] = p;
            else pyr[irp++] = p;
        }
        double dl = rec(px, pyl, l, mid, mt);
        double dr = rec(px, pyr, mid + 1, r, mt);
        if (mt != null) mt.exit();
        double d = Math.min(dl, dr);

        // Build strip in y-order
        Point[] strip = new Point[r - l + 1];
        int s = 0;
        for (Point p : py) {
            if (Math.abs(p.x - midX) < d) strip[s++] = p;
        }
        double best = d;
        for (int i = 0; i < s; i++) {
            // Check next up to 7 points
            for (int j = i + 1; j < s && j <= i + 7; j++) {
                best = Math.min(best, dist(strip[i], strip[j], mt));
            }
        }
        return best;
    }

    private static double dist(Point a, Point b, Metrics m) {
        double dx = a.x - b.x;
        double dy = a.y - b.y;
        double val = Math.hypot(dx, dy);
        // Not counting comparisons here; could count arithmetic ops if needed.
        return val;
    }
}