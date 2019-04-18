package rem.bruteforce;

public class BruteForceRunner {
    public void start() throws InterruptedException {
        ThreadSharedObject sharedObject = new ThreadSharedObject(10, 2);
        Thread thread1 = new Thread(new CountThread(sharedObject));
        thread1.setName("1");
        Thread thread2 = new Thread(new CountThread(sharedObject));
        thread2.setName("2");
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
    }
}
