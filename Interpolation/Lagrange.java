package Interpolation;
public class Lagrange extends Interpolation_Comm{
    public void interpolate(){
        for(int i=0;i<super.n;i++) {
            double t=super.y[i];
            for(int j=0;j<super.n;j++)
                if(j!=i)
                    t*=(super.x_i-super.x[j])/(super.x[i]-super.x[j]);
            super.y_i+=t;
        }
        System.out.println("At x="+super.x_i+", y="+super.y_i);
    }
    public static void main(String[] args) {
        Lagrange obj=new Lagrange();
        obj.arr();
        obj.x_input();
        obj.interpolate();
    }
}