package io.github.pavelannin.monadic.optional

import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * ###### EN:
 * Returns [Optional.Some] if the value of [Optional.Some] and [predicate] is true, otherwise [Optional.None].
 *
 * ###### RU:
 * Возвращает [Optional.Some] если значение [Optional.Some] и [predicate] истинный, иначе [Optional.None].
 *
 * ###### Example:
 * ```
 * Some(Unit).filter { true } // Result: Some(Unit)
 * Some(Unit).filter { false } // Result: None
 * None.filter { true } // Result: None
 * None.filter { false } // Result: None
 * ```
 */
public inline fun <Value> Optional<Value>.filter(predicate: (Value) -> Boolean): Optional<Value> {
    contract { callsInPlace(predicate, InvocationKind.AT_MOST_ONCE) }
    return flatMap { value -> if (predicate(value)) Optional.Some(value) else Optional.None }
}

/**
 * ###### EN:
 * Returns [Optional.Some] if the value of [Optional.Some] and [predicate] is false, otherwise [Optional.None].
 *
 * ###### RU:
 * Возвращает [Optional.Some] если значение [Optional.Some] и [predicate] ложный, иначе [Optional.None].
 *
 * ###### Example:
 * ```
 * Some(Unit).filter { false } // Result: Some(Unit)
 * Some(Unit).filter { true } // Result: None
 * None.filter { true } // Result: None
 * None.filter { false } // Result: None
 * ```
 */
public inline fun <Value> Optional<Value>.filterNot(predicate: (Value) -> Boolean): Optional<Value> {
    contract { callsInPlace(predicate, InvocationKind.AT_MOST_ONCE) }
    return flatMap { value-> if (!predicate(value)) Optional.Some(value) else Optional.None }
}

/**
 * ###### EN:
 * Returns [Optional.Some] if the value is [Optional.Some] and the type belongs to [Out], otherwise [Optional.None].
 *
 * ###### RU:
 * Возвращает [Optional.Some] если значение [Optional.Some] и тип принадлежит [Out], иначе [Optional.None].
 *
 * ###### Example:
 * ```
 * Some(Unit).filter<Unit>() // Result: Some(Unit)
 * Some(Unit).filter<String>() // Result: None
 * None.filter<Unit>() // Result: None
 * ```
 */
public inline fun <reified Out> Optional<*>.filterIsInstance(): Optional<Out> {
    return flatMap { value -> if (value is Out) Optional.Some(value) else Optional.None }
}
