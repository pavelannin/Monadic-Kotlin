package io.github.pavelannin.monadic.checkable

import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * ###### Signature:
 * `Checkable<In>` -> `Checkable<Out>`
 *
 * ###### EN:
 * Transforms to `Checkable<Out>` by applying the [transform] function.
 *
 * ###### RU:
 * Трансформирует в `Checkable<Out>`, применяя функцию [transform].
 *
 * ###### Example:
 * ```
 * Checked(Unit).map { _, _ -> "foo" } // Result: Checked("foo")
 * Unchecked(Unit).map { _, _ -> "foo" } // Result: Unchecked("foo")
 * ```
 */
public inline fun <In, Out> Checkable<In>.map(
    transform: (isChecked: Boolean, In) -> Out,
): Checkable<Out> {
    contract { callsInPlace(transform, InvocationKind.EXACTLY_ONCE) }
    val checked = isChecked()
    return Checkable(checked, transform(checked, checkable))
}

/**
 * ###### Signature:
 * `Checkable<In>` -> `Checkable<Out>`
 *
 * ###### EN:
 * Transforms to `Checkable<Out>` by applying the [checkedTransform] function if [Checkable.Checked],
 * or the [uncheckedTransform] function if [Unchecked].
 *
 * ###### RU:
 * Трансформирует в `Checkable<Out>`, применяя функцию [checkedTransform] если [Checkable.Checked],
 * или функцию [uncheckedTransform] если [Unchecked].
 *
 * ###### Example:
 * ```
 * Checked(Unit).map(
 *     checkedTransform = { "foo" },
 *     uncheckedTransform = { "bar" },
 * ) // Result: Checked("foo")
 *
 * Unchecked(Unit).map(
 *     checkedTransform = { "foo" },
 *     uncheckedTransform = { "bar" },
 * ) // Result: Unchecked("bar")
 * ```
 */
public inline fun <In, Out> Checkable<In>.map(
    checkedTransform: (In) -> Out,
    uncheckedTransform: (In) -> Out,
): Checkable<Out> {
    contract {
        callsInPlace(checkedTransform, InvocationKind.AT_MOST_ONCE)
        callsInPlace(uncheckedTransform, InvocationKind.AT_MOST_ONCE)
    }
    return if (isChecked()) Checkable.Checked(checkedTransform(checkable)) else Checkable.Unchecked(uncheckedTransform(checkable))
}

/**
 * ###### Signature:
 * `Checkable<In>` -> `Out`
 *
 * ###### EN:
 * Transforms to the value [Out]. Applies the [transform] function.
 *
 * ###### RU:
 * Трансформирует в значение [Out]. Применяет функцию [transform].
 *
 * ###### Example:
 * ```
 * Checked(Unit).fold { _, _ -> "foo" } // Result: "foo"
 * Unchecked(Unit).fold { _, _ -> "foo" } // Result: "foo"
 * ```
 */
public inline fun <In, Out> Checkable<In>.fold(
    transform: (isChecked: Boolean, In) -> Out,
): Out {
    contract { callsInPlace(transform, InvocationKind.EXACTLY_ONCE) }
    return transform(isChecked(), checkable)
}

/**
 * ###### Signature:
 * `Checkable<In>` -> `Out`
 *
 * ###### EN:
 * Transforms to the value [Out]. Applies the [checkedTransform] function if [Checkable.Checked],
 * or the [uncheckedTransform] function if [Unchecked].
 *
 * ###### RU:
 * Трансформирует в значение [Out]. Применяет функцию [checkedTransform] если [Checkable.Checked],
 * или функцию [uncheckedTransform] если [Unchecked].
 *
 * ###### Example:
 * ```
 * Checked(Unit).fold(
 *     checkedTransform = { "foo" },
 *     uncheckedTransform = { "bar" },
 * ) // Result: "foo"
 *
 * Unchecked(Unit).fold(
 *     checkedTransform = { "foo" },
 *     uncheckedTransform = { "bar" },
 * ) // Result: "bar"
 * ```
 */
public inline fun <In, Out> Checkable<In>.fold(
    checkedTransform: (In) -> Out,
    uncheckedTransform: (In) -> Out,
): Out {
    contract {
        callsInPlace(checkedTransform, InvocationKind.AT_MOST_ONCE)
        callsInPlace(uncheckedTransform, InvocationKind.AT_MOST_ONCE)
    }
    return if (this.isChecked()) checkedTransform(checkable) else uncheckedTransform(checkable)
}

/**
 * ###### Signature:
 * `Checkable<In>` -> `Checkable<Out>`
 *
 * ###### EN:
 * Transforms to `Checkable<Out>` by applying the [transform] function.
 *
 * ###### RU:
 * Трансформирует в `Checkable<Out>`, применяя функцию [transform].
 *
 * ###### Example:
 * ```
 * Checked(Unit).flatMap { _, _ -> Checked("bar") } // Result: Checked("bar")
 * Checked(Unit).flatMap { Unchecked("foo") } // Result: Unchecked("foo")
 *
 * Unchecked(Unit).flatMap { _, _ -> Checked("bar") } // Result: Checked("bar")
 * Unchecked(Unit).flatMap { _, _ -> Unchecked("foo") } // Result: Unchecked("foo")
 * ```
 */
public inline fun <In, Out> Checkable<In>.flatMap(
    transform: (isChecked: Boolean, In) -> Checkable<Out>,
): Checkable<Out> {
    contract { callsInPlace(transform, InvocationKind.EXACTLY_ONCE) }
    return transform(isChecked(), checkable)
}

/**
 * ###### Signature:
 * `Checkable<In>` -> `Checkable<Out>`
 *
 * ###### EN:
 * Transforms to `Checkable<Out>` by applying the [checkedTransform] function if [Checkable.Checked],
 * or the [uncheckedTransform] function if [Checkable.Unchecked].
 *
 * ###### RU:
 * Трансформирует в `Checkable<Out>`, применяя функцию [checkedTransform] если [Checkable.Checked],
 * или функцию [uncheckedTransform] если [Checkable.Unchecked].
 *
 * ###### Example:
 * ```
 * Checked(Unit).flatMap(
 *     checkedTransform = { Checkable.Unchecked("foo") },
 *     uncheckedTransform = { Checkable.Checked("bar") },
 * ) // Result: Unchecked("foo")
 *
 * Unchecked(Unit).flatMap(
 *     checkedTransform = { Checkable.Unchecked("foo") },
 *     uncheckedTransform = { Checkable.Checked("bar") },
 * ) // Result: Checked("bar")
 * ```
 */
public inline fun <In, Out> Checkable<In>.flatMap(
    checkedTransform: (In) -> Checkable<Out>,
    uncheckedTransform: (In) -> Checkable<Out>,
): Checkable<Out> {
    contract {
        callsInPlace(checkedTransform, InvocationKind.AT_MOST_ONCE)
        callsInPlace(uncheckedTransform, InvocationKind.AT_MOST_ONCE)
    }
    return when (this) {
        is Checkable.Checked -> checkedTransform(checkable)
        is Checkable.Unchecked -> uncheckedTransform(checkable)
    }
}

/**
 * ###### Signature:
 * `Checkable<Checkable<Value>>` -> `Checkable<Value>`
 *
 * ###### EN:
 * Reduces nesting.
 *
 * ###### RU:
 * Уменьшает вложенность.
 *
 * ###### Example:
 * ```
 * Checked(Checked(Unit)).flatten() // Result: Checked(Unit)
 * ```
 */
public fun <Value> Checkable<Checkable<Value>>.flatten(): Checkable<Value> = flatMap { _, value -> value }
