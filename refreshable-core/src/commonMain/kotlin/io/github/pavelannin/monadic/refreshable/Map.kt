package io.github.pavelannin.monadic.refreshable

import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * ###### Signature:
 * `Refreshable<Value>` -> `Refreshable<Out>`
 *
 * ###### EN:
 * Transforms to `Refreshable<Out>` by applying the [transform] function.
 *
 * ###### RU:
 * Трансформирует в `Refreshable<Out>`, применяя функцию [transform].
 *
 * ###### Example:
 * ```
 * Refreshing(Unit).map { _, _ -> "foo" } // Result: Refreshing("foo")
 * Refreshed(Unit).map { _, _ -> "foo" } // Result: Unchecked("foo")
 * ```
 */
public inline fun <Value, Out> Refreshable<Value>.map(
    transform: (isRefreshing: Boolean, Value) -> Out,
): Refreshable<Out> {
    contract { callsInPlace(transform, InvocationKind.EXACTLY_ONCE) }
    val refreshing = isRefreshing()
    return Refreshable(refreshing, transform(refreshing, refreshable))
}

/**
 * ###### Signature:
 * `Refreshable<Value>` -> `Refreshable<Out>`
 *
 * ###### EN:
 * Transforms to `Refreshable<Out>` by applying the [refreshingTransform] function if [Refreshable.Refreshing],
 * or the [refreshedTransform] function if [Refreshable.Refreshed].
 *
 * ###### RU:
 * Трансформирует в `Refreshable<Out>`, применяя функцию [refreshingTransform] если [Refreshable.Refreshing],
 * или функцию [refreshedTransform] если [Refreshable.Refreshed].
 *
 * ###### Example:
 * ```
 * Refreshing(Unit).map(
 *     refreshingTransform = { "foo" },
 *     refreshedTransform = { "bar" },
 * ) // Result: Refreshing("foo")
 *
 * Refreshed(Unit).map(
 *     refreshingTransform = { "foo" },
 *     refreshedTransform = { "bar" },
 * ) // Result: Refreshed("bar")
 * ```
 */
public inline fun <Value, Out> Refreshable<Value>.map(
    refreshingTransform: (Value) -> Out,
    refreshedTransform: (Value) -> Out,
): Refreshable<Out> {
    contract {
        callsInPlace(refreshingTransform, InvocationKind.AT_MOST_ONCE)
        callsInPlace(refreshedTransform, InvocationKind.AT_MOST_ONCE)
    }
    return if (isRefreshing()) {
        Refreshable.Refreshing(refreshingTransform(refreshable))
    } else {
        Refreshable.Refreshed(refreshedTransform(refreshable))
    }
}

/**
 * ###### Signature:
 * `Refreshable<Value>` -> `Result`
 *
 * ###### EN:
 * Transforms to the value [Out]. Applies the [transform] function.
 *
 * ###### RU:
 * Трансформирует в значение [Out]. Применяет функцию [transform].
 *
 * ###### Example:
 * ```
 * Refreshing(Unit).fold { _, _ -> "foo" } // Result: "foo"
 * Refreshed(Unit).fold { _, _ -> "foo" } // Result: "foo"
 * ```
 */
public inline fun <Value, Out> Refreshable<Value>.fold(
    transform: (isRefreshing: Boolean, Value) -> Out,
): Out {
    contract { callsInPlace(transform, InvocationKind.EXACTLY_ONCE) }
    return transform(isRefreshing(), refreshable)
}

/**
 * ###### Signature:
 * `Refreshable<Value>` -> `Result`
 *
 * ###### EN:
 * Transforms to the value [Out]. Applies the [refreshingTransform] function if [Refreshable.Refreshing],
 * or the [refreshedTransform] function if [Refreshable.Refreshed].
 *
 * ###### RU:
 * Трансформирует в значение [Out]. Применяет функцию [refreshingTransform] если [Refreshable.Refreshing],
 * или функцию [refreshedTransform] если [Refreshable.Refreshed].
 *
 * ###### Example:
 * ```
 * Refreshing(Unit).fold(
 *     refreshingTransform = { "foo" },
 *     refreshedTransform = { "bar" },
 * ) // Result: "foo"
 *
 * Refreshed(Unit).fold(
 *     refreshingTransform = { "foo" },
 *     refreshedTransform = { "bar" },
 * ) // Result: "bar"
 * ```
 */
public inline fun <Value, Out> Refreshable<Value>.fold(
    refreshingTransform: (Value) -> Out,
    refreshedTransform: (Value) -> Out,
): Out {
    contract {
        callsInPlace(refreshingTransform, InvocationKind.AT_MOST_ONCE)
        callsInPlace(refreshedTransform, InvocationKind.AT_MOST_ONCE)
    }
    return if (this.isRefreshing()) refreshingTransform(refreshable) else refreshedTransform(refreshable)
}


/**
 * ###### Signature:
 * `Refreshable<In>` -> `Refreshable<Out>`
 *
 * ###### EN:
 * Transforms to `Refreshable<Out>` by applying the [refreshingTransform] function if [Refreshable.Refreshing],
 * or the [refreshedTransform] function if [Refreshable.Refreshed].
 *
 * ###### RU:
 * Трансформирует в `Refreshable<Out>`, применяя функцию [refreshingTransform] если [Refreshable.Refreshing],
 * или функцию [refreshedTransform] если [Refreshable.Refreshed].
 *
 * ###### Example:
 * ```
 * Refreshing(Unit).flatMap(
 *     refreshingTransform = { Refreshable.Refreshed("foo") },
 *     refreshedTransform = { Refreshable.Refreshing("bar") },
 * ) // Result: Refreshed("foo")
 *
 * Refreshed(Unit).flatMap(
 *     refreshingTransform = { Refreshable.Refreshed("foo") },
 *     refreshedTransform = { Refreshable.Refreshing("bar") },
 * ) // Result: Refreshing("bar")
 * ```
 */
public inline fun <Value, Out> Refreshable<Value>.flatMap(
    refreshingTransform: (Value) -> Refreshable<Out>,
    refreshedTransform: (Value) -> Refreshable<Out>,
): Refreshable<Out> {
    contract {
        callsInPlace(refreshingTransform, InvocationKind.AT_MOST_ONCE)
        callsInPlace(refreshedTransform, InvocationKind.AT_MOST_ONCE)
    }
    return when (this) {
        is Refreshable.Refreshing -> refreshingTransform(refreshable)
        is Refreshable.Refreshed -> refreshedTransform(refreshable)
    }
}

/**
 * ###### Signature:
 * `Refreshable<Refreshable<Value>>` -> `Refreshable<Value>`
 *
 * ###### EN:
 * Reduces nesting.
 *
 * ###### RU:
 * Уменьшает вложенность.
 *
 * ###### Example:
 * ```
 * Refreshing(Refreshing(Unit)).flatten() // Result: Refreshing(Unit)
 * ```
 */
public fun <Value> Refreshable<Refreshable<Value>>.flatten(): Refreshable<Value> = flatMap { _, value -> value }
