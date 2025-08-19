package io.github.pavelannin.monadic.optional

import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import io.github.pavelannin.monadic.result.Result

/**
 * ###### EN:
 * Converts [optional] to [Result]. Transforming [Optional.Some] into [Result.Ok],
 * and [Optional.None] in [Result.Error] using [errorInitial].
 *
 * ###### RU:
 * Конвертирует [optional] в [Result]. Трансформируюя [Optional.Some] в [Result.Ok],
 * а [Optional.None] в [Result.Error] применяя [errorInitial].
 *
 * ###### Example:
 * ```
 * Result(Optional.Some(Unit)) { "foo" } // Result: Result.Ok(Unit)
 * Result(Optional.None) { "foo" } // Result: Result.Error("foo")
 * ```
 */
public inline operator  fun <Ok, Error> Result.Companion.invoke(
    optional: Optional<Ok>,
    errorInitial: () -> Error,
): Result<Ok, Error> {
    return optional.fold(
        someTransform = { value -> Result.Ok(value) },
        noneTransform = { Result.Error(errorInitial()) },
    )
}

/**
 * ###### EN:
 * Converts [Optional] to [Result]. Transforming [Optional.Some] into [Result.Ok],
 * and [Optional.None] in [Result.Error] using [noneTransform].
 *
 * ###### RU:
 * Конвертирует [Optional] в [Result]. Трансформируюя [Optional.Some] в [Result.Ok],
 * а [Optional.None] в [Result.Error] применяя [noneTransform].
 *
 * ###### Example:
 * ```
 * Optional.Some(Unit).toResult { "foo" }  // Result: Result.Ok(Unit)
 * Optional.None.toResult { "foo" }  // Result: Result.Error("foo")
 * ```
 */
public inline fun <Ok, Error> Optional<Ok>.toResult(
    noneTransform: () -> Error,
): Result<Ok, Error> {
    contract { callsInPlace(noneTransform, InvocationKind.AT_MOST_ONCE) }
    return Result(optional = this, errorInitial = noneTransform)
}
