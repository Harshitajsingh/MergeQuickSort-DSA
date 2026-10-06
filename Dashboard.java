import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 * Dashboard.java
 * 
 * Main Java Swing Desktop Application Window for:
 * "Sorting Algorithm Performance Analyzer" (Merge Sort vs Quick Sort).
 * 
 * Features a modern academic/technology UI layout with sidebar navigation,
 * informative cards, live performance benchmarking, interactive tables,
 * and a custom-painted execution time comparison bar chart.
 */
public class Dashboard extends JFrame {

    // Color Palette
    public static final Color COLOR_SIDEBAR_BG = new Color(15, 23, 42);       // Dark Slate #0F172A
    public static final Color COLOR_SIDEBAR_HOVER = new Color(30, 41, 59);    // Slate #1E293B
    public static final Color COLOR_PRIMARY = new Color(37, 99, 235);         // Royal Blue #2563EB
    public static final Color COLOR_PRIMARY_HOVER = new Color(29, 78, 216);   // Darker Blue
    public static final Color COLOR_CONTENT_BG = new Color(248, 250, 252);    // Crisp Gray #F8FAFC
    public static final Color COLOR_CARD_BG = Color.WHITE;
    public static final Color COLOR_BORDER = new Color(226, 232, 240);        // Light Slate Border #E2E8F0
    public static final Color COLOR_TEXT_MAIN = new Color(15, 23, 42);        // #0F172A
    public static final Color COLOR_TEXT_MUTED = new Color(100, 116, 139);    // #64748B
    public static final Color COLOR_SUCCESS = new Color(16, 185, 129);        // Emerald #10B981
    public static final Color COLOR_PURPLE = new Color(124, 58, 237);         // Violet #7C3AED

