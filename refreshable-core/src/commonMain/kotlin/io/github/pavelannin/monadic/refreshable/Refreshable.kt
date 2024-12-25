package io.github.pavelannin.monadic.refreshable

import io.github.pavelannin.monadic.refreshable.serialization.RefreshableSerializer
import kotlinx.serialization.Serializable
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * ###### EN:
 * The [Refreshable] type is a functional construct used to extend the [Value] type with states:
 * refreshing [Refreshing] or refreshed [Refreshed].
 *
 * [Refreshable] is convenient to use at the level of business logic and application view states.
 *
 * ###### RU:
 * Тип [Refreshable] - это функциональная конструкция, используемая для расширения типа [Value]
 * состояниями: обновляется [Refreshing] или обновленно [Refreshed].
 *
 * [Refreshable] удабно использовать на уровне бизнеслогики и view стейтов приложений.
 *
 * ###### Example:
 * ```
 * val refreshing = Refreshable.Refreshing(Unit)
 * val refreshed = Refreshable.Refreshed(Unit)
 * ```
 */
@Serializable(RefreshableSerializer::class)
public sealed class Refreshable<out Value> {
    public abstract val refreshable: Value

    public data class Refreshing<out T>(public override val refreshable: T) : Refreshable<T>() {
        public companion object {
            public operator fun invoke(): Refreshing<Unit> {
                return Refreshing(Unit)
            }
        }
    }

    public data class Refreshed<out T>(public override val refreshable: T) : Refreshable<T>() {
        public companion object {
            public operator fun invoke(): Refreshed<Unit> {
                return Refreshed(Unit)
            }
        }
    }

    /**
     * ###### EN:
     * Returns `true` if it is [Refreshing], or `false` otherwise.
     *
     * ###### RU:
     * Возвращает значение `true`, если [Refreshing], в противном случае `false`.
     *
     * ###### Example:
     * ```
     * Refreshing(Unit).isRefreshing // Result: true
     * Refreshed(Unit).isRefreshing // Result: false
     * ```
     */
    public fun isRefreshing(): Boolean {
        contract {
            returns(true) implies (this@Refreshable is Refreshing<Value>)
            returns(false) implies (this@Refreshable is Refreshed<Value>)
        }
        return this is Refreshing
    }

    /**
     * ###### EN:
     * Calls the [block] function if [Refreshing].
     *
     * ###### RU:
     * Вызывает функцию [block] если [Refreshing].
     *
     * ###### Example:
     * ```
     * Refreshable(Unit).onRefreshing { print("Hello") } // Log: Hello
     * Refreshable(Unit).onRefreshing { print("Hello") }
     * ```
     */
    public inline fun onRefreshing(block: (Value) -> Unit): Refreshable<Value> {
        contract { callsInPlace(block, InvocationKind.AT_MOST_ONCE) }
        if (this.isRefreshing()) block(refreshable)
        return this
    }

    /**
     * ###### EN:
     * Calls the [block] function if [Refreshed].
     *
     * ###### RU:
     * Вызывает функцию [block] если [Refreshed].
     *
     * ###### Example:
     * ```
     * Refreshable(Unit).onRefreshed { print("Hello") }
     * Refreshable(Unit).onRefreshed { print("Hello") } // Log: Hello
     * ```
     */
    public inline fun onRefreshed(block: (Value) -> Unit): Refreshable<Value> {
        contract { callsInPlace(block, InvocationKind.AT_MOST_ONCE) }
        if (!this.isRefreshing()) block(refreshable)
        return this
    }

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
    public inline fun <Out> map(transform: (isRefreshing: Boolean, Value) -> Out): Refreshable<Out> {
        contract { callsInPlace(transform, InvocationKind.EXACTLY_ONCE) }
        val refreshing = isRefreshing()
        return Refreshable(refreshing, transform(refreshing, refreshable))
    }

    /**
     * ###### Signature:
     * `Refreshable<Value>` -> `Refreshable<Out>`
     *
     * ###### EN:
     * Transforms to `Refreshable<Out>` by applying the [refreshingTransform] function if [Refreshing],
     * or the [refreshedTransform] function if [Refreshed].
     *
     * ###### RU:
     * Трансформирует в `Refreshable<Out>`, применяя функцию [refreshingTransform] если [Refreshing],
     * или функцию [refreshedTransform] если [Refreshed].
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
    public inline fun <Out> map(
        refreshingTransform: (Value) -> Out,
        refreshedTransform: (Value) -> Out,
    ): Refreshable<Out> {
        contract {
            callsInPlace(refreshingTransform, InvocationKind.AT_MOST_ONCE)
            callsInPlace(refreshedTransform, InvocationKind.AT_MOST_ONCE)
        }
        return if (isRefreshing()) Refreshing(refreshingTransform(refreshable)) else Refreshed(refreshedTransform(refreshable))
    }

    /**
     * ###### Signature:
     * `Refreshable<Value>` -> `Result`
     *
     * ###### EN:
     * Transforms to the value [Result]. Applies the [transform] function.
     *
     * ###### RU:
     * Трансформирует в значение [Result]. Применяет функцию [transform].
     *
     * ###### Example:
     * ```
     * Refreshing(Unit).fold { _, _ -> "foo" } // Result: "foo"
     * Refreshed(Unit).fold { _, _ -> "foo" } // Result: "foo"
     * ```
     */
    public inline fun <Result> fold(transform: (isRefreshing: Boolean, Value) -> Result): Result {
        contract { callsInPlace(transform, InvocationKind.EXACTLY_ONCE) }
        return transform(isRefreshing(), refreshable)
    }

