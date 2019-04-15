package rem.hw13;

import rem.hw13.sort.ParallelSortUtils;
import rem.hw13.sort.SortingTask;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class Application {
    public static void awaitTerminationAfterShutdown(ExecutorService threadPool) {
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
    public static void main(String[] args) {
        final Random random = new Random();
        final Integer[] generateArray = ParallelSortUtils.generateArray(20, Integer.class, () -> random.nextInt(100));
        ParallelSortUtils.printArray(generateArray);
        ParallelSortUtils.arraySort(generateArray, 5, 10);
        ParallelSortUtils.printArray(generateArray);
//        ParallelSortUtils.arraySort(generateArray);
//        ParallelSortUtils.printArray(generateArray);
        ForkJoinPool forkJoinPool = new ForkJoinPool(2);
        forkJoinPool.invoke(new SortingTask<>(generateArray, 2));
        forkJoinPool.shutdown();
        awaitTerminationAfterShutdown(forkJoinPool);
        ParallelSortUtils.printArray(generateArray);
    }
}
