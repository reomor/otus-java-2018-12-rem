package rem.hw13.sort;

import java.lang.reflect.Array;
import java.util.StringJoiner;

public class ParallelSortUtils {

    private ParallelSortUtils() {}

    public static <T extends Comparable<T>> void arrayMergeSort() {

    }

    public static <T extends Comparable<T>> void arraySort(T[] array) {
        arraySort(array, 0, array.length - 1);
    }

    public static <T extends Comparable<T>> void arraySort(T[] array, int leftEdge, int rightEdge) {
        final int arrayLength = array.length;
        if (leftEdge <0 || leftEdge >= arrayLength || rightEdge < 0 || rightEdge >= arrayLength) {
            throw new IndexOutOfBoundsException("one of the edges is out of range");
        }
        for (int i = leftEdge; i < rightEdge + 1; i++) {
            for (int j = leftEdge; j < rightEdge - (i - leftEdge); j++) {
                if (array[j].compareTo(array[j + 1]) > 0) {
                    T temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }

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
