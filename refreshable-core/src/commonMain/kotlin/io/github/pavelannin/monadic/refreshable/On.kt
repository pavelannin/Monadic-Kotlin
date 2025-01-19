package io.github.pavelannin.monadic.refreshable

import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * ###### EN:
 * Calls the [block] function if [Refreshable.Refreshing].
 *
 * ###### RU:
 * Вызывает функцию [block] если [Refreshable.Refreshing].
 *
 * ###### Example:
 * ```
 * Refreshable(Unit).onRefreshing { print("Hello") } // Log: Hello
 * Refreshable(Unit).onRefreshing { print("Hello") }
 * ```
 */
public inline fun <Value> Refreshable<Value>.onRefreshing(block: (Value) -> Unit): Refreshable<Value> {
    contract { callsInPlace(block, InvocationKind.AT_MOST_ONCE) }
    if (this.isRefreshing()) block(refreshable)
    return this
}

/**
 * ###### EN:
 * Calls the [block] function if [Refreshable.Refreshed].
 *
 * ###### RU:
 * Вызывает функцию [block] если [Refreshable.Refreshed].
 *
 * ###### Example:
 * ```
 * Refreshable(Unit).onRefreshed { print("Hello") }
 * Refreshable(Unit).onRefreshed { print("Hello") } // Log: Hello
 * ```
 */
public inline fun <Value> Refreshable<Value>.onRefreshed(block: (Value) -> Unit): Refreshable<Value> {
    contract { callsInPlace(block, InvocationKind.AT_MOST_ONCE) }
    if (!this.isRefreshing()) block(refreshable)
    return this
}
