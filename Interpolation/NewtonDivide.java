package Interpolation;
import java.util.Scanner;
public class NewtonDivide extends Interpolation_Comm{
    static Scanner sc=new Scanner(System.in);
    double diffTab[][];
    NewtonDivide() {
        diffTab=new double[0][0];
    }
    public void calTab() {
        int n=super.n;
        diffTab=new double[n][n];
        for(int i=0;i<n;i++)
            diffTab[i][0]=super.y[i];
        for(int j=1;j<n;j++)
            for(int i=0;i<n-j;i++) {
                diffTab[i][j]=(diffTab[i+1][j-1]-diffTab[i][j-1])/(super.x[i+j]-super.x[i]);
            }
    }
    public void interpolate() {
        super.y_i=diffTab[0][0];
        double p=1.0;
        for(int i=1;i<super.n;i++) {
            p*=(super.x_i-super.x[i-1]);
            super.y_i+=(p*diffTab[0][i]);
        }
        System.out.println("For x="+super.x_i+", y="+super.y_i);
    }
    public static void main(String[] args) {
        NewtonDivide obj=new NewtonDivide();
        obj.arr();
        obj.x_input();
        obj.calTab();
        obj.interpolate();
    }
}
