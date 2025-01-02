package com.github.xenonbyte

/**
 * A generic object pool that can manage and reuse instances of objects.
 *
 * This class is typically used in scenarios where objects are frequently created and destroyed.
 * Reusing object instances can help improve performance and memory efficiency, especially in resource-constrained environments.
 *
 * It allows for the efficient reuse of objects by maintaining a pool of instances that can be reused rather than creating new ones each time.
 * Objects that are reusable should implement the [com.github.xenonbyte.Reusable] interface.
 *
 * The pool has a maximum size (controlled by [maxPoolSize]),If the pool exceeds the maximum size, it will discard objects directly instead of saving them back.
 *
 * @param T The type of the objects that can be managed by this pool. Must be a subtype of [com.github.xenonbyte.Reusable].
 * @param maxPoolSize The maximum number of objects the pool can hold. Default is [ObjectPoolProvider.DEFAULT_POOL_MAX_SIZE].
 *
 * @see com.github.xenonbyte.Reusable
 * @author xubo
 */
class ObjectPool<T : Reusable> internal constructor(
    private val objectFactory: ObjectFactory<T>,
    private val maxPoolSize: Int
) {
    private var mPool: ObjectWrapper<T>? = null
    private var mPoolSize: Int = 0
    private val mLock = Any()

    /**
     * Obtain an object from the pool. If the pool has a reusable object, it will be returned and reused.
     * Otherwise, a new object will be created.
     *
     * @param args Arguments used for reusing or creating a new object.
     * @return A reusable or newly created object of type [T].
     */
    fun obtain(vararg args: Any?): T {
        synchronized(mLock) {
            val reuseObject: ObjectWrapper<T>? = mPool?.also {
                val next = it.mNext
                mPool = next
                mPoolSize--
            }

            return reuseObject?.value?.also {
                objectFactory.reuse(it, *args)
            } ?: objectFactory.create(*args)
        }
    }

    /**
     * Recycle the given object back into the pool. If the pool size is less than the maximum size,
     * the object will be added to the pool. Otherwise, it will be discarded.
     *
     * @param recycleObject The object to be recycled back into the pool.
     */
    fun recycle(recycleObject: T) {
        synchronized(mLock) {
            if (mPoolSize < maxPoolSize) {
                val objectWrapper = ObjectWrapper(recycleObject)
                objectWrapper.mNext = mPool
                mPool = objectWrapper
                mPoolSize++
            }
        }
    }

    fun clear() {
        synchronized(mLock) {
            mPoolSize = 0
            mPool = null
        }
    }

    private class ObjectWrapper<T : Reusable>(val value: T) {
        var mNext: ObjectWrapper<T>? = null
    }
}
