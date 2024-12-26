package io.github.pavelannin.monadic.either

import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * ###### Signature:
 * `Either<Left, Right>` -> `Either<Left, Out>`
 *
 * ###### EN:
 * Transforms to `Either<Left, Out>` by applying the [transform] function if [Either.Right].
 *
 * ###### RU:
 * Трансформирует в `Either<Left, Out>`, применяя функцию [transform] если [Either.Right].
 *
 * ###### Example:
 * ```
 * Right(1).map { "foo" } // Result: Right("foo")
 * Left(1).map { "foo" }  // Result: Left(1)
 * ```
 */
public inline fun <Left, Right, Out> Either<Left, Right>.map(
    transform: (Right) -> Out,
): Either<Left, Out> {
    contract { callsInPlace(transform, InvocationKind.AT_MOST_ONCE) }
    return if (this.isRight()) Either.Right(transform(right)) else this
}

/**
 *  ###### Signature:
 * `Either<Left, Right>` -> `Either<Out, Right>`
 *
 * ###### EN:
 * Transforms to `Either<Out, Right>` by applying the [transform] function if [Either.Left].
 *
 * ###### RU:
 * Трансформирует в `Either<Out, Right>`, применяя функцию [transform] если [Either.Left].
 *
 * ###### Example:
 * ```
 * Right(1).mapLeft { "foo" } // Result: Right(1)
 * Left(1).mapLeft { "foo" }  // Result: Left("foo")
 * ```
 */
public inline fun <Left, Right, Out> Either<Left, Right>.mapLeft(
    transform: (Left) -> Out,
): Either<Out, Right> {
    contract { callsInPlace(transform, InvocationKind.AT_MOST_ONCE) }
    return if (this.isLeft()) Either.Left(transform(left)) else this
}

/**
 *  ###### Signature:
 * `Either<Left, Right>` -> `Either<LeftOut, RightOut>`
 *
 * ###### EN:
 * Transforms to `Either<Left Out, RightOut>` by applying the [leftTransform] function
 * if [Either.Left], or the [rightTransform] function if [Right].
 *
 * ###### RU:
 * Трансформирует в `Either<LeftOut, RightOut>`, применяя функцию [leftTransform]
 * если [Either.Left], или функцию [rightTransform] если [Right].
 *
 * ###### Example:
 * ```
 * Right(1).bimap(
 *   leftTransform = { "foo" },
 *   rightTransform = { "bar" },
 * ) // Result: Right("bar")
 *
 * Left(1).bimap(
 *   leftTransform = { "foo" },
 *   rightTransform = { "bar" },
 * ) // Result: Left("foo")
 * ```
 */
public inline fun <Left, Right, LeftOut, RightOut> Either<Left, Right>.bimap(
    leftTransform: (Left) -> LeftOut,
    rightTransform: (Right) -> RightOut,
): Either<LeftOut, RightOut> {
    contract {
        callsInPlace(leftTransform, InvocationKind.AT_MOST_ONCE)
        callsInPlace(rightTransform, InvocationKind.AT_MOST_ONCE)
    }
    return if (this.isLeft()) Either.Left(leftTransform(left)) else Either.Right(rightTransform(right))
}

/**
 * ###### Signature:
 * `Either<Left, Right>` -> `Result`
 *
 * ###### EN:
 * Transforms to the value [Result]. Applies the [leftTransform] function if [Either.Left],
 * or the [rightTransform] function if [Either.Right].
 *
 * ###### RU:
 * Трансформирует в значение [Result]. Применяет функцию [leftTransform] если [Either.Left],
 * или функцию [rightTransform] если [Either.Right].
 *
 * ###### Example:
 * ```
 * Right(1).fold(
 *   leftTransform = { "foo" },
 *   rightTransform = { "bar" },
 * ) // Result: "bar"
 *
 * Left(1).fold(
 *   leftTransform = { "foo" },
 *   rightTransform = { "bar" },
 * ) // Result: "foo"
 * ```
 */
public inline fun <Left, Right, Result> Either<Left, Right>.fold(
    leftTransform: (Left) -> Result,
    rightTransform: (Right) -> Result,
): Result {
    contract {
        callsInPlace(leftTransform, InvocationKind.AT_MOST_ONCE)
        callsInPlace(rightTransform, InvocationKind.AT_MOST_ONCE)
    }
    return if (this.isLeft()) leftTransform(left) else rightTransform(right)
}

/**
 * ###### Signature:
 * `Either<Left, Right>` -> `Result`
 *
 * ###### EN:
 * Transforms `Either<Left, Right>` to the value [Result]. Applies the [transform] function
 * if [Either.Left], otherwise returns the value [initial].
 *
 * ###### RU:
 * Трансформирует в `Either<Left, Right>` в значение [Result]. Применяет функцию [transform]
 * если [Either.Left], иначе возвращает значение [initial].
 *
 * ###### Example:
 * ```
 * Right(1).foldLeft(
 *   initial = "foo",
 *   transform = { "bar" },
 * ) // Result: "foo"
 *
 * Left(1).foldLeft(
 *   initial = "foo",
 *   transform = { "bar" },
 * ) // Result: "bar"
 * ```
 */
