package rem;

import rem.bruteforce.BruteForceRunner;
import rem.readwrite.ReadWriteThreads;

public class Application {
    public static void main(String[] args) throws InterruptedException {
        BruteForceRunner bruteForceRunner = new BruteForceRunner();
        bruteForceRunner.start();

        ReadWriteThreads readWriteThreads = new ReadWriteThreads(10);
        readWriteThreads.start();
    }
}
