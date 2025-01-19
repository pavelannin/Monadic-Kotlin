package io.github.pavelannin.monadic.optional

import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * ###### EN:
 * Calls the [block] function if [Optional.Some].
 *
 * ###### RU:
 * Вызывает функцию [block] если [Optional.Some].
 *
 * ###### Example:
 * ```
 * Some(Unit).onSome { print("Hello") } // Log: Hello
 * None.onSome { print("Hello") }
 * ```
 */
public inline fun <Value> Optional<Value>.onSome(block: (Value) -> Unit): Optional<Value> {
    contract { callsInPlace(block, InvocationKind.AT_MOST_ONCE) }
    if (this.isSome()) block(some)
    return this
}

/**
 * ###### EN:
 * Calls the [block] function if [Optional.None].
 *
 * ###### RU:
 * Вызывает функцию [block] если [Optional.None].
 *
 * ###### Example:
 * ```
 * Some(Unit).onNone { print("Hello") }
 * None.onNone { print("Hello") } // Log: Hello
 * ```
 */
public inline fun <Value> Optional<Value>.onNone(block: () -> Unit): Optional<Value> {
    contract { callsInPlace(block, InvocationKind.AT_MOST_ONCE) }
    if (this.isNone()) block()
    return this
}
