package rem.hw04;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.NotificationEmitter;
import javax.management.NotificationFilter;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.*;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summarizingLong;

public class Application {
    private final static List<GarbageCollectionNotificationInfo> gcNotifications = new ArrayList<>();

    public static void main(String[] args) {
        addGCNotificationHandlers();
        OutOfMemoryProcess object = new OutOfMemoryProcess();
        object.run();
    }

    public static void printGCPhaseStatistics() {
        System.out.println("|-------------------------------------------|");
        gcNotifications.stream().collect(
                groupingBy(GarbageCollectionNotificationInfo::getGcName,
                        groupingBy(notificationInfo -> notificationInfo.getGcInfo().getStartTime() / 60 / 1000,
                                summarizingLong(notificationInfo -> notificationInfo.getGcInfo().getDuration())
                        ))).forEach((gcName, statisticsMap) -> {
            System.out.println(String.format("| %-42s|", gcName));
            System.out.println("| # min | gc call count | gc duration in ms |");
            statisticsMap.forEach((minute, stat) -> {
                System.out.println(String.format("|%6d |%14d |%18d |",
                        minute, stat.getCount(), stat.getSum()));
            });
            System.out.println("|-------------------------------------------|");
        });
    }

    private static void addGCNotificationHandlers() {
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
