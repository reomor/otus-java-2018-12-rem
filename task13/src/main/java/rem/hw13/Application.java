package rem.hw13;

import rem.hw13.sort.ParallelSortUtils;
import rem.hw13.sort.SortExecutor;

import java.util.Random;

public class Application {
    public static void main(String[] args) {
        final Random random = new Random();
        final Integer[] generateArray = ParallelSortUtils.generateArray(20, Integer.class, () -> random.nextInt(100));
        ParallelSortUtils.printArray(generateArray);
        SortExecutor sortExecutor = new SortExecutor(3);
        sortExecutor.parallelSort(generateArray);
        ParallelSortUtils.printArray(generateArray);
    }
}
