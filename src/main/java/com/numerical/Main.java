package com.numerical;

import java.util.Scanner;

import com.numerical.Interpolation.Lagrange;
import com.numerical.Interpolation.NewtonBackwardDifference;
import com.numerical.Interpolation.NewtonDivide;
import com.numerical.Interpolation.NewtonForwardDifference;
import com.numerical.Matrix.Gauss_Jacobi;
import com.numerical.Matrix.Gauss_Seidel;
import com.numerical.Numerical_Differentiation.Differentiate;
import com.numerical.Numerical_Integration.Gauss_Legendre;
import com.numerical.Numerical_Integration.Simpsons13;
import com.numerical.Numerical_Integration.Simpsons38;
import com.numerical.Numerical_Integration.Trapezoidal;
import com.numerical.Root_Finding.Bisection;
import com.numerical.Root_Finding.Falsi;
import com.numerical.Root_Finding.NewtonRaphson;
import com.numerical.Root_Finding.Secant;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n=======================================================");
            System.out.println("             NUMERICAL ANALYSIS TOOLKIT");
            System.out.println("=======================================================");
            System.out.println("--- Root Finding ---");
            System.out.println(" 1. Bisection Method");
            System.out.println(" 2. Regula Falsi Method");
            System.out.println(" 3. Newton-Raphson Method");
            System.out.println(" 4. Secant Method");
            System.out.println("\n--- Interpolation ---");
            System.out.println(" 5. Lagrange Interpolation");
            System.out.println(" 6. Newton Forward Difference");
            System.out.println(" 7. Newton Backward Difference");
            System.out.println(" 8. Newton Divided Difference");
            System.out.println("\n--- Numerical Integration ---");
            System.out.println(" 9. Trapezoidal Rule");
            System.out.println("10. Simpson's 1/3 Rule");
            System.out.println("11. Simpson's 3/8 Rule");
            System.out.println("12. Gauss-Legendre Quadrature");
            System.out.println("\n--- Numerical Differentiation ---");
            System.out.println("13. Finite Difference Differentiation");
            System.out.println("\n--- Matrix Solvers ---");
            System.out.println("14. Gauss-Jacobi Method");
            System.out.println("15. Gauss-Seidel Method");
            System.out.println("\n 0. Exit");
            System.out.println("=======================================================");
            System.out.print("Select an option (0-15): ");

            int choice = sc.nextInt();

            switch (choice) {
                // Root Finding
                case 1 -> Bisection.main(args);
                case 2 -> Falsi.main(args);
                case 3 -> NewtonRaphson.main(args);
                case 4 -> Secant.main(args);

                // Interpolation
                case 5 -> Lagrange.main(args);
                case 6 -> NewtonForwardDifference.main(args);
                case 7 -> NewtonBackwardDifference.main(args);
                case 8 -> NewtonDivide.main(args);

                // Numerical Integration
                case 9 -> Trapezoidal.main(args);
                case 10 -> Simpsons13.main(args);
                case 11 -> Simpsons38.main(args);
                case 12 -> Gauss_Legendre.main(args);

                // Numerical Differentiation
                case 13 -> Differentiate.main(args);

                // Matrix Solvers
                case 14 -> Gauss_Jacobi.main(args);
                case 15 -> Gauss_Seidel.main(args);

                case 0 -> {
                    System.out.println("\nExiting Numerical Analysis Toolkit. Goodbye!");
                    sc.close();
                    return;
                }
                default -> System.out.println("\nInvalid option! Please enter a number between 0 and 15.");
            }
        }
    }
}