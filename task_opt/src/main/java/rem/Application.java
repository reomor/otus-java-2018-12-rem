package rem;

import rem.bruteforce.BruteForceRunner;

public class Application {
    public static void main(String[] args) throws InterruptedException {
        //BruteForceRunner bruteForceRunner = new BruteForceRunner();
        //bruteForceRunner.start();

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
    }
}
