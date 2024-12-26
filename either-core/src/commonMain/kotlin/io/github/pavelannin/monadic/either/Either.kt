package io.github.pavelannin.monadic.either

import io.github.pavelannin.monadic.either.Either.Left
import io.github.pavelannin.monadic.either.Either.Right
import kotlinx.serialization.Serializable
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * ###### EN:
 * The [Either] monad is a functional programming construct used to handle alternative results or errors.
 * It is a pair of values [Left] (for incorrect or alternative values) and [Right] (for correct ones).
 * The Either monad allows you to handle both variants in the same type.
 * This makes its application in error handling very useful.
 *
 * ###### RU:
 * Монада [Either] - это функциональная конструкция, используемая для обработки альтернативных результатов или ошибок.
 * Собержит пару значений [Left] (для неправильных или альтернативных значений) и [Right] (для правильных значений).
 * Монада [Either] позволяет обрабатывать оба варианта в одном типе что делает применение монады удобным.
 *
 * ###### Example:
 * ```
 * val right = Either.Right(1)
 * val left = Either.Left("foo")
 * ```
 */
@Serializable(EitherSerializer::class)
public sealed interface Either<out Left, out Right> {
    /**
     * ###### EN:
     * The left value (for incorrect or alternative values) of the monad is [Either].
     *
     * ###### RU:
     * Левое значение (для неправильных или альтернативных значений) монады [Either].
     */
    public data class Left<out T>(public val left: T) : Either<T, Nothing> {
        public companion object {
            public operator fun invoke(): Left<Unit> {
                return Left(Unit)
            }
        }
    }

    /**
     * ###### EN:
     * The right value (for correct ones) of the monad is [Either].
     *
     * ###### RU:
     * Правое значение (для правильных значений) монады [Either].
     */
    public data class Right<out T>(public val right: T) : Either<Nothing, T> {
        public companion object {
            public operator fun invoke(): Right<Unit> {
                return Right(Unit)
            }
        }
    }

    public companion object {
        /**
         * ###### EN:
         * Converts the transformation function [transform] into a [Either] structure that returns a polymorphic
         * function that can be applied to all values of [Either].
         *
         * ###### RU:
         * Возвращает полиморфную функцию преобразующую функцию [transform] в структуру [Either].
         */
        public inline fun <Left, Right, RightOut> lift(
            crossinline transform: (Right) -> RightOut,
        ): (Either<Left, Right>) -> Either<Left, RightOut> {
            return { either -> either.map(transform) }
        }

        /**
         * ###### EN:
         * Converts the transformation function [transform] into a [Either] structure that returns a polymorphic
         * function that can be applied to all values of [Either].
         *
         * ###### RU:
         * Возвращает полиморфную функцию преобразующую функцию [transform] в структуру [Either].
         */
        public inline fun <Left, Right, RightOut> suspendLift(
            crossinline  transform: suspend (Right) -> RightOut,
        ): suspend (Either<Left, Right>) -> Either<Left, RightOut> {
            return { either -> either.map { transform(it) } }
        }

        /**
         * ###### EN:
         * Converts the transformation function [leftTransform] and [rightTransform] into a [Either] structure that returns a polymorphic
         * function that can be applied to all values of [Either].
         *
         * ###### RU:
         * Возвращает полиморфную функцию преобразующую функцию [leftTransform] и [rightTransform] в структуру [Either].
         */
        public inline fun <Left, Right, LeftOut, RightOut> lift(
            crossinline leftTransform: (Left) -> LeftOut,
            crossinline rightTransform: (Right) -> RightOut,
        ): (Either<Left, Right>) -> Either<LeftOut, RightOut> {
            return { either -> either.bimap(leftTransform, rightTransform) }
        }

        /**
         * ###### EN:
         * Converts the transformation function [leftTransform] and [rightTransform] into a [Either] structure that returns a polymorphic
         * function that can be applied to all values of [Either].
         *
         * ###### RU:
         * Возвращает полиморфную функцию преобразующую функцию [leftTransform] и [rightTransform] в структуру [Either].
         */
        public inline fun <Left, Right, LeftOut, RightOut> suspendLift(
            crossinline leftTransform: suspend (Left) -> LeftOut,
            crossinline rightTransform: suspend (Right) -> RightOut,
        ): suspend (Either<Left, Right>) -> Either<LeftOut, RightOut> {
            return { either ->
                either.bimap(
                    leftTransform = { leftTransform(it) },
                    rightTransform = { rightTransform(it) },
                )
            }
        }

        /**
         * ###### EN:
         * Wraps the execution of [block] in a 'try-catch'. [Either.Left] is returned if [block] returned an exception.
         *
         * ###### RU:
         * Оборачивает выполнение [block] в `try-catch`. Возвращается [Either.Left] если [block] вернул исключение.
         */
        public inline fun <Right> catch(block: () -> Right): Either<Throwable, Right> {
            return try {
                Right(block())
            } catch (error: Throwable) {
                Left(error)
            }
        }
    }
}

/**
 * ###### EN:
 * Returns `true` if it is [Either.Left], or `false` otherwise.
 *
 * ###### RU:
 * Возвращает значение `true`, если [Either.Left], в противном случае `false`.
 *
 * ###### Example:
 * ```
 * Right(1).isLeft // Result: false
 * Left(1).isLeft // Result: true
 * ```
 */
public fun <Left, Right> Either<Left, Right>.isLeft(): Boolean {
    contract {
        returns(true) implies (this@isLeft is Either.Left<Left>)
        returns(false) implies (this@isLeft is Either.Right<Right>)
    }
    return this is Either.Left
}

/**
 * ###### EN:
 * Returns `true` if it is [Either.Right], or `false` otherwise.
 *
 * ###### RU:
 * Возвращает значение `true`, если [Either.Right], в противном случае `false`.
 *
 * ###### Example:
 * ```
 * Right(1).isRight // Result: true
 * Left(1).isRight // Result: false
 * ```
 */
public fun <Left, Right> Either<Left, Right>.isRight(): Boolean {
    contract {
        returns(true) implies (this@isRight is Either.Right<Right>)
        returns(false) implies (this@isRight is Either.Left<Left>)
    }
    return this is Either.Right
}
