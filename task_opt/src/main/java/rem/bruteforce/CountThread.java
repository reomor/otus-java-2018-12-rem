package rem.bruteforce;

public class CountThread implements Runnable {
    private final ThreadSharedObject sharedObject;

    public CountThread(ThreadSharedObject sharedObject) {
        this.sharedObject = sharedObject;
    }

    @Override
    public void run() {
        int currentValue = 0;
        while (true) {
            currentValue = sharedObject.getCurrentValue();
            if (currentValue == 0) {
                break;
            }
            System.out.println(" ".repeat(currentValue - 1) + currentValue + " (" + Thread.currentThread().getName() + ")");
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
