package io.github.pavelannin.monadic.optional

/**
 * ###### EN:
 * Returns `value` if it is [Optional.Some], or `null` otherwise.
 *
 * ###### RU:
 * Возвращает значение `value`, если [Optional.Some], в противном случае `null`.
 *
 * ###### Example:
 * ```
 * Some(Unit).getOrNull() // Result: Unit
 * None.getOrNull() // Result: null
 * ```
 */
public fun <Value> Optional<Value>.getOrNull(): Value? {
    return if (this.isSome()) some else null
}
