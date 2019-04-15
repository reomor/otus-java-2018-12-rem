package rem.hw13.sort;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

/**
 * Executor to run parallel sort
 */
public class SortExecutor {
    private ForkJoinPool forkJoinPool;
    private int nThread;

    public SortExecutor(int nThread) {
        this.forkJoinPool = new ForkJoinPool(nThread);
        this.nThread = nThread;
    }

    public <T extends Comparable<T>> void parallelSort(T[] array) {
        forkJoinPool.invoke(new SortingTask<>(array, nThread));
        forkJoinPool.shutdown();
        awaitTerminationAfterShutdown(forkJoinPool);
        ParallelSortUtils.arrayMergeSort(array, nThread);
    }

    private static void awaitTerminationAfterShutdown(ExecutorService threadPool) {
        threadPool.shutdown();
        try {
            if (!threadPool.awaitTermination(60, TimeUnit.SECONDS)) {
                threadPool.shutdownNow();
            }
        } catch (InterruptedException ex) {
            threadPool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
