# LRU Cache Kata

## Description

This kata involves implementing a Least Recently Used (LRU) cache, a data structure that combines a hash table and a linked list to provide fast access while maintaining a usage-based eviction policy

### What is an LRU Cache?

An LRU (Least Recently Used) cache is a type of cache that removes the least recently used items when the cache reaches its capacity limit. It's commonly used in memory management, database caching, and web applications to optimize performance by keeping frequently accessed data readily available.

### Requirements

The LRU Cache implementation must satisfy the following constraints:

- All operations must have $O(1)$ average time complexity
- The cache has a fixed capacity set at construction time
- The cache is designed for single-threaded use (no synchronization needed)
- Implementation may use any Java SE classes but no external libraries
- When the cache is full, adding a new item evicts the least recently used item
- Accessing an item (get or put) makes it the most recently used

### API

The LRU Cache provides the following operations:

- `LruCache.ofCapacity(int capacity)`: Creates a new LRU cache with the specified capacity
- `Optional<V> get(K key)`: Returns the value associated with the key, or empty if not present
- `void put(K key, V value)`: Associates the value with the key, evicting the least recently used item if necessary

### Warning
 the test suite is not exhaustive, so feel free to add more tests if you feel like it.

## Technology Stack

| Component | Version |
|-----------|---------|
| Java LTS  | 21 |
| JUnit 5   | 5.12.2 |
| AssertJ   | 3.27.3 |

## Running Tests

```bash
./mvnw test
```
