package io.github.pavelannin.monadic.either

import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * ###### EN:
 * Returns `value` if it is [Either.Left], or `null` otherwise.
 *
 * ###### RU:
 * Возвращает значение `value`, если [Either.Left], в противном случае `null`.
 *
 * ###### Example:
 * ```
 * Right(1).leftOrNull() // Result: null
 * Left(1).leftOrNull() // Result: 1
 * ```
 */
public fun <Left, Right> Either<Left, Right>.leftOrNull(): Left? {
    return if (this.isLeft()) left else null
}

/**
 * ###### EN:
 * Returns `value` if it is [Either.Right], or `null` otherwise.
 *
 * ###### RU:
 * Возвращает значение `value`, если [Either.Right], в противном случае `null`.
 *
 * ###### Example:
 * ```
 * Right(1).rightOrNull() // Result: 1
 * Left(1).rightOrNull() // Result: null
 * ```
 */
public fun <Left, Right> Either<Left, Right>.rightOrNull(): Right? {
    return if (this.isRight()) right else null
}

/**
 * ###### EN:
 * Returns value if it is [Either.Left], or [default] otherwise.
 *
 * ###### RU:
 * Возвращает значение, если [Either.Left], в противном случае [default].
 *
 * ###### Example:
 * ```
 * Left(1).leftOrDefault(2) // Result: 1
 * Right(1).leftOrDefault(2) // Result: 2
 * ```
 */
public fun <Left, Right> Either<Left, Right>.leftOrDefault(default: Left): Left {
    return leftOrNull() ?: default
}

/**
 * ###### EN:
 * Returns value if it is [Either.Right], or [default] otherwise.
 *
 * ###### RU:
 * Возвращает значение, если [Either.Right], в противном случае [default].
 *
 * ###### Example:
 * ```
 * Left(1).rightOrDefault(2) // Result: 2
 * Right(1).rightOrDefault(2) // Result: 1
 * ```
 */
public fun <Left, Right> Either<Left, Right>.rightOrDefault(default: Right): Right {
    return rightOrNull() ?: default
}

/**
 * ###### EN:
 * Returns the value if [Either.Left], otherwise applies the [transform] function
 *
 * ###### RU:
 * Возвращает значение, если [Either.Left], в противном случае применяет функцию [transform].
 *
 * ###### Example:
 * ```
 * Left(1).leftOrElse { 2 } // Result: 1
 * Right(1).leftOrElse { 2 } // Result: 2
 * ```
 */
public inline fun <Left, Right> Either<Left, Right>.leftOrElse(
    transform: (Right) -> Left,
): Left {
    contract { callsInPlace(transform, InvocationKind.AT_MOST_ONCE) }
    return if (isLeft()) left else transform(right)
}

/**
 * ###### EN:
 * Returns the value if [Either.Right], otherwise applies the [transform] function
 *
 * ###### RU:
 * Возвращает значение, если [Either.Right], в противном случае применяет функцию [transform].
 *
 * ###### Example:
 * ```
 * Left(1).rightOrElse { 2 } // Result: 2
 * Right(1).rightOrElse { 2 } // Result: 1
 * ```
 */
public inline fun <Left, Right> Either<Left, Right>.rightOrElse(
    transform: (Left) -> Right,
): Right {
    contract { callsInPlace(transform, kotlin.contracts.InvocationKind.AT_MOST_ONCE) }
    return if (isRight()) right else transform(left)
}

/**
 * ###### Signature:
 * `Either<Result, Result>` -> `Result`
 *
 * ###### EN:
 * Returns a value if both sides contain the same type.
 *
 * ###### RU:
 * Возвращает значение, если обе стороны содержат один и тот же тип.
 *
 * ###### Example:
 * ```
 * Right(1).take() // Result: 1
 * Left(1).take() // Result: 1
 * ```
 */
public fun <Value> Either<Value, Value>.take(): Value {
    return if (isRight()) right else left
}
