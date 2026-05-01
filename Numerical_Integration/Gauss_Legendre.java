package Numerical_Integration;
import java.util.Scanner;
public class Gauss_Legendre {
    Scanner sc=new Scanner(System.in);
    int n;
    double arr[],a,b, result[],err[];
    Gauss_Legendre() {
        n=0;
        arr=new double[0];
        a=b=0.0;
        result=new double[4];
        err=new double[3];
    }
    public void input() {
        System.out.println("Enter the order of the function");
        n=sc.nextInt()+1;
        String s="(_%d_)x^%d";
        System.out.print("f(x)=");
        for(int i=0;i<n;i++) {    
            System.out.print(String.format(s,i+1,n-i-1));
            System.out.print((i!=n-1)?"+":"");
        }
        System.out.println("\nEnter the coefficients accordingly");
        arr=new double[n];
        for(int i=0;i<n;i++)
            arr[i]=sc.nextDouble();
        System.out.println("Enter the lower limit and the upper limit of integration separately.");
        a=sc.nextDouble();
        b=sc.nextDouble();
        if(a>b) {
            System.out.println("Lower limit cannot be greater than upper limit");
            System.exit(0);
        }
        else if(a==b) {
            System.out.println("As lower limit is equal to upper limit, the result is 0");
            System.exit(0);
        }
        result[3]=integ_func(b)-integ_func(a);
    }
    public double func(double t) {
        double x=(1.0/2.0)*((b-a)*t+(b+a));
        double sum=0.0;
        for(int i=0;i<n;i++)
            sum+=arr[i]*Math.pow(x,n-i-1);
        sum*=(b-a)/2.0;
        return sum;
    }
    public void point_1() {
        result[0]=2.0*func(0.0);
        err[0]=Math.abs(result[3]-result[0])*100/result[3];
    }
    public void point_2() {
        result[1]=func(-1.0/Math.sqrt(3.0))+func(1.0/Math.sqrt(3.0));
        err[1]=Math.abs(result[3]-result[1])*100/result[3];
    }
    public void point_3() {
        result[2]=(1.0/9.0)*(5.0*func(-Math.sqrt(3.0/5.0))+8.0*func(0.0)+5.0*func(Math.sqrt(3.0/5.0)));
        err[2]=Math.abs(result[3]-result[2])*100/result[3];
    }
    public void display() {
        System.out.println("The actual value of integration is "+result[3]);
        System.out.println("The result of integration using 1 point Gauss Legendre Rule is "+result[0] + " and the percentage error is "+String.format("%.4f", err[0])+"%");
        System.out.println("The result of integration using 2 points Gauss Legendre Rule is "+result[1] + " and the percentage error is "+String.format("%.4f", err[1])+"%");
        System.out.println("The result of integration using 3 points Gauss Legendre Rule is "+result[2] + " and the percentage error is "+String.format("%.4f", err[2])+"%");
    }
    public double integ_func(double t) {
        double k=0.0;
        for(int i=0;i<n;i++)
            k+=arr[i]*Math.pow(t,n-i)/(n-i);
        return k;
    }
    public static void main(String[] args) {
        Gauss_Legendre obj=new Gauss_Legendre();
        obj.input();
        obj.point_1();
        obj.point_2();
        obj.point_3();
        obj.display();
    }
}
