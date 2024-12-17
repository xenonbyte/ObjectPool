package com.longcin.pool

/**
 * Object Factory
 *
 * @author xubo
 */
interface ObjectFactory<T : Reusable> {

    /**
     * Create a new object of type [T] with the given arguments.
     *
     * @param args Arguments used to create the object.
     * @return A newly created instance of [T].
     */
    fun create(vararg args: Any?): T

    /**
     * Reuse the given object and perform necessary reinitialization.
     *
     * @param instance The instance to reuse.
     * @param args Arguments for modifying the reused object data.
     */
    fun reuse(instance: T, vararg args: Any?)
}