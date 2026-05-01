package Numerical_Integration;

public class Trapezoidal extends Integration_Comm {
    public void calculate() {
        super.result=(h/2)*(y[a]+y[b]+2*sum());
    }
    public double sum() {
        double s=0.0;
        for(int i=a+1;i<b;i++)
            s+=y[i];
        return s;
    }
    public static void main(String[] args) {
        Trapezoidal obj=new Trapezoidal();
        obj.arr();
        obj.input();
        obj.calculate();
        obj.display();
    }
}
