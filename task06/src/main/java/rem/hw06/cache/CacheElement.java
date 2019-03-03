package rem.hw06.cache;

public class CacheElement<K, V> {
    private final K key;
    private final V value;
    private final long creationTime;
    private long lastAccessTime;

    public CacheElement(K key, V value) {
        this.key = key;
        this.value = value;
        this.creationTime = this.lastAccessTime = getCurrentTime();
    }

    protected long getCurrentTime() {
        return System.currentTimeMillis();
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
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
