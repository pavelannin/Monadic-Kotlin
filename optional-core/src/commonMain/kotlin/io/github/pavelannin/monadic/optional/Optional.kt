package io.github.pavelannin.monadic.optional

import io.github.pavelannin.monadic.optional.Optional.None
import io.github.pavelannin.monadic.optional.Optional.Some
import io.github.pavelannin.monadic.optional.serialization.OptionalSerializer
import kotlinx.serialization.Serializable
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * ###### EN:
 * The [Optional] monad is a functional construct used for a value that can be `null'.
 *
 * ###### RU:
 * Монада [Optional] - это функциональная конструкция, используемая для значения, которое может быть `null`.
 *
 * ###### Example:
 * ```
 * val some = Optional.Some(1)
 * val none = Optional.None
 * ```
 */
@Serializable(OptionalSerializer::class)
public sealed class Optional<out Value> {
    public data class Some<out T>(public val some: T) : Optional<T>()
    public data object None : Optional<Nothing>()

    /**
     * ###### EN:
     * Returns `true` if it is [Some], or `false` otherwise.
     *
     * ###### RU:
     * Возвращает значение `true`, если [Some], в противном случае `false`.
     *
     * ###### Example:
     * ```
     * Some(Unit).isSome // Result: true
     * None.isSome // Result: false
     * ```
     */
    @OptIn(ExperimentalContracts::class)
    public fun isSome(): Boolean {
        contract {
            returns(true) implies (this@Optional is Some<Value>)
            returns(false) implies (this@Optional is None)
        }
        return this is Some
    }

    /**
     * ###### EN:
     * Returns `true` if it is [None], or `false` otherwise.
     *
     * ###### RU:
     * Возвращает значение `true`, если [None], в противном случае `false`.
     *
     * ###### Example:
     * ```
     * Some(Unit).isSome // Result: false
     * None.isSome // Result: true
     * ```
     */
    @OptIn(ExperimentalContracts::class)
    public fun isNone(): Boolean {
        contract {
            returns(true) implies (this@Optional is None)
            returns(false) implies (this@Optional is Some<Value>)
        }
        return this is None
    }

    /**
     * ###### EN:
     * Returns `value` if it is [Some], or `null` otherwise.
     *
     * ###### RU:
     * Возвращает значение `value`, если [Some], в противном случае `null`.
     *
     * ###### Example:
     * ```
     * Some(Unit).getOrNull() // Result: Unit
     * None.getOrNull() // Result: null
     * ```
     */
    public fun getOrNull(): Value? {
        return if (this.isSome()) some else null
    }

    /**
     * ###### EN:
     * Calls the [block] function if [Some].
     *
     * ###### RU:
     * Вызывает функцию [block] если [Some].
     *
     * ###### Example:
     * ```
     * Some(Unit).onSome { print("Hello") } // Log: Hello
     * None.onSome { print("Hello") }
     * ```
     */
    @OptIn(ExperimentalContracts::class)
    public inline fun onSome(block: (Value) -> Unit): Optional<Value> {
        contract { callsInPlace(block, InvocationKind.AT_MOST_ONCE) }
        if (this.isSome()) block(some)
        return this
    }

    /**
     * ###### EN:
     * Calls the [block] function if [None].
     *
     * ###### RU:
     * Вызывает функцию [block] если [None].
     *
     * ###### Example:
     * ```
     * Some(Unit).onNone { print("Hello") }
     * None.onNone { print("Hello") } // Log: Hello
     * ```
     */
    @OptIn(ExperimentalContracts::class)
    public inline fun onNone(block: () -> Unit): Optional<Value> {
        contract { callsInPlace(block, InvocationKind.AT_MOST_ONCE) }
        if (this.isNone()) block()
        return this
    }

    /**
     * ###### Signature:
     * `Optional<Value>` -> `Optional<Out>`
     *
     * ###### EN:
     * Transforms to `Optional<Out>` by applying the [transform] function if [Some].
     *
     * ###### RU:
     * Трансформирует в `Optional<Out>`, применяя функцию [transform] если [Some].
     *
     * ###### Example:
     * ```
     * Some(Unit).map { "foo" } // Result: Some("foo")
     * None.map { "foo" }  // Result: None
     * ```
     */
    @OptIn(ExperimentalContracts::class)
    public inline fun <Out> map(transform: (Value) -> Out): Optional<Out> {
        contract { callsInPlace(transform, InvocationKind.AT_MOST_ONCE) }
        return if (this.isSome()) Some(transform(some)) else this
    }

    /**
     * ###### Signature:
     * `Optional<Value>` -> `Result?`
     *
     * ###### EN:
     * Transforms to the value [Result]. Applies the [transform] function if [Some], or `null` if [None].
     *
     * ###### RU:
     * Трансформирует в значение [Result]. Применяет функцию [transform] если [Some], или `null` если [None].
     *
     * ###### Example:
     * ```
     * Some(Unit).fold { "foo" } // Result: "foo"
     * None.fold { "foo" } // Result: null
     * ```
     */
    @OptIn(ExperimentalContracts::class)
    public inline fun <Result> fold(transform: (Value) -> Result): Result? {
        contract { callsInPlace(transform, InvocationKind.AT_MOST_ONCE) }
        return if (this.isSome()) transform(some) else null
    }

    /**
     * ###### Signature:
     * `Optional<Value>` -> `Result`
     *
     * ###### EN:
     * Transforms to the value [Result]. Applies the [someTransform] function if [Some],
     * or the [noneTransform] function if [None].
     *
     * ###### RU:
     * Трансформирует в значение [Result]. Применяет функцию [someTransform] если [Some],
     * или функцию [noneTransform] если [None].
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
    @OptIn(ExperimentalContracts::class)
    public inline fun <Result> fold(
        someTransform: (Value) -> Result,
        noneTransform: () -> Result,
    ): Result {
        contract {
            callsInPlace(someTransform, InvocationKind.AT_MOST_ONCE)
            callsInPlace(noneTransform, InvocationKind.AT_MOST_ONCE)
        }
        return if (this.isSome()) someTransform(some) else noneTransform()
    }

    /**
     * ###### EN:
     * Returns [Some] if the value of [Some] and [predicate] is true, otherwise [None].
     *
     * ###### RU:
     * Возвращает [Some] если значение [Some] и [predicate] истинный, иначе [None].
     *
     * ###### Example:
     * ```
     * Some(Unit).filter { true } // Result: Some(Unit)
     * Some(Unit).filter { false } // Result: None
     * None.filter { true } // Result: None
     * None.filter { false } // Result: None
     * ```
     */
    @OptIn(ExperimentalContracts::class)
    public inline fun filter(predicate: (Value) -> Boolean): Optional<Value> {
        contract { callsInPlace(predicate, InvocationKind.AT_MOST_ONCE) }
        return flatMap { value -> if (predicate(value)) Some(value) else None }
    }

    /**
     * ###### EN:
     * Returns [Some] if the value of [Some] and [predicate] is false, otherwise [None].
     *
     * ###### RU:
     * Возвращает [Some] если значение [Some] и [predicate] ложный, иначе [None].
     *
     * ###### Example:
     * ```
     * Some(Unit).filter { false } // Result: Some(Unit)
     * Some(Unit).filter { true } // Result: None
     * None.filter { true } // Result: None
     * None.filter { false } // Result: None
     * ```
     */
    @OptIn(ExperimentalContracts::class)
    public inline fun filterNot(predicate: (Value) -> Boolean): Optional<Value> {
        contract { callsInPlace(predicate, InvocationKind.AT_MOST_ONCE) }
        return flatMap { value-> if (!predicate(value)) Some(value) else None }
    }

    public companion object {
        /**
         * ###### EN:
         * Returns [Some] if [value] is not `null', otherwise [None].
         *
         * ###### RU:
         * Возвращает [Some] если [value] не равно `null`, иначе [None].
         *
         * ###### Example:
         * ```
         * Optional.fromNullable(Unit) // Result: Some(Unit)
         * Optional.fromNullable(null) // Result: None
         * ```
         */
        public fun <Value> fromNullable(value: Value?): Optional<Value> {
            return if (value != null) Some(value) else None
        }

        /**
         * ###### EN:
         * Returns [Some] with the value [value].
         *
         * ###### RU:
         * Возвращает [Some] со значением [value].
         *
         * ###### Example:
         * ```
         * Optional(Unit) // Result: Some(Unit)
         * Optional(null) // Result: Some(null)
         * ```
         */
        public operator fun <Value> invoke(value: Value): Optional<Value> {
            return Some(value)
        }

        /**
         * ###### EN:
         * Wraps the execution of [block] in a 'try-catch'. [None] is returned if [block] returned an exception.
         *
         * ###### RU:
         * Оборачивает выполнение [block] в `try-catch`. Возвращается [None] если [block] вернул исключение.
         *
         * ###### Example:
         * ```
         * Optional.catch { Unit } // Result: Some(Unit)
         * Optional.catch { error("all") } // Result: None
         * ```
         */
        public inline fun <Value> catch(block: () -> Value): Optional<Value> {
            return try {
                Some(block())
            } catch (ignore: Throwable) {
                None
            }
        }
    }
}

