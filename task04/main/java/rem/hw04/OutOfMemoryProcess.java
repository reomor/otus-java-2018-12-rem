package rem.hw04;

import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;

import static rem.hw04.Application.printGCPhaseStatistics;

public class OutOfMemoryProcess extends Thread {
    private static Deque<String> queue = new LinkedBlockingDeque<>();

    public void clearQueue() {
        queue.clear();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }

    @Override
    public void run() {
        System.out.println("Starting the loop");
        int count = 0;
        try {
            while (true) {
                for (int i = 0; i < 10; i++) {
                    queue.push(String.valueOf(i));
                }
                for (int i = 0; i < 9; i++) {
                    queue.poll();
                }
                count++;
                if (count % 100 == 0) {
                    try {
                        Thread.sleep(75);
                    } catch (InterruptedException ignore) {
                    }
                }
            }
        } catch (java.lang.OutOfMemoryError e) {
            clearQueue();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignore) {
            }
            System.out.println("Out of loop with OOM exception");
            printGCPhaseStatistics();
        }
    }
}
