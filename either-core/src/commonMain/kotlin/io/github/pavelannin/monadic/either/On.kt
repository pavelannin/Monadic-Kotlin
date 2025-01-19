package io.github.pavelannin.monadic.either

import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * ###### EN:
 * Calls the [block] function if [Either.Left].
 *
 * ###### RU:
 * Вызывает функцию [block] если [Either.Left].
 *
 * ###### Example:
 * ```
 * Right(1).onLeft { print("Hello") }
 * Left(1).onLeft { print("Hello") } // Log: Hello
 * ```
 */
public inline fun <Left, Right> Either<Left, Right>.onLeft(
    block: (Left) -> Unit,
): Either<Left, Right> {
    contract { callsInPlace(block, InvocationKind.AT_MOST_ONCE) }
    if (this.isLeft()) block(left)
    return this
}

/**
 * ###### EN:
 * Calls the [block] function if [Either.Right].
 *
 * ###### RU:
 * Вызывает функцию [block] если [Either.Right].
 *
 * ###### Example:
 * ```
 * Right(1).onRight { print("Hello") }  // Log: Hello
 * Left(1).onRight { print("Hello") }
 * ```
 */
public inline fun <Left, Right> Either<Left, Right>.onRight(
    block: (Right) -> Unit,
): Either<Left, Right> {
    contract { callsInPlace(block, InvocationKind.AT_MOST_ONCE) }
    if (this.isRight()) block(right)
    return this
}
