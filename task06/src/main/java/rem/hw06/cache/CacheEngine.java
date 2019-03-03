package rem.hw06.cache;

public interface CacheEngine<K, V> {
    void put(K key,  V value);
    V get(K key);
    void destroy();
    long getHitCount();
    long getMissCount();
}
