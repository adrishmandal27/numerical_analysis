import java.util.Scanner;
public class Bisection
{
    Scanner sc=new Scanner(System.in);
    double a,b;
    int n;
    double arr[];
    Bisection()
    {
        a=b=0.0;
        n=0;
    }
    public void arr()
    {
        System.out.println("Enter the order of the function");
        n=sc.nextInt()+1;
        String s="(_%d_)x^%d";
        for(int i=0;i<n;i++)
        {    
            System.out.print(String.format(s,i+1,n-i-1));
            System.out.print((i!=n-1)?"+":"");
        }
        System.out.println("=0\nEnter the coefficients accordingly");
        arr=new double[n];
        for(int i=0;i<n;i++)
            arr[i]=sc.nextDouble();
    }
    public void range()
    {
        System.out.println("Enter the minimum and maximum value separately");
        a=sc.nextDouble();
        b=sc.nextDouble();
        if(a>=b)
        {
            System.out.println("Minimum value cannot be greater than or equal to Maximum value!");
            System.exit(0);
        }
    }
    public double func(double t)
    {
        double sum=0;
        for(int i=0;i<n;i++)
            sum+=arr[i]*Math.pow(t,n-i-1);
        return sum;
    }
    public void check() 
    {
        if (func(a)*func(b)>=0) 
        {
            System.out.println("No root or more than 1 root present in this range.");
            return;
        }
        double x_old;
        double x_new=a;
        boolean terms=false;
        while (!terms) 
        {
            x_old=x_new;
            x_new=(a+b)/2;
            if (Math.abs(x_new-x_old)<=0.000000001 && x_old!=a) 
                terms=true;
            else
                if(func(a)*func(x_new)<0) 
                    b=x_new; 
                else
                    a=x_new;
        }
            System.out.println("The root is: " +x_new);
    }
    public static void main(String[] args)
    {
        Bisection obj=new Bisection();
        obj.arr();
        obj.range();
        obj.check();
    }
}