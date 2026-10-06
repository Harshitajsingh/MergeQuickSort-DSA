import java.awt.GraphicsEnvironment;
import java.util.Random;
import java.util.Scanner;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Main.java
 * 
 * Entry point for the "Sorting Algorithm Performance Analyzer" project.
 * Launches the modern Java Swing desktop GUI dashboard by default.
 * Also preserves a console fallback mode (via --cli argument).
 */
public class Main {

    public static void main(String[] args) {
        // Check if user requested console mode or if running in headless environment
        if ((args.length > 0 && "--cli".equalsIgnoreCase(args[0])) || GraphicsEnvironment.isHeadless()) {
            runConsoleMode();
            return;
        }

        // Apply native system Look and Feel for modern component rendering
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
            // Fallback to default Swing Look and Feel
        }

        // Launch the Java Swing Desktop Application
        SwingUtilities.invokeLater(() -> {
            Dashboard dashboard = new Dashboard();
            dashboard.setVisible(true);
        });
    }

    /**
     * Fallback console mode to ensure terminal-based array testing still works.
     */
    private static void runConsoleMode() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Sorting Algorithm Performance Analyzer (Console Mode) ===");
        System.out.println("1. Manual Array Input");
        System.out.println("2. Test Input Conditions (Random, Already Sorted, Reverse-Sorted)");
        System.out.print("Choose an option (1 or 2): ");

        if (!scanner.hasNextInt()) {
            System.out.println("No input provided. Exiting console mode.");
            scanner.close();
            return;
        }

        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                runManualArrayTest(scanner);
                break;
            case 2:
                runConditionTests(scanner);
                break;
            default:
                System.out.println("Invalid choice. Please run the program again and choose 1 or 2.");
                break;
        }

        scanner.close();
    }

    private static void runManualArrayTest(Scanner scanner) {
        System.out.print("\nEnter the number of elements: ");
        if (!scanner.hasNextInt()) return;
        int n = scanner.nextInt();

        if (n <= 0) {
            System.out.println("Array size must be greater than 0.");
            return;
        }

        int[] originalArray = new int[n];
        System.out.println("Enter the elements:");
        for (int i = 0; i < n; i++) {
            originalArray[i] = scanner.nextInt();
        }

        PerformanceAnalyzer.TestResult res = PerformanceAnalyzer.runBenchmarkOnArray("Manual Input", originalArray);

        System.out.println("\nMerge Sort Result:");
        System.out.println("Time Taken: " + res.getMergeFormatted());

        System.out.println("\nQuick Sort Result:");
        System.out.println("Time Taken: " + res.getQuickFormatted());

        System.out.println("\nFaster Algorithm: " + res.getFasterAlgorithm());
        System.out.println(res.getDifferenceDescription());

        System.out.println("\nMerge Sort:");
        System.out.println("Best Case: O(n log n) | Average Case: O(n log n) | Worst Case: O(n log n)");
        System.out.println("\nQuick Sort:");
        System.out.println("Best Case: O(n log n) | Average Case: O(n log n) | Worst Case: O(n²)");
    }

    private static void runConditionTests(Scanner scanner) {
        System.out.print("\nEnter array size for testing: ");
        if (!scanner.hasNextInt()) return;
        int size = scanner.nextInt();

        if (size <= 0) {
            System.out.println("Array size must be greater than 0.");
            return;
        }

        System.out.println("\n===========================================");
        System.out.println("       INPUT CONDITIONS TEST RESULTS       ");
        System.out.println("===========================================");

        for (String condition : new String[]{"Random", "Already Sorted", "Reverse-Sorted"}) {
            PerformanceAnalyzer.TestResult res = PerformanceAnalyzer.runBenchmark(condition, size);
            System.out.println("\nInput Type: " + res.getInputType());
            System.out.println("Array Size: " + res.getArraySize());
            System.out.println("Merge Sort Time: " + res.getMergeFormatted());
            System.out.println("Quick Sort Time: " + res.getQuickFormatted());
            System.out.println("Faster Algorithm: " + res.getFasterAlgorithm());
        }
    }
}
