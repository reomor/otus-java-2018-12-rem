package rem.hw13.sort;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

/**
 * Executor to run parallel sort. If array length is less than {@code 128} then sort will nbe serial.
 */
public class SortExecutor {
    private ForkJoinPool forkJoinPool;
    private int nThread;
    private int MINIMUM_ARRAY_LENGTH_FOR_PARALLELISM = 128;

    public SortExecutor(int nThread) {
        this.forkJoinPool = new ForkJoinPool(nThread);
        this.nThread = nThread;
    }

    public <T extends Comparable<T>> void parallelSort(T[] array) {
        if (array.length < MINIMUM_ARRAY_LENGTH_FOR_PARALLELISM) {
            forkJoinPool.invoke(new SortingTask<>(array, 1));
        } else {
            forkJoinPool.invoke(new SortingTask<>(array, nThread));
        }
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
