package Numerical_Integration;
import java.util.Scanner;
public class Integration_Comm {
    Scanner sc = new Scanner(System.in);
    int n,a,b;
    double x[], y[], h, result;
    Integration_Comm() {
        n=a=b=0;
        x=new double[0];
        y=new double[0];
        h=result=0.0;
    }
    public void arr() {
        System.out.println("Enter the total number of points");
        n=sc.nextInt();
        if(n<2) {
            System.out.println("At least two points are required");
            System.exit(0);
        }
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
        h=x[1]-x[0];
        for(int i=1;i<n-1;i++)
            if(x[i+1]-x[i]!=h) {
                System.out.println("x-coordinates are not equally spaced");
                System.exit(0);
            }
    }
    public void input() {
        System.out.println("Enter the index number of the lower limit and the upper limit for the integration.");
        a=sc.nextInt();
        b=sc.nextInt();
        if(a==b) {
            result=0.0;
            display();
            System.exit(0);
        }
        else if(a<0 || a>=n || b<0 || b>=n || a>b) {
            System.out.println("Invalid index number");
            System.exit(0);
        }
    }
    public void display() {
        System.out.println("The integration of the function from "+ x[a] + " to " + x[b] + " is: " + result);
    }
}
