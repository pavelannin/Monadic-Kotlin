package io.github.pavelannin.monadic.result

import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * ###### Signature:
 * `Result<Ok, Error>` -> `Result<Out, Error>`
 *
 * ###### EN:
 * Returns [result] if [Result.Ok].
 *
 * ###### RU:
 * Возвращает [result] если [Result.Ok].
 *
 * ###### Example:
 * ```
 * Ok(1).and(Ok("bar")) // Result: Ok("bar")
 * Ok(1).and(Error("zoo")) // Result: Error("zoo")
 *
 * Error(1).and(Ok("bar")) // Result: Error(1)
 * Error(1).and(Error("zoo")) // Result: Error(1)
 * ```
 */
public fun <Ok, Error, Out> Result<Ok, Error>.and(result: Result<Out, Error>): Result<Out, Error> {
    return flatMap { result }
}

/**
 * ###### Signature:
 * `Result<Ok, Error>` -> `Result<Out, Error>`
 *
 * ###### EN:
 * Transforms to `Result<Out, Error>` by applying the [transform] function if [Result.Ok].
 *
 * ###### RU:
 * Трансформирует в `Result<Out, Error>`, применяя функцию [transform] если [Result.Ok].
 *
 * ###### Example:
 * ```
 * Ok(1).flatMap { Ok("bar") } // Result: Ok("bar")
 * Ok(1).flatMap { Error("zoo") } // Result: Error("zoo")
 *
 * Error(1).flatMap { Ok("bar") } // Result: Error(1)
 * Error(1).flatMap { Error("zoo") } // Result: Error(1)
 * ```
 */
public inline fun <Ok, Error, Out> Result<Ok, Error>.andThen(transform: (Ok) -> Result<Out, Error>): Result<Out, Error> {
    contract { callsInPlace(transform, InvocationKind.AT_MOST_ONCE) }
    return flatMap(transform)
}
