package edu.gapo.daa;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Random;
import java.util.Arrays;

public class SelectTest {
    @Test
    public void agreesWithSort() {
        Random rnd = new Random(3);
        for (int t = 0; t < 100; t++) {
            int n = 1000;
            int[] a = rnd.ints(n).toArray();
            int k = rnd.nextInt(n);
            int[] b = a.clone();
            Arrays.sort(b);
            Metrics m = new Metrics();
            int val = Select.select(a, k, m);
            assertEquals(b[k], val);
        }
    }
}