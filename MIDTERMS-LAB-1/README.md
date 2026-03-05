# Sales Trend Moving Average
**Programming 2 – Machine Problem**
University of Perpetual Help System DALTA – Molino Campus
BS Information Technology - Game Development

**Student:** VALENZUELA
**Dataset:** [VGChartz Video Game Sales 2024](https://www.kaggle.com/datasets/asaniczka/video-game-sales-2024)

---

## Task Description
Apply a simple analytics smoothing technique on video game sales data:
- Sort dataset by release date
- Compute a **3-record Simple Moving Average (SMA)** on total sales
- Display original sales alongside the computed moving average

---

## Project Structure

```
VALENZUELA/
│
├── JAVA/
│   ├── SalesTrendAnalyzer.java   ← Main class (entry point, file I/O, display)
│   ├── DataRecord.java           ← Data model class (one record per game)
│   ├── PROGRAM-FLOW.md           ← Program flow diagram
│   └── README.md                 ← This file
│
└── JS/
    ├── index.js                  ← Entry point (file prompt, validation, runner)
    └── dataProcessor.js          ← Module (CSV parsing, MA computation, display)
```

---

## How to Run

### ☕ Java
```bash
# Step 1: Compile
javac DataRecord.java SalesTrendAnalyzer.java

# Step 2: Run
java SalesTrendAnalyzer

# Step 3: When prompted, enter the full file path:
Enter dataset file path: C:\Users\Student\Downloads\vgchartz-2024.csv
```

### 🟨 JavaScript (Node.js)
```bash
# Run
node index.js

# When prompted, enter the full file path:
Enter dataset file path: C:\Users\Student\Downloads\vgchartz-2024.csv
```

---

## Requirements Compliance

| Requirement | Status |
|-------------|--------|
| Prompt user for full file path on startup | ✅ |
| Validate if file exists | ✅ |
| Validate if file is readable | ✅ |
| Validate if file is CSV format | ✅ |
| Loop until valid path is entered | ✅ |
| Do NOT hardcode dataset path | ✅ |
| Do NOT hardcode dataset values | ✅ |
| Java: use BufferedReader/Scanner | ✅ BufferedReader used |
| JavaScript: use fs module | ✅ |
| Java: at least one separate class | ✅ `DataRecord.java` |
| JavaScript: separate module/functions | ✅ `dataProcessor.js` |
| Implement error handling (try-catch) | ✅ |
| Output must be formatted and readable | ✅ |

---

## Sample Output

```
============================================================
  SALES TREND MOVING AVERAGE – VGChartz 2024 Dataset
  PROGRAMMING 2 – Machine Problem | VALENZUELA
  University of Perpetual Help DALTA – Molino Campus
============================================================

Enter dataset file path: /path/to/vgchartz-2024.csv

[OK] File found. Processing...

Total records loaded: 8
==========================================================================================
#    Title                                   Release Date  Sales (M)   Moving Avg (3-rec)
------------------------------------------------------------------------------------------
   1.Duck Hunt                               1984-04-09    28.31         —
   2.Super Mario Bros                        1985-09-13    40.24         —
   3.The Legend of Zelda                     1986-02-21    6.51        25.02
   4.Tetris                                  1989-06-14    35.00       27.25
   ...
==========================================================================================

SUMMARY
----------------------------------------
Total records processed : 8
Average sales (all)     : 80.9175 M
Highest 3-rec MA        : 168.6333 M
Lowest  3-rec MA        : 24.2967 M
==========================================================================================
```

---

## Moving Average Formula

For record at index `i` (0-based), with window size `n = 3`:

```
MA[i] = (sales[i-2] + sales[i-1] + sales[i]) / 3    if i >= 2
MA[i] = null                                          if i < 2
```
