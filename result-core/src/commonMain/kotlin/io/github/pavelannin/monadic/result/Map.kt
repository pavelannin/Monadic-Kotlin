package io.github.pavelannin.monadic.result

import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

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
 * Ok(1).map { "foo" } // Result: Ok("foo")
 * Error(1).map { "foo" }  // Result: Error(1)
 * ```
 */
public inline fun <Ok, Error, Out> Result<Ok, Error>.map(transform: (Ok) -> Out): Result<Out, Error> {
    contract { callsInPlace(transform, InvocationKind.AT_MOST_ONCE) }
    return if (isOk()) Result.Ok(transform(ok)) else this
}

/**
 *  ###### Signature:
 * `Result<Ok, Error>` -> `Result<Ok, Out>`
 *
 * ###### EN:
 * Transforms to `Result<Ok, Out>` by applying the [transform] function if [Result.Error].
 *
 * ###### RU:
 * Трансформирует в `Result<Ok, Out>`, применяя функцию [transform] если [Result.Error].
 *
 * ###### Example:
 * ```
 * OK(1).mapError { "foo" } // Result: Ok(1)
 * Error(1).mapError { "foo" }  // Result: Error("foo")
 * ```
 */
public inline fun <Ok, Error, Out> Result<Ok, Error>.mapError(transform: (Error) -> Out): Result<Ok, Out> {
    contract { callsInPlace(transform, InvocationKind.AT_MOST_ONCE) }
    return if (isError()) Result.Error(transform(error)) else this
}

/**
 *  ###### Signature:
 * `Result<Ok, Error>` -> `Result<OkOut, ErrorOut>`
 *
 * ###### EN:
 * Transforms to `Result<OkOut, ErrorOut>` by applying the [okTransform] function if [Result.Ok], or the [errorTransform]
 * function if [Result.Error].
 *
 * ###### RU:
 * Трансформирует в `Result<OkOut, ErrorOut>`, применяя функцию [okTransform] если [Result.Ok], или
 * функцию [errorTransform] если [Result.Error].
 *
 * ###### Example:
 * ```
 * Ok(1).bimap(
 *   okTransform = { "foo" },
 *   errorTransform = { "bar" },
 * ) // Result: Ok("foo")
 *
 * Error(1).bimap(
 *   okTransform = { "foo" },
 *   errorTransform = { "bar" },
 * ) // Result: Error("bar")
 * ```
 */
public inline fun <Ok, Error, OkOut, ErrorOut> Result<Ok, Error>.bimap(
    okTransform: (Ok) -> OkOut,
    errorTransform: (Error) -> ErrorOut,
): Result<OkOut, ErrorOut> {
    contract {
        callsInPlace(okTransform, InvocationKind.AT_MOST_ONCE)
        callsInPlace(errorTransform, InvocationKind.AT_MOST_ONCE)
    }
    return if (isOk()) Result.Ok(okTransform(ok)) else Result.Error(errorTransform(error))
}


/**
 * ###### Signature:
 * `Result<Ok, Error>` -> `Value`
 *
 * ###### EN:
 * Transforms to the value [Value]. Applies the [okTransform] function if [Result.Ok],
 * or the [errorTransform] function if [Result.Error].
 *
 * ###### RU:
 * Трансформирует в значение [Value]. Применяет функцию [okTransform] если [Result.Ok],
 * или функцию [errorTransform] если [Result.Error].
 *
 * ###### Example:
 * ```
 * Ok(1).fold(
 *   okTransform = { "foo" },
 *   errorTransform = { "bar" },
 * ) // Result: "foo"
 *
 * Error(1).fold(
 *   okTransform = { "foo" },
 *   errorTransform = { "bar" },
 * ) // Result: "bar"
 * ```
 */
public inline fun <Ok, Error, Value> Result<Ok, Error>.fold(
    okTransform: (Ok) -> Value,
    errorTransform: (Error) -> Value,
): Value {
    contract {
        callsInPlace(okTransform, InvocationKind.AT_MOST_ONCE)
        callsInPlace(errorTransform, InvocationKind.AT_MOST_ONCE)
    }
    return if (isOk()) okTransform(ok) else errorTransform(error)
}

/**
 * ###### Signature:
 * `Result<OK, Error>` -> `Value`
 *
 * ###### EN:
 * Transforms to the value [Value]. Applies the [transform] function if [Result.Ok], otherwise
 * returns the value [initial].
 *
 * ###### RU:
 * Трансформирует в значение [Value]. Применяет функцию [transform] если [Result.Ok],
 * иначе возвращает значение [initial].
 *
 * ###### Example:
 * ```
 * Ok(1).foldOk(
 *   initial = "foo",
 *   transform = { "bar" },
 * ) // Result: "bar"
 *
 * Error(1).foldOk(
 *   initial = "foo",
 *   transform = { "bar" },
 * ) // Result: "foo"
 * ```
 */
public inline fun <Ok, Error, Value> Result<Ok, Error>.foldOk(
    initial: Value,
    transform: (Ok) -> Value,
): Value {
    contract { callsInPlace(transform, InvocationKind.AT_MOST_ONCE) }
    return if (isOk()) transform(ok) else initial
}

/**
 * ###### Signature:
 * `Result<OK, Error>` -> `Value`
 *
 * ###### EN:
 * Transforms to the value [Value]. Applies the [transform] function if [Result.Error], otherwise
 * returns the value [initial].
 *
 * ###### RU:
 * Трансформирует в значение [Value]. Применяет функцию [transform] если [Result.Error],
 * иначе возвращает значение [initial].
 *
 * ###### Example:
 * ```
 * Ok(1).foldError(
 *   initial = "foo",
 *   transform = { "bar" },
 * ) // Result: "foo"
 *
 * Error(1).foldError(
 *   initial = "foo",
 *   transform = { "bar" },
 * ) // Result: "bar"
 * ```
 */
public inline fun <Ok, Error, Value> Result<Ok, Error>.foldError(
    initial: Value,
    transform: (Error) -> Value,
): Value {
    contract { callsInPlace(transform, InvocationKind.AT_MOST_ONCE) }
    return if (isError()) transform(error) else initial
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
public inline fun <Ok, Error, Out> Result<Ok, Error>.flatMap(
    transform: (Ok) -> Result<Out, Error>,
): Result<Out, Error> {
    contract { callsInPlace(transform, InvocationKind.AT_MOST_ONCE) }
    return if (isOk()) transform(ok) else this
}

/**
 * ###### Signature:
 * `Result<On, Error>` -> `Result<Ok, Out>`
 *
 * ###### EN:
 * Transforms to `Result<Ok, Out>` by applying the [transform] function if [Result.Error].
 *
 * ###### RU:
 * Трансформирует в `Result<Ok, Out>`, применяя функцию [transform] если [Result.Error].
 *
 * ###### Example:
 * ```
 * Ok(1).flatMapError { Ok("bar") } // Result: Ok(1)
 * Ok(1).flatMapError { Error("zoo") } // Result: Ok(1)
 *
 * Error(1).flatMapError { Ok("bar") } // Result: Ok("bar")
 * Error(1).flatMapError { Error("zoo") } // Result: Error("zoo")
 * ```
 */
public inline fun <Ok, Error, Out> Result<Ok, Error>.flatMapError(
    transform: (Error) -> Result<Ok, Out>,
): Result<Ok, Out> {
    contract { callsInPlace(transform, InvocationKind.AT_MOST_ONCE) }
    return if (isError()) transform(error) else this
}


/**
 * ###### Signature:
 * `Result<Result<Ok, Error>, Error>` -> `Result<Ok, Error>`
 *
 * ###### EN:
 * Reduces nesting for [Result.Ok].
 *
 * ###### RU:
 * Уменьшает вложенность для [Result.Ok].
 *
 * ###### Example:
 * ```
 * Ok(Ok(1)).flatten() // Result: Ok(1)
 * ```
 */
public fun <Ok, Error> Result<Result<Ok, Error>, Error>.flatten(): Result<Ok, Error> = flatMap { it }

/**
 * ###### Signature:
 * `Result<Value, Value>` -> `Value`
 *
 * ###### EN:
 * Returns a value if both sides contain the same type.
 *
 * ###### RU:
 * Возвращает значение, если обе стороны содержат один и тот же тип.
 *
 * ###### Example:
 * ```
 * Ok(1).take() // Result: 1
 * Error(1).take() // Result: 1
 * ```
 */
public fun <Value> Result<Value, Value>.take(): Value {
    return if (isOk()) ok else error
}
