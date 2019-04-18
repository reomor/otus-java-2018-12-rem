package rem.bruteforce;

public class ThreadSharedObject {
    private final int limit;
    private final int nThreads;
    private static volatile int currentValue = 1;
    private static volatile int addend = 1;
    private static volatile int requestsNumber = 1;

    public ThreadSharedObject(int nThreads) {
        this(-1, nThreads);
    }

    public ThreadSharedObject(int limit, int nThreads) {
        this.limit = limit > 1 ? limit : 10;
        this.nThreads = nThreads > 1 ? nThreads : 1;
    }

    public synchronized int getCurrentValue() {
        if (currentValue < 1) {
            return 0;
        }
        if (requestsNumber < nThreads) {
            requestsNumber++;
            return currentValue;
        } else {
            requestsNumber = 1;
            if (currentValue >= limit) {
                addend = -1;
            }
            final int tmp = currentValue;
            currentValue += addend;
            return tmp;
        }
    }
}
