import java.util.Scanner;
public class Secant {
    Scanner sc=new Scanner(System.in);
    double x0,x1,x2,arr[];
    int n;
    Secant() {
        x0=x1=x2=0.0;
        n=0;
    }
    public void arr() {
        System.out.println("Enter the order of the function");
        n=sc.nextInt()+1;
        String s="(_%d_)x^%d";
        for(int i=0;i<n;i++) {    
            System.out.print(String.format(s,i+1,n-i-1));
            System.out.print((i!=n-1)?"+":"");
        }
        System.out.println("=0\nEnter the coefficients accordingly");
        arr=new double[n];
        for(int i=0;i<n;i++)
            arr[i]=sc.nextDouble();
    }
    public double func(double t) {
        double sum=0.0;
        for(int i=0;i<n;i++)
            sum+=arr[i]*Math.pow(t,n-i-1);
        return sum;
    }
    public void range() {
        System.out.println("Enter the minimum and the maximum values in which the root lies");
        x0=sc.nextDouble();
        x1=sc.nextDouble();
        if(x0>x1) {
            System.out.println("Minimum cannot be greater than maximum");
            System.exit(0);
        }
    }
    public void check() {
        if(func(x0)*func(x1)>=0) {
            System.out.println("No root or more than 1 root in the given range");
            return;
        }
        double diff;
        do {
            if((func(x1)-func(x0)==0))
                return;
            x2=x1-func(x1)*(x1-x0)/(func(x1)-func(x0));
            diff=Math.abs(x2-x1);
            x0=x1;
            x1=x2;
        } while(diff>0.000000001);
        System.out.println("The root is "+x2);
    }
    public static void main(String[] args)
    {
        Secant obj=new Secant();
        obj.arr();
        obj.range();
        obj.check();
    }
}
