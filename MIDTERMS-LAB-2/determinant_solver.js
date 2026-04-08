/**
 * =====================================================
 * Student Name    : Valenzuela, Mykee L.
 * Course          : Math 101 — Linear Algebra
 * Assignment      : Programming Assignment 1 — 3x3 Matrix Determinant Solver
 * School          : University of Perpetual Help System DALTA, Molino Campus
 * Date            : April 8, 2026
 * GitHub Repo     : https://github.com/[your-username]/uphsd-cs-valenzuela-mykee
 * Runtime         : Node.js (run with: node determinant_solver.js)
 *
 * Description:
 *   JavaScript equivalent of DeterminantSolver.java. This script computes
 *   the determinant of the same hardcoded 3x3 matrix using cofactor expansion
 *   along the first row. All intermediate steps are logged to the console
 *   using console.log() for complete transparency of the solution process.
 * =====================================================
 */

// ── SECTION 1: Matrix Declaration ───────────────────────────────────
// My assigned 3x3 matrix (Student #37 — Valenzuela, Mykee L.)
// Stored as a 2D array where each inner array represents one row.
const matrix = [
    [1, 4, 6],   // Row 1
    [3, 5, 2],   // Row 2
    [4, 1, 3]    // Row 3
];

// ── SECTION 2: Matrix Printer ────────────────────────────────────────
// Iterates over each row and prints it between border characters to
// display the matrix in a clean, readable bracket format.
function printMatrix(m) {
    console.log(`┌               ┐`);
    m.forEach(row => {
        const fmt = row.map(v => v.toString().padStart(3)).join("  ");
        console.log(`│ ${fmt}  │`);
    });
    console.log(`└               ┘`);
}

// ── SECTION 3: 2×2 Determinant Helper ───────────────────────────────
// Receives four numbers that form a 2x2 matrix (row by row) and
// returns the determinant using the cross-multiplication formula: ad - bc.
function computeMinor(a, b, c, d) {
    // Multiply the two diagonals and subtract the anti-diagonal from the main
    return (a * d) - (b * c);
}

// ── SECTION 4: Step-by-Step Determinant Solver ──────────────────────
// Drives the entire output. Prints the matrix, calculates each 2x2 minor
// by removing one column from the first row, applies cofactor signs,
// and sums everything to produce the final determinant.
function solveDeterminant(m) {
    const line = "=".repeat(52);

    // Print the header section with student info and the matrix
    console.log(line);
    console.log("  3x3 MATRIX DETERMINANT SOLVER");
    console.log("  Student: Valenzuela, Mykee L.");
    console.log("  Assigned Matrix:");
    console.log(line);
    printMatrix(m);
    console.log(line);
    console.log();
    console.log("Expanding along Row 1 (cofactor expansion):");
    console.log();

    // ── Step 1: Minor M₁₁ ──
    // Remove row 0 and column 0; the four remaining elements are at
    // positions [1][1], [1][2], [2][1], [2][2].
    const minor11 = computeMinor(m[1][1], m[1][2], m[2][1], m[2][2]);
    console.log(
        `  Step 1 — Minor M₁₁: det([${m[1][1]},${m[1][2]}],[${m[2][1]},${m[2][2]}])` +
        ` = (${m[1][1]}×${m[2][2]}) - (${m[1][2]}×${m[2][1]}) = ${m[1][1]*m[2][2]} - ${m[1][2]*m[2][1]} = ${minor11}`
    );

    // ── Step 2: Minor M₁₂ ──
    // Remove row 0 and column 1; remaining elements at
    // positions [1][0], [1][2], [2][0], [2][2].
    const minor12 = computeMinor(m[1][0], m[1][2], m[2][0], m[2][2]);
    console.log(
        `  Step 2 — Minor M₁₂: det([${m[1][0]},${m[1][2]}],[${m[2][0]},${m[2][2]}])` +
        ` = (${m[1][0]}×${m[2][2]}) - (${m[1][2]}×${m[2][0]}) = ${m[1][0]*m[2][2]} - ${m[1][2]*m[2][0]} = ${minor12}`
    );

    // ── Step 3: Minor M₁₃ ──
    // Remove row 0 and column 2; remaining elements at
    // positions [1][0], [1][1], [2][0], [2][1].
    const minor13 = computeMinor(m[1][0], m[1][1], m[2][0], m[2][1]);
    console.log(
        `  Step 3 — Minor M₁₃: det([${m[1][0]},${m[1][1]}],[${m[2][0]},${m[2][1]}])` +
        ` = (${m[1][0]}×${m[2][1]}) - (${m[1][1]}×${m[2][0]}) = ${m[1][0]*m[2][1]} - ${m[1][1]*m[2][0]} = ${minor13}`
    );

    // ── Cofactor Terms ──
    // The alternating sign rule: column 0 → +, column 1 → −, column 2 → +
    // Each term multiplies the sign, the pivot element from row 0, and the minor.
    const c11 =  m[0][0] * minor11;   // positive cofactor
    const c12 = -m[0][1] * minor12;   // negative cofactor
    const c13 =  m[0][2] * minor13;   // positive cofactor

    console.log();
    console.log(`  Cofactor C₁₁ = (+1) × ${m[0][0]} × ${minor11} = ${c11}`);
    console.log(`  Cofactor C₁₂ = (-1) × ${m[0][1]} × ${minor12} = ${c12}`);
    console.log(`  Cofactor C₁₃ = (+1) × ${m[0][2]} × ${minor13} = ${c13}`);

    // ── Final Determinant ──
    // Sum the three signed cofactor terms to arrive at the determinant.
    const det = c11 + c12 + c13;
    console.log();
    console.log(`  det(M) = ${c11} + (${c12}) + ${c13}`);
    console.log(line);
    console.log(`  ✓  DETERMINANT = ${det}`);

    // ── Singular Matrix Check ──
    // Print a warning if the determinant is zero, meaning no inverse exists.
    if (det === 0) {
        console.log("  ⚠ The matrix is SINGULAR — it has no inverse.");
    }
    console.log(line);
}

// ── SECTION 5: Program Entry Point ──────────────────────────────────
// Kick off the solver using our declared matrix.
solveDeterminant(matrix);