    // Fonts
    public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 22);
    public static final Font FONT_SUBTITLE = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONT_CARD_TITLE = new Font("Segoe UI", Font.BOLD, 16);
    public static final Font FONT_BODY = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONT_BODY_BOLD = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font FONT_KPI_VALUE = new Font("Segoe UI", Font.BOLD, 18);
    public static final Font FONT_KPI_LABEL = new Font("Segoe UI", Font.PLAIN, 11);

    // CardLayout and Navigation
    private CardLayout cardLayout;
    private JPanel contentPanel;
    private List<JButton> navButtons = new ArrayList<>();
    private String currentPage = "HOME";

    // Performance Data Models
    private final List<PerformanceAnalyzer.TestResult> history = new ArrayList<>();
    private PerformanceAnalyzer.TestResult latestResult = null;

    // Performance Analysis Page Components
    private JLabel lblKpiSize;
    private JLabel lblKpiMerge;
    private JLabel lblKpiQuick;
    private JLabel lblKpiWinner;
    private BarComparisonChart chart;
    private DefaultTableModel tableModel;

    // Run Test Page Components
    private JTextField txtArraySize;
    private JComboBox<String> cmbCondition;
    private JLabel lblResultStatus;
    private JLabel lblResultType;
    private JLabel lblResultSize;
    private JLabel lblResultMerge;
    private JLabel lblResultQuick;
    private JLabel lblResultWinner;
    private JLabel lblResultSummary;
    private JPanel pnlResultsCard;

    public Dashboard() {
        setTitle("Sorting Algorithm Performance Analyzer — Merge Sort vs Quick Sort");
        setSize(1120, 740);
        setMinimumSize(new Dimension(980, 640));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center on screen

        initUI();

        // Run an initial quick benchmark (size 1000) so the dashboard has sample data immediately
        runBenchmarkAction("Random", 1000, false);
    }

    private void initUI() {
        JPanel rootPanel = new JPanel(new BorderLayout());
        rootPanel.setBackground(COLOR_CONTENT_BG);

        // 1. Sidebar on the West
        JPanel sidebar = createSidebar();
        rootPanel.add(sidebar, BorderLayout.WEST);

        // 2. Main Content Area with CardLayout
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(COLOR_CONTENT_BG);

        // Add Pages
        contentPanel.add(createScrollable(createHomePage()), "HOME");
        contentPanel.add(createScrollable(createPerformancePage()), "PERFORMANCE");
        contentPanel.add(createScrollable(createRunTestPage()), "RUN_TEST");
        contentPanel.add(createScrollable(createAlgorithmsPage()), "ALGORITHMS");
        contentPanel.add(createScrollable(createAboutPage()), "ABOUT");

        rootPanel.add(contentPanel, BorderLayout.CENTER);
        setContentPane(rootPanel);
    }

    private JScrollPane createScrollable(JPanel panel) {
        JScrollPane scroll = new JScrollPane(panel);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.setBackground(COLOR_CONTENT_BG);
        return scroll;
    }

    // =========================================================================
    // SIDEBAR NAVIGATION
    // =========================================================================
    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(COLOR_SIDEBAR_BG);
        sidebar.setPreferredSize(new Dimension(240, 700));
        sidebar.setBorder(new EmptyBorder(25, 16, 20, 16));

        // Brand / Title Area
        JLabel lblLogo = new JLabel("⚡ DSA ANALYZER");
        lblLogo.setForeground(Color.WHITE);
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 17));
        lblLogo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblSub = new JLabel("Merge Sort vs Quick Sort");
        lblSub.setForeground(new Color(148, 163, 184));
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblSub.setAlignmentX(Component.LEFT_ALIGNMENT);

        sidebar.add(lblLogo);
        sidebar.add(Box.createVerticalStrut(4));
        sidebar.add(lblSub);
        sidebar.add(Box.createVerticalStrut(25));

        // Navigation Menu
        sidebar.add(createNavButton("🏠  Home", "HOME"));
        sidebar.add(Box.createVerticalStrut(6));
        sidebar.add(createNavButton("📊  Performance Analysis", "PERFORMANCE"));
        sidebar.add(Box.createVerticalStrut(6));
        sidebar.add(createNavButton("🔢  Run Test", "RUN_TEST"));
        sidebar.add(Box.createVerticalStrut(6));
        sidebar.add(createNavButton("📚  Algorithms", "ALGORITHMS"));
        sidebar.add(Box.createVerticalStrut(6));
        sidebar.add(createNavButton("ℹ️  About Project", "ABOUT"));

        sidebar.add(Box.createVerticalGlue());

        // Sidebar Footer Tag
        JLabel lblFooter = new JLabel("Java DSA Mini-Project");
        lblFooter.setForeground(new Color(100, 116, 139));
        lblFooter.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblFooter.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(lblFooter);

        // Highlight Home as active initially
        updateNavState("HOME");

        return sidebar;
    }

    private JButton createNavButton(String text, String pageKey) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setForeground(new Color(226, 232, 240));
        btn.setBackground(COLOR_SIDEBAR_BG);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setMaximumSize(new Dimension(210, 42));
        btn.setPreferredSize(new Dimension(210, 42));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(8, 14, 8, 14));

        btn.addActionListener(e -> navigateTo(pageKey));

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!pageKey.equals(currentPage)) {
                    btn.setBackground(COLOR_SIDEBAR_HOVER);
                }
            }
            @Override
            public void mouseExited(MouseEvent e) {
                if (!pageKey.equals(currentPage)) {
                    btn.setBackground(COLOR_SIDEBAR_BG);
                }
            }
        });

        btn.putClientProperty("PAGE_KEY", pageKey);
        navButtons.add(btn);
        return btn;
    }

    private void navigateTo(String pageKey) {
        currentPage = pageKey;
        cardLayout.show(contentPanel, pageKey);
        updateNavState(pageKey);
    }

    private void updateNavState(String activePageKey) {
        for (JButton btn : navButtons) {
            String key = (String) btn.getClientProperty("PAGE_KEY");
            if (activePageKey.equals(key)) {
                btn.setBackground(COLOR_PRIMARY);
                btn.setForeground(Color.WHITE);
            } else {
                btn.setBackground(COLOR_SIDEBAR_BG);
                btn.setForeground(new Color(203, 213, 225));
            }
        }
    }

    // =========================================================================
    // PAGE 1: HOME PAGE
    // =========================================================================
    private JPanel createHomePage() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(COLOR_CONTENT_BG);
        panel.setBorder(new EmptyBorder(30, 35, 30, 35));

        // Header Title Area
        JLabel lblBadge = new JLabel("⚡ SORTING ALGORITHM PERFORMANCE ANALYZER");
        lblBadge.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblBadge.setForeground(COLOR_TEXT_MAIN);
        lblBadge.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblSub = new JLabel("Compare Merge Sort and Quick Sort under different input conditions.");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSub.setForeground(COLOR_TEXT_MUTED);
        lblSub.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(lblBadge);
        panel.add(Box.createVerticalStrut(6));
        panel.add(lblSub);
        panel.add(Box.createVerticalStrut(25));

        // Two Cards Side-by-Side: Merge Sort vs Quick Sort
        JPanel cardsRow = new JPanel(new GridLayout(1, 2, 20, 0));
        cardsRow.setOpaque(false);
        cardsRow.setMaximumSize(new Dimension(1000, 220));
        cardsRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Merge Sort Card
        JPanel cardMerge = createInfoCard(
            "🔵 Merge Sort",
            "Divide and Conquer Algorithm",
            new String[][] {
                {"Best Case:", "O(n log n)"},
                {"Average Case:", "O(n log n)"},
                {"Worst Case:", "O(n log n)"},
                {"Space Complexity:", "O(n)"}
            },
            COLOR_PRIMARY
        );

        // Quick Sort Card
        JPanel cardQuick = createInfoCard(
            "🟣 Quick Sort",
            "Divide & Conquer (Pivot: Last Element)",
            new String[][] {
                {"Best Case:", "O(n log n)"},
                {"Average Case:", "O(n log n)"},
                {"Worst Case:", "O(n²)"},
                {"Space Complexity:", "O(log n)"}
            },
            COLOR_PURPLE
        );

        cardsRow.add(cardMerge);
        cardsRow.add(cardQuick);
        panel.add(cardsRow);
        panel.add(Box.createVerticalStrut(25));

        // Section: Project Objective
        JPanel objectiveCard = createCardPanel();
        objectiveCard.setLayout(new BoxLayout(objectiveCard, BoxLayout.Y_AXIS));
        objectiveCard.setMaximumSize(new Dimension(1000, 160));
        objectiveCard.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblObjHeading = new JLabel("🎯 Project Objective");
        lblObjHeading.setFont(FONT_CARD_TITLE);
        lblObjHeading.setForeground(COLOR_TEXT_MAIN);

        JLabel lblObjBody = new JLabel("<html>To experimentally compare the performance of <b>Merge Sort</b> and <b>Quick Sort</b> using different input conditions:<br/>"
                + "• <b>Random Array:</b> Realistic benchmark of average-case efficiency.<br/>"
                + "• <b>Already Sorted Array:</b> Tests behavior when data is already ordered (Quick Sort worst-case scenario).<br/>"
                + "• <b>Reverse-Sorted Array:</b> Tests behavior with inverted order (Quick Sort worst-case scenario).</html>");
        lblObjBody.setFont(FONT_BODY);
        lblObjBody.setForeground(COLOR_TEXT_MUTED);

        objectiveCard.add(lblObjHeading);
        objectiveCard.add(Box.createVerticalStrut(10));
        objectiveCard.add(lblObjBody);
        panel.add(objectiveCard);
        panel.add(Box.createVerticalStrut(25));

        // "Start Analysis" Button
        JButton btnStart = createPrimaryButton("Start Analysis  →");
        btnStart.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnStart.addActionListener(e -> navigateTo("RUN_TEST"));
        panel.add(btnStart);

        panel.add(Box.createVerticalGlue());
        return panel;
    }

    private JPanel createInfoCard(String title, String subtitle, String[][] rows, Color accentColor) {
        JPanel card = createCardPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitle.setForeground(accentColor);

        JLabel lblSub = new JLabel(subtitle);
        lblSub.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblSub.setForeground(COLOR_TEXT_MUTED);

        card.add(lblTitle);
        card.add(Box.createVerticalStrut(3));
        card.add(lblSub);
        card.add(Box.createVerticalStrut(14));

        for (String[] row : rows) {
            JPanel rowPanel = new JPanel(new BorderLayout());
            rowPanel.setOpaque(false);
            rowPanel.setMaximumSize(new Dimension(450, 24));

            JLabel lblKey = new JLabel(row[0]);
            lblKey.setFont(FONT_BODY);
            lblKey.setForeground(COLOR_TEXT_MUTED);

            JLabel lblVal = new JLabel(row[1]);
            lblVal.setFont(FONT_BODY_BOLD);
            lblVal.setForeground(COLOR_TEXT_MAIN);

            rowPanel.add(lblKey, BorderLayout.WEST);
            rowPanel.add(lblVal, BorderLayout.EAST);
            card.add(rowPanel);
            card.add(Box.createVerticalStrut(4));
        }

        return card;
    }

    // =========================================================================
    // PAGE 2: RUN TEST PAGE
    // =========================================================================
    private JPanel createRunTestPage() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(COLOR_CONTENT_BG);
        panel.setBorder(new EmptyBorder(30, 35, 30, 35));

        // Header
        JLabel lblTitle = new JLabel("🔢 Run Performance Test");
        lblTitle.setFont(FONT_TITLE);
        lblTitle.setForeground(COLOR_TEXT_MAIN);
        lblTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblSub = new JLabel("Configure test parameters, generate datasets, and measure execution times.");
        lblSub.setFont(FONT_SUBTITLE);
        lblSub.setForeground(COLOR_TEXT_MUTED);
        lblSub.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(lblTitle);
        panel.add(Box.createVerticalStrut(4));
        panel.add(lblSub);
        panel.add(Box.createVerticalStrut(20));

        // Configuration Card
        JPanel configCard = createCardPanel();
        configCard.setLayout(new BoxLayout(configCard, BoxLayout.Y_AXIS));
        configCard.setMaximumSize(new Dimension(1000, 240));
        configCard.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Array Size row
        JLabel lblSize = new JLabel("Array Size (Number of Elements):");
        lblSize.setFont(FONT_BODY_BOLD);
        lblSize.setForeground(COLOR_TEXT_MAIN);

        JPanel sizeInputRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        sizeInputRow.setOpaque(false);
        sizeInputRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        txtArraySize = new JTextField("1000", 8);
        txtArraySize.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtArraySize.setPreferredSize(new Dimension(110, 34));
        sizeInputRow.add(txtArraySize);

        // Preset buttons
        for (int preset : new int[]{100, 1000, 5000, 10000}) {
            JButton btnPreset = new JButton(String.valueOf(preset));
            btnPreset.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            btnPreset.setBackground(new Color(241, 245, 249));
            btnPreset.setForeground(COLOR_TEXT_MAIN);
            btnPreset.setFocusPainted(false);
            btnPreset.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btnPreset.addActionListener(e -> txtArraySize.setText(String.valueOf(preset)));
            sizeInputRow.add(btnPreset);
        }

        // Input Condition row
        JLabel lblCond = new JLabel("Input Condition:");
        lblCond.setFont(FONT_BODY_BOLD);
        lblCond.setForeground(COLOR_TEXT_MAIN);

        String[] conditions = {"Random Array", "Already Sorted Array", "Reverse-Sorted Array"};
        cmbCondition = new JComboBox<>(conditions);
        cmbCondition.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cmbCondition.setPreferredSize(new Dimension(220, 34));
        cmbCondition.setMaximumSize(new Dimension(220, 34));
        cmbCondition.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Action Buttons Row
        JPanel actionRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        actionRow.setOpaque(false);
        actionRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton btnRun = createPrimaryButton("▶  Run Performance Test");
        btnRun.addActionListener(e -> handleSingleTestRun());

        JButton btnRunAll = new JButton("⚡ Benchmark All 3 Conditions");
        btnRunAll.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnRunAll.setBackground(new Color(241, 245, 249));
        btnRunAll.setForeground(COLOR_TEXT_MAIN);
        btnRunAll.setFocusPainted(false);
        btnRunAll.setPreferredSize(new Dimension(240, 40));
        btnRunAll.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRunAll.addActionListener(e -> handleRunAllConditions());

        actionRow.add(btnRun);
        actionRow.add(btnRunAll);

        configCard.add(lblSize);
        configCard.add(Box.createVerticalStrut(6));
        configCard.add(sizeInputRow);
        configCard.add(Box.createVerticalStrut(14));
        configCard.add(lblCond);
        configCard.add(Box.createVerticalStrut(6));
        configCard.add(cmbCondition);
        configCard.add(Box.createVerticalStrut(18));
        configCard.add(actionRow);

        panel.add(configCard);
        panel.add(Box.createVerticalStrut(20));

        // Results Card (dynamically updated)
        pnlResultsCard = createCardPanel();
        pnlResultsCard.setLayout(new BoxLayout(pnlResultsCard, BoxLayout.Y_AXIS));
        pnlResultsCard.setMaximumSize(new Dimension(1000, 240));
        pnlResultsCard.setAlignmentX(Component.LEFT_ALIGNMENT);

        lblResultStatus = new JLabel("LATEST TEST RESULT");
        lblResultStatus.setFont(FONT_CARD_TITLE);
        lblResultStatus.setForeground(COLOR_TEXT_MAIN);

        lblResultType = new JLabel("Condition: -");
        lblResultType.setFont(FONT_BODY);
        lblResultSize = new JLabel("Array Size: -");
        lblResultSize.setFont(FONT_BODY);
        lblResultMerge = new JLabel("Merge Sort Time: -");
        lblResultMerge.setFont(FONT_BODY_BOLD);
        lblResultQuick = new JLabel("Quick Sort Time: -");
        lblResultQuick.setFont(FONT_BODY_BOLD);
        lblResultWinner = new JLabel("Faster Algorithm: -");
        lblResultWinner.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblResultSummary = new JLabel("Run a test above to view detailed execution metrics.");
        lblResultSummary.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblResultSummary.setForeground(COLOR_TEXT_MUTED);

        JButton btnViewTable = new JButton("View Full Comparison Table & Charts  →");
        btnViewTable.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnViewTable.setForeground(COLOR_PRIMARY);
        btnViewTable.setBackground(Color.WHITE);
        btnViewTable.setBorderPainted(false);
        btnViewTable.setFocusPainted(false);
        btnViewTable.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnViewTable.addActionListener(e -> navigateTo("PERFORMANCE"));

        pnlResultsCard.add(lblResultStatus);
        pnlResultsCard.add(Box.createVerticalStrut(10));
        pnlResultsCard.add(lblResultType);
        pnlResultsCard.add(Box.createVerticalStrut(4));
        pnlResultsCard.add(lblResultSize);
        pnlResultsCard.add(Box.createVerticalStrut(6));
        pnlResultsCard.add(lblResultMerge);
        pnlResultsCard.add(Box.createVerticalStrut(4));
        pnlResultsCard.add(lblResultQuick);
        pnlResultsCard.add(Box.createVerticalStrut(6));
        pnlResultsCard.add(lblResultWinner);
        pnlResultsCard.add(Box.createVerticalStrut(6));
        pnlResultsCard.add(lblResultSummary);
        pnlResultsCard.add(Box.createVerticalStrut(12));
        pnlResultsCard.add(btnViewTable);

        panel.add(pnlResultsCard);
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    private void handleSingleTestRun() {
        int size = parseAndValidateSize();
        if (size <= 0) return;

        String condition = (String) cmbCondition.getSelectedItem();
        runBenchmarkAction(condition, size, true);
    }

    private void handleRunAllConditions() {
        int size = parseAndValidateSize();
        if (size <= 0) return;

        runBenchmarkAction("Random", size, false);
        runBenchmarkAction("Already Sorted", size, false);
        runBenchmarkAction("Reverse-Sorted", size, true);

        JOptionPane.showMessageDialog(this,
            "Successfully benchmarked all 3 conditions for size " + size + "!\nView the Performance Analysis tab for side-by-side comparison.",
            "Benchmark Complete", JOptionPane.INFORMATION_MESSAGE);
    }

    private int parseAndValidateSize() {
        String input = txtArraySize.getText().trim();
        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an array size.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return -1;
        }
        try {
            int size = Integer.parseInt(input);
            if (size <= 0) {
                JOptionPane.showMessageDialog(this, "Array size must be greater than 0.", "Invalid Value", JOptionPane.WARNING_MESSAGE);
                return -1;
            }
            if (size > 100000) {
                JOptionPane.showMessageDialog(this, "For optimal UI responsiveness, please enter a size <= 100,000.", "Size Warning", JOptionPane.WARNING_MESSAGE);
                return -1;
            }
            return size;
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid numeric integer for the array size.", "Format Error", JOptionPane.ERROR_MESSAGE);
            return -1;
        }
    }

    private void runBenchmarkAction(String inputType, int size, boolean showMessage) {
        PerformanceAnalyzer.TestResult res = PerformanceAnalyzer.runBenchmark(inputType, size);
        recordTestResult(res);
    }

    private void recordTestResult(PerformanceAnalyzer.TestResult res) {
        this.latestResult = res;
        this.history.add(res);

        // Update Run Test live results box
        if (lblResultType != null) {
            lblResultType.setText("Condition: " + res.getInputType());
            lblResultSize.setText("Array Size: " + res.getArraySize());
            lblResultMerge.setText("Merge Sort Time: " + res.getMergeFormatted());
            lblResultQuick.setText("Quick Sort Time: " + res.getQuickFormatted());

            if ("Merge Sort".equals(res.getFasterAlgorithm())) {
                lblResultWinner.setText("Faster Algorithm: 🔵 Merge Sort");
                lblResultWinner.setForeground(COLOR_PRIMARY);
            } else if ("Quick Sort".equals(res.getFasterAlgorithm())) {
                lblResultWinner.setText("Faster Algorithm: 🟣 Quick Sort");
                lblResultWinner.setForeground(COLOR_PURPLE);
            } else {
                lblResultWinner.setText("Faster Algorithm: ⚖️ Equal Time");
                lblResultWinner.setForeground(COLOR_TEXT_MAIN);
            }

            lblResultSummary.setText(res.getDifferenceDescription());
        }

        // Update Performance Analysis Page KPI cards
        if (lblKpiSize != null) {
            lblKpiSize.setText(String.format("%,d elements", res.getArraySize()));
            lblKpiMerge.setText(String.format("%.2f ms", res.getMergeTimeMs()));
            lblKpiQuick.setText(res.isQuickStackOverflow() ? "O(n²) Overflow" : String.format("%.2f ms", res.getQuickTimeMs()));
            lblKpiWinner.setText(res.getFasterAlgorithm());

            chart.updateChart(res);

            // Append row to comparison table
            tableModel.addRow(new Object[]{
                res.getInputType(),
                String.format("%,d", res.getArraySize()),
                String.format("%.2f ms", res.getMergeTimeMs()),
                res.isQuickStackOverflow() ? "Stack Overflow" : String.format("%.2f ms", res.getQuickTimeMs()),
                res.getFasterAlgorithm()
            });
        }
    }

    // =========================================================================
    // PAGE 3: PERFORMANCE ANALYSIS PAGE
    // =========================================================================
    private JPanel createPerformancePage() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(COLOR_CONTENT_BG);
        panel.setBorder(new EmptyBorder(30, 35, 30, 35));

        // Header
        JLabel lblTitle = new JLabel("📊 Performance Analysis Dashboard");
        lblTitle.setFont(FONT_TITLE);
        lblTitle.setForeground(COLOR_TEXT_MAIN);
        lblTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblSub = new JLabel("Experimental metrics, comparative table, and graphical execution time analysis.");
        lblSub.setFont(FONT_SUBTITLE);
        lblSub.setForeground(COLOR_TEXT_MUTED);
        lblSub.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(lblTitle);
        panel.add(Box.createVerticalStrut(4));
        panel.add(lblSub);
        panel.add(Box.createVerticalStrut(20));

        // Top 4 KPI Metric Cards in a Grid
        JPanel kpiGrid = new JPanel(new GridLayout(1, 4, 15, 0));
        kpiGrid.setOpaque(false);
        kpiGrid.setMaximumSize(new Dimension(1000, 95));
        kpiGrid.setAlignmentX(Component.LEFT_ALIGNMENT);

        lblKpiSize = new JLabel("-");
        lblKpiMerge = new JLabel("-");
        lblKpiQuick = new JLabel("-");
        lblKpiWinner = new JLabel("-");

        kpiGrid.add(createKpiCard("INPUT SIZE", lblKpiSize, COLOR_TEXT_MAIN));
        kpiGrid.add(createKpiCard("MERGE SORT TIME", lblKpiMerge, COLOR_PRIMARY));
        kpiGrid.add(createKpiCard("QUICK SORT TIME", lblKpiQuick, COLOR_PURPLE));
        kpiGrid.add(createKpiCard("FASTER ALGORITHM", lblKpiWinner, COLOR_SUCCESS));

        panel.add(kpiGrid);
        panel.add(Box.createVerticalStrut(20));

        // Visual Bar Comparison Chart Card
        JPanel chartCard = createCardPanel();
        chartCard.setLayout(new BorderLayout());
        chartCard.setMaximumSize(new Dimension(1000, 180));
        chartCard.setAlignmentX(Component.LEFT_ALIGNMENT);

        chart = new BarComparisonChart();
        chartCard.add(chart, BorderLayout.CENTER);

        panel.add(chartCard);
        panel.add(Box.createVerticalStrut(20));

        // Comparison Table Section Card
        JPanel tableCard = createCardPanel();
        tableCard.setLayout(new BorderLayout(0, 10));
        tableCard.setMaximumSize(new Dimension(1000, 240));
        tableCard.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblTableTitle = new JLabel("📋 Experimental Comparison Table");
        lblTableTitle.setFont(FONT_CARD_TITLE);
        lblTableTitle.setForeground(COLOR_TEXT_MAIN);
        tableCard.add(lblTableTitle, BorderLayout.NORTH);

        String[] columnNames = {"Input Type", "Array Size", "Merge Sort", "Quick Sort", "Faster Algorithm"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // read-only table
            }
        };

        JTable table = new JTable(tableModel);
        table.setFont(FONT_BODY);
        table.setRowHeight(28);
        table.setGridColor(COLOR_BORDER);
        table.setShowGrid(true);
        table.setFillsViewportHeight(true);

        // Header style
        JTableHeader header = table.getTableHeader();
        header.setFont(FONT_BODY_BOLD);
        header.setBackground(new Color(241, 245, 249));
        header.setForeground(COLOR_TEXT_MAIN);
        header.setPreferredSize(new Dimension(header.getWidth(), 32));

        // Center align table contents
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setBorder(BorderFactory.createLineBorder(COLOR_BORDER));
        tableCard.add(tableScroll, BorderLayout.CENTER);

        panel.add(tableCard);
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    private JPanel createKpiCard(String label, JLabel valueLabel, Color valueColor) {
        JPanel card = createCardPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel lbl = new JLabel(label);
        lbl.setFont(FONT_KPI_LABEL);
        lbl.setForeground(COLOR_TEXT_MUTED);

        valueLabel.setFont(FONT_KPI_VALUE);
        valueLabel.setForeground(valueColor);

        card.add(lbl);
        card.add(Box.createVerticalStrut(6));
        card.add(valueLabel);
        return card;
    }

    // =========================================================================
    // PAGE 4: ALGORITHMS PAGE
    // =========================================================================
    private JPanel createAlgorithmsPage() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(COLOR_CONTENT_BG);
        panel.setBorder(new EmptyBorder(30, 35, 30, 35));

        JLabel lblTitle = new JLabel("📚 Algorithm Theory & Specifications");
        lblTitle.setFont(FONT_TITLE);
        lblTitle.setForeground(COLOR_TEXT_MAIN);
        lblTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblSub = new JLabel("In-depth breakdown of the Divide-and-Conquer sorting algorithms.");
        lblSub.setFont(FONT_SUBTITLE);
        lblSub.setForeground(COLOR_TEXT_MUTED);
        lblSub.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(lblTitle);
        panel.add(Box.createVerticalStrut(4));
        panel.add(lblSub);
        panel.add(Box.createVerticalStrut(25));

        // MERGE SORT Card
        JPanel cardMerge = createCardPanel();
        cardMerge.setLayout(new BoxLayout(cardMerge, BoxLayout.Y_AXIS));
        cardMerge.setMaximumSize(new Dimension(1000, 220));
        cardMerge.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblMTitle = new JLabel("🔵 MERGE SORT");
        lblMTitle.setFont(new Font("Segoe UI", Font.BOLD, 17));
        lblMTitle.setForeground(COLOR_PRIMARY);

        JLabel lblMDesc = new JLabel("<html><b>Concept:</b> Merge Sort is a <b>Divide and Conquer</b> algorithm. "
                + "It divides the array into smaller halves, recursively sorts them, and then merges the sorted halves back together in linear time.</html>");
        lblMDesc.setFont(FONT_BODY);
        lblMDesc.setForeground(COLOR_TEXT_MAIN);

        JLabel lblMComplexity = new JLabel("<html><b>Time & Space Complexity:</b><br/>"
                + "• <b>Best Case:</b> O(n log n)<br/>"
                + "• <b>Average Case:</b> O(n log n)<br/>"
                + "• <b>Worst Case:</b> O(n log n)<br/>"
                + "• <b>Space Complexity:</b> O(n) (requires temporary auxiliary arrays for merging)</html>");
        lblMComplexity.setFont(FONT_BODY);
        lblMComplexity.setForeground(COLOR_TEXT_MUTED);

        cardMerge.add(lblMTitle);
        cardMerge.add(Box.createVerticalStrut(8));
        cardMerge.add(lblMDesc);
        cardMerge.add(Box.createVerticalStrut(10));
        cardMerge.add(lblMComplexity);
        panel.add(cardMerge);
        panel.add(Box.createVerticalStrut(20));

        // QUICK SORT Card
        JPanel cardQuick = createCardPanel();
        cardQuick.setLayout(new BoxLayout(cardQuick, BoxLayout.Y_AXIS));
        cardQuick.setMaximumSize(new Dimension(1000, 240));
        cardQuick.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblQTitle = new JLabel("🟣 QUICK SORT");
        lblQTitle.setFont(new Font("Segoe UI", Font.BOLD, 17));
        lblQTitle.setForeground(COLOR_PURPLE);

        JLabel lblQDesc = new JLabel("<html><b>Concept:</b> Quick Sort is a <b>Divide and Conquer</b> algorithm. "
                + "It selects a pivot, partitions the array around the pivot, and recursively sorts the two sides.<br/>"
                + "<i>Note: This project implements the Lomuto partition scheme using the <b>last element</b> as the pivot.</i></html>");
        lblQDesc.setFont(FONT_BODY);
        lblQDesc.setForeground(COLOR_TEXT_MAIN);

        JLabel lblQComplexity = new JLabel("<html><b>Time & Space Complexity:</b><br/>"
                + "• <b>Best Case:</b> O(n log n)<br/>"
                + "• <b>Average Case:</b> O(n log n)<br/>"
                + "• <b>Worst Case:</b> O(n²) (occurs when partitions are heavily unbalanced, such as already sorted or reverse-sorted inputs with end-element pivot)<br/>"
                + "• <b>Space Complexity:</b> O(log n) call stack auxiliary memory</html>");
        lblQComplexity.setFont(FONT_BODY);
        lblQComplexity.setForeground(COLOR_TEXT_MUTED);

        cardQuick.add(lblQTitle);
        cardQuick.add(Box.createVerticalStrut(8));
        cardQuick.add(lblQDesc);
        cardQuick.add(Box.createVerticalStrut(10));
        cardQuick.add(lblQComplexity);
        panel.add(cardQuick);

        panel.add(Box.createVerticalGlue());
        return panel;
    }

    // =========================================================================
    // PAGE 5: ABOUT PROJECT PAGE
    // =========================================================================
    private JPanel createAboutPage() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(COLOR_CONTENT_BG);
        panel.setBorder(new EmptyBorder(30, 35, 30, 35));

        JLabel lblTitle = new JLabel("ℹ️ About Project");
        lblTitle.setFont(FONT_TITLE);
        lblTitle.setForeground(COLOR_TEXT_MAIN);
        lblTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblSub = new JLabel("College Data Structures and Algorithms Mini-Project Documentation.");
        lblSub.setFont(FONT_SUBTITLE);
        lblSub.setForeground(COLOR_TEXT_MUTED);
        lblSub.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(lblTitle);
        panel.add(Box.createVerticalStrut(4));
        panel.add(lblSub);
        panel.add(Box.createVerticalStrut(25));

        // Project Specifications Card
        JPanel infoCard = createCardPanel();
        infoCard.setLayout(new BoxLayout(infoCard, BoxLayout.Y_AXIS));
        infoCard.setMaximumSize(new Dimension(1000, 200));
        infoCard.setAlignmentX(Component.LEFT_ALIGNMENT);

        String[][] details = {
            {"Project:", "Performance Analysis and Comparison of Merge Sort and Quick Sort"},
            {"Technology:", "Java (Java Swing Desktop GUI)"},
            {"Concept:", "Data Structures and Algorithms (DSA)"},
            {"Technique:", "Divide and Conquer"},
            {"Purpose:", "Experimental performance comparison under diverse input conditions"}
        };

        for (String[] item : details) {
            JPanel r = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 3));
            r.setOpaque(false);
            JLabel k = new JLabel(item[0]);
            k.setFont(FONT_BODY_BOLD);
            k.setForeground(COLOR_TEXT_MAIN);
            k.setPreferredSize(new Dimension(100, 20));

            JLabel v = new JLabel(item[1]);
            v.setFont(FONT_BODY);
            v.setForeground(COLOR_TEXT_MUTED);

            r.add(k);
            r.add(v);
            infoCard.add(r);
        }

        panel.add(infoCard);
        panel.add(Box.createVerticalStrut(20));

        // Conclusion Card
        JPanel conclusionCard = createCardPanel();
        conclusionCard.setLayout(new BoxLayout(conclusionCard, BoxLayout.Y_AXIS));
        conclusionCard.setMaximumSize(new Dimension(1000, 160));
        conclusionCard.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblConcHeading = new JLabel("🎯 Experimental Conclusion");
        lblConcHeading.setFont(FONT_CARD_TITLE);
        lblConcHeading.setForeground(COLOR_TEXT_MAIN);

        JLabel lblConcText = new JLabel("<html><p style='line-height:1.4;'>"
                + "\"<b>Merge Sort provides consistent O(n log n) performance</b>, while <b>Quick Sort can be faster for favorable inputs but may reach O(n²) for unfavorable input arrangements when the last element is selected as the pivot.</b>\""
                + "</p><br/>"
                + "• <b>Random Input:</b> Quick Sort exhibits lower constant factors and higher cache locality, typically completing faster.<br/>"
                + "• <b>Sorted & Reverse-Sorted Inputs:</b> The Lomuto partition scheme creates heavily skewed partitions (1 element vs n-1 elements), causing Quick Sort's time to spike sharply, confirming theoretical expectations.</html>");
        lblConcText.setFont(FONT_BODY);
        lblConcText.setForeground(COLOR_TEXT_MUTED);

        conclusionCard.add(lblConcHeading);
        conclusionCard.add(Box.createVerticalStrut(10));
        conclusionCard.add(lblConcText);
        panel.add(conclusionCard);

        panel.add(Box.createVerticalGlue());
        return panel;
    }

    // =========================================================================
    // UI HELPER METHODS
    // =========================================================================
    private JPanel createCardPanel() {
        JPanel card = new JPanel();
        card.setBackground(COLOR_CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_BORDER, 1),
            new EmptyBorder(18, 20, 18, 20)
        ));
        return card;
    }

    private JButton createPrimaryButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setForeground(Color.WHITE);
        btn.setBackground(COLOR_PRIMARY);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setPreferredSize(new Dimension(210, 40));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(COLOR_PRIMARY_HOVER);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(COLOR_PRIMARY);
            }
        });
        return btn;
    }
}
