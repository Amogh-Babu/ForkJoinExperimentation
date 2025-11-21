import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class FilterEmpty {
    static ForkJoinPool POOL = new ForkJoinPool();
    static int CUTOFF;

    // Behavior should match Sequential.filterEmpty
    // Ignoring the initialization of arrays, your implementation must have linear work and log(n) span
    public static String[] filterEmpty(String[] arr, int cutoff){
        FilterEmpty.CUTOFF = cutoff;
        // TODO: Implement to match the filter/pack procedure discussed in class.
        // Reminder: the main steps are:
        // 1) do a map on the arr of strings
        // 2) do prefix sum on the map result (implementation provided for you in ParallelPrefix.java)
        // 3) initialize and array whose length matches the last value in the prefix sum result
        // 4) do a map to populate that new array.
        return null;
    }

}