/**
 * ###### Signature:
 * `Optional<In>` -> `Optional<Out>`
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
@OptIn(ExperimentalContracts::class)
public inline fun <In, Out> Optional<In>.flatMap(transform: (In) -> Optional<Out>): Optional<Out> {
    contract { callsInPlace(transform, InvocationKind.AT_MOST_ONCE) }
    return when (this) {
        Optional.None -> Optional.None
        is Optional.Some -> transform(some)
    }
}

/**
 * ###### Signature:
 * `Optional<Value1>` & `Optional<Value2>` -> `Optional<Result>`
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
@OptIn(ExperimentalContracts::class)
public inline fun <Value1, Value2, Result> Optional<Value1>.zip(
    optional: Optional<Value2>,
    transform: (Value1, Value2) -> Result,
): Optional<Result> {
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
 * `Optional<Value1>` & `Optional<Value2>` & `Optional<Value3>` -> `Optional<Result>`
 *
 * ###### EN:
 * Combines three [Optional.Some] to a single value by applying the [transform] function.
 *
 * ###### RU:
 * Объединяет three [Optional.Some] в одно значение, применяя функцию [transform].
 */
@OptIn(ExperimentalContracts::class)
public inline fun <Value1, Value2, Value3, Result> Optional<Value1>.zip(
    optional2: Optional<Value2>,
    optional3: Optional<Value3>,
    transform: (Value1, Value2, Value3) -> Result,
): Optional<Result> {
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
 * `Optional<Value1>` & `Optional<Value2>` & `Optional<Value3>` & `Optional<Value4>` -> `Optional<Result>`
 *
 * ###### EN:
 * Combines four [Optional.Some] to a single value by applying the [transform] function.
 *
 * ###### RU:
 * Объединяет four [Optional.Some] в одно значение, применяя функцию [transform].
 */
@OptIn(ExperimentalContracts::class)
public inline fun <Value1, Value2, Value3, Value4, Result> Optional<Value1>.zip(
    optional2: Optional<Value2>,
    optional3: Optional<Value3>,
    optional4: Optional<Value4>,
    transform: (Value1, Value2, Value3, Value4) -> Result,
): Optional<Result> {
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
 * & `Optional<Value5>` -> `Optional<Result>`
 *
 * ###### EN:
 * Combines five [Optional.Some] to a single value by applying the [transform] function.
 *
 * ###### RU:
 * Объединяет five [Optional.Some] в одно значение, применяя функцию [transform].
 */
@OptIn(ExperimentalContracts::class)
public inline fun <Value1, Value2, Value3, Value4, Value5, Result> Optional<Value1>.zip(
    optional2: Optional<Value2>,
    optional3: Optional<Value3>,
    optional4: Optional<Value4>,
    optional5: Optional<Value5>,
    transform: (Value1, Value2, Value3, Value4, Value5) -> Result,
): Optional<Result> {
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

/**
 * ###### EN:
 * Returns [Some] if the value is [Some] and the type belongs to [T], otherwise [None].
 *
 * ###### RU:
 * Возвращает [Some] если значение [Some] и тип принадлежит [T], иначе [None].
 *
 * ###### Example:
 * ```
 * Some(Unit).filter<Unit>() // Result: Some(Unit)
 * Some(Unit).filter<String>() // Result: None
 * None.filter<Unit>() // Result: None
 * ```
 */
public inline fun <reified T> Optional<*>.filterIsInstance(): Optional<T> {
    return flatMap { value -> if (value is T) Optional.Some(value) else Optional.None }
}
