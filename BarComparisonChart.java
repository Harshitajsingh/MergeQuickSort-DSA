import java.awt.*;
import javax.swing.*;

/**
 * BarComparisonChart.java
 * 
 * A clean, lightweight Java Swing component that visually compares
 * the execution times of Merge Sort and Quick Sort using custom-painted
 * proportional horizontal bars. No external charting libraries needed.
 */
public class BarComparisonChart extends JPanel {

    private PerformanceAnalyzer.TestResult result;

    public BarComparisonChart() {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(550, 160));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    }

    public void updateChart(PerformanceAnalyzer.TestResult result) {
        this.result = result;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        if (result == null) {
            g2.setColor(new Color(148, 163, 184));
            g2.setFont(new Font("Segoe UI", Font.ITALIC, 14));
            String placeholder = "Run a test to view visual execution time comparison.";
            FontMetrics fm = g2.getFontMetrics();
            int strW = fm.stringWidth(placeholder);
            g2.drawString(placeholder, (width - strW) / 2, height / 2);
            g2.dispose();
            return;
        }

        // Title
        g2.setFont(new Font("Segoe UI", Font.BOLD, 13));
        g2.setColor(new Color(51, 65, 85));
        String title = "Execution Time Comparison (" + result.getInputType() + ", " + result.getArraySize() + " items)";
        g2.drawString(title, 15, 20);

        long mergeNs = Math.max(1, result.getMergeTimeNs());
        long quickNs = Math.max(1, result.getQuickTimeNs());
        long maxNs = Math.max(mergeNs, quickNs);

        int labelX = 15;
        int barStartX = 120;
        int maxBarWidth = Math.max(100, width - barStartX - 190);
        int barHeight = 26;

        // Bar 1: Merge Sort
        int mergeBarWidth = (int) (((double) mergeNs / maxNs) * maxBarWidth);
        mergeBarWidth = Math.max(20, mergeBarWidth);

        int yMerge = 40;
        g2.setFont(new Font("Segoe UI", Font.BOLD, 12));
        g2.setColor(new Color(30, 41, 59));
        g2.drawString("Merge Sort", labelX, yMerge + 18);

        // Bar shape
        g2.setColor(new Color(37, 99, 235)); // Vibrant Blue
        g2.fillRoundRect(barStartX, yMerge, mergeBarWidth, barHeight, 8, 8);

        // Time text
        g2.setColor(new Color(15, 23, 42));
        g2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        String mergeText = String.format("%.2f ms (%d ns)", result.getMergeTimeMs(), result.getMergeTimeNs());
        g2.drawString(mergeText, barStartX + mergeBarWidth + 10, yMerge + 18);

        // Winner icon if Merge Sort won
        if ("Merge Sort".equals(result.getFasterAlgorithm())) {
            g2.setColor(new Color(16, 185, 129));
            g2.setFont(new Font("Segoe UI", Font.BOLD, 11));
            g2.drawString("★ FASTER", barStartX + mergeBarWidth + g2.getFontMetrics().stringWidth(mergeText) + 20, yMerge + 18);
        }

        // Bar 2: Quick Sort
        int yQuick = 85;
        int quickBarWidth = (int) (((double) quickNs / maxNs) * maxBarWidth);
        quickBarWidth = Math.max(20, quickBarWidth);

        g2.setFont(new Font("Segoe UI", Font.BOLD, 12));
        g2.setColor(new Color(30, 41, 59));
        g2.drawString("Quick Sort", labelX, yQuick + 18);

        // Bar shape
        if (result.isQuickStackOverflow()) {
            g2.setColor(new Color(239, 68, 68)); // Red for overflow
        } else {
            g2.setColor(new Color(139, 92, 246)); // Purple
        }
        g2.fillRoundRect(barStartX, yQuick, quickBarWidth, barHeight, 8, 8);

        // Time text
        g2.setColor(new Color(15, 23, 42));
        g2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        String quickText = result.getQuickFormatted();
        g2.drawString(quickText, barStartX + quickBarWidth + 10, yQuick + 18);

        // Winner icon if Quick Sort won
        if ("Quick Sort".equals(result.getFasterAlgorithm())) {
            g2.setColor(new Color(16, 185, 129));
            g2.setFont(new Font("Segoe UI", Font.BOLD, 11));
            g2.drawString("★ FASTER", barStartX + quickBarWidth + g2.getFontMetrics().stringWidth(quickText) + 20, yQuick + 18);
        }

        // Summary footer line
        g2.setColor(new Color(100, 116, 139));
        g2.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        g2.drawString(result.getDifferenceDescription(), 15, 135);

        g2.dispose();
    }
}
