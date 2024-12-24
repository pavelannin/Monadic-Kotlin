package io.github.pavelannin.monadic.result

import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * ###### EN:
 * Returns value if it is [Result.Ok], or `null` otherwise.
 *
 * ###### RU:
 * Возвращает значение, если [Result.Ok], в противном случае `null`.
 *
 * ###### Example:
 * ```
 * Ok(1).okOrNull() // Result: 1
 * Error(1).okOrNull() // Result: null
 * ```
 */
public fun <Ok, Error> Result<Ok, Error>.okOrNull(): Ok? {
    return if (this.isOk()) ok else null
}

/**
 * ###### EN:
 * Returns value if it is [Result.Error], or `null` otherwise.
 *
 * ###### RU:
 * Возвращает значение, если [Result.Error], в противном случае `null`.
 *
 * ###### Example:
 * ```
 * OK(1).errorOrNull() // Result: null
 * Error(1).errorOrNull() // Result: 1
 * ```
 */
public fun <Ok, Error> Result<Ok, Error>.errorOrNull(): Error? {
    return if (this.isError()) error else null
}

/**
 * ###### EN:
 * Returns value if it is [Result.Ok], or [default] otherwise.
 *
 * ###### RU:
 * Возвращает значение, если [Result.Ok], в противном случае [default].
 *
 * ###### Example:
 * ```
 * Ok(1).okOr(2) // Result: 1
 * Error(1).okOr(2) // Result: 2
 * ```
 */
public fun <Ok, Error> Result<Ok, Error>.okOr(default: Ok): Ok {
    return okOrNull() ?: default
}

/**
 * ###### EN:
 * Returns value if it is [Result.Error], or [default] otherwise.
 *
 * ###### RU:
 * Возвращает значение, если [Result.Error], в противном случае [default].
 *
 * ###### Example:
 * ```
 * OK(1).errorOr(2) // Result: 2
 * Error(1).errorOr(2) // Result: 1
 * ```
 */
public fun <Ok, Error> Result<Ok, Error>.errorOr(default: Error): Error {
    return errorOrNull() ?: default
}

/**
 * ###### EN:
 * Returns the value if [Result.Ok], otherwise applies the [transform] function
 *
 * ###### RU:
 * Возвращает значение, если [Result.Ok], в противном случае применяет функцию [transform].
 *
 * ###### Example:
 * ```
 * OK(1).okOrElse { 2 } // Result: 1
 * Error(1).ookOrElse { 2 } // Result: 2
 * ```
 */
public inline fun <Ok, Error> Result<Ok, Error>.okOrElse(transform: (Error) -> Ok): Ok {
    contract { callsInPlace(transform, InvocationKind.AT_MOST_ONCE) }
    return if (isOk()) ok else transform(error)
}

/**
 * ###### EN:
 * Returns the value if [Result.Error], otherwise applies the [transform] function
 *
 * ###### RU:
 * Возвращает значение, если [Result.Error], в противном случае применяет функцию [transform].
 *
 * ###### Example:
 * ```
 * OK(1).errorOrElse { 2 } // Result: 2
 * Error(1).errorOrElse { 2 } // Result: 1
 * ```
 */
public inline fun <Ok, Error> Result<Ok, Error>.errorOrElse(transform: (Ok) -> Error): Error {
    contract { callsInPlace(transform, InvocationKind.AT_MOST_ONCE) }
    return if (isError()) error else transform(ok)
}
