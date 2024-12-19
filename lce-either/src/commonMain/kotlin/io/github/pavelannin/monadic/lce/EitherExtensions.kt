package io.github.pavelannin.monadic.lce

import io.github.pavelannin.monadic.either.Either

/**
 * ###### EN:
 * Converts [either] to [LCE]. Transforming [Either.Left] into [LCE.Error], a [Either.Right] in [LCE.Content].
 *
 * ###### RU:
 * Конвертирует [either] в [LCE]. Трансформируюя [Either.Left] в [LCE.Error], a [Either.Right] в [LCE.Content].
 *
 * ###### Example:
 * ```
 * LCE(Either.Left(1)) // Result: LCE.Error(1)
 * LCE(Either.Right(1)) // Result: LCE.Content(1)
 * ```
 */
public operator fun <Left, Right> LCE.Companion.invoke(
    either: Either<Left, Right>,
): LCE<Nothing, Right, Left> = either.fold(
    leftTransform = { left -> LCE.Error(left) },
    rightTransform = { right -> LCE.Content(right) },
)

/**
 * ###### EN:
 * Converts [Either] to [LCE]. Transforming [Either.Left] into [LCE.Error], a [Either.Right] in [LCE.Content].
 *
 * ###### RU:
 * Конвертирует [Either] в [LCE]. Трансформируюя [Either.Left] в [LCE.Error], a [Either.Right] в [LCE.Content].
 *
 * ###### Example:
 * ```
 * Either.Left(1).toLCE() // Result: LCE.Error(1)
 * Either.Right(1).toLCE() // Result: LCE.Content(1)
 * ```
 */
public fun <Left, Right> Either<Left, Right>.toLCE(): LCE<Nothing, Right, Left> = LCE(either = this)

/**
 * ###### EN:
 * Converts [either] to [LCE]. Transforming [Either.Left] into [LCE.Error] using [leftTransform], and [Either.Right] into [LCE.Content] using [rightTransform].
 *
 * ###### RU:
 * Конвертирует [either] в [LCE]. Трансформируя [Either.Left] в [LCE.Error], применяя [leftTransform], a [Either.Right] в [LCE.Content], применяя [rightTransform].
 *
 * ###### Example:
 * ```
 * LCE(
 *     either = Either.Left(1),
 *     leftTransform = { left -> left.toString() },
 *     rightTransform = { right -> right.toString() }
 * ) // Result: LCE.Error("1")
 * LCE(
 *     either = Either.Right(1),
 *     leftTransform = { left -> left.toString() },
 *     rightTransform = { right -> right.toString() }
 * ) // Result: LCE.Content("1")
 * ```
 */
internal inline operator fun <Left, Right, LeftLCE, RightLCE> LCE.Companion.invoke(
    either: Either<Left, Right>,
    leftTransform: (Left) -> LeftLCE,
    rightTransform: (Right) -> RightLCE,
): LCE<Nothing, RightLCE, LeftLCE> = either.fold(
    leftTransform = { left -> LCE.Error(leftTransform(left)) },
    rightTransform = { right -> LCE.Content(rightTransform(right)) },
)

/**
 * ###### EN:
 * Converts [Either] to [LCE]. Transforming [Either.Left] into [LCE.Error] using [leftTransform], and [Either.Right] into [LCE.Content] using [rightTransform].
 *
 * ###### RU:
 * Конвертирует [Either] в [LCE]. Трансформируя [Either.Left] в [LCE.Error], применяя [leftTransform], a [Either.Right] в [LCE.Content], применяя [rightTransform].
 *
 * ###### Example:
 * ```
 * Either.Left(1).toLCE(leftTransform = { left -> left.toString() }, rightTransform = { right -> right.toString() }) // Result: LCE.Error("1")
 * Either.Right(1).toLCE(leftTransform = { left -> left.toString() }, rightTransform = { right -> right.toString() }) // Result: LCE.Content("1")
 * ```
 */
internal inline fun <Left, Right, LeftLCE, RightLCE> Either<Left, Right>.toLCE(
    leftTransform: (Left) -> LeftLCE,
    rightTransform: (Right) -> RightLCE
): LCE<Nothing, RightLCE, LeftLCE> = LCE(
    either = this,
    rightTransform = rightTransform,
    leftTransform = leftTransform
)

/**
 * ###### EN:
 * Converts [Either] to [LCE]. Transforming [Either.Left] into [LCE.Error] using [leftTransform], and [Either.Right] into [LCE.Content] using [rightTransform].
 * @param loadingValue helps to cast [Either] to the required generic type of [LCE.Loading].
 *
 * ###### RU:
 * Конвертирует [Either] в [LCE]. Трансформируя [Either.Left] в [LCE.Error], применяя [leftTransform], a [Either.Right] в [LCE.Content], применяя [rightTransform].
 * @param loadingValue помогает привести [Either] к нужному generic типу [LCE.Loading].
 *
 * ###### Example:
 * ```
 * Either.Left(1).toLCE(leftTransform = { left -> left.toString() }, rightTransform = { right -> right.toString() }) // Result: LCE.Error("1")
 * Either.Right(1).toLCE(leftTransform = { left -> left.toString() }, rightTransform = { right -> right.toString() }) // Result: LCE.Content("1")
 * ```
 */
@Suppress("UNUSED_PARAMETER")
internal inline fun <Left, Right, Loading, Content, Error> Either<Left, Right>.toLCE(
    leftTransform: (Left) -> Error,
    rightTransform: (Right) -> Content,
    loadingValue: Loading
): LCE<Loading, Content, Error> = LCE(
    either = this,
    rightTransform = rightTransform,
    leftTransform = leftTransform
)
