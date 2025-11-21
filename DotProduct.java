import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class DotProduct {
    static ForkJoinPool POOL = new ForkJoinPool();
    static int CUTOFF;

    // Behavior should match Sequential.dotProduct
    // Your implementation must have linear work and log(n) span
    public static int dotProduct(int[] a, int[]b, int cutoff){
        DotProduct.CUTOFF = cutoff;
        return POOL.invoke(new DotProductTask(0, a.length, a, b));
    }

    private static class DotProductTask extends RecursiveTask<Integer>{
        int lo;
        int hi;
        int[] a;
        int[]  b;

        public DotProductTask(int lo, int hi, int[] a, int[] b){
            this.lo = lo;
            this.hi = hi;
            this.a = a;
            this.b = b;
        }

        public Integer compute(){
            if (hi - lo <= CUTOFF) {
                int ans = 0;
                for (int i = lo; i < hi; i++) { ans += a[i] * b[i]; }
                return ans;
            } else {
                DotProductTask left = new DotProductTask(lo, (hi + lo) / 2, a, b);
                DotProductTask right = new DotProductTask((hi + lo) / 2, hi, a, b);
                left.fork();
                int rightAns = right.compute();
                int leftAns = left.join();
                return leftAns + rightAns;
            }
        }
    }
}
