package rem.hw06.cache;

import java.lang.ref.SoftReference;
import java.util.*;
import java.util.function.Function;

public class CacheEngineOnTimers<K, V> implements CacheEngine<K, V> {
    private final int maxElements;
    private final long lifeTimeMs;
    private final long idleTimeMs;
    private final boolean isEternal;

    private final Map<K, SoftReference<CacheElement<K, V>>> softReferenceMap = new HashMap<>();
    private final Timer timer = new Timer();

    private long hitCount;
    private long missCount;

    public CacheEngineOnTimers(int maxElements, long lifeTimeMs, long idleTimeMs) {
        this.maxElements = maxElements;
        this.lifeTimeMs = lifeTimeMs > 0 ? lifeTimeMs : 0;
        this.idleTimeMs = lifeTimeMs > 0 ? 0 : idleTimeMs > 0 ? idleTimeMs : 0;
        this.isEternal = lifeTimeMs == 0 && idleTimeMs == 0;
        this.hitCount = 0;
        this.missCount = 0;
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

    @Override
    public void put(K key,  V value) {
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

        if (!isEternal) {
            if (lifeTimeMs != 0) {
                TimerTask lifeTimerTask = getTimerTask(key, lifeElement -> lifeElement.getCreationTime() + lifeTimeMs);
                timer.schedule(lifeTimerTask, lifeTimeMs);
            } else if (idleTimeMs != 0) {
                TimerTask idleTimerTask = getTimerTask(key, idleElement -> idleElement.getLastAccessTime() + idleTimeMs);
                timer.schedule(idleTimerTask, idleTimeMs, idleTimeMs);
            }
        }
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
        timer.cancel();
    }

    @Override
    public long getHitCount() {
        return this.hitCount;
    }

    @Override
    public long getMissCount() {
        return this.missCount;
    }

    private TimerTask getTimerTask(final K key, Function<CacheElement<K, V>, Long> timeFunction) {
        return new TimerTask() {
            @Override
            public void run() {
                SoftReference<CacheElement<K, V>> elementSoftReference = softReferenceMap.get(key);
                CacheElement<K, V> element = null;
                if (elementSoftReference == null ||
                        (element = softReferenceMap.get(key).get()) == null ||
                        (timeFunction.apply(element) < System.currentTimeMillis())) {
                    softReferenceMap.remove(key);
                    this.cancel();
                }
            }
        };
    }
}
