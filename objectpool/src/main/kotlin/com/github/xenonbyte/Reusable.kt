package com.github.xenonbyte

/**
 * Indicates a reusable object.
 * An instance of a class that implements the [com.github.xenonbyte.Reusable] interface
 * Can be managed and reused through [com.github.xenonbyte.ObjectPool].
 *
 * This is usually used in scenarios where objects are created frequently.
 * Reusing object instances can improve performance and memory efficiency.
 *
 * @see com.github.xenonbyte.ObjectPool
 * @author xubo
 */
interface Reusable {

}

