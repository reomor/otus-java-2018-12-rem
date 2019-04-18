package rem;

import rem.bruteforce.BruteForceRunner;
import rem.readwrite.ReadWriteThreads;

public class Application {
    public static void main(String[] args) throws InterruptedException {
        BruteForceRunner bruteForceRunner = new BruteForceRunner();
        // при 100, например, уже возникают коллизии при выводе на консоль
        // поэтому консоль - ресурс, работу с которым тоже надо синхронизовать)
        bruteForceRunner.start(20);

//        ReadWriteThreads readWriteThreads = new ReadWriteThreads(10);
//        readWriteThreads.start();
    }
}
