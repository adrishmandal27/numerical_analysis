import java.util.Scanner;
public class Root_Comm {
    Scanner sc = new Scanner(System.in);
    double a, b, x, arr[], arr_diff[];
    int n;
    Root_Comm() {
        a = b = x = 0.0;
        n = 0;
        arr = new double[0];
        arr_diff = new double[0];
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
        arr_diff=new double[n-1];
        for(int i=0;i<n;i++)
            arr[i]=sc.nextDouble();
        for(int i=0;i<n-1;i++)
            arr_diff[i]=arr[i]*(n-i-1);
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
    public double func(double t) {
        double sum=0;
        for(int i=0;i<n;i++)
            sum+=arr[i]*Math.pow(t,n-i-1);
        return sum;
    }
    public double func_diff(double t) {
        double sum=0;
        for(int i=0;i<n-1;i++)
            sum+=arr_diff[i]*Math.pow(t,n-i-2);
        return sum;
    }
}
