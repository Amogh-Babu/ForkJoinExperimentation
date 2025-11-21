import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

public class MatrixMultiply {
    static ForkJoinPool POOL = new ForkJoinPool();
    static int CUTOFF;

    // Behavior should match Sequential.multiply.
    // Ignoring the initialization of arrays, your implementation should have n^3 work and log(n) span
    public static int[][] multiply(int[][] a, int[][] b, int cutoff){
        MatrixMultiply.CUTOFF = cutoff;
        int[][] product = new int[a.length][b[0].length];
        POOL.invoke(new MatrixMultiplyAction(0, a.length, 0, a.length, a, b, product));
        return product;
    }

    // Behavior should match the 2d version of Sequential.dotProduct.
    // Your implementation must have linear work and log(n) span
    public static int dotProduct(int[][] a, int[][] b, int row, int col, int cutoff){
        MatrixMultiply.CUTOFF = cutoff;
        return POOL.invoke(new DotProductTask(row, col, 0, a.length, a, b));
    }

    private static class MatrixMultiplyAction extends RecursiveAction{
        int left;
        int right;
        int top;
        int bottom;
        int[][] a;
        int[][] b;
        int[][] product;

        public MatrixMultiplyAction(int left, int right, int top, int bottom, int[][] a, int[][] b, int[][] product){
            this.left = left;
            this.right = right;
            this.top = top;
            this.bottom = bottom;
            this.a = a;
            this.b = b;
            this.product = product;
        }

        public void compute(){
            if ( (right-left) <= CUTOFF || (bottom-top) <= CUTOFF ) {
                for (int row = top; row < bottom; row++) {
                    for (int col = left; col < right; col++) {
                        product[row][col] = POOL.invoke(new DotProductTask(row, col, 0, a.length, a, b));
                    }
                }
            } else {
                MatrixMultiplyAction topLeft = new MatrixMultiplyAction(left, (left+right)/2, top, (top+bottom)/2, a, b, product);
                MatrixMultiplyAction topRight = new MatrixMultiplyAction((left+right)/2, right , top, (top+bottom)/2, a, b, product);
                MatrixMultiplyAction bottomLeft = new MatrixMultiplyAction(left, (left+right)/2, (top+bottom)/2, bottom, a, b, product);
                MatrixMultiplyAction bottomRight = new MatrixMultiplyAction((left+right)/2, right , (top+bottom)/2, bottom, a, b, product);
                topLeft.fork();
                topRight.fork();
                bottomLeft.fork();
                bottomRight.compute();
                topLeft.join();
                topRight.join();
                bottomLeft.join();
            }
        }

    }

    private static class DotProductTask extends RecursiveTask<Integer>{
        int row;
        int col;
        int lo;
        int hi;
        int[][] a;
        int[][]  b;

        public DotProductTask(int row, int col, int lo, int hi, int[][] a, int[][] b){
            this.row = row;
            this.col = col;
            this.lo = lo;
            this.hi = hi;
            this.a = a;
            this.b = b;
        }

        public Integer compute(){
            if (hi - lo <= CUTOFF) {
                int ans = 0;
                for (int i = lo; i < hi; i++) { ans += a[row][i] * b[i][col]; }
                return ans;
            } else {
                DotProductTask left = new DotProductTask(row, col, lo, (hi + lo) / 2, a, b);
                DotProductTask right = new DotProductTask(row, col, (hi + lo) / 2, hi, a, b);
                left.fork();
                int rightAns = right.compute();
                int leftAns = left.join();
                return leftAns + rightAns;
            }
        }
    }
    
}
