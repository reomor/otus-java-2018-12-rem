package rem.barrier;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierRunner {
    private volatile int currentNumber = 1;
    private final int LIMIT = 10;
    private boolean limitIsReached = false;
    private final int nThread;
    private final CyclicBarrier barrier;

    public CyclicBarrierRunner(int nThread) {
        this.nThread = nThread;
        this.barrier = new CyclicBarrier(nThread, () -> {
            if (!limitIsReached && currentNumber >= LIMIT) {
                limitIsReached = true;
            }
            if (limitIsReached) {
                currentNumber -= 1;
            } else {
                currentNumber += 1;
            }
        });
    }

    public void start() throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < nThread; i++) {
            final Thread thread = new Thread(() -> {
                while (currentNumber > 0) {
                    try {
                        System.out.println(" ".repeat(currentNumber) + currentNumber + "(" + Thread.currentThread().getName() + ")");
                        barrier.await();
                    } catch (InterruptedException | BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            });
            threads.add(thread);
        }
        threads.forEach(Thread::start);
        for (Thread thread : threads) {
            thread.join();
        }
    }
}
