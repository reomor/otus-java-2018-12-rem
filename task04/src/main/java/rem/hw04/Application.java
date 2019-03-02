package rem.hw04;

public class Application {

    public static void main(String[] args) {
       GarbageCollectorStatistics statistics = new GarbageCollectorStatistics();
       statistics.gatherGCStatistics();
    }
}
