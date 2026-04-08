/**
 * =====================================================
 * Student Name    : Valenzuela, Mykee L.
 * Course          : Math 101 — Linear Algebra
 * Assignment      : Programming Assignment 1 — 3x3 Matrix Determinant Solver
 * School          : University of Perpetual Help System DALTA, Molino Campus
 * Date            : April 8, 2026
 * GitHub Repo     : https://github.com/[your-username]/uphsd-cs-valenzuela-mykee
 *
 * Description:
 *   This program computes the determinant of a hardcoded 3x3 matrix assigned
 *   to Mykee L. Valenzuela for Math 101. The solution is computed using cofactor
 *   expansion along the first row. Each intermediate step (2x2 minor,
 *   cofactor term, running sum) is printed to the console in a readable format.
 * =====================================================
 */
public class DeterminantSolver {

    // ── SECTION 1: Matrix Declaration ───────────────────────────────────
    // This is my assigned 3x3 matrix from the assignment sheet (Student #37).
    // Values are hardcoded as a 2D integer array arranged in row-major order.
    static int[][] matrix = {
        { 1, 4, 6 },   // Row 1 of assigned matrix
        { 3, 5, 2 },   // Row 2 of assigned matrix
        { 4, 1, 3 }    // Row 3 of assigned matrix
    };

    // ── SECTION 2: 2×2 Determinant Helper ───────────────────────────────
    // This method takes four integers representing a 2x2 matrix and returns
    // its determinant using the standard formula: (a * d) - (b * c).
    // It is reused for each of the three minors in the cofactor expansion.
    static int computeMinor(int a, int b, int c, int d) {
        // Multiply the main diagonal and subtract the anti-diagonal product
        return (a * d) - (b * c);
    }

    // ── SECTION 3: Matrix Printer ────────────────────────────────────────
    // Loops through each row of the matrix and prints it inside border
    // characters so the output resembles a proper matrix notation.
    static void printMatrix(int[][] m) {
        System.out.println("┌               ┐");
        for (int[] row : m) {
            System.out.printf("│  %2d  %2d  %2d  │%n", row[0], row[1], row[2]);
        }
        System.out.println("└               ┘");
    }

    // ── SECTION 4: Step-by-Step Determinant Solver ──────────────────────
    // This is the core method. It prints the matrix, computes each 2x2 minor
    // by eliminating one column at a time from row 0, applies the alternating
    // sign rule to get each cofactor term, then sums them for the final answer.
    static void solveDeterminant(int[][] m) {

        // Print the header banner and the matrix
        System.out.println("=".repeat(52));
        System.out.println("  3x3 MATRIX DETERMINANT SOLVER");
        System.out.println("  Student: Valenzuela, Mykee L.");
        System.out.println("  Assigned Matrix:");
        System.out.println("=".repeat(52));
        printMatrix(m);
        System.out.println("=".repeat(52));
        System.out.println();
        System.out.println("Expanding along Row 1 (cofactor expansion):");
        System.out.println();

        // ── Step 1: Compute minor M₁₁ ──
        // Cross out row 0 and column 0; the leftover elements form this minor.
        // Remaining positions: [1][1], [1][2], [2][1], [2][2]
        int minor11 = computeMinor(m[1][1], m[1][2], m[2][1], m[2][2]);
        System.out.printf("  Step 1 — Minor M\u2081\u2081: det([%d,%d],[%d,%d]) = (%d\u00d7%d)-(%d\u00d7%d) = %d - %d = %d%n",
            m[1][1], m[1][2], m[2][1], m[2][2],
            m[1][1], m[2][2], m[1][2], m[2][1],
            m[1][1]*m[2][2], m[1][2]*m[2][1], minor11);

        // ── Step 2: Compute minor M₁₂ ──
        // Cross out row 0 and column 1; the leftover elements form this minor.
        // Remaining positions: [1][0], [1][2], [2][0], [2][2]
        int minor12 = computeMinor(m[1][0], m[1][2], m[2][0], m[2][2]);
        System.out.printf("  Step 2 — Minor M\u2081\u2082: det([%d,%d],[%d,%d]) = (%d\u00d7%d)-(%d\u00d7%d) = %d - %d = %d%n",
            m[1][0], m[1][2], m[2][0], m[2][2],
            m[1][0], m[2][2], m[1][2], m[2][0],
            m[1][0]*m[2][2], m[1][2]*m[2][0], minor12);

        // ── Step 3: Compute minor M₁₃ ──
        // Cross out row 0 and column 2; the leftover elements form this minor.
        // Remaining positions: [1][0], [1][1], [2][0], [2][1]
        int minor13 = computeMinor(m[1][0], m[1][1], m[2][0], m[2][1]);
        System.out.printf("  Step 3 — Minor M\u2081\u2083: det([%d,%d],[%d,%d]) = (%d\u00d7%d)-(%d\u00d7%d) = %d - %d = %d%n",
            m[1][0], m[1][1], m[2][0], m[2][1],
            m[1][0], m[2][1], m[1][1], m[2][0],
            m[1][0]*m[2][1], m[1][1]*m[2][0], minor13);

        // ── Cofactor Terms ──
        // The signs alternate: C₁₁ is positive, C₁₂ is negative, C₁₃ is positive.
        // Each cofactor is the sign times the pivot element times its minor.
        int c11 =  m[0][0] * minor11;   // +1 * element * minor
        int c12 = -m[0][1] * minor12;   // -1 * element * minor
        int c13 =  m[0][2] * minor13;   // +1 * element * minor

        System.out.println();
        System.out.printf("  Cofactor C\u2081\u2081 = (+1) \u00d7 %d \u00d7 %d = %d%n", m[0][0], minor11, c11);
        System.out.printf("  Cofactor C\u2081\u2082 = (-1) \u00d7 %d \u00d7 %d = %d%n", m[0][1], minor12, c12);
        System.out.printf("  Cofactor C\u2081\u2083 = (+1) \u00d7 %d \u00d7 %d = %d%n", m[0][2], minor13, c13);

        // ── Final Determinant ──
        // The determinant is the sum of all three cofactor terms.
        int det = c11 + c12 + c13;
        System.out.printf("%n  det(M) = %d + (%d) + %d%n", c11, c12, c13);
        System.out.println("=".repeat(52));
        System.out.printf("  \u2713  DETERMINANT = %d%n", det);

        // ── Singular Matrix Check ──
        // A zero determinant means the matrix has no inverse (it is singular).
        if (det == 0) {
            System.out.println("  \u26a0 The matrix is SINGULAR \u2014 it has no inverse.");
        }
        System.out.println("=".repeat(52));
    }

    // ── SECTION 5: Entry Point ───────────────────────────────────────────
    // Java starts execution here; we simply call our solver with the matrix.
    public static void main(String[] args) {
        solveDeterminant(matrix);
    }

}
