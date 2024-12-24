package io.github.pavelannin.monadic.lce

import io.github.pavelannin.monadic.result.Result
import io.github.pavelannin.monadic.result.fold

/**
 * ###### EN:
 * Converts [Result] to [LCE]. Transforming [Result.Ok] into [LCE.Content], a [Result.Error] in [LCE.Error].
 *
 * ###### RU:
 * Конвертирует [either] в [LCE]. Трансформируюя [Result.Ok] в [LCE.Content], a [Result.Error] в [LCE.Error].
 *
 * ###### Example:
 * ```
 * LCE(Result.Ok(1)) // Result: LCE.Content(1)
 * LCE(Result.Error(1)) // Result: LCE.Error(1)
 * ```
 */
public operator fun <Ok, Error> LCE.Companion.invoke(
    result: Result<Ok, Error>,
): LCE<Nothing, Ok, Error> = result.fold(
    okTransform = { ok -> LCE.Content(ok) },
    errorTransform = { error -> LCE.Error(error) },
)

/**
 * ###### EN:
 * Converts [Result] to [LCE]. Transforming [Result.Ok] into [LCE.Content], a [Result.Error] in [LCE.Error].
 *
 * ###### RU:
 * Конвертирует [Result] в [LCE]. Трансформируюя [Result.Ok] в [LCE.Content], a [Result.Error] в [LCE.Error].
 *
 * ###### Example:
 * ```
 * Result.Ok(1).toLCE() // Result: LCE.Content(1)
 * Result.Error(1).toLCE() // Result: LCE.Error(1)
 * ```
 */
public fun <Ok, Error> Result<Ok, Error>.toLCE(): LCE<Nothing, Ok, Error> = LCE(result = this)
