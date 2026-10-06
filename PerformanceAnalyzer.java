import java.util.ArrayList;
import java.util.List;

/**
 * PerformanceAnalyzer.java
 * 
 * Orchestrates fair benchmarking between Merge Sort and Quick Sort.
 * Ensures both algorithms receive identical, independent copies of the dataset
 * and measures execution times accurately using System.nanoTime().
 */
public class PerformanceAnalyzer {

    /**
     * Data model holding the results of a single benchmark test.
     */
    public static class TestResult {
        private final String inputType;
        private final int arraySize;
        private final long mergeTimeNs;
        private final long quickTimeNs;
        private final String fasterAlgorithm;
        private final boolean quickStackOverflow;

        public TestResult(String inputType, int arraySize, long mergeTimeNs, long quickTimeNs, 
                          String fasterAlgorithm, boolean quickStackOverflow) {
            this.inputType = inputType;
            this.arraySize = arraySize;
            this.mergeTimeNs = mergeTimeNs;
            this.quickTimeNs = quickTimeNs;
            this.fasterAlgorithm = fasterAlgorithm;
            this.quickStackOverflow = quickStackOverflow;
        }

        public String getInputType() {
            return inputType;
        }

        public int getArraySize() {
            return arraySize;
        }

        public long getMergeTimeNs() {
            return mergeTimeNs;
        }

        public long getQuickTimeNs() {
            return quickTimeNs;
        }

        public double getMergeTimeMs() {
            return mergeTimeNs / 1_000_000.0;
        }

        public double getQuickTimeMs() {
            return quickTimeNs / 1_000_000.0;
        }

        public String getFasterAlgorithm() {
            return fasterAlgorithm;
        }

        public boolean isQuickStackOverflow() {
            return quickStackOverflow;
        }

        public String getMergeFormatted() {
            return String.format("%.2f ms (%d ns)", getMergeTimeMs(), mergeTimeNs);
        }

        public String getQuickFormatted() {
            if (quickStackOverflow) {
                return "Stack Overflow (O(n²))";
            }
            return String.format("%.2f ms (%d ns)", getQuickTimeMs(), quickTimeNs);
        }

        public String getDifferenceDescription() {
            if (quickStackOverflow) {
                return "Merge Sort finished safely; Quick Sort exceeded recursion limit";
            }
            if (mergeTimeNs == quickTimeNs) {
                return "Both algorithms performed equally";
            }
            long diffNs = Math.abs(mergeTimeNs - quickTimeNs);
            double diffMs = diffNs / 1_000_000.0;
            double ratio = (mergeTimeNs > quickTimeNs) 
                    ? (double) mergeTimeNs / Math.max(1, quickTimeNs)
                    : (double) quickTimeNs / Math.max(1, mergeTimeNs);
            return String.format("%s was %.2fx faster (difference: %.2f ms / %,d ns)", 
                    fasterAlgorithm, ratio, diffMs, diffNs);
        }
    }

    /**
     * Runs a benchmark on a specific input condition and size.
     * 
     * @param inputType "Random", "Already Sorted", or "Reverse-Sorted"
     * @param size Number of elements
     * @return TestResult containing metrics
     */
    public static TestResult runBenchmark(String inputType, int size) {
        int[] originalArray;

        if (inputType.toLowerCase().contains("reverse")) {
            originalArray = ArrayGenerator.generateReverseSorted(size);
        } else if (inputType.toLowerCase().contains("sorted")) {
            originalArray = ArrayGenerator.generateSorted(size);
        } else {
            originalArray = ArrayGenerator.generateRandom(size);
        }

        return runBenchmarkOnArray(inputType, originalArray);
    }

    /**
     * Runs a fair benchmark on any provided original array by cloning two independent copies.
     * 
     * @param inputType Descriptive label of the input
     * @param originalArray The original dataset
     * @return TestResult containing metrics
     */
    public static TestResult runBenchmarkOnArray(String inputType, int[] originalArray) {
        int size = originalArray.length;

        // Step 1: Create two independent copies of the exact same array
        int[] mergeCopy = ArrayGenerator.copy(originalArray);
        int[] quickCopy = ArrayGenerator.copy(originalArray);

        // Step 2: Measure Merge Sort execution time
        long startMerge = System.nanoTime();
        MergeSort.sort(mergeCopy);
        long endMerge = System.nanoTime();
        long mergeDuration = endMerge - startMerge;

        // Step 3: Measure Quick Sort execution time (with defensive recursion guard)
        boolean stackOverflow = false;
        long startQuick = System.nanoTime();
        try {
            QuickSort.sort(quickCopy);
        } catch (StackOverflowError e) {
            stackOverflow = true;
        }
        long endQuick = System.nanoTime();
        long quickDuration = endQuick - startQuick;

        // Step 4: Determine faster algorithm
        String faster;
        if (stackOverflow) {
            faster = "Merge Sort";
        } else if (mergeDuration < quickDuration) {
            faster = "Merge Sort";
        } else if (quickDuration < mergeDuration) {
            faster = "Quick Sort";
        } else {
            faster = "Equal Time";
        }

        return new TestResult(inputType, size, mergeDuration, quickDuration, faster, stackOverflow);
    }

    /**
     * Runs benchmarks across all 3 standard conditions (Random, Sorted, Reverse Sorted).
     * 
     * @param size Number of elements
     * @return List of 3 TestResults
     */
    public static List<TestResult> runAllConditions(int size) {
        List<TestResult> results = new ArrayList<>();
        results.add(runBenchmark("Random", size));
        results.add(runBenchmark("Already Sorted", size));
        results.add(runBenchmark("Reverse-Sorted", size));
        return results;
    }
}
