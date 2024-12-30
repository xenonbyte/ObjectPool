package com.github.xenonbyte

import java.util.concurrent.locks.ReentrantReadWriteLock

/**
 * A store that holds and manages object pools.
 *
 *
 * @see ObjectPool
 * @author xubo
 */
class ObjectPoolStore {
    private val mPoolMap = mutableMapOf<String, ObjectPool<*>>()
    private val mLock = ReentrantReadWriteLock()

    /**
     * Retrieves an object pool associated with the given key from the store.
     *
     * @param key The key associated with the object pool.
     * @return The object pool for the specified key, or null if the pool does not exist.
     */
    fun <T : Reusable> get(key: String): ObjectPool<T>? {
        mLock.readLock().lock()
        val pool = mPoolMap[key]?.run {
            this as ObjectPool<T>
        }
        mLock.readLock().unlock()
        return pool
    }

    /**
     * Stores the provided object pool in the store, associated with the given key.
     *
     * @param key The key to associate with the object pool.
     * @param pool The object pool to be stored.
     */
    fun <T : Reusable> put(key: String, pool: ObjectPool<T>) {
        mLock.writeLock().lock()
        val oldPool = mPoolMap.put(key, pool)
        oldPool?.clear()
        mLock.writeLock().unlock()
    }

    /**
     * Clears all the object pools stored in the store.
     * This will release any objects that are held in the pools and free up resources.
     */
    fun clear() {
        mLock.writeLock().lock()
        for (pool in mPoolMap.values) {
            pool.clear()
        }
        mPoolMap.clear()
        mLock.writeLock().unlock()
    }
}