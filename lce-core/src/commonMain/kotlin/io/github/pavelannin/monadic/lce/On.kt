package io.github.pavelannin.monadic.lce

import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * ###### EN:
 * Calls the [block] function if [LCE.Loading].
 *
 * ###### RU:
 * Вызывает функцию [block] если [LCE.Loading].
 *
 * ###### Example:
 * ```
 * Loading(1).onLoading { print("Hello") } // Log: Hello
 * Content(1).onLoading { print("Hello") }
 * Error(1).onLoading { print("Hello") }
 * ```
 */
public inline fun <L, C, E> LCE<L, C, E>.onLoading(block: (L) -> Unit): LCE<L, C, E> {
    contract { callsInPlace(block, InvocationKind.AT_MOST_ONCE) }
    if (this.isLoading()) block(loading)
    return this
}

/**
 * ###### EN:
 * Calls the [block] function if [LCE.Content].
 *
 * ###### RU:
 * Вызывает функцию [block] если [LCE.Content].
 *
 * ###### Example:
 * ```
 * Loading(1).onContent { print("Hello") }
 * Content(1).onContent { print("Hello") } // Log: Hello
 * Error(1).onContent { print("Hello") }
 * ```
 */
public inline fun <L, C, E> LCE<L, C, E>.onContent(block: (C) -> Unit): LCE<L, C, E> {
    contract { callsInPlace(block, InvocationKind.AT_MOST_ONCE) }
    if (this.isContent()) block(content)
    return this
}

/**
 * ###### EN:
 * Calls the [block] function if [LCE.Error].
 *
 * ###### RU:
 * Вызывает функцию [block] если [LCE.Error].
 *
 * ###### Example:
 * ```
 * Loading(1).onError { print("Hello") }
 * Content(1).onError { print("Hello") }
 * Error(1).onError { print("Hello") } // Log: Hello
 * ```
 */
public inline fun <L, C, E> LCE<L, C, E>.onError(block: (E) -> Unit): LCE<L, C, E> {
    contract { callsInPlace(block, InvocationKind.AT_MOST_ONCE) }
    if (this.isError()) block(error)
    return this
}
