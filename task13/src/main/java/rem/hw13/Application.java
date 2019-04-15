package rem.hw13;

import rem.hw13.sort.ParallelSort;

import java.util.Random;

public class Application {
    public static void main(String[] args) {
        final Random random = new Random();
        final Integer[] generateArray = ParallelSort.generateArray(20, Integer.class, () -> random.nextInt(100));
        ParallelSort.printArray(generateArray);
    }
}
