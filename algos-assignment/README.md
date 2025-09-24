# GAPO DESIGN and ANALYSIS OF ALGORITHMS — Assignment 1

Implements: MergeSort (buffer + cutoff), QuickSort (randomized + smaller-first recursion),
Deterministic Select (Median-of-Medians), and Closest Pair of Points (2D). Includes a CLI that
runs algorithms, collects metrics, and writes CSV rows for plotting.

## Build & Run
```bash
mvn -q -DskipTests package
# Example: run mergesort on n=100000 for 3 trials
mvn -q exec:java -Dexec.args="--algo mergesort --n 100000 --trials 3 --csv out.csv"
```
