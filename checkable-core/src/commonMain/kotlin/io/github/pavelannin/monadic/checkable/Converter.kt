package io.github.pavelannin.monadic.checkable

/**
 * ###### Signature:
 * `Checkable<Value>` -> `Checkable.Checked<Value>`
 *
 * ###### EN:
 * Transforms to [Checkable.Checked].
 *
 * ###### RU:
 * Трансформирует в [Checkable.Checked].
 *
 * ###### Example:
 * ```
 * Checked(Unit).toChecked() // Result: Checked(Unit)
 * Unchecked(Unit).toChecked() // Result: Checked(Unit)
 * ```
 */
public fun <Value> Checkable<Value>.toChecked(): Checkable.Checked<Value> {
    return Checkable.Checked(checkable)
}

/**
 * ###### Signature:
 * `Checkable<Value>` -> `Checkable.Unchecked<Value>`
 *
 * ###### EN:
 * Transforms to [Checkable.Unchecked].
 *
 * ###### RU:
 * Трансформирует в [Checkable.Unchecked].
 *
 * ###### Example:
 * ```
 * Checked(Unit).toUnchecked() // Result: Unchecked(Unit)
 * Unchecked(Unit).toUnchecked() // Result: Unchecked(Unit)
 * ```
 */
public fun <Value> Checkable<Value>.toUnchecked(): Checkable.Unchecked<Value> {
    return Checkable.Unchecked(checkable)
}
