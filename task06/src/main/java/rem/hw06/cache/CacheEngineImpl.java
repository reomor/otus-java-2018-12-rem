package rem.hw06.cache;

import java.lang.ref.SoftReference;
import java.util.*;
import java.util.function.Function;

public class CacheEngineImpl<K, V> implements CacheEngine<K, V> {
    private final int maxElements;
    private final long lifeTimeMs;
    private final long idleTimeMs;
    private final boolean isEternal;

    private final Map<K, SoftReference<CacheElement<K, V>>> softReferenceMap = new HashMap<>();
    private final Timer timer = new Timer();

    public CacheEngineImpl(int maxElements, long lifeTimeMs, long idleTimeMs, boolean isEternal) {
        this.maxElements = maxElements;
        this.lifeTimeMs = lifeTimeMs > 0 ? lifeTimeMs : 0;
        this.idleTimeMs = idleTimeMs > 0 ? idleTimeMs : 0;
        this.isEternal = lifeTimeMs == 0 && idleTimeMs == 0 || isEternal;
    }

    @Override
    public void put(CacheElement<K, V> element) {
        // effectively remove random element if max size is reached
        if (softReferenceMap.size() == maxElements) {
            softReferenceMap.keySet()
                    .stream()
                    .skip((int) (softReferenceMap.size() * Math.random()))
                    .findFirst()
                    .ifPresent(softReferenceMap::remove);
        }
        // map allows null as a key
        K key = element.getKey();
        softReferenceMap.put(key, new SoftReference<>(element));

        if (!isEternal) {
            if (lifeTimeMs != 0) {
                TimerTask lifeTimerTask = getTimerTask(key, lifeElement -> lifeElement.getCreationTime() + lifeTimeMs);
                timer.schedule(lifeTimerTask, lifeTimeMs);
            }
            if (idleTimeMs != 0) {
                TimerTask idleTimerTask = getTimerTask(key, idleElement -> idleElement.getLastAccessTime() + idleTimeMs);
                timer.schedule(idleTimerTask, idleTimeMs, idleTimeMs);
            }
        }
    }

    @Override
    public CacheElement<K, V> get(K key) {
        SoftReference<CacheElement<K, V>> cacheElementSoftReference = softReferenceMap.get(key);
        CacheElement<K, V> element = null;
        if((element = cacheElementSoftReference.get()) != null) {
            element.refreshAccessTime();
        }
        return cacheElementSoftReference.get();
    }

    private TimerTask getTimerTask(final K key, Function<CacheElement<K, V>, Long> timeFunction) {
        return new TimerTask() {
            @Override
            public void run() {
                CacheElement<K, V> cacheElement = softReferenceMap.get(key).get();
                if (cacheElement == null || (timeFunction.apply(cacheElement) < System.currentTimeMillis())) {
                    softReferenceMap.remove(key);
                    this.cancel();
                }
            }
        };
    }
}
