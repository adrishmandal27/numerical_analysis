package com.numerical.Numerical_Differentiation;

import java.util.Scanner;

public class Differentiate {
    Scanner sc = new Scanner(System.in);
    int n, pos;
    double[] x, y;
    double[][] deltaTab, nablaTab;
    double h, firstDeriv, secondDeriv;
    boolean isValid = true;

    public Differentiate() {
        n = pos = 0;
        x = new double[0];
        y = new double[0];
        deltaTab = new double[0][0];
        nablaTab = new double[0][0];
        h = firstDeriv = secondDeriv = 0.0;
    }

    public void arr() {
        System.out.print("Enter the total number of points: ");
        n = sc.nextInt();

        if (n < 2) {
            System.out.println("Error: At least 2 points are required for differentiation.");
            isValid = false;
            return;
        }

        x = new double[n];
        y = new double[n];

        System.out.println("Enter " + n + " points (x-coordinate, y-coordinate):");
        for (int i = 0; i < n; i++) {
            System.out.print("x_" + i + ": ");
            x[i] = sc.nextDouble();
            System.out.print("y_" + i + ": ");
            y[i] = sc.nextDouble();
        }
        System.out.println();

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (Math.abs(x[i] - x[j]) < 1e-9) {
                    System.out.println("Error: Two or more identical x-coordinates present.");
                    isValid = false;
                    return;
                }
            }
        }

        h = x[1] - x[0];
        for (int i = 1; i < n - 1; i++) {
            if (Math.abs((x[i + 1] - x[i]) - h) > 1e-7) {
                System.out.println("Error: x-coordinates are not equally spaced.");
                isValid = false;
                return;
            }
        }
    }

    public void input() {
        if (!isValid) return;

        System.out.print("Enter the index number (0 to " + (n - 1) + ") to find derivatives: ");
        pos = sc.nextInt();

        if (pos < 0 || pos >= n) {
            System.out.println("Invalid index number.");
            isValid = false;
            return;
        }

        if (pos <= n / 2) {
            forwardDiff();
        } else {
            backwardDiff();
        }
    }

    public void forwardDiff() {
        firstDeriv = secondDeriv = 0.0;
        deltaTab = new double[n][n];

        for (int i = 0; i < n; i++) {
            deltaTab[i][0] = y[i];
        }

        for (int j = 1; j < n; j++) {
            for (int i = 0; i < n - j; i++) {
                deltaTab[i][j] = deltaTab[i + 1][j - 1] - deltaTab[i][j - 1];
            }
        }

        for (int j = 1; j < n - pos; j++) {
            firstDeriv += Math.pow(-1, j - 1) * deltaTab[pos][j] / j;
        }

        for (int j = 2; j < n - pos; j++) {
            double t = 0.0;
            for (int i = 1; i < j; i++) {
                t += 1.0 / i;
            }
            t *= 2.0 / j;
            secondDeriv += Math.pow(-1, j) * deltaTab[pos][j] * t;
        }
    }

    public void backwardDiff() {
        firstDeriv = secondDeriv = 0.0;
        nablaTab = new double[n][n];

        for (int i = 0; i < n; i++) {
            nablaTab[i][0] = y[i];
        }

        for (int j = 1; j < n; j++) {
            for (int i = n - 1; i >= j; i--) {
                nablaTab[i][j] = nablaTab[i][j - 1] - nablaTab[i - 1][j - 1];
            }
        }

        for (int j = 1; j <= pos; j++) {
            firstDeriv += nablaTab[pos][j] / j;
        }

        for (int j = 2; j <= pos; j++) {
            double t = 0.0;
            for (int i = 1; i < j; i++) {
                t += 1.0 / i;
            }
            t *= 2.0 / j;
            secondDeriv += nablaTab[pos][j] * t;
        }
    }

    public void display() {
        if (!isValid) return;

        firstDeriv /= h;
        secondDeriv /= (h * h);

        System.out.printf("\nFirst derivative at x_%d (x = %.4f): %.6f\n", pos, x[pos], firstDeriv);
        System.out.printf("Second derivative at x_%d (x = %.4f): %.6f\n", pos, x[pos], secondDeriv);
    }

    public static void main(String[] args) {
        Differentiate obj = new Differentiate();
        obj.arr();
        obj.input();
        obj.display();
    }
}