package com.github.xenonbyte


/**
 * [ObjectPoolStore] Owner
 * Implementations of this interface provide a repository that can store and retrieve object pools.
 *
 * @see ObjectPoolStore
 * @author xubo
 */
interface ObjectPoolStoreOwner {
    /**
     * The owned [ObjectPoolStore]
     */
    val store: ObjectPoolStore
}