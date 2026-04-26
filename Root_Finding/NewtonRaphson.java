package Root_Finding;
public class NewtonRaphson extends Root_Comm {
    double x1,x2;
    double arr[],arr_diff[];
    NewtonRaphson() {
        x1=0.0;
        x2=10.0;
    }
    public void check() {
        while(Math.abs(x2-x1)>.0000000001) {
            x1=x2;
            if(super.func_diff(x1)!=0)
                x2=x1-super.func(x1)/super.func_diff(x1);
        }
        System.out.println("The root is "+x2);
    }
    public static void main(String[] args) {
        NewtonRaphson obj=new NewtonRaphson();
        obj.arr();
        obj.check();
    }
}