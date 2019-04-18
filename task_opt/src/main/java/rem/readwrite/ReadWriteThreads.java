package rem.readwrite;

import java.util.concurrent.Semaphore;

public class ReadWriteThreads {
    private final int limit;
    private volatile int current = 1;
    private volatile int addend = 1;
    private volatile boolean available = true;
    private static final Semaphore CONSOLE = new Semaphore(1, true);

    public ReadWriteThreads(int limit) {
        this.limit = limit > 1 ? limit : 10;
    }

    private synchronized int get() {
        try {
            while (!available) {
                wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        available = false;
        notify();
        return current;
    }

    private synchronized int put() {
        try {
            while (available) {
                wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (current >= limit) {
            addend = -1;
        }
        int tmp = current;
        current += addend;
        available = true;
        notify();
        return tmp;
    }

    public void start() throws InterruptedException {
        Thread writeThread = new Thread(new WriteThread());
        writeThread.setName("wTh");
        Thread readThread = new Thread(new ReadThread());
        readThread.setName("rTh");
        writeThread.start();
        readThread.start();
        writeThread.join();
        readThread.join();
    }

    class WriteThread implements Runnable {
        @Override
        public void run() {
            int current;
            while ((current = put()) != 0) {
                try {
                    CONSOLE.acquire();
                    System.out.println(" ".repeat(current - 1) + current + " (" + Thread.currentThread().getName() + ")");
                    CONSOLE.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    class ReadThread implements Runnable {
        @Override
        public void run() {
            int current;
            while ((current = get()) != 0) {
                try {
                    CONSOLE.acquire();
                    System.out.println(" ".repeat(current - 1) + current + " (" + Thread.currentThread().getName() + ")");
                    CONSOLE.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
