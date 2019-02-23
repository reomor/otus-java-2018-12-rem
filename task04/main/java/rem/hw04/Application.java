package rem.hw04;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.NotificationEmitter;
import javax.management.NotificationFilter;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;

public class Application {
    private final static List<GarbageCollectionNotificationInfo> gcNotifications = new ArrayList<>();

    public static void main(String[] args) {
        addGCNotificationHandlers();
        System.out.println("Starting the loop");
        int size = 5_000_000;
//        int counter = 0;
        long start = System.currentTimeMillis();
        while (System.currentTimeMillis() - start < 120 * 1_000L) {
            final int local = size;
            final Object[] array = new Object[local];
            System.out.println("Array of size: " + array.length + " created");

            for (int i = 0; i < local; i++) {
                array[i] = new String(new char[0]);
            }
            System.out.println("Created " + local + " objects.");
//            counter++;
//            if (counter % 100 == 0) {
//                GCMain.STORAGE.put(System.currentTimeMillis(), array);
//            }
//            Thread.sleep(1000);
        }
    }

    public static void addGCNotificationHandlers() {
        for (GarbageCollectorMXBean gcBean : ManagementFactory.getGarbageCollectorMXBeans()) {
            ((NotificationEmitter) gcBean).addNotificationListener(
                    (notification, handback) -> {
                        GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
                        gcNotifications.add(info);
                    },
                    (NotificationFilter) notification -> GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION.equals(notification.getType()),
                    null
            );
        }
    }
}
