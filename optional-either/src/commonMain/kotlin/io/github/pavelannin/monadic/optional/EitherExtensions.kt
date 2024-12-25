package io.github.pavelannin.monadic.optional

import io.github.pavelannin.monadic.either.Either
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * ###### EN:
 * Converts [optional] to [Either]. Transforming [Optional.Some] into [Either.Right],
 * and [Optional.None] in [Either.Left] using [leftInitial].
 *
 * ###### RU:
 * Конвертирует [optional] в [Either]. Трансформируюя [Optional.Some] в [Either.Right],
 * а [Optional.None] в [Either.Left] применяя [leftInitial].
 *
 * ###### Example:
 * ```
 * Either(Optional.Some(Unit)) { "foo" } // Result: Either.Right(Unit)
 * Either(Optional.None) { "foo" } // Result: Either.Left("foo")
 * ```
 */
public inline operator  fun <Left, Right> Either.Companion.invoke(
    optional: Optional<Right>,
    leftInitial: () -> Left,
): Either<Left, Right> {
    return optional.fold(
        someTransform = { value -> Either.Right(value) },
        noneTransform = { Either.Left(leftInitial()) },
    )
}

/**
 * ###### EN:
 * Converts [Optional] to [Either]. Transforming [Optional.Some] into [Either.Right],
 * and [Optional.None] in [Either.Left] using [noneTransform].
 *
 * ###### RU:
 * Конвертирует [Optional] в [Either]. Трансформируюя [Optional.Some] в [Either.Right],
 * а [Optional.None] в [Either.Left] применяя [noneTransform].
 *
 * ###### Example:
 * ```
 * Optional.Some(Unit).toEither { "foo" }  // Result: Either.Right(Unit)
 * Optional.None.toEither { "foo" }  // Result: Either.Left("foo")
 * ```
 */
public inline fun <Left, Right> Optional<Right>.toEither(
    noneTransform: () -> Left,
): Either<Left, Right> {
    contract { callsInPlace(noneTransform, InvocationKind.AT_MOST_ONCE) }
    return Either(optional = this, leftInitial = noneTransform)
}
