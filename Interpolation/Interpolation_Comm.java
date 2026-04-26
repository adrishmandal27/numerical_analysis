package Interpolation;
import java.util.Scanner;
public class Interpolation_Comm {
    Scanner sc=new Scanner(System.in);
    double x[],y[],x_i,y_i;
    int n;
    Interpolation_Comm() {
        n=0;
        x_i=y_i=0.0;
        x=new double[0];
        y=new double[0];
    }
    public void arr() {
        System.out.println("Enter the total number of points");
        n=sc.nextInt();
        x=new double[n];
        y=new double[n];
        System.out.println("Enter "+n+" points (x-coordinate,y-coordinate)");
        for(int i=0;i<n;i++) {
            System.out.print("x_"+i+":");
            x[i]=sc.nextDouble();
            System.out.print("y_"+i+":");
            y[i]=sc.nextDouble();
        }System.out.println("");
        for(int i=0;i<n;i++)
            for(int j=i+1;j<n;j++)
                if(x[i]==x[j]) {
                    System.out.println("Two or more same x-coordinate present");
                    System.exit(0);
                }
    }
    public void x_input() {
        System.out.println("Enter the value of x to find the value of y");
        x_i=sc.nextDouble();
    }
}