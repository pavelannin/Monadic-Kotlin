package io.github.pavelannin.monadic.lce

/**
 * ###### EN:
 * Returns `value` if it is [LCE.Loading], or `null` otherwise.
 *
 * ###### RU:
 * Возвращает значение `value`, если [LCE.Loading], в противном случае `null`.
 *
 * ###### Example:
 * ```
 * Loading(1).loadingOrNull() // Result: 1
 * Content(1).loadingOrNull() // Result: null
 * Error(1).loadingOrNull() // Result: null
 * ```
 */
public fun <L, C, E> LCE<L, C, E>.loadingOrNull(): L? {
    return if (this.isLoading()) loading else null
}

/**
 * ###### EN:
 * Returns `value` if it is [LCE.Content], or `null` otherwise.
 *
 * ###### RU:
 * Возвращает значение `value`, если [LCE.Content], в противном случае `null`.
 *
 * ###### Example:
 * ```
 * Loading(1).contentOrNull() // Result: null
 * Content(1).contentOrNull() // Result: 1
 * Error(1).contentOrNull() // Result: null
 * ```
 */
public fun <L, C, E> LCE<L, C, E>.contentOrNull(): C? {
    return if (this.isContent()) content else null
}

/**
 * ###### EN:
 * Returns `value` if it is [LCE.Error], or `null` otherwise.
 *
 * ###### RU:
 * Возвращает значение `value`, если [LCE.Error], в противном случае `null`.
 *
 * ###### Example:
 * ```
 * Loading(1).errorOrNull() // Result: null
 * Content(1).errorOrNull() // Result: null
 * Error(1).errorOrNull() // Result: 1
 * ```
 */
public fun <L, C, E> LCE<L, C, E>.errorOrNull(): E? {
    return if (this.isError()) error else null
}