public inline fun <Left, Right, Result> Either<Left, Right>.foldLeft(
    initial: Result,
    transform: (Left) -> Result,
): Result {
    contract { callsInPlace(transform, InvocationKind.AT_MOST_ONCE) }
    return if (this.isLeft()) transform(left) else initial
}

/**
 * ###### Signature:
 * `Either<Left, Right>` -> `Result`
 *
 * ###### EN:
 * Transforms `Either<Left, Right>` to the value [Result]. Applies the [transform] function
 * if [Either.Right], otherwise returns the value [initial].
 *
 * ###### RU:
 * Трансформирует в `Either<Left, Right>` в значение [Result]. Применяет функцию [transform]
 * если [Either.Right], иначе возвращает значение [initial].
 *
 * ###### Example:
 * ```
 * Right(1).foldLeft(
 *   initial = "foo",
 *   transform = { "bar" },
 * ) // Result: "bar"
 *
 * Left(1).foldLeft(
 *   initial = "foo",
 *   transform = { "bar" },
 * ) // Result: "foo"
 * ```
 */
public inline fun <Left, Right, Result> Either<Left, Right>.foldRight(
    initial: Result,
    transform: (Right) -> Result,
): Result {
    contract { callsInPlace(transform, InvocationKind.AT_MOST_ONCE) }
    return if (this.isRight()) transform(right) else initial
}


/**
 * ###### Signature:
 * `Either<Left, Right>` -> `Either<Left, Out>`
 *
 * ###### EN:
 * Transforms to `Either<Left, Out>` by applying the [transform] function if [Either.Right].
 *
 * ###### RU:
 * Трансформирует в `Either<Left, Out>`, применяя функцию [transform] если [Either.Right].
 *
 * ###### Example:
 * ```
 * Right(1).flatMap { Either.Right("bar") } // Result: Right("bar")
 * Right(1).flatMap { Either.Left("zoo") } // Result: Left("zoo")
 *
 * Left(1).flatMap { Either.Right("bar") } // Result: Left(1)
 * Left(1).flatMap { Either.Left("zoo") } // Result: Left(1)
 * ```
 */
public inline fun <Left, Right, Out> Either<Left, Right>.flatMap(
    transform: (Right) -> Either<Left, Out>,
): Either<Left, Out> {
    contract { callsInPlace(transform, InvocationKind.AT_MOST_ONCE) }
    return if (isRight()) transform(right) else this
}

/**
 * ###### Signature:
 * `Either<Left, Right>` -> `Either<Out, Right>`
 *
 * ###### EN:
 * Transforms to `Either<Out, Right>` by applying the [transform] function if [Either.Left].
 *
 * ###### RU:
 * Трансформирует в `Either<Out, Right>`, применяя функцию [transform] если [Either.Left].
 *
 * ###### Example:
 * ```
 * Right(1).flatMapLeft { Either.Right("bar") } // Result: Right(1)
 * Right(1).flatMapLeft { Either.Left("zoo") } // Result: Right(1)
 *
 * Left(1).flatMapLeft { Either.Right("bar") } // Result: Right("bar")
 * Left(1).flatMapLeft { Either.Left("zoo") } // Result: Left("zoo")
 * ```
 */
public inline fun <Left, Right, Out> Either<Left, Right>.flatMapLeft(
    transform: (Left) -> Either<Out, Right>,
): Either<Out, Right> {
    contract { callsInPlace(transform, InvocationKind.AT_MOST_ONCE) }
    return if (isLeft()) transform(left) else this
}

/**
 * ###### Signature:
 * `Either<Left, Right>` -> `Either<Right, Left>`
 *
 * ###### EN:
 * The value of [Either.Right] will be returned to [Either.Left], and the
 * value of [Either.Left] will be returned to [Either.Right].
 *
 * ###### RU:
 * Значение [Either.Right] будет возвращено в [Either.Left], а значение
 * [Either.Left] будет возвращено в [Either.Right].
 *
 * ###### Example:
 * ```
 * Right(1).swap() // Result: Left(1)
 * Left(1).swap() // Result: Right(1)
 * ```
 */
public fun <Left, Right> Either<Left, Right>.swap(): Either<Right, Left> = fold(
    leftTransform = { Either.Right(it) },
    rightTransform = { Either.Left(it) },
)


/**
 * ###### Signature:
 * `Either<Left, Either<Left, Right>>` -> `Either<Left, Right>`
 *
 * ###### EN:
 * Reduces nesting for [Either.Right].
 *
 * ###### RU:
 * Уменьшает вложенность для [Either.Right].
 *
 * ###### Example:
 * ```
 * Right(Right(1)).flatten() // Result: Right(1)
 * ```
 */
public fun <Left, Right> Either<Left, Either<Left, Right>>.flatten(): Either<Left, Right> = flatMap { it }
