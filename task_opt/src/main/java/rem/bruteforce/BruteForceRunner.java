package rem.bruteforce;

public class BruteForceRunner {
    public void start() throws InterruptedException {
        final int nThread = 4;
        ThreadSharedObject sharedObject = new ThreadSharedObject(5, nThread);
        Thread thread1 = new Thread(new CountThread(sharedObject));
        thread1.setName("1");
        Thread thread2 = new Thread(new CountThread(sharedObject));
        thread2.setName("2");
        Thread thread3 = new Thread(new CountThread(sharedObject));
        thread3.setName("3");
        Thread thread4 = new Thread(new CountThread(sharedObject));
        thread4.setName("4");
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread1.join();
        thread2.join();
        thread3.join();
        thread4.join();
    }
}
