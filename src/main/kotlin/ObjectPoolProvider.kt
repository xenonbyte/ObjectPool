package com.github.xenonbyte

/**
 * Provides the program class for ObjectPool
 *
 * @param Owner The owner of the pool storage, responsible for storing and retrieving the object pool.
 *
 * @see ObjectPoolStore
 * @see ObjectPool
 * @see ObjectFactory
 * @author xubo
 */
class ObjectPoolProvider private constructor(
    private val owner: ObjectPoolStoreOwner
) {

    companion object {
        private const val DEFAULT_POOL_MAX_SIZE = 5
        private const val DEFAULT_POOL_KEY_PREFIX = "xenon_byte.pool.DEFAULT_KEY";
        private val GLOBAL_POOL_STORE_OWNER = object : ObjectPoolStoreOwner {
            override val store: ObjectPoolStore
                get() = ObjectPoolStore()

        }

        /**
         * create an instance of [ObjectPoolProvider].
         * @param owner The owner that holds the pool store.
         * @return An instance of [ObjectPoolProvider].
         */
        @JvmStatic
        fun create(owner: ObjectPoolStoreOwner): ObjectPoolProvider {
            return ObjectPoolProvider(owner)
        }

        /**
         * Create a global instance of [ObjectPoolProvider].
         * @param Owner The owner of the pool storage.
         * @return An instance of [ObjectPoolProvider].
         */
        @JvmStatic
        fun global(): ObjectPoolProvider {
            return create(GLOBAL_POOL_STORE_OWNER)
        }
    }

    /**
     * Retrieves an object pool for a specific object type. If the pool does not exist in the store,
     * it creates a new pool using the provided factory and stores it.
     *
     * @param clazz The class of the object type [T] that the pool manages.
     * @param factory The factory used to create objects of type [T].
     * @return The object pool for the given object type.
     */
    fun <T : Reusable> get(
        clazz: Class<T>,
        factory: ObjectFactory<T>
    ): ObjectPool<T> {
        return get(clazz, factory, DEFAULT_POOL_MAX_SIZE)
    }

    /**
     * Retrieves an object pool for a specific object type. If the pool does not exist in the store,
     * it creates a new pool using the provided factory and stores it.
     *
     * @param clazz The class of the object type [T] that the pool manages.
     * @param factory The factory used to create objects of type [T].
     * @param maxPoolSize The maximum size of the pool. Defaults to [ObjectPoolProvider.DEFAULT_POOL_MAX_SIZE].
     * @return The object pool for the given object type.
     */
    fun <T : Reusable> get(
        clazz: Class<T>,
        factory: ObjectFactory<T>,
        maxPoolSize: Int = DEFAULT_POOL_MAX_SIZE
    ): ObjectPool<T> {
        val key: String = DEFAULT_POOL_KEY_PREFIX + ":" + clazz.getCanonicalName()
        val pool = owner.store.get(key) ?: run {
            val newPool = createObjectPool(factory, maxPoolSize)
            owner.store.put(key, newPool)
            newPool
        }
        return pool
    }

    /**
     * Create a new object pool using a factory.
     *
     * @param factory The factory used to create objects of type [T].
     * @param maxPoolSize The maximum size of the pool.
     * @return The newly created object pool.
     */
    private fun <T : Reusable> createObjectPool(factory: ObjectFactory<T>, maxPoolSize: Int): ObjectPool<T> {
        return ObjectPool(factory, maxPoolSize);
    }
}