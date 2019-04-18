package rem.bruteforce;

import java.util.ArrayList;
import java.util.List;

public class BruteForceRunner {
    public void start(int nThread) throws InterruptedException {
        if (nThread < 1) nThread = 1;
        ThreadSharedObject sharedObject = new ThreadSharedObject(5, nThread);
        List<Thread> list = new ArrayList<>();
        for (int i = 0; i < nThread; i++) {
            Thread thread = new Thread(new CountThread(sharedObject));
            thread.setName(String.valueOf(i));
            list.add(thread);
        }
        list.forEach(Thread::start);
        for (Thread thread : list) {
            thread.join();
        }
    }
}
