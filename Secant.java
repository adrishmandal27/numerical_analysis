public class Secant extends Root_Comm {
    double x;
    Secant() {
        x=0.0;
    }
    public void check() {
        if(super.func(super.a)*super.func(super.b)>=0) {
            System.out.println("No root or more than 1 root in the given range");
            return;
        }
        double diff;
        do {
            if((super.func(super.b)-super.func(super.a)==0))
                return;
            x=super.b-super.func(super.b)*(super.b-super.a)/(super.func(super.b)-super.func(super.a));
            diff=Math.abs(x-super.b);
            super.a=super.b;
            super.b=x;
        } while(diff>0.000000001);
        System.out.println("The root is "+x);
    }
    public static void main(String[] args) {
        Secant obj=new Secant();
        obj.arr();
        obj.range();
        obj.check();
    }
}
