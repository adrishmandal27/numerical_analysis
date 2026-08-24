# Numerical Analysis Toolkit

**🌐 Live Web Portal:** [https://adrishmandal27.github.io/numerical_analysis/](https://adrishmandal27.github.io/numerical_analysis/)

![Numerical Analysis Web Portal Preview](preview.png)

A high-performance computational suite implementing classical numerical methods. Originally built as a modular Java library and CLI, the project features a complete, interactive **Web-Based Portal** powered by a secure JavaScript math engine, p5.js graphing, and offline PWA support.

---

## 📌 Features

### 1. Root-Finding Algorithms
* **Bisection Method:** Brackets roots using binary interval bisection.
* **Regula Falsi (False Position):** Linear interpolation-based root finding.
* **Newton-Raphson Method:** Fast quadratic convergence using first-order derivatives.
* **Secant Method:** Finite-difference approximation of Newton's method without direct derivatives.

### 2. Interpolation & Approximation
* **Lagrange Interpolation:** Computes exact polynomial fits for non-uniformly spaced data.
* **Newton Forward Difference:** Polynomial interpolation for equally spaced tabular data from initial nodes.
* **Newton Backward Difference:** Polynomial interpolation tailored for points near the end of tabular data.
* **Newton Divided Difference:** General divided difference scheme for arbitrary node spacing.
* **Linear Interpolation:** Basic linear estimation between bounding nodes.

### 3. Numerical Integration & Calculus
* **Trapezoidal Rule:** First-order composite Newton-Cotes quadrature.
* **Simpson's 1/3 Rule:** Quadratic parabolic approximation over uniform sub-intervals.
* **Simpson's 3/8 Rule:** Cubic approximation for enhanced accuracy over 3n intervals.
* **Gauss-Legendre Quadrature:** High-precision orthogonal polynomial root-weight evaluation.
* **Numerical Differentiation:** Forward and backward finite difference approximations for first and second derivatives.

### 4. Linear Matrix Solvers
* **Gauss-Jacobi Iteration:** Parallel iterative solver for strictly diagonally dominant linear systems.
* **Gauss-Seidel Iteration:** Successive-displacement accelerated linear system solver.

---

## 🎨 Web Portal & PWA Features
* **Interactive p5.js Graphing:** Real-time Cartesian plane rendering with plotted function curves and root visualization.
* **Progressive Web App (PWA):** Fully installable on desktop and mobile devices with offline capability via a custom Service Worker (`sw.js`).
* **Algebraic Polynomial Expander:** Automatically formats interpolation results into standard polynomial form ($F(x) = ax^n + \dots$).
* **Data Exporting:** Zero-latency CSV data export functionality directly from algorithm iteration tables.
* **State Persistence:** Remembers active tabs using `localStorage` to prevent data loss on accidental page refreshes.
* **Security Hardening:** Built-in Regex input sanitization to block code injection and XSS attempts.

---

## 🏗 Project Structure

```text
numerical_analysis/
├── .github/
│   └── workflows/
│       └── maven-build.yml       # Automated GitHub Actions CI/CD Pipeline
├── src/                          # Java Backend & CLI Library
│   └── main/
│       └── java/
│           └── com/
│               └── numerical/
│                   ├── Main.java
│                   ├── Root_Finding/
│                   ├── Interpolation/
│                   ├── Numerical_Integration/
│                   ├── Numerical_Differentiation/
│                   └── Matrix/
├── index.html                    # Main Math Engine & Dashboard
├── engine.js                     # Core Numerical Algorithms & Security Layer
├── grapher.js                    # p5.js Cartesian Graphing Engine
├── style.css                     # Custom UI Styles & Tailwind Configuration
├── manifest.json                 # PWA Configuration & Metadata
├── sw.js                         # Offline Caching Service Worker
├── pom.xml                       # Maven Build Configuration
└── README.md