import java.io.*;
import java.util.*;

/**
 * SalesTrendAnalyzer.java
 * Main entry point – prompts for file path, validates, loads CSV,
 * computes 3-record moving average, and displays results.
 *
 * PROGRAMMING 2 – MACHINE PROBLEM
 * University of Perpetual Help System DALTA – Molino Campus
 * BS Information Technology - Game Development
 * Student: VALENZUELA
 *
 * Dataset: https://www.kaggle.com/datasets/asaniczka/video-game-sales-2024
 *
 * Compile:  javac DataRecord.java SalesTrendAnalyzer.java
 * Run:      java SalesTrendAnalyzer
 */
public class SalesTrendAnalyzer {

    private static final int WINDOW   = 3;   // moving average window
    private static final int SHOW_MAX = 50;  // max rows to print

    // ── Main ──────────────────────────────────────────────────────────────────

    public static void main(String[] args) {

        printBanner();

        Scanner input = new Scanner(System.in);
        File file     = promptForFile(input);

        System.out.println("\n[OK] File found. Processing...\n");

        try {
            List<DataRecord> records = loadCSV(file);

            if (records.isEmpty()) {
                System.out.println("[ERROR] No valid records found in the dataset.");
                return;
            }

            System.out.println("Total records loaded: " + records.size());

            Collections.sort(records);              // sort by release date
            computeMovingAverage(records, WINDOW);  // attach MA values
            displayResults(records);

        } catch (IOException e) {
            System.out.println("[ERROR] Could not read file: " + e.getMessage());
        }
    }

    // ── File Prompt & Validation ──────────────────────────────────────────────

    private static File promptForFile(Scanner input) {
        File file;
        while (true) {
            System.out.print("Enter dataset file path: ");
            String path = input.nextLine().trim();
            file = new File(path);

            if (!file.exists()) {
                System.out.println("[ERROR] File not found. Please try again.\n");
            } else if (!file.isFile()) {
                System.out.println("[ERROR] Path is not a file. Please try again.\n");
            } else if (!file.canRead()) {
                System.out.println("[ERROR] File is not readable. Please try again.\n");
            } else if (!path.toLowerCase().endsWith(".csv")) {
                System.out.println("[ERROR] File does not appear to be a CSV. Please try again.\n");
            } else {
                break; // valid
            }
        }
        return file;
    }

    // ── CSV Loading ───────────────────────────────────────────────────────────

    private static List<DataRecord> loadCSV(File file) throws IOException {
        List<DataRecord> records = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String headerLine = br.readLine();
            if (headerLine == null) return records;

            String[] headers = splitCSV(headerLine);
            int titleIdx = findIndex(headers, "title", "game", "name");
            int dateIdx  = findIndex(headers, "release_date", "date", "year", "release_year");
            int salesIdx = findIndex(headers, "total_sales", "global_sales", "sales", "total sales");

            if (salesIdx == -1) {
                throw new IOException(
                    "Could not find a sales column. Headers: " + Arrays.toString(headers));
            }

            String line;
            int lineNo = 1;
            while ((line = br.readLine()) != null) {
                lineNo++;
                try {
                    String[] cols = splitCSV(line);
                    if (cols.length <= salesIdx) continue;

                    String title    = titleIdx != -1 && titleIdx < cols.length
                                      ? cols[titleIdx] : "Record " + lineNo;
                    String rawDate  = dateIdx  != -1 && dateIdx  < cols.length
                                      ? cols[dateIdx]  : "";
                    String salesStr = cols[salesIdx].trim();

                    double sales = Double.parseDouble(salesStr);
                    records.add(new DataRecord(title, rawDate, sales));

                } catch (NumberFormatException | ArrayIndexOutOfBoundsException ignored) {
                    // skip malformed rows silently
                }
            }
        }
        return records;
    }

    // ── Moving Average ────────────────────────────────────────────────────────

    private static void computeMovingAverage(List<DataRecord> records, int window) {
        for (int i = window - 1; i < records.size(); i++) {
            double sum = 0;
            for (int j = i - window + 1; j <= i; j++) {
                sum += records.get(j).getTotalSales();
            }
            records.get(i).setMovingAverage(sum / window);
        }
    }

    // ── Display ───────────────────────────────────────────────────────────────

    private static void displayResults(List<DataRecord> records) {
        String divider = "=".repeat(90);
        String dashes  = "-".repeat(90);

        System.out.println(divider);
        System.out.printf("%-5s %-40s %-14s %-12s %s%n",
            "#", "Title", "Release Date", "Sales (M)", "Moving Avg (3-rec)");
        System.out.println(dashes);

        int limit = Math.min(records.size(), SHOW_MAX);
        for (int i = 0; i < limit; i++) {
            DataRecord r = records.get(i);
            String title = truncate(r.getTitle(), 38);
            System.out.printf("%-5s %-40s %-14s %-12s %s%n",
                (i + 1) + ".",
                title,
                r.getDateString(),
                String.format("%.2f", r.getTotalSales()),
                r.getMAString());
        }

        if (records.size() > SHOW_MAX) {
            System.out.println("\n  ... and " + (records.size() - SHOW_MAX) + " more records.");
        }

        System.out.println(divider);

        // Summary
        DoubleSummaryStatistics maStats = records.stream()
            .filter(r -> r.getMovingAverage() != null)
            .mapToDouble(DataRecord::getMovingAverage)
            .summaryStatistics();

        double avgSales = records.stream()
            .mapToDouble(DataRecord::getTotalSales)
            .average()
            .orElse(0);

        System.out.println("\nSUMMARY");
        System.out.println("-".repeat(40));
        System.out.printf("Total records processed : %d%n",        records.size());
        System.out.printf("Average sales (all)     : %.4f M%n",    avgSales);
        System.out.printf("Highest 3-rec MA        : %.4f M%n",    maStats.getMax());
        System.out.printf("Lowest  3-rec MA        : %.4f M%n",    maStats.getMin());
        System.out.println(divider);
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private static String[] splitCSV(String line) {
        List<String> result = new ArrayList<>();
        StringBuilder cur = new StringBuilder();
        boolean inQuotes = false;
        for (char ch : line.toCharArray()) {
            if (ch == '"') { inQuotes = !inQuotes; continue; }
            if (ch == ',' && !inQuotes) { result.add(cur.toString()); cur.setLength(0); continue; }
            cur.append(ch);
        }
        result.add(cur.toString());
        return result.toArray(new String[0]);
    }

    private static int findIndex(String[] headers, String... candidates) {
        for (String c : candidates) {
            for (int i = 0; i < headers.length; i++) {
                if (headers[i].trim().toLowerCase().equals(c)) return i;
            }
        }
        return -1;
    }

    private static String truncate(String s, int max) {
        return s.length() > max ? s.substring(0, max - 1) + "…" : s;
    }

    private static void printBanner() {
        System.out.println("=".repeat(60));
        System.out.println("  SALES TREND MOVING AVERAGE – VGChartz 2024 Dataset");
        System.out.println("  PROGRAMMING 2 – Machine Problem | VALENZUELA");
        System.out.println("  University of Perpetual Help DALTA – Molino Campus");
        System.out.println("=".repeat(60));
        System.out.println();
    }
}
