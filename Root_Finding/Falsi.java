package Root_Finding;
public class Falsi extends Root_Comm{
    double x;
    Falsi() {
        x=0.0;
        n=0;
    }
    public void check() {
        while(true) {
            if(super.func(super.a)*super.func(super.b)>=0) {
                System.out.println("No root or more than1 root in the given range");
                break;
            }
            double x0=x;
            x=super.a-super.func(super.a)*(super.b-super.a)/(super.func(super.b)-super.func(super.a));
            if(Math.abs(x-x0)<=0.000000001) {
                System.out.println("The root is "+x);
                break;
            }
            if(super.func(x)*super.func(super.b)<0)
                super.a=x;
            else
                super.b=x;
        }
    }
    public static void main(String[] args) {
        Falsi obj=new Falsi();
        obj.arr();
        obj.range();
        obj.check();
    }
}
