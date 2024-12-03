package com.longcin.pool

/**
 * Indicates a reusable object.
 * An instance of a class that implements the [com.longcin.pool.Reusable] interface
 * Can be managed and reused through [com.longcin.pool.ObjectPool].
 *
 * This is usually used in scenarios where objects are created frequently.
 * Reusing object instances can improve performance and memory efficiency.
 *
 * @see com.longcin.pool.ObjectPool
 * @author xubo
 */
interface Reusable {

}

