/*
 * dataProcessor.js
 * Handles CSV parsing, moving average computation, and display output.
 */

'use strict';

// ─── Parse CSV ────────────────────────────────────────────────────────────────

/**
 * Parses raw CSV content into an array of record objects.
 * Expects columns: title, release_date, total_sales (from VGChartz 2024)
 * @param {string} rawContent
 * @returns {{ title: string, releaseDate: Date|null, totalSales: number }[]}
 */
function parseCSV(rawContent) {
  const lines = rawContent.split('\n').map((l) => l.trim()).filter(Boolean);

  if (lines.length < 2) return [];

  const headers = splitCSVLine(lines[0]).map((h) => h.toLowerCase().trim());

  const titleIdx   = findIndex(headers, ['title', 'game', 'name']);
  const dateIdx    = findIndex(headers, ['release_date', 'date', 'year', 'release_year']);
  const salesIdx   = findIndex(headers, ['total_sales', 'global_sales', 'sales', 'total sales']);

  if (salesIdx === -1) {
    throw new Error(
      `Could not find a sales column. Headers found: [${headers.join(', ')}]`
    );
  }

  const records = [];

  for (let i = 1; i < lines.length; i++) {
    try {
      const cols = splitCSVLine(lines[i]);

      const title      = titleIdx !== -1 ? (cols[titleIdx] || '').replace(/^"|"$/g, '').trim() : `Record ${i}`;
      const dateRaw    = dateIdx  !== -1 ? (cols[dateIdx]  || '').replace(/^"|"$/g, '').trim() : '';
      const salesRaw   = (cols[salesIdx] || '').replace(/^"|"$/g, '').trim();

      const totalSales = parseFloat(salesRaw);
      if (isNaN(totalSales)) continue; // skip rows with no numeric sales

      const releaseDate = parseDateFlexible(dateRaw);

      records.push({ title, releaseDate, totalSales });
    } catch (_) {
      // skip malformed rows
    }
  }

  return records;
}

// ─── Compute 3-Record Moving Average ─────────────────────────────────────────

/**
 * Sorts records by release date (ascending), then computes the
 * N-record simple moving average on totalSales.
 * @param {{ title: string, releaseDate: Date|null, totalSales: number }[]} records
 * @param {number} window  – window size (default 3)
 * @returns {(record & { movingAverage: number|null })[]}
 */
function computeMovingAverage(records, window = 3) {
  // Sort by date; records with no date go to the end
  const sorted = [...records].sort((a, b) => {
    if (!a.releaseDate && !b.releaseDate) return 0;
    if (!a.releaseDate) return 1;
    if (!b.releaseDate) return -1;
    return a.releaseDate - b.releaseDate;
  });

  return sorted.map((rec, idx) => {
    if (idx < window - 1) {
      return { ...rec, movingAverage: null };
    }
    const slice = sorted.slice(idx - window + 1, idx + 1);
    const avg   = slice.reduce((sum, r) => sum + r.totalSales, 0) / window;
    return { ...rec, movingAverage: parseFloat(avg.toFixed(4)) };
  });
}

// ─── Display Results ──────────────────────────────────────────────────────────

/**
 * Prints the results table to stdout.
 * @param {(record & { movingAverage: number|null })[]} records
 */
function displayResults(records) {
  const SHOW_MAX = 50; // limit console output; adjust as needed

  console.log('='.repeat(90));
  console.log(
    padEnd('#',     5)  +
    padEnd('Title', 40) +
    padEnd('Release Date', 14) +
    padEnd('Sales (M)', 12) +
    'Moving Avg (3-rec)'
  );
  console.log('-'.repeat(90));

  const toShow = records.slice(0, SHOW_MAX);

  toShow.forEach((rec, idx) => {
    const num      = String(idx + 1).padStart(4) + '.';
    const title    = truncate(rec.title, 38);
    const date     = rec.releaseDate ? rec.releaseDate.toISOString().split('T')[0] : 'N/A';
    const sales    = rec.totalSales.toFixed(2);
    const ma       = rec.movingAverage !== null ? rec.movingAverage.toFixed(2) : '  —  ';

    console.log(
      padEnd(num,   5)  +
      padEnd(title, 40) +
      padEnd(date,  14) +
      padEnd(sales, 12) +
      ma
    );
  });

  if (records.length > SHOW_MAX) {
    console.log(`\n  ... and ${records.length - SHOW_MAX} more records.`);
  }

  console.log('='.repeat(90));

  // Summary stats
  const validMA  = records.filter((r) => r.movingAverage !== null);
  const maxMA    = Math.max(...validMA.map((r) => r.movingAverage));
  const minMA    = Math.min(...validMA.map((r) => r.movingAverage));
  const avgSales = records.reduce((s, r) => s + r.totalSales, 0) / records.length;

  console.log('\nSUMMARY');
  console.log('-'.repeat(40));
  console.log(`Total records processed : ${records.length}`);
  console.log(`Average sales (all)     : ${avgSales.toFixed(4)} M`);
  console.log(`Highest 3-rec MA        : ${maxMA.toFixed(4)} M`);
  console.log(`Lowest  3-rec MA        : ${minMA.toFixed(4)} M`);
  console.log('='.repeat(90));
}

// ─── Helpers ──────────────────────────────────────────────────────────────────

function splitCSVLine(line) {
  const result = [];
  let cur = '';
  let inQuotes = false;
  for (let i = 0; i < line.length; i++) {
    const ch = line[i];
    if (ch === '"') { inQuotes = !inQuotes; continue; }
    if (ch === ',' && !inQuotes) { result.push(cur); cur = ''; continue; }
    cur += ch;
  }
  result.push(cur);
  return result;
}

function findIndex(headers, candidates) {
  for (const c of candidates) {
    const idx = headers.indexOf(c);
    if (idx !== -1) return idx;
  }
  return -1;
}

function parseDateFlexible(raw) {
  if (!raw || raw === 'N/A' || raw === '') return null;
  // Handle year-only like "2021"
  if (/^\d{4}$/.test(raw)) return new Date(`${raw}-01-01`);
  const d = new Date(raw);
  return isNaN(d.getTime()) ? null : d;
}

function padEnd(str, len) {
  return String(str).substring(0, len).padEnd(len);
}

function truncate(str, max) {
  return str.length > max ? str.substring(0, max - 1) + '…' : str;
}

module.exports = { parseCSV, computeMovingAverage, displayResults };
