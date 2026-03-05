/*
 * PROGRAMMING 2 – MACHINE PROBLEM
 * University of Perpetual Help System DALTA – Molino Campus
 * BS Information Technology - Game Development
 *
 * Task: Sales Trend Moving Average
 * Student: VALENZUELA
 *
 * Dataset: https://www.kaggle.com/datasets/asaniczka/video-game-sales-2024
 */

'use strict';

const fs = require('fs');
const readline = require('readline');
const { parseCSV, computeMovingAverage, displayResults } = require('./dataProcessor');

const rl = readline.createInterface({
  input: process.stdin,
  output: process.stdout,
});

// ─── File Path Input Loop ────────────────────────────────────────────────────

function askFilePath() {
  rl.question('Enter dataset file path: ', (inputPath) => {
    const trimmed = inputPath.trim();

    // Validate: exists
    if (!fs.existsSync(trimmed)) {
      console.log('[ERROR] File not found. Please try again.\n');
      return askFilePath();
    }

    // Validate: is a file (not a directory)
    const stat = fs.statSync(trimmed);
    if (!stat.isFile()) {
      console.log('[ERROR] Path is not a file. Please try again.\n');
      return askFilePath();
    }

    // Validate: readable & CSV format
    if (!trimmed.toLowerCase().endsWith('.csv')) {
      console.log('[ERROR] File does not appear to be a CSV. Please try again.\n');
      return askFilePath();
    }

    console.log('\n[OK] File found. Processing...\n');
    rl.close();
    run(trimmed);
  });
}

// ─── Main Program ─────────────────────────────────────────────────────────────

function run(filePath) {
  try {
    const rawContent = fs.readFileSync(filePath, 'utf-8');
    const records = parseCSV(rawContent);

    if (records.length === 0) {
      console.log('[ERROR] No valid records found in the dataset.');
      return;
    }

    console.log(`Total records loaded: ${records.length}`);

    const withMA = computeMovingAverage(records, 3);
    displayResults(withMA);

  } catch (err) {
    console.error('[ERROR] Failed to process file:', err.message);
  }
}

// ─── Entry Point ──────────────────────────────────────────────────────────────

console.log('='.repeat(60));
console.log('  SALES TREND MOVING AVERAGE – VGChartz 2024 Dataset');
console.log('='.repeat(60));
console.log();

askFilePath();