    /**
     * ###### Signature:
     * `Refreshable<Value>` -> `Result`
     *
     * ###### EN:
     * Transforms to the value [Result]. Applies the [refreshingTransform] function if [Refreshing],
     * or the [refreshedTransform] function if [Refreshed].
     *
     * ###### RU:
     * Трансформирует в значение [Result]. Применяет функцию [refreshingTransform] если [Refreshing],
     * или функцию [refreshedTransform] если [Refreshed].
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
    public inline fun <Result> fold(
        refreshingTransform: (Value) -> Result,
        refreshedTransform: (Value) -> Result,
    ): Result {
        contract {
            callsInPlace(refreshingTransform, InvocationKind.AT_MOST_ONCE)
            callsInPlace(refreshedTransform, InvocationKind.AT_MOST_ONCE)
        }
        return if (this.isRefreshing()) refreshingTransform(refreshable) else refreshedTransform(refreshable)
    }

    /**
     * ###### Signature:
     * `Refreshable<Value>` -> `Refreshing<Value>`
     *
     * ###### EN:
     * Transforms to `Refreshing<Value>`.
     *
     * ###### RU:
     * Трансформирует в `Refreshing<Value>`.
     *
     * ###### Example:
     * ```
     * Refreshing(Unit).toRefreshing() // Result: Refreshing(Unit)
     * Refreshed(Unit).toRefreshing() // Result: Refreshing(Unit)
     * ```
     */
    public fun toRefreshing(): Refreshing<Value> {
        return Refreshing(refreshable)
    }

    /**
     * ###### Signature:
     * `Refreshable<Value>` -> `Refreshed<Value>`
     *
     * ###### EN:
     * Transforms to `Refreshed<Value>`.
     *
     * ###### RU:
     * Трансформирует в `Refreshed<Value>`.
     *
     * ###### Example:
     * ```
     * Refreshing(Unit).toRefreshed() // Result: Refreshed(Unit)
     * Refreshed(Unit).toRefreshed() // Result: Refreshed(Unit)
     * ```
     */
    public fun toRefreshed(): Refreshed<Value> {
        return Refreshed(refreshable)
    }

    public companion object {
        /**
         * ###### EN:
         * Returns [Refreshable] with the value [value].
         *
         * ###### RU:
         * Возвращает [Refreshable] со значением [value].
         *
         * ###### Example:
         * ```
         * Refreshable(true, Unit) // Result: Refreshing(Unit)
         * Refreshable(false, Unit) // Result: Refreshed(Unit)
         * ```
         */
        public operator fun <Value> invoke(refreshing: Boolean, value: Value): Refreshable<Value> {
            return if (refreshing) Refreshing(value) else Refreshed(value)
        }
    }
}

/**
 * ###### Signature:
 * `Refreshable<In>` -> `Refreshable<Out>`
 *
 * ###### EN:
 * Transforms to `Refreshable<Out>` by applying the [transform] function.
 *
 * ###### RU:
 * Трансформирует в `Refreshable<Out>`, применяя функцию [transform].
 *
 * ###### Example:
 * ```
 * Refreshing(Unit).flatMap { _, _ -> Refreshing("bar") } // Result: Refreshing("bar")
 * Refreshing(Unit).flatMap { Unchecked("foo") } // Result: Refreshed("foo")
 *
 * Refreshed(Unit).flatMap { _, _ -> Refreshing("bar") } // Result: Refreshing("bar")
 * Refreshed(Unit).flatMap { _, _ -> Refreshed("foo") } // Result: Refreshed("foo")
 * ```
 */
public inline fun <In, Out> Refreshable<In>.flatMap(
    transform: (isChecked: Boolean, In) -> Refreshable<Out>,
): Refreshable<Out> {
    contract { callsInPlace(transform, InvocationKind.EXACTLY_ONCE) }
    return transform(isRefreshing(), refreshable)
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
public inline fun <In, Out> Refreshable<In>.flatMap(
    refreshingTransform: (In) -> Refreshable<Out>,
    refreshedTransform: (In) -> Refreshable<Out>,
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
