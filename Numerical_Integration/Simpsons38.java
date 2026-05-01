package Numerical_Integration;

public class Simpsons38 extends Integration_Comm {
    double remains,multiple_3;
    Simpsons38(){
        remains=0.0;
        multiple_3=0.0;
    }
    public void calculate() {
        int count=b-a;
        if(count%3!=0) {
            System.out.println("The number of intervals must be a multiple of 3 for Simpson's 3/8 rule " +count);
            System.exit(0);
        }
        remains=0.0;
        multiple_3=0.0;
        for(int i=a+1;i<b;i++) {
            multiple_3+=((i-a)%3==0)?y[i]:0.0;
            remains+=((i-a)%3!=0)?y[i]:0.0;
        }
        super.result=(3.0*h/8.0)*(y[a]+y[b]+3.0*remains+2.0*multiple_3);
    }
    public static void main(String[] args) {
        Simpsons38 obj=new Simpsons38();
        obj.arr();
        obj.input();
        obj.calculate();
        obj.display();
    }
}