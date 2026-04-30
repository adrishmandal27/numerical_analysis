package Matrix;
import java.util.Scanner;
public class Matrix_Comm {
    double matrix[][],X[],B[];
    int n;
    Scanner sc = new Scanner(System.in);
    Matrix_Comm() {
        n = 0;
        matrix = new double[0][0];
        X = new double[0];
        B = new double[0];
    }
    public void check_diagonaldominance() {
        boolean isDiagonallyDominant = true;
        for (int i = 0; i < n; i++) {
             double sum = 0;
            for (int j = 0; j < n; j++) 
                if (i != j) sum += Math.abs(matrix[i][j]);
            if (Math.abs(matrix[i][i]) <= sum) {
                isDiagonallyDominant = false;
                break;
            }
        }
        if (!isDiagonallyDominant) 
           make_diagonaldominant();
    }
    public void make_diagonaldominant() {
        for (int i = 0; i < n; i++) {
            double max = Math.abs(matrix[i][i]);
            int maxIndex = i;
            for (int j = 0; j < n; j++) {
                if (Math.abs(matrix[j][i]) > max) {
                    max = Math.abs(matrix[j][i]);
                    maxIndex = j;
                }
            }
            if (maxIndex != i) {
                double[] tempRow = matrix[i];
                matrix[i] = matrix[maxIndex];
                matrix[maxIndex] = tempRow;

                double tempB = B[i];
                B[i] = B[maxIndex];
                B[maxIndex] = tempB;
            }
        }
        for(int i=0;i<n;i++) 
            if(matrix[i][i]==0) {
                System.out.println("Zero diagonal element found. Cannot proceed!.");
                System.exit(0);;
            }
    }
    public void input() {
        System.out.println("Enter the number of variables");
        n = sc.nextInt();
        matrix = new double[n][n];
        X = new double[n];
        B = new double[n];
        System.out.println("A=");
        for(int i=0;i<n;i++) {
            for(int j=0;j<n;j++) 
                System.out.print("a"+i+j+" ");
            System.out.println();
        }
        System.out.print("B= [");
        for(int i=0;i<n;i++)
            System.out.print("k"+i+" ");
        System.out.println("]T");
        System.out.println("AX=B\nEnter the values of A and B");
        for(int i=0;i<n;i++) {
            for(int j=0;j<n;j++) {
                System.out.print("a"+i+j+"=");
                matrix[i][j] = sc.nextDouble();
            }
        }
        for(int i=0;i<n;i++) {
            System.out.print("k"+i+"=");
            B[i] = sc.nextDouble();
        }
    }
    public void display() {
        System.out.print("The solution is:\nB= [");
        for(int i=0;i<n;i++) {
            System.out.print(X[i] + " ");
        }
        System.out.println("]T");
    }
}

