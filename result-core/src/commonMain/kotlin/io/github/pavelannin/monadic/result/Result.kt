package io.github.pavelannin.monadic.result

import kotlinx.serialization.Serializable
import kotlin.contracts.contract
import kotlin.native.ObjCName

/**
 * ###### EN:
 * The [Result] monad is a functional construct that represents success ([Ok]) or failure ([Error]).
 *
 * ###### RU:
 * Монада [Result] - это функциональная конструкция, представляет успех ([Ok]) или неудачу ([Error]).
 *
 * ###### Example:
 * ```
 * val ok = Result.Ok(1)
 * val error = Result.Error(1)
 * ```
 */
@Serializable(ResultSerializer::class)
@ObjCName("MonadicResult")
public sealed interface Result<out Ok, out Error> {
    public data class Ok<out T>(public val ok: T) : Result<T, Nothing> {
        public companion object {
            public operator fun invoke(): Ok<Unit> {
                return Ok(Unit)
            }
        }
    }

    public data class Error<out T>(public val error: T) : Result<Nothing, T> {
        public companion object {
            public operator fun invoke(): Error<Unit> {
                return Error(Unit)
            }
        }
    }

    public companion object {
        /**
         * ###### EN:
         * Converts the transformation function [transform] into a [Result] structure that returns a polymorphic
         * function that can be applied to all values of [Result].
         *
         * ###### RU:
         * Возвращает полиморфную функцию преобразующую функцию [transform] в структуру [Result].
         */
        public inline fun <Ok, Error, Out> lift(
            crossinline transform: (Ok) -> Out,
        ): (Result<Ok, Error>) -> Result<Out, Error> {
            return { result -> result.map(transform) }
        }

        /**
         * ###### EN:
         * Converts the transformation function [transform] into a [Result] structure that returns a polymorphic
         * function that can be applied to all values of [Result].
         *
         * ###### RU:
         * Возвращает полиморфную функцию преобразующую функцию [transform] в структуру [Result].
         */
        public inline fun <Ok, Error, Out> suspendLift(
            crossinline  transform: suspend (Ok) -> Out,
        ): suspend (Result<Ok, Error>) -> Result<Out, Error> {
            return { result -> result.map { transform(it) } }
        }

        /**
         * ###### EN:
         * Converts the transformation function [okTransform] and [errorTransform] into a [Result] structure
         * that returns a polymorphic function that can be applied to all values of [Result].
         *
         * ###### RU:
         * Возвращает полиморфную функцию преобразующую функцию [okTransform] и [errorTransform] в структуру [Result].
         */
        public inline fun <Ok, Error, OkOut, ErrorOut> lift(
            crossinline okTransform: (Ok) -> OkOut,
            crossinline errorTransform: (Error) -> ErrorOut,
        ): (Result<Ok, Error>) -> Result<OkOut, ErrorOut> {
            return { result -> result.bimap(okTransform, errorTransform) }
        }

        /**
         * ###### EN:
         * Converts the transformation function [okTransform] and [errorTransform] into a [Result] structure
         * that returns a polymorphic function that can be applied to all values of [Result].
         *
         * ###### RU:
         * Возвращает полиморфную функцию преобразующую функцию [okTransform] и [errorTransform] в структуру [Result].
         */
        public inline fun <Ok, Error, OkOut, ErrorOut> suspendLift(
            crossinline okTransform: suspend (Ok) -> OkOut,
            crossinline errorTransform: suspend (Error) -> ErrorOut,
        ): suspend (Result<Ok, Error>) -> Result<OkOut, ErrorOut> {
            return { result ->
                result.bimap(
                    okTransform = { okTransform(it) },
                    errorTransform = { errorTransform(it) },
                )
            }
        }

        /**
         * ###### EN:
         * Wraps the execution of [block] in a 'try-catch'. [Error] is returned if [block] returned an exception.
         *
         * ###### RU:
         * Оборачивает выполнение [block] в `try-catch`. Возвращается [Error] если [block] вернул исключение.
         */
        public inline fun <Ok> catch(block: () -> Ok): Result<Ok, Throwable> {
            return try {
                Ok(block())
            } catch (error: Throwable) {
                Error(error)
            }
        }
    }
}

/**
 * ###### EN:
 * Returns `true` if it is [Result.Ok], or `false` otherwise.
 *
 * ###### RU:
 * Возвращает значение `true`, если [Result.Ok], в противном случае `false`.
 *
 * ###### Example:
 * ```
 * Ok(1).isOk() // Result: true
 * Error(1).isOk() // Result: false
 * ```
 */
public fun <Ok, Error> Result<Ok, Error>.isOk(): Boolean {
    contract {
        returns(true) implies (this@isOk is Result.Ok<Ok>)
        returns(false) implies (this@isOk is Result.Error<Error>)
    }
    return this is Result.Ok
}

/**
 * ###### EN:
 * Returns `true` if it is [Result.Error], or `false` otherwise.
 *
 * ###### RU:
 * Возвращает значение `true`, если [Result.Error], в противном случае `false`.
 *
 * ###### Example:
 * ```
 * Ok(1).isError() // Result: false
 * Error(1).isError() // Result: true
 * ```
 */
public fun <Ok, Error> Result<Ok, Error>.isError(): Boolean {
    contract {
        returns(true) implies (this@isError is Result.Error<Error>)
        returns(false) implies (this@isError is Result.Ok<Ok>)
    }
    return this is Result.Error
}
