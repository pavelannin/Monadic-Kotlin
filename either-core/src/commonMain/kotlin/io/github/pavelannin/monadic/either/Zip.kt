package io.github.pavelannin.monadic.either

import kotlin.contracts.InvocationKind
import kotlin.contracts.contract


/**
 * ###### Signature:
 * `Either<Left, Right1>` & `Either<Left, Right2>` -> `Either<Left, Result>`
 *
 * ###### EN:
 * Combines two [Either.Right] to a single value by applying the [transform] function.
 *
 * ###### RU:
 * Объединяет два [Either.Right] в одно значение, применяя функцию [transform].
 *
 * ###### Example:
 * ```
 * Right(1).zip(Right(1)) { r1, r2 -> r1 + r2 } // Result: Right(2)
 *
 * Right(1).zip(Left("foo")) { r1, r2 -> r1 + r2 } // Result: Left("foo")
 * Left("foo").zip(Right(1)) { r1, r2 -> r1 + r2 } // Result: Left("foo")
 * Left("foo").zip(Left("foo")) { r1, r2 -> r1 + r2 } // Result: Left("foo")
 * ```
 */
public inline fun <Left, Right1, Right2, Result> Either<Left, Right1>.zip(
    either: Either<Left, Right2>,
    transform: (Right1, Right2) -> Result,
): Either<Left, Result> {
    contract { callsInPlace(transform, InvocationKind.AT_MOST_ONCE) }
    return flatMap { right1 -> either.map { right2 -> transform(right1, right2) } }
}

/**
 * ###### Signature:
 * `Either<Left, Right1>` & `Either<Left, Right2>` -> `Either<Left, Pair<Right1, Right2>`
 *
 * ###### EN:
 * Combines two [Either.Right] to a single value.
 *
 * ###### RU:
 * Объединяет два [Either.Right] в одно значение.
 *
 * @see zip
 */
public fun <Left, Right1, Right2> Either<Left, Right1>.zip(
    either: Either<Left, Right2>,
): Either<Left, Pair<Right1, Right2>> = zip(either, ::Pair)

/**
 * ###### Signature:
 * `Either<Left, Right1>` & `Either<Left, Right2>` & `Either<Left, Right3>` -> `Either<Left, Result>`
 *
 * ###### EN:
 * Combines three [Either.Right] to a single value by applying the [transform] function.
 *
 * ###### RU:
 * Объединяет три [Either.Right] в одно значение, применяя функцию [transform].
 *
 * @see zip
 */
public inline fun <Left, Right1, Right2, Right3, Result> Either<Left, Right1>.zip(
    either2: Either<Left, Right2>,
    either3: Either<Left, Right3>,
    transform: (Right1, Right2, Right3) -> Result,
): Either<Left, Result> {
    contract { callsInPlace(transform, InvocationKind.AT_MOST_ONCE) }
    return flatMap { right1 ->
        either2.flatMap { right2 ->
            either3.map { right3 ->
                transform(right1, right2, right3)
            }
        }
    }
}

/**
 * ###### Signature:
 * `Either<Left, Right1>` & `Either<Left, Right2>` & `Either<Left, Right3>` -> `Either<Left, Triple<Right1, Right2, Right3>>`
 *
 * ###### EN:
 * Combines three [Either.Right] to a single value.
 *
 * ###### RU:
 * Объединяет три [Either.Right] в одно значение.
 *
 * @see zip
 */
public fun <Left, Right1, Right2, Right3> Either<Left, Right1>.zip(
    either2: Either<Left, Right2>,
    either3: Either<Left, Right3>,
): Either<Left, Triple<Right1, Right2, Right3>> = zip(either2, either3, ::Triple)

/**
 * ###### Signature:
 * `Either<Left, Right1>` & `Either<Left, Right2>` & `Either<Left, Right3>` &
 * `Either<Left, Right4>` -> `Either<Left, Result>`
 *
 * ###### EN:
 * Combines four [Either.Right] to a single value by applying the [transform] function.
 *
 * ###### RU:
 * Объединяет четыре [Either.Right] в одно значение, применяя функцию [transform].
 *
 * @see zip
 */
public inline fun <Left, Right1, Right2, Right3, Right4, Result> Either<Left, Right1>.zip(
    either2: Either<Left, Right2>,
    either3: Either<Left, Right3>,
    either4: Either<Left, Right4>,
    transform: (Right1, Right2, Right3, Right4) -> Result,
): Either<Left, Result> {
    contract { callsInPlace(transform, InvocationKind.AT_MOST_ONCE) }
    return flatMap { right1 ->
        either2.flatMap { right2 ->
            either3.flatMap { right3 ->
                either4.map { right4 ->
                    transform(right1, right2, right3, right4)
                }
            }
        }
    }
}

/**
 * ###### Signature:
 * `Either<Left, Right1>` & `Either<Left, Right2>` & `Either<Left, Right3>` &
 * `Either<Left, Right4>` & `Either<Left, Right5>` -> `Either<Left, Result>`
 *
 * ###### EN:
 * Combines five [Either.Right] to a single value by applying the [transform] function.
 *
 * ###### RU:
 * Объединяет пять [Either.Right] в одно значение, применяя функцию [transform].
 *
 * @see zip
 */
public inline fun <Left, Right1, Right2, Right3, Right4, Right5, Result> Either<Left, Right1>.zip(
    either2: Either<Left, Right2>,
    either3: Either<Left, Right3>,
    either4: Either<Left, Right4>,
    either5: Either<Left, Right5>,
    transform: (Right1, Right2, Right3, Right4, Right5) -> Result,
): Either<Left, Result> {
    contract { callsInPlace(transform, InvocationKind.AT_MOST_ONCE) }
    return flatMap { right1 ->
        either2.flatMap { right2 ->
            either3.flatMap { right3 ->
                either4.flatMap { right4 ->
                    either5.map { right5 ->
                        transform(right1, right2, right3, right4, right5)
                    }
                }
            }
        }
    }
}
