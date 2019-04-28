package rem.hw13.sort;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("SortExecutor must")
class SortExecutorTest {
    @Test
    @DisplayName("sort integer array in 3 threads")
    public void shouldReturnSortedIntegers_whenArrayLengthIs1000AndNThreadsIs3_thenSort() {
        SortExecutor sortExecutor = new SortExecutor(3);
        final Class<Integer> clazz = Integer.class;
        Random random = new Random();
        final Integer[] integers = ParallelSortUtils.generateArray(1000, clazz, () -> {
            return random.nextInt(1000);
        });
        sortExecutor.parallelSort(integers);
        for (int i = 0; i < integers.length - 1; i++) {
            assertTrue(integers[i] <= integers[i + 1]);
        }
    }

    @Test
    @DisplayName("sort custom class array in 4 threads")
    public void shouldReturnSortedCustomClass_whenArrayLengthIs200AndNThreadsIs4_whenSortWithCustomOrder() {
        SortExecutor sortExecutor = new SortExecutor(4);

        class MyClass implements Comparable<MyClass> {
            private String string;

            public MyClass(String string) {
                this.string = string;
            }

            @Override
            public int compareTo(MyClass o) {
                return Integer.compare(this.string.length(), o.string.length());
            }
        }
        Random random = new Random();
        final MyClass[] generateArray = ParallelSortUtils.generateArray(200, MyClass.class, () -> {
            final String randomString = UtilKt.randomString(random.nextInt(12));
            return new MyClass(randomString);
        });
        sortExecutor.parallelSort(generateArray);
        for (int i = 0; i < generateArray.length - 1; i++) {
            assertTrue(generateArray[i].string.length() <= generateArray[i + 1].string.length());
        }
    }

    @Test
    @DisplayName("sort integer array in 1 threads")
    public void shouldReturnSortedIntegers_whenArrayLengthIs500AndNThreadsIs1_thenSort() {
        SortExecutor sortExecutor = new SortExecutor(1);
        final Class<Integer> clazz = Integer.class;
        Random random = new Random();
        final Integer[] integers = ParallelSortUtils.generateArray(500, clazz, () -> {
            return random.nextInt(100);
        });
        sortExecutor.parallelSort(integers);
        for (int i = 0; i < integers.length - 1; i++) {
            assertTrue(integers[i] <= integers[i + 1]);
        }
    }
}