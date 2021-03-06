package rem.hw06;

import rem.hw06.cache.CacheEngine;
import rem.hw06.cache.CacheEngineOnMonitor;
import rem.hw06.cache.CacheEngineOnTimers;

import java.util.Random;

public class Application {
    public static void main(String[] args) {
        final int size = 64;
        CacheEngine<Integer, Integer[]> cache = new CacheEngineOnMonitor<>(size, 1000, 0);
        for (int i = 0; i < size; i++) {
            cache.put(i, new Integer[256 * 1024]);
        }
        for (int i = 0; i < size; i++) {
            Integer[] cacheElement = cache.get(i);
            String format = String.format("key is: %s, value is: %d", i, cacheElement != null ? cacheElement.length : -1);
            System.out.println(format);
        }
        System.out.println("Cache hits: " + cache.getHitCount());
        System.out.println("Cache misses: " + cache.getMissCount());
        Random random = new Random();
        int iterationLimit = 1000;
        for (int i = 0; i < iterationLimit; i++) {
            System.out.println("Iteration: " + i + "/" + iterationLimit);
            int nextInt = random.nextInt(size);
            cache.get(nextInt);
            try {
                Thread.sleep(random.nextInt(250));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            nextInt = random.nextInt(size);
            cache.put(nextInt, new Integer[256 * 1024]);
        }
        System.out.println("Cache hits: " + cache.getHitCount());
        System.out.println("Cache misses: " + cache.getMissCount());
        cache.destroy();
    }
}
