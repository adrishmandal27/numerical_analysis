public class Bisection extends Root_Comm {
    
    public void check()  {
        if (super.func(super.a)*super.func(super.b)>=0) {
            System.out.println("No root or more than 1 root present in this range.");
            return;
        }
        double x_old;
        double x_new=super.a;
        boolean terms=false;
        while (!terms) {
            x_old=x_new;
            x_new=(super.a+super.b)/2;
            if (Math.abs(x_new-x_old)<=0.000000001 && x_old!=super.a) 
                terms=true;
            else
                if(super.func(super.a)*super.func(x_new)<0) 
                    super.b=x_new; 
                else
                    super.a=x_new;
        }
            System.out.println("The root is: " +x_new);
    }
    public static void main(String[] args) {
        Bisection obj=new Bisection();
        obj.arr();
        obj.range();
        obj.check();
    }
}