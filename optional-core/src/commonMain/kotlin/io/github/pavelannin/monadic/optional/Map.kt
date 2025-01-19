package io.github.pavelannin.monadic.optional

import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * ###### Signature:
 * `Optional<Value>` -> `Optional<Out>`
 *
 * ###### EN:
 * Transforms to `Optional<Out>` by applying the [transform] function if [Optional.Some].
 *
 * ###### RU:
 * Трансформирует в `Optional<Out>`, применяя функцию [transform] если [Optional.Some].
 *
 * ###### Example:
 * ```
 * Some(Unit).map { "foo" } // Result: Some("foo")
 * None.map { "foo" }  // Result: None
 * ```
 */
public inline fun <Value, Out> Optional<Value>.map(transform: (Value) -> Out): Optional<Out> {
    contract { callsInPlace(transform, InvocationKind.AT_MOST_ONCE) }
    return if (this.isSome()) Optional.Some(transform(some)) else this
}

/**
 * ###### Signature:
 * `Optional<Value>` -> `Out?`
 *
 * ###### EN:
 * Transforms to the value [Out]. Applies the [transform] function if [Optional.Some], or `null` if [Optional.None].
 *
 * ###### RU:
 * Трансформирует в значение [Out]. Применяет функцию [transform] если [Optional.Some], или `null` если [Optional.None].
 *
 * ###### Example:
 * ```
 * Some(Unit).fold { "foo" } // Result: "foo"
 * None.fold { "foo" } // Result: null
 * ```
 */
public inline fun <Value, Out> Optional<Value>.fold(transform: (Value) -> Out): Out? {
    contract { callsInPlace(transform, InvocationKind.AT_MOST_ONCE) }
    return if (this.isSome()) transform(some) else null
}

/**
 * ###### Signature:
 * `Optional<Value>` -> `Out`
 *
 * ###### EN:
 * Transforms to the value [Out]. Applies the [someTransform] function if [Optional.Some],
 * or the [noneTransform] function if [Optional.None].
 *
 * ###### RU:
 * Трансформирует в значение [Out]. Применяет функцию [someTransform] если [Optional.Some],
 * или функцию [noneTransform] если [Optional.None].
 *
 * ###### Example:
 * ```
 * Some(Unit).fold(
 *   someTransform = { "foo" },
 *   noneTransform = { "bar" },
 * ) // Result: "foo"
 *
 * None.fold(
 *   someTransform = { "foo" },
 *   noneTransform = { "bar" },
 * ) // Result: "bar"
 * ```
 */
public inline fun <Value, Out> Optional<Value>.fold(
    someTransform: (Value) -> Out,
    noneTransform: () -> Out,
): Out {
    contract {
        callsInPlace(someTransform, InvocationKind.AT_MOST_ONCE)
        callsInPlace(noneTransform, InvocationKind.AT_MOST_ONCE)
    }
    return if (this.isSome()) someTransform(some) else noneTransform()
}


/**
 * ###### Signature:
 * `Optional<Value>` -> `Optional<Out>`
 *
 * ###### EN:
 * Transforms to `Optional<Out>` by applying the [transform] function if [Optional.Some].
 *
 * ###### RU:
 * Трансформирует в `Optional<Out>`, применяя функцию [transform] если [Optional.Some].
 *
 * ###### Example:
 * ```
 * Some(Unit).flatMap { Optional.Some("bar") } // Result: Some("bar")
 * Some(Unit).flatMap { Optional.None } // Result: None
 *
 * None.flatMap { Optional.Some("bar") } // Result: None
 * None.flatMap { Optional.None } // Result: None
 * ```
 */
public inline fun <Value, Out> Optional<Value>.flatMap(transform: (Value) -> Optional<Out>): Optional<Out> {
    contract { callsInPlace(transform, InvocationKind.AT_MOST_ONCE) }
    return when (this) {
        Optional.None -> Optional.None
        is Optional.Some -> transform(some)
    }
}

/**
 * ###### Signature:
 * `Optional<Optional<Value>>` -> `Optional<Value>`
 *
 * ###### EN:
 * Reduces nesting for [Optional.Some].
 *
 * ###### RU:
 * Уменьшает вложенность для [Optional.Some].
 *
 * ###### Example:
 * ```
 * Some(Some(Unit)).flatten() // Result: Some(Unit)
 * ```
 */
public fun <Value> Optional<Optional<Value>>.flatten(): Optional<Value> = flatMap { it }
