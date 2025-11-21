import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class DotProduct {
    static ForkJoinPool POOL = new ForkJoinPool();
    static int CUTOFF;

    // Behavior should match Sequential.dotProduct
    // Your implementation must have linear work and log(n) span
    public static int dotProduct(int[] a, int[]b, int cutoff){
        DotProduct.CUTOFF = cutoff;
        return POOL.invoke(new DotProductTask()); // TODO: add parameters to match your constructor
    }

    private static class DotProductTask extends RecursiveTask<Integer>{
        // TODO: select fields

        public DotProductTask(){
            // TODO: implement constructor
        }

        public Integer compute(){
            //TODO: Implement.
            return 0;
        }
    }
    
}
