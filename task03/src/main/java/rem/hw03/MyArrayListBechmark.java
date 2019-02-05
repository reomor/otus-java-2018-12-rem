package rem.hw03;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 3)
public class MyArrayListBechmark {

    @org.openjdk.jmh.annotations.State(Scope.Thread)
    public static class State {
        List<Payload> list = new ArrayList<>();
        List<Payload> myList = new MyArrayList<>();

        int iterations = 1_000_000;

        long payloadIndex = -1;
        Payload payload = new Payload(987654321, "Additional payload test");

        @Setup(Level.Trial)
        public void setUp() {
            for (long i = 0; i < iterations; i++) {
                list.add(new Payload(123456789L, "Payload test"));
                myList.add(new Payload(123456789L, "Payload test"));
            }

            list.add(payload);
            myList.add(payload);
            payloadIndex = list.indexOf(payload);
        }
    }

    // ADD
    @Benchmark
    public void testAddAtList(MyArrayListBechmark.State state) {
        state.list.add(state.iterations, new Payload(state.iterations, "Add test"));
    }

    @Benchmark
    public void testAddAtMyList(MyArrayListBechmark.State state) {
        state.myList.add(state.iterations, new Payload(state.iterations, "Add test"));
    }

    // CONTAINS
    @Benchmark
    public void testContainsAtList(MyArrayListBechmark.State state) {
        state.list.contains(state.payload);
    }

    @Benchmark
    public void testContainsAtMyList(MyArrayListBechmark.State state) {
        state.myList.contains(state.payload);
    }

    // REMOVE
    @Benchmark
    public void testRemoveAtList(MyArrayListBechmark.State state) {
        state.list.remove(state.payload);
    }

    @Benchmark
    public void testRemoveAtMyList(MyArrayListBechmark.State state) {
        state.myList.remove(state.payload);
    }

    // GET
    @Benchmark
    public void testGetAtList(MyArrayListBechmark.State state) {
        state.list.get(state.iterations);
    }

    @Benchmark
    public void testGetAtMyList(MyArrayListBechmark.State state) {
        state.myList.get(state.iterations);
    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(MyArrayListBechmark.class.getSimpleName()).threads(3)
                .forks(1).shouldFailOnError(true)
                .shouldDoGC(true)
                .jvmArgs("-server").build();
        new Runner(options).run();
    }
}
