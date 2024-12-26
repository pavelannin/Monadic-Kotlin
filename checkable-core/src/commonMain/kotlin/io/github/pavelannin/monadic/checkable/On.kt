package io.github.pavelannin.monadic.checkable

import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * ###### EN:
 * Calls the [block] function if [Checkable.Checked].
 *
 * ###### RU:
 * Вызывает функцию [block] если [Checkable.Checked].
 *
 * ###### Example:
 * ```
 * Checked(Unit).onChecked { print("Hello") } // Log: Hello
 * Unchecked(Unit).onChecked { print("Hello") }
 * ```
 */
public inline fun <Value> Checkable<Value>.onChecked(block: (Value) -> Unit): Checkable<Value> {
    contract { callsInPlace(block, InvocationKind.AT_MOST_ONCE) }
    if (this.isChecked()) block(checkable)
    return this
}

/**
 * ###### EN:
 * Calls the [block] function if [Checkable.Unchecked].
 *
 * ###### RU:
 * Вызывает функцию [block] если [Checkable.Unchecked].
 *
 * ###### Example:
 * ```
 * Checked(Unit).onUnchecked { print("Hello") }
 * Unchecked(Unit).onUnchecked { print("Hello") } // Log: Hello
 * ```
 */
public inline fun <Value> Checkable<Value>.onUnchecked(block: (Value) -> Unit): Checkable<Value> {
    contract { callsInPlace(block, InvocationKind.AT_MOST_ONCE) }
    if (!this.isChecked()) block(checkable)
    return this
}
