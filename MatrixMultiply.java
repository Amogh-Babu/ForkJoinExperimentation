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
        POOL.invoke(new MatrixMultiplyAction()); // TODO: add parameters to match your constructor
        return product;
    }

    // Behavior should match the 2d version of Sequential.dotProduct.
    // Your implementation must have linear work and log(n) span
    public static int dotProduct(int[][] a, int[][] b, int row, int col, int cutoff){
        MatrixMultiply.CUTOFF = cutoff;
        return POOL.invoke(new DotProductTask()); // TODO: add parameters to match your constructor
    }

    private static class MatrixMultiplyAction extends RecursiveAction{
        // TODO: select fields

        public MatrixMultiplyAction(){
            // TODO: implement constructor
        }

        public void compute(){
            // TODO: implement
        }

    }

    private static class DotProductTask extends RecursiveTask<Integer>{
        // TODO: select fields

        public DotProductTask(){
            // TODO: implement constructor
        }

        public Integer compute(){
            // TODO: implement
            return 0;
        }

    }
    
}
