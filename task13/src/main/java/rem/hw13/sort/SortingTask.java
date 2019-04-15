package rem.hw13.sort;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

/**
 * RecursiveAction task which divide array on smaller arrays depend on number of Threads {@param nThread}
 * @param <T> generic type for array elements, extends {@link Comparable}
 */
public class SortingTask<T extends Comparable<T>> extends RecursiveAction {
    private int nThreads = 1;
    private int leftEdge;
    private int rightEdge;
    private T[] array;

    public SortingTask(T[] array) {
        this(array, 1, 0, array.length - 1);
    }

    public SortingTask(T[] array, int nThreads) {
        this(array, nThreads, 0, array.length - 1);
    }

    public SortingTask(T[] array, int nThreads, int leftEdge, int rightEdge) {
        this.array = array;
        this.nThreads = nThreads;
        this.leftEdge = leftEdge;
        this.rightEdge = rightEdge;
    }

    @Override
    protected void compute() {
        if (nThreads > 1) {
            ForkJoinTask.invokeAll(createSubTasks());
        } else {
            ParallelSortUtils.arraySort(array, leftEdge, rightEdge);
        }
    }

    private Collection<SortingTask> createSubTasks() {
        List<SortingTask> subtasks = new ArrayList<>();
        if (nThreads < 1) return Collections.emptyList();
        int rangePerThread = array.length / nThreads;
        int currentLeftEdge = 0;
        for (int i = 0; i < nThreads - 1; i++) {
            subtasks.add(new SortingTask<>(array, 1, currentLeftEdge, currentLeftEdge + rangePerThread - 1));
            currentLeftEdge += rangePerThread;
        }
        subtasks.add(new SortingTask<>(array, 1, currentLeftEdge, rightEdge));
        return subtasks;
    }
}
