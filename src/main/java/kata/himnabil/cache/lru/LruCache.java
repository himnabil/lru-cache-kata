package kata.himnabil.cache.lru;

import java.util.Optional;

/**
 * LRU (Least Recently Used) cache interface.
 * 
 * <p>This interface defines an LRU cache implementation with the following constraints:
 * <ul>
 *     <li>All operations must have O(1) average time complexity</li>
 *     <li>Capacity is fixed at construction time through {@link #ofCapacity(int)}</li>
 *     <li>The cache is designed for single-threaded use (no synchronization needed)</li>
 *     <li>Implementation may use any Java SE classes but no external libraries</li>
 *     <li>When the cache is full, adding a new item evicts the least recently used item</li>
 *     <li>Accessing an item (get or put) makes it the most recently used</li>
 * </ul>
 *
 * @param <K> the type of keys maintained by this cache
 * @param <V> the type of mapped values
 */
public interface LruCache<K, V> {

    /**
     * Creates a new LRU cache with the specified capacity.
     * 
     * @param capacity the maximum number of elements the cache can hold
     * @param <K> the type of keys maintained by this cache
     * @param <V> the type of mapped values
     * @return a new LRU cache instance with the specified capacity
     * @throws IllegalArgumentException if capacity is not positive
     */
    static <K, V> LruCache<K, V> ofCapacity(int capacity) {
        // TODO: create LRU cache with capacity of 'capacity' elements
        return null;
    }

    /**
     * Returns the value associated with the specified key in this cache, 
     * or an empty Optional if the key is not present.
     * 
     * <p>This operation will update the key's position in the LRU order 
     * if the key is found in the cache.
     *
     * @param key the key whose associated value is to be returned
     * @return an Optional containing the value associated with the key,
     *         or an empty Optional if the key is not present in the cache
     */
    Optional<V> get(K key);

    /**
     * Associates the specified value with the specified key in this cache.
     * 
     * <p>If the cache already contains the key, the old value is replaced by
     * the new value, and the key becomes the most recently used.
     * 
     * <p>If the cache does not contain the key, the key-value pair is added
     * to the cache. If the cache is at capacity, the least recently used entry
     * is removed before the new key-value pair is added.
     *
     * @param key the key with which the specified value is to be associated
     * @param value the value to be associated with the specified key
     */
    void put(K key, V value);
}
