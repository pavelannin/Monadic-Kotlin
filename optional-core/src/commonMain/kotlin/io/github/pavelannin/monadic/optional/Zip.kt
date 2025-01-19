package io.github.pavelannin.monadic.optional

import kotlin.contracts.InvocationKind
import kotlin.contracts.contract


/**
 * ###### Signature:
 * `Optional<Value1>` & `Optional<Value2>` -> `Optional<Out>`
 *
 * ###### EN:
 * Combines two [Optional.Some] to a single value by applying the [transform] function.
 *
 * ###### RU:
 * Объединяет два [Optional.Some] в одно значение, применяя функцию [transform].
 *
 * ###### Example:
 * ```
 * Some(1).zip(Some(1)) { r1, r2 -> r1 + r2 } // Result: Some(2)
 *
 * Some(1).zip(None) { r1, r2 -> r1 + r2 } // Result: None
 * None.zip(Some(1)) { r1, r2 -> r1 + r2 } // Result: None
 * None.zip(None) { r1, r2 -> r1 + r2 } // Result: None
 * ```
 */
public inline fun <Value1, Value2, Out> Optional<Value1>.zip(
    optional: Optional<Value2>,
    transform: (Value1, Value2) -> Out,
): Optional<Out> {
    contract { callsInPlace(transform, InvocationKind.AT_MOST_ONCE) }
    return flatMap { value1 -> optional.map { value2 -> transform(value1, value2) } }
}

/**
 * ###### Signature:
 * `Optional<Value1>` & `Optional<Value2>` -> `Optional<Pair<Value1, Value2>>`
 *
 * ###### EN:
 * Combines two [Optional.Some] to a single value.
 *
 * ###### RU:
 * Объединяет два [Optional.Some] в одно значение.
 */
public fun <Value1, Value2> Optional<Value1>.zip(
    optional: Optional<Value2>,
): Optional<Pair<Value1, Value2>> = zip(optional, ::Pair)

/**
 * ###### Signature:
 * `Optional<Value1>` & `Optional<Value2>` & `Optional<Value3>` -> `Optional<Out>`
 *
 * ###### EN:
 * Combines three [Optional.Some] to a single value by applying the [transform] function.
 *
 * ###### RU:
 * Объединяет three [Optional.Some] в одно значение, применяя функцию [transform].
 */
public inline fun <Value1, Value2, Value3, Out> Optional<Value1>.zip(
    optional2: Optional<Value2>,
    optional3: Optional<Value3>,
    transform: (Value1, Value2, Value3) -> Out,
): Optional<Out> {
    contract { callsInPlace(transform, InvocationKind.AT_MOST_ONCE) }
    return flatMap { value1 ->
        optional2.flatMap { value2 ->
            optional3.map { value3 ->
                transform(value1, value2, value3)
            }
        }
    }
}

/**
 * ###### Signature:
 * `Optional<Value1>` & `Optional<Value2>` & `Optional<Value3>` -> `Optional<Triple<Value1, Value2, Value3>>`
 *
 * ###### EN:
 * Combines three [Optional.Some] to a single value.
 *
 * ###### RU:
 * Объединяет three [Optional.Some] в одно значение.
 */
public fun <Value1, Value2, Value3> Optional<Value1>.zip(
    optional2: Optional<Value2>,
    optional3: Optional<Value3>,
): Optional<Triple<Value1, Value2, Value3>> = zip(optional2, optional3, ::Triple)

/**
 * ###### Signature:
 * `Optional<Value1>` & `Optional<Value2>` & `Optional<Value3>` & `Optional<Value4>` -> `Optional<Out>`
 *
 * ###### EN:
 * Combines four [Optional.Some] to a single value by applying the [transform] function.
 *
 * ###### RU:
 * Объединяет four [Optional.Some] в одно значение, применяя функцию [transform].
 */
public inline fun <Value1, Value2, Value3, Value4, Out> Optional<Value1>.zip(
    optional2: Optional<Value2>,
    optional3: Optional<Value3>,
    optional4: Optional<Value4>,
    transform: (Value1, Value2, Value3, Value4) -> Out,
): Optional<Out> {
    contract { callsInPlace(transform, InvocationKind.AT_MOST_ONCE) }
    return flatMap { value1 ->
        optional2.flatMap { value2 ->
            optional3.flatMap { value3 ->
                optional4.map { value4 ->
                    transform(value1, value2, value3, value4)
                }
            }
        }
    }
}

/**
 * ###### Signature:
 * `Optional<Value1>` & `Optional<Value2>` & `Optional<Value3>` & `Optional<Value4>`
 * & `Optional<Value5>` -> `Optional<Out>`
 *
 * ###### EN:
 * Combines five [Optional.Some] to a single value by applying the [transform] function.
 *
 * ###### RU:
 * Объединяет five [Optional.Some] в одно значение, применяя функцию [transform].
 */
public inline fun <Value1, Value2, Value3, Value4, Value5, Out> Optional<Value1>.zip(
    optional2: Optional<Value2>,
    optional3: Optional<Value3>,
    optional4: Optional<Value4>,
    optional5: Optional<Value5>,
    transform: (Value1, Value2, Value3, Value4, Value5) -> Out,
): Optional<Out> {
    contract { callsInPlace(transform, InvocationKind.AT_MOST_ONCE) }
    return flatMap { value1 ->
        optional2.flatMap { value2 ->
            optional3.flatMap { value3 ->
                optional4.flatMap { value4 ->
                    optional5.map { value5 ->
                        transform(value1, value2, value3, value4, value5)
                    }
                }
            }
        }
    }
}
