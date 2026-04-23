import java.util.Scanner;
public class NewtonRaphson
{
    Scanner sc=new Scanner(System.in);
    double x1,x2,err;
    int n;
    double arr[],arr_diff[];
    NewtonRaphson()
    {
        x1=err=0.0;
        x2=10.0;
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
        arr_diff=new double[n-1];
        for(int i=0;i<n;i++)
            arr[i]=sc.nextDouble();
        for(int i=0;i<n-1;i++)
            arr_diff[i]=arr[i]*(n-i-1);
    }
    public double func(double t)
    {
        double sum=0;
        for(int i=0;i<n;i++)
            sum+=arr[i]*Math.pow(t,n-i-1);
        return sum;
    }
    public double func_diff(double t)     
    {
        double sum=0;
        for(int i=0;i<n-1;i++)
            sum+=arr_diff[i]*Math.pow(t,n-i-2);
        return sum;
    }
    public void check() 
    {
        while(Math.abs(x2-x1)>.0000000001)
        {
            x1=x2;
            if(func_diff(x1)!=0)
                x2=x1-func(x1)/func_diff(x1);
        }
        System.out.println("The root is "+x2);
    }
    public static void main(String[] args)
    {
        NewtonRaphson obj=new NewtonRaphson();
        obj.arr();
        obj.check();
    }
}