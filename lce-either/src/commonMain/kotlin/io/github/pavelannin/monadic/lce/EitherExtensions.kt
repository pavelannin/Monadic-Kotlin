package io.github.pavelannin.monadic.lce

import io.github.pavelannin.monadic.either.Either
import io.github.pavelannin.monadic.either.fold

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
