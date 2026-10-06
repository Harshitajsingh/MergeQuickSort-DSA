# ⚡ Sorting Algorithm Performance Analyzer
### *Merge Sort vs Quick Sort — Desktop Performance Benchmarking Suite*

A polished, college-level Java Swing desktop application designed to analyze and experimentally compare the execution efficiency of **Merge Sort** and **Quick Sort** under diverse input conditions.

---

## 📌 Project Overview & Objectives

The goal of this project is to experimentally evaluate the divide-and-conquer sorting algorithms **Merge Sort** and **Quick Sort** using exact, high-precision nanosecond timings (`System.nanoTime()`).

1. **Pure Scratch DSA Core**: Implemented without any built-in sorting methods (`Arrays.sort()`).
2. **Fair Comparison Protocol**: Both algorithms operate on independent, identical deep copies of the original generated array.
3. **Multi-Condition Benchmarking**:
   - 🎲 **Random Array**: Realistic benchmark of average-case behavior.
   - 📈 **Already Sorted Array**: Demonstrates worst-case partitioning degradation in Quick Sort.
   - 📉 **Reverse-Sorted Array**: Demonstrates worst-case partitioning degradation in Quick Sort.
4. **Modern Java Swing Interface**: A desktop interface modeled like a modern academic dashboard, featuring sidebar navigation, KPI metric cards, comparative data tables, and custom-painted graphical execution time bars.

---

## 🏗️ Project Architecture

```text
MergeQuickSortProject/
├── Main.java                 # Application entry point; initializes Look & Feel and launches GUI
├── Dashboard.java            # Main Java Swing window with sidebar navigation and card views
├── BarComparisonChart.java   # Custom-painted Swing component for visual bar chart comparison
├── PerformanceAnalyzer.java  # Benchmark executor and TestResult data model
├── ArrayGenerator.java       # Utility for generating random, sorted, and reverse-sorted arrays
├── MergeSort.java            # Pure scratch Merge Sort implementation (Divide & Conquer)
├── QuickSort.java            # Pure scratch Quick Sort implementation (Lomuto Partition Scheme)
└── README.md                 # Comprehensive project documentation
```

---

## 🖥️ Application Pages & Features

### 1. 🏠 Home Page
- **Header**: Title and subtitle introducing the performance analyzer.
- **Algorithm Cards**: Clear summary of **Merge Sort** and **Quick Sort** time complexities ($O(n \log n)$ vs $O(n^2)$) and auxiliary space complexities.
- **Objective Summary**: Concise explanation of the experimental goals.
- **Start Analysis Button**: Direct shortcut to the testing interface.

### 2. 🔢 Run Test Page
- **Flexible Array Sizes**: Presets for `100`, `1,000`, `5,000`, `10,000`, plus custom inputs.
- **Condition Selection**: Choose between *Random*, *Already Sorted*, or *Reverse-Sorted* datasets.
- **One-Click Benchmark**:
  - `Run Performance Test`: Runs a single selected condition.
  - `⚡ Benchmark All 3 Conditions`: Sequentially benchmarks all three conditions and updates the dashboard.
- **Live Result Card**: Displays instant execution times, the faster algorithm, and the speedup ratio.

### 3. 📊 Performance Analysis Page
- **KPI Metric Cards**: Displays Input Size, Merge Sort Time, Quick Sort Time, and the Winning Algorithm.
- **Visual Bar Comparison Chart**: Custom-painted horizontal proportional bars comparing both algorithms with millisecond/nanosecond labels and victory badges.
- **Comparative Data Table**: Dynamic history table showing every test performed with Input Type, Size, Merge Sort Time, Quick Sort Time, and Winner.

### 4. 📚 Algorithms Page
- In-depth theoretical comparison of **Merge Sort** ($O(n \log n)$ guarantee, stable, $O(n)$ space) and **Quick Sort** (in-place, cache-friendly, $O(n^2)$ worst case on sorted/reverse-sorted inputs with end-element pivot).

### 5. ℹ️ About Project Page
- College mini-project metadata, concept breakdown, Divide-and-Conquer technique summary, and experimental conclusions.

---

## ⏱️ Theoretical Time & Space Complexities

| Algorithm | Best Case | Average Case | Worst Case | Space Complexity | Stability |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **Merge Sort** | $O(n \log n)$ | $O(n \log n)$ | $O(n \log n)$ | $O(n)$ | Stable |
| **Quick Sort** | $O(n \log n)$ | $O(n \log n)$ | $O(n^2)$ | $O(\log n)$ | Unstable |

---

## 🚀 How to Run the Application

### Running in VS Code
1. Open the project folder in VS Code (`File` > `Open Folder...` > `MergeQuickSortProject`).
2. Open [`Main.java`](file:///c:/Users/Harshita%20Singh/OneDrive/Desktop/MergeQuickSortProject/Main.java).
3. Press `F5` or click the **Run** button at the top-right. The Swing Desktop Window will launch centered on screen.

### Running via Terminal / PowerShell
```powershell
# 1. Compile all Java files
javac -encoding UTF-8 *.java

# 2. Launch the desktop GUI
java Main

# (Optional) Launch in terminal console mode
java Main --cli
```

---

## 🔬 Experimental Observations & Conclusions

1. **Random Data**:
   - Quick Sort generally outperforms Merge Sort by $1.2\times - 2.5\times$ due to in-place operations, low constant factor overhead, and superior CPU cache locality.
2. **Already Sorted & Reverse-Sorted Data**:
   - Because this implementation uses the last element as the pivot (Lomuto partition scheme), sorted and reverse-sorted arrays cause worst-case unbalanced partitions of sizes $0$ and $n-1$.
   - Quick Sort's execution time degrades dramatically toward $O(n^2)$, whereas Merge Sort continues to execute reliably in $O(n \log n)$ time, winning decisively.
3. **Memory Trade-Off**:
   - Merge Sort requires $O(n)$ additional memory for merging buffers.
   - Quick Sort requires only $O(\log n)$ recursion stack frames in favorable cases.
