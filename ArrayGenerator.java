import java.util.Random;

/**
 * ArrayGenerator.java
 * 
 * Utility class to generate arrays for performance analysis.
 * Generates arrays under 3 standard benchmark conditions:
 * 1. Random Array (arbitrary values)
 * 2. Already Sorted Array (1, 2, 3, ..., size)
 * 3. Reverse-Sorted Array (size, size - 1, ..., 1)
 */
public class ArrayGenerator {

    /**
     * Generates a random array with integers between 0 and 9999.
     * 
     * @param size Number of elements
     * @return Generated array
     */
    public static int[] generateRandom(int size) {
        Random random = new Random();
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = random.nextInt(10000);
        }
        return arr;
    }

    /**
     * Generates an already sorted array in ascending order: [1, 2, ..., size].
     * 
     * @param size Number of elements
     * @return Generated sorted array
     */
    public static int[] generateSorted(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = i + 1;
        }
        return arr;
    }

    /**
     * Generates a reverse-sorted array in descending order: [size, size - 1, ..., 1].
     * 
     * @param size Number of elements
     * @return Generated reverse-sorted array
     */
    public static int[] generateReverseSorted(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = size - i;
        }
        return arr;
    }

    /**
     * Helper to create an exact, independent copy of an array.
     * This ensures both sorting algorithms start with the identical dataset.
     * 
     * @param original The array to copy
     * @return A new independent copy
     */
    public static int[] copy(int[] original) {
        if (original == null) {
            return null;
        }
        int[] copy = new int[original.length];
        for (int i = 0; i < original.length; i++) {
            copy[i] = original[i];
        }
        return copy;
    }
}
