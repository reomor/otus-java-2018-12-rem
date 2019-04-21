package rem;

import rem.barrier.CyclicBarrierRunner;
import rem.bruteforce.BruteForceRunner;

public class Application {
    public static void main(String[] args) throws InterruptedException {
//        BruteForceRunner bruteForceRunner = new BruteForceRunner();
        // при 100, например, уже возникают коллизии при выводе на консоль
        // поэтому консоль - ресурс, работу с которым тоже надо синхронизовать)
//        bruteForceRunner.start(20);

//        ReadWriteThreads readWriteThreads = new ReadWriteThreads(10);
//        readWriteThreads.start();

        CyclicBarrierRunner cyclicBarrierRunner = new CyclicBarrierRunner(4);
        cyclicBarrierRunner.start();
    }
}
