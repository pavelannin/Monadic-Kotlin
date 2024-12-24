package io.github.pavelannin.monadic.result

import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * ###### EN:
 * Calls the [block] function if [Ok].
 *
 * ###### RU:
 * Вызывает функцию [block] если [Ok].
 *
 * ###### Example:
 * ```
 * Ok(1).onOk { print("Hello") }  // Log: Hello
 * Error(1).onOk { print("Hello") }
 * ```
 */
public inline fun <Ok, Error> Result<Ok, Error>.onOk(block: (Ok) -> Unit): Result<Ok, Error> {
    contract { callsInPlace(block, InvocationKind.AT_MOST_ONCE) }
    if (isOk()) block(ok)
    return this
}

/**
 * ###### EN:
 * Calls the [block] function if [Error].
 *
 * ###### RU:
 * Вызывает функцию [block] если [Error].
 *
 * ###### Example:
 * ```
 * OK(1).onError { print("Hello") }
 * Error(1).onError { print("Hello") } // Log: Hello
 * ```
 */
public inline fun <Ok, Error> Result<Ok, Error>.onError(block: (Error) -> Unit): Result<Ok, Error> {
    contract { callsInPlace(block, InvocationKind.AT_MOST_ONCE) }
    if (isError()) block(error)
    return this
}
