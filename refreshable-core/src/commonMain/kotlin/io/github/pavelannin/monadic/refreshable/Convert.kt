package io.github.pavelannin.monadic.refreshable

/**
 * ###### Signature:
 * `Refreshable<Value>` -> `Refreshing<Value>`
 *
 * ###### EN:
 * Transforms to `[Refreshable.Refreshing]`.
 *
 * ###### RU:
 * Трансформирует в [Refreshable.Refreshing].
 *
 * ###### Example:
 * ```
 * Refreshing(Unit).toRefreshing() // Result: Refreshing(Unit)
 * Refreshed(Unit).toRefreshing() // Result: Refreshing(Unit)
 * ```
 */
public fun <Value> Refreshable<Value>.toRefreshing(): Refreshable.Refreshing<Value> {
    return Refreshable.Refreshing(refreshable)
}

/**
 * ###### Signature:
 * `Refreshable<Value>` -> `Refreshed<Value>`
 *
 * ###### EN:
 * Transforms to [Refreshable.Refreshed].
 *
 * ###### RU:
 * Трансформирует в [Refreshable.Refreshed].
 *
 * ###### Example:
 * ```
 * Refreshing(Unit).toRefreshed() // Result: Refreshed(Unit)
 * Refreshed(Unit).toRefreshed() // Result: Refreshed(Unit)
 * ```
 */
public fun <Value> Refreshable<Value>.toRefreshed(): Refreshable.Refreshed<Value> {
    return Refreshable.Refreshed(refreshable)
}
