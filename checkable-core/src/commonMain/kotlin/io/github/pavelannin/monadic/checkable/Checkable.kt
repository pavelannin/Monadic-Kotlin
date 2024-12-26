package io.github.pavelannin.monadic.checkable

import io.github.pavelannin.monadic.checkable.serialization.CheckableSerializer
import kotlinx.serialization.Serializable
import kotlin.contracts.contract

/**
 * ###### EN:
 * The [Checkable] type is a functional construct used to extend the [Value] type with
 * states: checked (marked) [Checked] or Unchecked [Unchecked].
 *
 * [Checkable] is convenient to use at the level of business logic and application view states.
 *
 * ###### RU:
 * Тип [Checkable] - это функциональная конструкция, используемая для расширения типа [Value]
 * состояниями: проверенно (отмеченно) [Checked] или непроверенно (неотмеченно) [Unchecked].
 *
 * [Checkable] удабно использовать на уровне бизнеслогики и view стейтов приложений.
 *
 * ###### Example:
 * ```
 * val checked = Checkable.Checked(Unit)
 * val unchecked = Checkable.Unchecked(Unit)
 * ```
 */
@Serializable(CheckableSerializer::class)
public sealed interface Checkable<out Value> {
    public val checkable: Value

    public data class Checked<out T>(public override val checkable: T) : Checkable<T> {
        public companion object {
            public operator fun invoke(): Checked<Unit> {
                return Checked(Unit)
            }
        }
    }

    public data class Unchecked<out T>(public override val checkable: T) : Checkable<T> {
        public companion object {
            public operator fun invoke(): Unchecked<Unit> {
                return Unchecked(Unit)
            }
        }
    }

    public companion object {
        /**
         * ###### EN:
         * Returns [Checkable] with the value [value].
         *
         * ###### RU:
         * Возвращает [Checkable] со значением [value].
         *
         * ###### Example:
         * ```
         * Checkable(true, Unit) // Result: Checked(Unit)
         * Checkable(false, Unit) // Result: Unchecked(Unit)
         * ```
         */
        public operator fun <Value> invoke(checked: Boolean, value: Value): Checkable<Value> {
            return if (checked) Checked(value) else Unchecked(value)
        }
    }
}

/**
 * ###### EN:
 * Returns `true` if it is [Checkable.Checked], or `false` otherwise.
 *
 * ###### RU:
 * Возвращает значение `true`, если [Checkable.Checked], в противном случае `false`.
 *
 * ###### Example:
 * ```
 * Checked(Unit).isChecked // Result: true
 * Unchecked(Unit).isChecked // Result: false
 * ```
 */
public fun <Value> Checkable<Value>.isChecked(): Boolean {
    contract {
        returns(true) implies (this@isChecked is Checkable.Checked<Value>)
        returns(false) implies (this@isChecked is Checkable.Unchecked<Value>)
    }
    return this is Checkable.Checked
}
