import java.util.Scanner;
public class Falsi {
    Scanner sc=new Scanner(System.in);
    double x,a,b, arr[];
    int n;
    Falsi() {
        x=a=b=0.0;
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
        a=sc.nextDouble();
        b=sc.nextDouble();
        if(a>b) {
            System.out.println("Minimum cannot be greater than maximum");
            System.exit(0);
        }
    }
    public void check() {
        while(true) {
            if(func(a)*func(b)>=0) {
                System.out.println("No root or more than1 root in the given range");
                break;
            }
            double x0=x;
            x=a-func(a)*(b-a)/(func(b)-func(a));
            if(Math.abs(x-x0)<=0.000000001) {
                System.out.println("The root is "+x);
                break;
            }
            if(func(x)*func(b)<0)
                a=x;
            else
                b=x;
        }
    }
    public static void main(String[] args) {
        Falsi obj=new Falsi();
        obj.arr();
        obj.range();
        obj.check();
    }
}
