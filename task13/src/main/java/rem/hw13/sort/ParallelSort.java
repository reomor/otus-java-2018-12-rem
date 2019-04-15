package rem.hw13.sort;

import java.lang.reflect.Array;
import java.util.StringJoiner;

public class ParallelSort {

    private ParallelSort() {}

    public static <T extends Comparable<T>> T[] generateArray(int length, Class<T> clazz, TGenerator<T> generator) {
        final Object instance = Array.newInstance(clazz, length);
        for (int i = 0; i < length; i++) {
            Array.set(instance, i, generator.generate());
        }
        return (T[]) instance;
    }

    public static <T extends Comparable<T>> void printArray(T[] array) {
        StringJoiner stringJoiner = new StringJoiner(", ");
        for (T item : array) {
            stringJoiner.add(item.toString());
        }
        System.out.println("[" + stringJoiner.toString() + "]");
    }
}
