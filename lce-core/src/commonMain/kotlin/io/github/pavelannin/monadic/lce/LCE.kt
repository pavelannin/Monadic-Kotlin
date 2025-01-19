package io.github.pavelannin.monadic.lce

import kotlinx.serialization.Serializable
import kotlin.Boolean
import kotlin.Nothing
import kotlin.Unit
import kotlin.contracts.contract
import kotlin.native.ObjCName

/**
 * ###### EN:
 * The [LCE] type is a functional construct used to handle the state of an asynchronous
 * operation (network request, database operation, or file storage operation).
 * Contains the values [Loading] (for loading), [Content] (for a successful value) and [Error] (for errors).
 *
 * The [LCE] type allows you to process all the values of an asynchronous operation in one type,
 * applicable to the View state in MVI approaches.
 *
 * ###### RU:
 * Тип [LCE] - это функциональная конструкция, используемая для обработки состояния асинхронной
 * операции (сетевой запрос, работа с БД или работа с файловым хранилищем).
 * Собержит значения [Loading] (для загрузки), [Content] (для успешного значения) и [Error] (для ошибок).
 *
 * Тип [LCE] позволяет обрабатывать все значения асинхронной операции в одном типе, применим для состояния View в MVI подходах.
 *
 * ###### Example:
 * ```
 * val loading = LCE.Loading(1)
 * val content = LCE.Content(1)
 * val error = LCE.Error(1)
 * ```
 */
@Serializable(LCESerializer::class)
@ObjCName("MonadicLCE")
public sealed interface LCE<out Loading, out Content, out Error> {
    public data class Loading<out T>(public val loading: T) : LCE<T, Nothing, Nothing> {
        public companion object {
            public operator fun invoke(): Loading<Unit> = Loading(Unit)
        }
    }

    public data class Content<out T>(public val content: T) : LCE<Nothing, T, Nothing> {
        public companion object {
            public operator fun invoke(): Content<Unit> = Content(Unit)
        }
    }

    public data class Error<out T>(public val error: T) : LCE<Nothing, Nothing, T> {
        public companion object {
            public operator fun invoke(): Error<Unit> = Error(Unit)
        }
    }
}

/**
 * ###### EN:
 * Returns `true` if it is [LCE.Loading], or `false` otherwise.
 *
 * ###### RU:
 * Возвращает значение `true`, если [LCE.Loading], в противном случае `false`.
 *
 * ###### Example:
 * ```
 * Loading(1).isLoading // Result: true
 * Content(1).isLoading // Result: false
 * Error(1).isLoading // Result: false
 * ```
 */
public fun <L, C, E> LCE<L, C, E>.isLoading(): Boolean {
    contract { returns(true) implies (this@isLoading is LCE.Loading<L>) }
    return this is LCE.Loading
}

/**
 * ###### EN:
 * Returns `true` if it is [LCE.Content], or `false` otherwise.
 *
 * ###### RU:
 * Возвращает значение `true`, если [LCE.Content], в противном случае `false`.
 *
 * ###### Example:
 * ```
 * Loading(1).isContent // Result: false
 * Content(1).isContent // Result: true
 * Error(1).isContent // Result: false
 * ```
 */
public fun <L, C, E> LCE<L, C, E>.isContent(): Boolean {
    contract { returns(true) implies (this@isContent is LCE.Content<C>) }
    return this is LCE.Content
}

/**
 * ###### EN:
 * Returns `true` if it is [LCE.Error], or `false` otherwise.
 *
 * ###### RU:
 * Возвращает значение `true`, если [LCE.Error], в противном случае `false`.
 *
 * ###### Example:
 * ```
 * Loading(1).isError // Result: false
 * Content(1).isError // Result: false
 * Error(1).isError // Result: true
 * ```
 */
public fun  <L, C, E> LCE<L, C, E>.isError(): Boolean {
    contract { returns(true) implies (this@isError is LCE.Error<E>) }
    return this is LCE.Error
}

/**
 * ###### EN:
 * Returns the value `true` if [LCE.Content] or [LCE.Error], otherwise `false`.
 *
 * ###### RU:
 * Возвращает значение `true`, если [LCE.Content] или [LCE.Error], в противном случае `false`.
 *
 * ###### Example:
 * ```
 * Loading(1).isComplete // Result: false
 * Content(1).isComplete // Result: true
 * Error(1).isComplete // Result: true
 * ```
 */
public fun <L, C, E> LCE<L, C, E>.isComplete(): Boolean {
    contract { returns(false) implies (this@isComplete is LCE.Loading<L>) }
    return isContent() || isError()
}
