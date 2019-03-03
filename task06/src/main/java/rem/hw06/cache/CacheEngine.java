package rem.hw06.cache;

public interface CacheEngine<K, V> {
    void put(CacheElement<K, V> element);
    CacheElement<K, V> get(K key);
    void destroy();
    long getHitCount();
    long getMissCount();
}
