import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * DataRecord.java
 * Represents a single video game sales record from the VGChartz 2024 dataset.
 *
 * PROGRAMMING 2 – MACHINE PROBLEM
 * University of Perpetual Help System DALTA – Molino Campus
 * BS Information Technology - Game Development
 * Student: VALENZUELA
 */
public class DataRecord implements Comparable<DataRecord> {

    private String    title;
    private LocalDate releaseDate;
    private double    totalSales;
    private Double    movingAverage; // null until computed

    // ── Constructors ──────────────────────────────────────────────────────────

    public DataRecord(String title, String rawDate, double totalSales) {
        this.title       = title.trim();
        this.releaseDate = parseDate(rawDate.trim());
        this.totalSales  = totalSales;
        this.movingAverage = null;
    }

    // ── Date Parsing ──────────────────────────────────────────────────────────

    private static final DateTimeFormatter[] FORMATS = {
        DateTimeFormatter.ofPattern("yyyy-MM-dd"),
        DateTimeFormatter.ofPattern("MM/dd/yyyy"),
        DateTimeFormatter.ofPattern("dd-MM-yyyy"),
        DateTimeFormatter.ofPattern("yyyy"),
    };

    private LocalDate parseDate(String raw) {
        if (raw == null || raw.isEmpty() || raw.equals("N/A")) return null;

        // Year-only "2021" → 2021-01-01
        if (raw.matches("\\d{4}")) {
            try { return LocalDate.of(Integer.parseInt(raw), 1, 1); }
            catch (Exception ignored) {}
        }

        for (DateTimeFormatter fmt : FORMATS) {
            try { return LocalDate.parse(raw, fmt); }
            catch (DateTimeParseException ignored) {}
        }
        return null;
    }

    // ── Comparable (sort by date) ─────────────────────────────────────────────

    @Override
    public int compareTo(DataRecord other) {
        if (this.releaseDate == null && other.releaseDate == null) return 0;
        if (this.releaseDate == null) return 1;
        if (other.releaseDate == null) return -1;
        return this.releaseDate.compareTo(other.releaseDate);
    }

    // ── Getters / Setters ─────────────────────────────────────────────────────

    public String    getTitle()        { return title; }
    public LocalDate getReleaseDate()  { return releaseDate; }
    public double    getTotalSales()   { return totalSales; }
    public Double    getMovingAverage(){ return movingAverage; }
    public void      setMovingAverage(double ma) { this.movingAverage = ma; }

    // ── Display helpers ───────────────────────────────────────────────────────

    public String getDateString() {
        return releaseDate != null ? releaseDate.toString() : "N/A";
    }

    public String getMAString() {
        return movingAverage != null ? String.format("%.2f", movingAverage) : "  —  ";
    }
}
