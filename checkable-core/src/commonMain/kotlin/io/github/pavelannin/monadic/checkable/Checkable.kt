package io.github.pavelannin.monadic.checkable

import io.github.pavelannin.monadic.checkable.serialization.CheckableSerializer
import kotlinx.serialization.Serializable
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
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
public sealed class Checkable<out Value> {
    public abstract val checkable: Value

    public data class Checked<out T>(public override val checkable: T) : Checkable<T>() {
        public companion object {
            public operator fun invoke(): Checked<Unit> {
                return Checked(Unit)
            }
        }
    }

    public data class Unchecked<out T>(public override val checkable: T) : Checkable<T>() {
        public companion object {
            public operator fun invoke(): Unchecked<Unit> {
                return Unchecked(Unit)
            }
        }
    }

    /**
     * ###### EN:
     * Returns `true` if it is [Checked], or `false` otherwise.
     *
     * ###### RU:
     * Возвращает значение `true`, если [Checked], в противном случае `false`.
     *
     * ###### Example:
     * ```
     * Checked(Unit).isChecked // Result: true
     * Unchecked(Unit).isChecked // Result: false
     * ```
     */
    @OptIn(ExperimentalContracts::class)
    public fun isChecked(): Boolean {
        contract {
            returns(true) implies (this@Checkable is Checked<Value>)
            returns(false) implies (this@Checkable is Unchecked<Value>)
        }
        return this is Checked
    }

    /**
     * ###### EN:
     * Calls the [block] function if [Checked].
     *
     * ###### RU:
     * Вызывает функцию [block] если [Checked].
     *
     * ###### Example:
     * ```
     * Checked(Unit).onChecked { print("Hello") } // Log: Hello
     * Unchecked(Unit).onChecked { print("Hello") }
     * ```
     */
    @OptIn(ExperimentalContracts::class)
    public inline fun onChecked(block: (Value) -> Unit): Checkable<Value> {
        contract { callsInPlace(block, InvocationKind.AT_MOST_ONCE) }
        if (this.isChecked()) block(checkable)
        return this
    }

    /**
     * ###### EN:
     * Calls the [block] function if [Unchecked].
     *
     * ###### RU:
     * Вызывает функцию [block] если [Unchecked].
     *
     * ###### Example:
     * ```
     * Checked(Unit).onUnchecked { print("Hello") }
     * Unchecked(Unit).onUnchecked { print("Hello") } // Log: Hello
     * ```
     */
    @OptIn(ExperimentalContracts::class)
    public inline fun onUnchecked(block: (Value) -> Unit): Checkable<Value> {
        contract { callsInPlace(block, InvocationKind.AT_MOST_ONCE) }
        if (!this.isChecked()) block(checkable)
        return this
    }

    /**
     * ###### Signature:
     * `Checkable<Value>` -> `Checkable<Out>`
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
    @OptIn(ExperimentalContracts::class)
    public inline fun <Out> map(transform: (isChecked: Boolean, Value) -> Out): Checkable<Out> {
        contract { callsInPlace(transform, InvocationKind.EXACTLY_ONCE) }
        val checked = isChecked()
        return Checkable(checked, transform(checked, checkable))
    }

    /**
     * ###### Signature:
     * `Checkable<Value>` -> `Checkable<Out>`
     *
     * ###### EN:
     * Transforms to `Checkable<Out>` by applying the [checkedTransform] function if [Checked],
     * or the [uncheckedTransform] function if [Unchecked].
     *
     * ###### RU:
     * Трансформирует в `Checkable<Out>`, применяя функцию [checkedTransform] если [Checked],
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
    @OptIn(ExperimentalContracts::class)
    public inline fun <Out> map(
        checkedTransform: (Value) -> Out,
        uncheckedTransform: (Value) -> Out,
    ): Checkable<Out> {
        contract {
            callsInPlace(checkedTransform, InvocationKind.AT_MOST_ONCE)
            callsInPlace(uncheckedTransform, InvocationKind.AT_MOST_ONCE)
        }
        return if (isChecked()) Checked(checkedTransform(checkable)) else Unchecked(uncheckedTransform(checkable))
    }

    /**
     * ###### Signature:
     * `Checkable<Value>` -> `Result`
     *
     * ###### EN:
     * Transforms to the value [Result]. Applies the [transform] function.
     *
     * ###### RU:
     * Трансформирует в значение [Result]. Применяет функцию [transform].
     *
     * ###### Example:
     * ```
     * Checked(Unit).fold { _, _ -> "foo" } // Result: "foo"
     * Unchecked(Unit).fold { _, _ -> "foo" } // Result: "foo"
     * ```
     */
    @OptIn(ExperimentalContracts::class)
    public inline fun <Result> fold(transform: (isChecked: Boolean, Value) -> Result): Result {
        contract { callsInPlace(transform, InvocationKind.EXACTLY_ONCE) }
        return transform(isChecked(), checkable)
    }

    /**
     * ###### Signature:
     * `Checkable<Value>` -> `Result`
     *
     * ###### EN:
     * Transforms to the value [Result]. Applies the [checkedTransform] function if [Checked],
     * or the [uncheckedTransform] function if [Unchecked].
     *
     * ###### RU:
     * Трансформирует в значение [Result]. Применяет функцию [checkedTransform] если [Checked],
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
    @OptIn(ExperimentalContracts::class)
    public inline fun <Result> fold(
        checkedTransform: (Value) -> Result,
        uncheckedTransform: (Value) -> Result,
    ): Result {
        contract {
            callsInPlace(checkedTransform, InvocationKind.AT_MOST_ONCE)
            callsInPlace(uncheckedTransform, InvocationKind.AT_MOST_ONCE)
        }
        return if (this.isChecked()) checkedTransform(checkable) else uncheckedTransform(checkable)
    }

    /**
     * ###### Signature:
     * `Checkable<Value>` -> `Checked<Value>`
     *
     * ###### EN:
     * Transforms to `Checked<Value>`.
     *
     * ###### RU:
     * Трансформирует в `Checked<Value>`.
     *
     * ###### Example:
     * ```
     * Checked(Unit).toChecked() // Result: Checked(Unit)
     * Unchecked(Unit).toChecked() // Result: Checked(Unit)
     * ```
     */
    public fun toChecked(): Checked<Value> {
        return Checked(checkable)
    }

    /**
     * ###### Signature:
     * `Checkable<Value>` -> `Unchecked<Value>`
     *
     * ###### EN:
     * Transforms to `Unchecked<Value>`.
     *
     * ###### RU:
     * Трансформирует в `Unchecked<Value>`.
     *
     * ###### Example:
     * ```
     * Checked(Unit).toUnchecked() // Result: Unchecked(Unit)
     * Unchecked(Unit).toUnchecked() // Result: Unchecked(Unit)
     * ```
     */
    public fun toUnchecked(): Unchecked<Value> {
        return Unchecked(checkable)
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
@OptIn(ExperimentalContracts::class)
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
@OptIn(ExperimentalContracts::class)
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
