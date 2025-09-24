package edu.gapo.daa;

public class Metrics {
    public long comps = 0;
    public long moves = 0;
    public int maxDepth = 0;
    private int curDepth = 0;

    public void enter() {
        curDepth++;
        if (curDepth > maxDepth) maxDepth = curDepth;
    }
    public void exit() { curDepth--; }

    public void reset() {
        comps = 0; moves = 0; maxDepth = 0; curDepth = 0;
    }
}