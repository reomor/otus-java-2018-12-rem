package rem.hw13.sort;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.StringJoiner;

/**
 *
 */
public class ParallelSortUtils {

    private ParallelSortUtils() {}

    /**
     * Merges all sorted blocks in array. Every block must have already been sorted.
     * @param array  - array with already sorted blocks in
     * @param nThreads - number of threads
     * @param <T> - generic type for array elements
     */
    public static <T extends Comparable<T>> void arrayMergeSort(T[] array, int nThreads) {
        if (nThreads < 1) return;
        final int rangePerThread = array.length / nThreads;
        int currentLeftEdge = 0;
        // array of ranges for every thread
        int[] rangeLengths = new int[nThreads];
        // current position of cursor in certain range
        int[] currentPositionsInRange = new int[nThreads];
        // shift for start position of every block in array
        int[] blockPositionsShift = new int[nThreads];
        // init arrays except last, because it's length may differ
        for (int i = 0; i < nThreads - 1; i++) {
            rangeLengths[i] =  rangePerThread;
            blockPositionsShift[i] = currentLeftEdge;
            currentLeftEdge += rangePerThread;
        }
        blockPositionsShift[nThreads - 1] = currentLeftEdge;
        rangeLengths[nThreads - 1] = array.length - currentLeftEdge;
        @SuppressWarnings("unchecked")
        T[] result = Arrays.copyOfRange(array, 0, array.length);
        int resultPosition = 0;
        while(resultPosition < array.length) {
            T localMin = null;
            int localIndex = 0;
            // find first non-used in merge element in blocks
            for (int i = 0; i < nThreads; i++) {
                if (currentPositionsInRange[i] < rangeLengths[i]) {
                    localMin = result[currentPositionsInRange[i] + blockPositionsShift[i]];
                    localIndex = i;
                    break;
                }
            }
            // compare element with other elements in blocks and find minimum
            for (int i = 0; i < nThreads; i++) {
                final T currentResultInRange;
                if (currentPositionsInRange[i] < rangeLengths[i] && (currentResultInRange = result[currentPositionsInRange[i] + blockPositionsShift[i]]) != null && localMin.compareTo(currentResultInRange) > 0) {
                    localMin = currentResultInRange;
                    localIndex = i;
                }
            }
            // put in result array
            array[resultPosition++] = localMin;
            currentPositionsInRange[localIndex]++;
        }
    }

    /**
     * Sort whole array
     * @param array - array to sort
     * @param <T> - generic type for array element
     */
    public static <T extends Comparable<T>> void arraySort(T[] array) {
        arraySort(array, 0, array.length - 1);
    }

    /**
     * Sort elements in array from {@code leftEdge} to {@code rightEdge}
     * @param array - array to sort
     * @param leftEdge - left index (included)
     * @param rightEdge - right index (included)
     * @param <T> - generic type for array element
     */
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

    /**
     * Generates array with length {@code length}
     * @param length - length of new array
     * @param clazz - type of array elements
     * @param generator - function-generator
     * @param <T> - generic type for array element
     * @return new array with certain parameters
     */
    public static <T extends Comparable<T>> T[] generateArray(int length, Class<T> clazz, TGenerator<T> generator) {
        final Object instance = Array.newInstance(clazz, length);
        for (int i = 0; i < length; i++) {
            Array.set(instance, i, generator.generate());
        }
        return (T[]) instance;
    }

    /**
     * Prints array on console
     * @param array - array to print
     * @param <T> - generic type for array element
     */
    public static <T extends Comparable<T>> void printArray(T[] array) {
        StringJoiner stringJoiner = new StringJoiner(", ");
        for (T item : array) {
            stringJoiner.add(item.toString());
        }
        System.out.println("[" + stringJoiner.toString() + "]");
    }
}
