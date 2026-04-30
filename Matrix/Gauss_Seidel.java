package Matrix;
public class Gauss_Seidel extends Matrix_Comm {
    double Xarr[][];
    Gauss_Seidel() {
        Xarr = new double[0][0];
    }
    public void solve() {
        Xarr=new double[10000][super.n];
        for(int i=0;i<super.n;i++) {
            Xarr[0][i]=0;
        }
        for(int j=1;j<10000;j++) {
            for(int i=0;i<super.n;i++) {
                double sum=0.0;
                for(int k=0;k<super.n;k++) 
                    if(k<i) 
                        sum+=super.matrix[i][k]*Xarr[j][k];
                    else if(k>i)
                        sum+=super.matrix[i][k]*Xarr[j-1][k];
                Xarr[j][i]=(super.B[i]-sum)/super.matrix[i][i];
                if(Math.abs(Xarr[j][i]-Xarr[j-1][i])<=0.000000001) {
                    for(int m=0;m<super.n;m++) 
                        X[m]=Math.round(Xarr[j][m]*10000.0)/10000.0;
                    return;
                }
            }
        }
    }
    public static void main(String[] args) {
        Gauss_Seidel obj = new Gauss_Seidel();
        obj.input();
        obj.check_diagonaldominance();
        obj.solve();
        obj.display();
    }
}
