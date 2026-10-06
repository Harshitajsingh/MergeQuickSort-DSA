/**
 * QuickSort.java
 * 
 * This class implements the Quick Sort algorithm from scratch.
 * Quick Sort is a divide-and-conquer algorithm that selects a 'pivot' element
 * and partitions the array such that elements smaller than the pivot go to the left,
 * and elements greater than the pivot go to the right.
 */
public class QuickSort {

    /**
     * Main method to sort the entire array using Quick Sort.
     * 
     * @param arr The array to be sorted
     */
    public static void sort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        quickSort(arr, 0, arr.length - 1);
    }

    /**
     * Recursive helper method to partition and sort subarrays.
     * 
     * @param arr The array being sorted
     * @param low The starting index of the subarray
     * @param high The ending index of the subarray
     */
    private static void quickSort(int[] arr, int low, int high) {
        // Base condition: keep partitioning as long as low < high
        if (low < high) {
            // Find the partition index (pivot is placed at its correct sorted position)
            int partitionIndex = partition(arr, low, high);

            // Recursively sort elements before and after partition
            quickSort(arr, low, partitionIndex - 1);
            quickSort(arr, partitionIndex + 1, high);
        }
    }

    /**
     * Helper method to partition the array around a pivot element.
     * We pick the last element arr[high] as the pivot.
     * 
     * @param arr The array to partition
     * @param low The starting index
     * @param high The ending index (index of pivot)
     * @return The final index position of the pivot
     */
    private static int partition(int[] arr, int low, int high) {
        // Choose the last element as the pivot
        int pivot = arr[high];

        // Index of smaller element, indicates the right position of pivot found so far
        int i = low - 1;

        // Traverse the subarray and compare each element with the pivot
        for (int j = low; j < high; j++) {
            // If current element is smaller than or equal to pivot
            if (arr[j] <= pivot) {
                i++;
                // Swap arr[i] and arr[j]
                swap(arr, i, j);
            }
        }

        // Place pivot at its correct sorted position by swapping arr[i + 1] and arr[high]
        swap(arr, i + 1, high);

        return i + 1; // Return the partition index
    }

    /**
     * Helper method to swap two elements in an array.
     * 
     * @param arr The array
     * @param i First index
     * @param j Second index
     */
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
