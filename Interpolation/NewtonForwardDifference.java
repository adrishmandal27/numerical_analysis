package Interpolation;
public class NewtonForwardDifference extends Interpolation_Comm{
    double deltaTab[][],h;
    NewtonForwardDifference() {
        deltaTab=new double[0][0];
        h=0.0;
    }
    public void calTab() {
        h=super.x[1]-super.x[0];
        for(int i=1;i<n;i++)
            if(super.x[i]-super.x[i-1]!=h) {
                System.out.println("x-coordinates are not equally spaced");
                System.exit(0);
            }
        int n=super.n;
        deltaTab=new double[n][n];
        for(int i=0;i<n;i++)
            deltaTab[i][0]=super.y[i];
        for(int j=1;j<n;j++)
            for(int i=0;i<n-j;i++) {
                deltaTab[i][j]=deltaTab[i+1][j-1]-deltaTab[i][j-1];
            }
        
    }
    public void interpolate() {
        super.y_i=deltaTab[0][0];
        double p=1.0;
        double u=(super.x_i-super.x[0])/h;
        for(int i=1;i<super.n;i++) {    
            p*=(u-i+1)/i;
            super.y_i+=(p*deltaTab[0][i]);
        }
        System.out.println("For x="+super.x_i+", y="+super.y_i);
    }
    public static void main(String[] args) {
        NewtonForwardDifference obj=new NewtonForwardDifference();
        obj.arr();
        obj.x_input();
        obj.calTab();
        obj.interpolate();
    }
}
