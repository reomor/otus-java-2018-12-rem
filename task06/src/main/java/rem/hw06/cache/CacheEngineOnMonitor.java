package rem.hw06.cache;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class CacheEngineOnMonitor<K, V> implements CacheEngine<K, V> {
    private final int maxElements;
    private final long lifeTimeMs;
    private final long idleTimeMs;
    private final boolean isEternal;

    private final Map<K, SoftReference<CacheElement<K, V>>> softReferenceMap = new ConcurrentHashMap<>();
    private final Function<CacheElement<K, V>, Long> timeFunction;

    private long hitCount;
    private long missCount;
    private long MIN_MONITOR_SLEEP_TIME = 10L;
    private MonitorThread monitor;

    public CacheEngineOnMonitor(int maxElements, long lifeTimeMs, long idleTimeMs) {
        this.maxElements = maxElements;
        this.lifeTimeMs = lifeTimeMs > 0 ? lifeTimeMs : 0;
        this.idleTimeMs = lifeTimeMs > 0 ? 0 : idleTimeMs > 0 ? idleTimeMs : 0;
        this.isEternal = lifeTimeMs == 0 && idleTimeMs == 0;
        if (lifeTimeMs > 0) {
            timeFunction = cacheElement -> cacheElement.getCreationTime() + lifeTimeMs;
        } else if (idleTimeMs > 0) {
            timeFunction = cacheElement -> cacheElement.getLastAccessTime() + idleTimeMs;
        } else {
            timeFunction = null;
        }
        this.hitCount = 0;
        this.missCount = 0;
        if (!this.isEternal) {
            monitor = new MonitorThread();
            monitor.start();
        }
    }

    private class CacheElement<KK, VV> {
        private final KK key;
        private final VV value;
        private final long creationTime;
        private long lastAccessTime;

        public CacheElement(KK key, VV value) {
            this.key = key;
            this.value = value;
            this.creationTime = this.lastAccessTime = getCurrentTime();
        }

        private long getCurrentTime() {
            return System.currentTimeMillis();
        }

        public KK getKey() {
            return key;
        }

        public VV getValue() {
            return value;
        }

        public long getCreationTime() {
            return creationTime;
        }

        public long getLastAccessTime() {
            return lastAccessTime;
        }

        public void refreshAccessTime() {
            this.lastAccessTime = getCurrentTime();
        }
    }

    private class MonitorThread extends Thread {
        @Override
        public void run() {
            try {
                while (!this.isInterrupted()) {
                    long start = System.currentTimeMillis();
                    ArrayList<K> list = new ArrayList<>(softReferenceMap.keySet());
                    for (K key : list) {
                        CacheElement<K, V> element = null;
                        if (softReferenceMap.get(key) != null && (element = softReferenceMap.get(key).get()) != null) {
                            if (timeFunction.apply(element) < System.currentTimeMillis()) {
                                softReferenceMap.remove(key);
                            }
                        }
                    }
                    long monitorTime = System.currentTimeMillis() - start;
                    long cacheTime = Math.max(lifeTimeMs, idleTimeMs);
                    long delay = monitorTime < cacheTime ? cacheTime - monitorTime : MIN_MONITOR_SLEEP_TIME;
                    Thread.sleep(delay);
                    list.clear();
                }
            } catch (InterruptedException e) {
                System.out.println("Monitor is interrupted");
            }
        }
    }

    @Override
    public void put(K key, V value) {
        CacheElement<K, V> element = new CacheElement<>(key, value);
        // effectively remove random element if max size is reached
        if (softReferenceMap.size() == maxElements) {
            softReferenceMap.keySet()
                    .stream()
                    .skip((int) (softReferenceMap.size() * Math.random()))
                    .findFirst()
                    .ifPresent(softReferenceMap::remove);
        }
        // map allows null as a key
        softReferenceMap.put(key, new SoftReference<>(element));
    }

    @Override
    public V get(K key) {
        SoftReference<CacheElement<K, V>> elementSoftReference = softReferenceMap.get(key);
        CacheElement<K, V> element = null;
        if (elementSoftReference != null && (element = elementSoftReference.get()) != null) {
            element.refreshAccessTime();
            hitCount++;
        } else {
            // remove record from cache, because reference doesn't exist anymore
            softReferenceMap.remove(key);
            missCount++;
            return null;
        }
        return element.value;
    }

    @Override
    public void destroy() {
        monitor.interrupt();
    }

    @Override
    public long getHitCount() {
        return this.hitCount;
    }

    @Override
    public long getMissCount() {
        return this.missCount;
    }
}
