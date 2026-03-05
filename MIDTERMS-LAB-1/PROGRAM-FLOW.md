# PROGRAM FLOW – Sales Trend Moving Average
**VALENZUELA | Programming 2 – Machine Problem**
University of Perpetual Help System DALTA – Molino Campus
BS Information Technology - Game Development

---

```
START
  |
  v
+-----------------------------+
|  Display program banner     |
+-----------------------------+
  |
  v
+-----------------------------+
|  Prompt user for dataset    |
|  file path                  |
+-----------------------------+
  |
  v
+-----------------------------+
|  Validate file:             |
|  - Does it exist?           |
|  - Is it a file (not dir)?  |
|  - Is it readable?          |
|  - Does it end in .csv?     |
+-----------------------------+
  |
  |--- INVALID -----> Display error message
  |                        |
  |                        v
  |                   Ask for file path again (loop back)
  |
  |--- VALID -------> Proceed
  |
  v
+-----------------------------+
|  Load CSV into memory       |
|  - Read header row          |
|  - Detect column indices    |
|    (title, date, sales)     |
|  - Parse each row into a    |
|    DataRecord object        |
|  - Skip malformed rows      |
+-----------------------------+
  |
  v
+-----------------------------+
|  Sort records by            |
|  release_date (ascending)   |
|  (null dates go to end)     |
+-----------------------------+
  |
  v
+-----------------------------+
|  Compute 3-Record           |
|  Simple Moving Average      |
|                             |
|  For each record i:         |
|  if i < 2  → MA = null      |
|  if i >= 2 → MA =           |
|   (sales[i-2] +             |
|    sales[i-1] +             |
|    sales[i]) / 3            |
+-----------------------------+
  |
  v
+-----------------------------+
|  Display formatted table:   |
|  #  | Title | Date |        |
|  Sales (M) | Moving Avg     |
+-----------------------------+
  |
  v
+-----------------------------+
|  Display summary stats:     |
|  - Total records            |
|  - Average sales            |
|  - Highest MA               |
|  - Lowest MA                |
+-----------------------------+
  |
  v
END
```

---

## Analytics Performed
| Step | Description |
|------|-------------|
| Sort | Records sorted by `release_date` in ascending order |
| Moving Average | 3-record Simple Moving Average (SMA) applied to `total_sales` |
| Summary | Overall average, highest and lowest MA values reported |

## Error Handling
| Scenario | Handling |
|----------|----------|
| File not found | Error message + re-prompt |
| Path is a directory | Error message + re-prompt |
| File not readable | Error message + re-prompt |
| Not a .csv file | Error message + re-prompt |
| Malformed CSV row | Row silently skipped, processing continues |
| No valid records | Graceful exit with message |
| Unreadable file at runtime | Caught by try-catch, error displayed |
