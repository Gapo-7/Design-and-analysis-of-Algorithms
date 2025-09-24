package edu.gapo.daa;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Random;

public class ClosestPairTest {
    private static double brute(Point[] pts) {
        double best = Double.POSITIVE_INFINITY;
        for (int i = 0; i < pts.length; i++) {
            for (int j = i + 1; j < pts.length; j++) {
                double dx = pts[i].x - pts[j].x;
                double dy = pts[i].y - pts[j].y;
                double d = Math.hypot(dx, dy);
                if (d < best) best = d;
            }
        }
        return best;
    }

    @Test
    public void smallAgreesWithBrute() {
        Random rnd = new Random(4);
        int n = 500;
        Point[] pts = new Point[n];
        for (int i = 0; i < n; i++) pts[i] = new Point(rnd.nextDouble()*1e5, rnd.nextDouble()*1e5);
        Metrics m = new Metrics();
        double fast = ClosestPair.solve(pts, m);
        double slow = brute(pts);
        assertEquals(slow, fast, 1e-9);
    }
}