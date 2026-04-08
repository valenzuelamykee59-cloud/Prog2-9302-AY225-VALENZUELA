# uphsd-cs-valenzuela-mykee

**Student:** Valenzuela, Mykee L.
**Section:** BSIT-GD
**Course:** Math 101 – Linear Algebra, UPHSD Molino Campus
**Assignment:** Programming Assignment 1 — 3×3 Matrix Determinant Solver
**Academic Year:** 2025–2026

---

## Assigned Matrix

```
|  1   4   6  |
|  3   5   2  |
|  4   1   3  |
```

---

## How to Run

### Java
```bash
javac DeterminantSolver.java
java DeterminantSolver
```

### JavaScript (Node.js)
```bash
node determinant_solver.js
```

---

## Sample Output

```
====================================================
  3x3 MATRIX DETERMINANT SOLVER
  Student: Valenzuela, Mykee L.
  Assigned Matrix:
====================================================
┌               ┐
│   1   4   6  │
│   3   5   2  │
│   4   1   3  │
└               ┘
====================================================

Expanding along Row 1 (cofactor expansion):

  Step 1 — Minor M₁₁: det([5,2],[1,3]) = (5×3) - (2×1) = 15 - 2 = 13
  Step 2 — Minor M₁₂: det([3,2],[4,3]) = (3×3) - (2×4) = 9 - 8 = 1
  Step 3 — Minor M₁₃: det([3,5],[4,1]) = (3×1) - (5×4) = 3 - 20 = -17

  Cofactor C₁₁ = (+1) × 1 × 13 = 13
  Cofactor C₁₂ = (-1) × 4 × 1 = -4
  Cofactor C₁₃ = (+1) × 6 × -17 = -102

  det(M) = 13 + (-4) + -102

====================================================
  ✓  DETERMINANT = -93
====================================================
```

---

## Final Determinant Value

**det(M) = -93**

---

## Repository Structure

```
uphsd-cs-valenzuela-mykee/
├── linear-algebra/
│   └── assignment-01/
│       ├── DeterminantSolver.java
│       ├── determinant_solver.js
│       └── README.md
└── README.md
```
