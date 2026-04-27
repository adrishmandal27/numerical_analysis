package Interpolation;
public class NewtonBackwardDifference extends Interpolation_Comm{
    double nablaTab[][],h;
    NewtonBackwardDifference() {
        nablaTab=new double[0][0];
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
        nablaTab=new double[n][n];
        for(int i=0;i<n;i++)
            nablaTab[i][0]=super.y[i];
        for(int j=1;j<n;j++)
            for(int i=n-1;i>=j;i--) {
                nablaTab[i][j]=nablaTab[i][j-1]-nablaTab[i-1][j-1];
            }
        
    }
    public void interpolate() {
        super.y_i=nablaTab[n-1][0];
        double p=1.0;
        double u=(super.x_i-super.x[n-1])/h;
        for(int i=1;i<super.n;i++) {    
            p*=(u+i-1)/i;
            super.y_i+=(p*nablaTab[n-1][i]);
        }
        System.out.println("For x="+super.x_i+", y="+super.y_i);
    }
    public static void main(String[] args) {
        NewtonBackwardDifference obj=new NewtonBackwardDifference();
        obj.arr();
        obj.x_input();
        obj.calTab();
        obj.interpolate();
    }
}
