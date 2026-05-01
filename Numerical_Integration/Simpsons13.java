package Numerical_Integration;
public class Simpsons13 extends Integration_Comm {
    double odd,even;
    Simpsons13(){
        odd=0.0;
        even=0.0;
    }
    public void calculate() {
        int count=b-a;
        if(count%2!=0) {
            System.out.println("The number of intervals must be even for Simpson's 1/3 rule " +count);
            System.exit(0);
        }
        odd=0.0;
        even=0.0;
        for(int i=a+1;i<b;i++) {
            even+=((i-a)%2==0)?y[i]:0.0;
            odd+=((i-a)%2!=0)?y[i]:0.0;
        }
        super.result=(h/3.0)*(y[a]+y[b]+4*odd+2*even);
    }
    public static void main(String[] args) {
        Simpsons13 obj=new Simpsons13();
        obj.arr();
        obj.input();
        obj.calculate();
        obj.display();
    }
}