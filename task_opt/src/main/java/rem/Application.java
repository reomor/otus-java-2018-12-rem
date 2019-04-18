package rem;

import rem.bruteforce.CountThread;
import rem.bruteforce.ThreadSharedObject;

public class Application {
    public static void main(String[] args) throws InterruptedException {
        ThreadSharedObject sharedObject = new ThreadSharedObject(3, 2);
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
