package io.github.pavelannin.monadic.optional

import io.github.pavelannin.monadic.optional.serialization.OptionalSerializer
import kotlinx.serialization.Serializable
import kotlin.contracts.contract
import kotlin.native.ObjCName

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
@ObjCName("MonadicOptional")
public sealed interface Optional<out Value> {
    public data class Some<out T>(public val some: T) : Optional<T> {
        public companion object {
            public operator fun invoke(): Some<Unit> = Some(Unit)
        }
    }
    public data object None : Optional<Nothing>

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
 * ###### EN:
 * Returns `true` if it is [Optional.Some], or `false` otherwise.
 *
 * ###### RU:
 * Возвращает значение `true`, если [Optional.Some], в противном случае `false`.
 *
 * ###### Example:
 * ```
 * Some(Unit).isSome // Result: true
 * None.isSome // Result: false
 * ```
 */
public fun <Value> Optional<Value>.isSome(): Boolean {
    contract {
        returns(true) implies (this@isSome is Optional.Some<Value>)
        returns(false) implies (this@isSome is Optional.None)
    }
    return this is Optional.Some
}

/**
 * ###### EN:
 * Returns `true` if it is [Optional.None], or `false` otherwise.
 *
 * ###### RU:
 * Возвращает значение `true`, если [Optional.None], в противном случае `false`.
 *
 * ###### Example:
 * ```
 * Some(Unit).isSome // Result: false
 * None.isSome // Result: true
 * ```
 */
public fun <Value> Optional<Value>.isNone(): Boolean {
    contract {
        returns(true) implies (this@isNone is Optional.None)
        returns(false) implies (this@isNone is Optional.Some<Value>)
    }
    return this is Optional.None
}
