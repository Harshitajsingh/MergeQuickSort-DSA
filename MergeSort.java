/**
 * MergeSort.java
 * 
 * This class implements the Merge Sort algorithm from scratch.
 * Merge Sort is a divide-and-conquer algorithm that divides the array
 * into two halves, recursively sorts them, and then merges the sorted halves.
 */
public class MergeSort {

    /**
     * Main method to sort the entire array using Merge Sort.
     * 
     * @param arr The array to be sorted
     */
    public static void sort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        mergeSort(arr, 0, arr.length - 1);
    }

    /**
     * Recursive helper method to divide and sort subarrays.
     * 
     * @param arr The array being sorted
     * @param left The starting index of the subarray
     * @param right The ending index of the subarray
     */
    private static void mergeSort(int[] arr, int left, int right) {
        // Base condition: if left is less than right, there are at least 2 elements
        if (left < right) {
            // Find the middle point to divide the array into two halves
            int mid = left + (right - left) / 2;

            // Recursively sort the first half
            mergeSort(arr, left, mid);

            // Recursively sort the second half
            mergeSort(arr, mid + 1, right);

            // Merge the two sorted halves
            merge(arr, left, mid, right);
        }
    }

    /**
     * Helper method to merge two sorted halves into a single sorted subarray.
     * First subarray is arr[left..mid]
     * Second subarray is arr[mid+1..right]
     * 
     * @param arr The array containing both subarrays
     * @param left The start index of the first subarray
     * @param mid The end index of the first subarray
     * @param right The end index of the second subarray
     */
    private static void merge(int[] arr, int left, int mid, int right) {
        // Calculate lengths of two temporary subarrays
        int n1 = mid - left + 1;
        int n2 = right - mid;

        // Create temporary arrays
        int[] leftArray = new int[n1];
        int[] rightArray = new int[n2];

        // Copy data to temporary arrays
        for (int i = 0; i < n1; i++) {
            leftArray[i] = arr[left + i];
        }
        for (int j = 0; j < n2; j++) {
            rightArray[j] = arr[mid + 1 + j];
        }

        // Initial indices for leftArray, rightArray, and merged array
        int i = 0; // index for leftArray
        int j = 0; // index for rightArray
        int k = left; // index for merged array in arr

        // Compare elements and merge in sorted order
        while (i < n1 && j < n2) {
            if (leftArray[i] <= rightArray[j]) {
                arr[k] = leftArray[i];
                i++;
            } else {
                arr[k] = rightArray[j];
                j++;
            }
            k++;
        }

        // Copy any remaining elements of leftArray
        while (i < n1) {
            arr[k] = leftArray[i];
            i++;
            k++;
        }

        // Copy any remaining elements of rightArray
        while (j < n2) {
            arr[k] = rightArray[j];
            j++;
            k++;
        }
    }
}
