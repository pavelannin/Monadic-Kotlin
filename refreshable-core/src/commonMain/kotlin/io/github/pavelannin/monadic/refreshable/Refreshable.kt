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
 * ###### EN:
 * Returns `true` if it is [Refreshable.Refreshing], or `false` otherwise.
 *
 * ###### RU:
 * Возвращает значение `true`, если [Refreshable.Refreshing], в противном случае `false`.
 *
 * ###### Example:
 * ```
 * Refreshing(Unit).isRefreshing // Result: true
 * Refreshed(Unit).isRefreshing // Result: false
 * ```
 */
public fun <Value> Refreshable<Value>.isRefreshing(): Boolean {
    contract {
        returns(true) implies (this@isRefreshing is Refreshable.Refreshing<Value>)
        returns(false) implies (this@isRefreshing is Refreshable.Refreshed<Value>)
    }
    return this is Refreshable.Refreshing
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
