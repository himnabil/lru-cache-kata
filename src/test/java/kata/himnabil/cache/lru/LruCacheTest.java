package kata.himnabil.cache.lru;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class LruCacheTest {

    @Nested
    @DisplayName("Initialization tests")
    class InitializationTests {
        @Test
        @DisplayName("Should create cache with specified capacity")
        void shouldCreateCacheWithSpecifiedCapacity() {
            // This test will rely on functional behavior since we can't directly test capacity
            LruCache<String, Integer> cache = LruCache.ofCapacity(2);
            
            cache.put("first", 1);
            cache.put("second", 2);
            
            assertThat(cache.get("first")).contains(1);
            assertThat(cache.get("second")).contains(2);
        }
        
        @ParameterizedTest
        @ValueSource(ints = {-1, 0})
        @DisplayName("Should throw exception for non-positive capacity")
        void shouldThrowExceptionForNonPositiveCapacity(int invalidCapacity) {
            assertThatThrownBy(() -> LruCache.ofCapacity(invalidCapacity))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
    
    @Nested
    @DisplayName("Basic operations tests")
    class BasicOperationsTests {
        @Test
        @DisplayName("Should return empty optional for non-existent key")
        void shouldReturnEmptyOptionalForNonExistentKey() {
            LruCache<String, Integer> cache = LruCache.ofCapacity(5);
            
            Optional<Integer> result = cache.get("non-existent");
            
            assertThat(result).isEmpty();
        }
        
        @Test
        @DisplayName("Should store and retrieve value")
        void shouldStoreAndRetrieveValue() {
            LruCache<String, Integer> cache = LruCache.ofCapacity(5);
            
            cache.put("key", 42);
            Optional<Integer> result = cache.get("key");
            
            assertThat(result).contains(42);
        }
        
        @Test
        @DisplayName("Should replace existing value")
        void shouldReplaceExistingValue() {
            LruCache<String, Integer> cache = LruCache.ofCapacity(5);
            
            cache.put("key", 1);
            cache.put("key", 2);
            
            assertThat(cache.get("key")).contains(2);
        }
        
        @Test
        @DisplayName("Should handle null values")
        void shouldHandleNullValues() {
            LruCache<String, String> cache = LruCache.ofCapacity(5);
            
            cache.put("key", null);
            
            assertThat(cache.get("key")).isEmpty();
        }
    }
    
    @Nested
    @DisplayName("LRU eviction tests")
    class LruEvictionTests {
        @Test
        @DisplayName("Should evict least recently used item when cache is full")
        void shouldEvictLeastRecentlyUsedItemWhenCacheIsFull() {
            LruCache<String, Integer> cache = LruCache.ofCapacity(2);
            
            cache.put("first", 1);
            cache.put("second", 2);
            cache.put("third", 3);  // This should evict "first"
            
            assertThat(cache.get("first")).isEmpty();
            assertThat(cache.get("second")).contains(2);
            assertThat(cache.get("third")).contains(3);
        }
        
        @Test
        @DisplayName("Should update LRU order when getting existing item")
        void shouldUpdateLruOrderWhenGettingExistingItem() {
            LruCache<String, Integer> cache = LruCache.ofCapacity(2);
            
            cache.put("first", 1);
            cache.put("second", 2);
            
            // Access "first" to make it most recently used
            cache.get("first");
            
            // Add a new item which should evict "second" instead of "first"
            cache.put("third", 3);
            
            assertThat(cache.get("first")).contains(1);
            assertThat(cache.get("second")).isEmpty();
            assertThat(cache.get("third")).contains(3);
        }
        
        @Test
        @DisplayName("Should update LRU order when putting existing item")
        void shouldUpdateLruOrderWhenPuttingExistingItem() {
            LruCache<String, Integer> cache = LruCache.ofCapacity(2);
            
            cache.put("first", 1);
            cache.put("second", 2);
            
            // Update "first" to make it most recently used
            cache.put("first", 11);
            
            // Add a new item which should evict "second" instead of "first"
            cache.put("third", 3);
            
            assertThat(cache.get("first")).contains(11);
            assertThat(cache.get("second")).isEmpty();
            assertThat(cache.get("third")).contains(3);
        }
    }
    
    @Nested
    @DisplayName("Complex scenario tests")
    class ComplexScenarioTests {
        @Test
        @DisplayName("Should handle complex access pattern correctly")
        void shouldHandleComplexAccessPatternCorrectly() {
            LruCache<String, Integer> cache = LruCache.ofCapacity(3);
            
            // Add initial items
            cache.put("A", 1);
            cache.put("B", 2);
            cache.put("C", 3);
            
            // Access B, making C the least recently used
            cache.get("B");
            
            // Add D, which should evict C
            cache.put("D", 4);
            
            assertThat(cache.get("A")).contains(1);
            assertThat(cache.get("B")).contains(2);
            assertThat(cache.get("C")).isEmpty();
            assertThat(cache.get("D")).contains(4);
            
            // Access A, making B the least recently used
            cache.get("A");
            
            // Add E, which should evict B
            cache.put("E", 5);
            
            assertThat(cache.get("A")).contains(1);
            assertThat(cache.get("B")).isEmpty();
            assertThat(cache.get("D")).contains(4);
            assertThat(cache.get("E")).contains(5);
        }
        
        @Test
        @DisplayName("Should handle high number of operations correctly")
        void shouldHandleHighNumberOfOperationsCorrectly() {
            LruCache<Integer, Integer> cache = LruCache.ofCapacity(10);
            
            // Insert 10 items
            for (int i = 0; i < 10; i++) {
                cache.put(i, i);
            }
            
            // Access items in reverse order
            for (int i = 9; i >= 0; i--) {
                assertThat(cache.get(i)).contains(i);
            }
            
            // Insert 5 new items
            for (int i = 10; i < 15; i++) {
                cache.put(i, i);
            }
            
            // The first 5 items should be evicted
            for (int i = 0; i < 5; i++) {
                assertThat(cache.get(i)).isEmpty();
            }
            
            // The last 10 items should still be in the cache
            for (int i = 5; i < 15; i++) {
                assertThat(cache.get(i)).contains(i);
            }
        }
    }
}
