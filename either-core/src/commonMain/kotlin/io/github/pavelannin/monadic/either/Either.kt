package io.github.pavelannin.monadic.either

import kotlinx.serialization.Serializable
import kotlin.contracts.ExperimentalContracts
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
public sealed class Either<out Left, out Right> {
    /**
     * ###### EN:
     * The left value (for incorrect or alternative values) of the monad is [Either].
     *
     * ###### RU:
     * Левое значение (для неправильных или альтернативных значений) монады [Either].
     */
    public data class Left<T>(public val left: T) : Either<T, Nothing>() {

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
    public data class Right<T>(public val right: T) : Either<Nothing, T>() {

        public companion object {
            public operator fun invoke(): Right<Unit> {
                return Right(Unit)
            }
        }
    }

    /**
     * ###### EN:
     * Returns `true` if it is [Left], or `false` otherwise.
     *
     * ###### RU:
     * Возвращает значение `true`, если [Left], в противном случае `false`.
     *
     * ###### Example:
     * ```
     * Right(1).isLeft // Result: false
     * Left(1).isLeft // Result: true
     * ```
     */
    @OptIn(ExperimentalContracts::class)
    public fun isLeft(): Boolean {
        contract {
            returns(true) implies (this@Either is Either.Left<Left>)
            returns(false) implies (this@Either is Either.Right<Right>)
        }
        return this is Either.Left
    }

    /**
     * ###### EN:
     * Returns `true` if it is [Right], or `false` otherwise.
     *
     * ###### RU:
     * Возвращает значение `true`, если [Right], в противном случае `false`.
     *
     * ###### Example:
     * ```
     * Right(1).isRight // Result: true
     * Left(1).isRight // Result: false
     * ```
     */
    @OptIn(ExperimentalContracts::class)
    public fun isRight(): Boolean {
        contract {
            returns(true) implies (this@Either is Either.Right<Right>)
            returns(false) implies (this@Either is Either.Left<Left>)
        }
        return this is Either.Right
    }

    /**
     * ###### EN:
     * Returns `value` if it is [Left], or `null` otherwise.
     *
     * ###### RU:
     * Возвращает значение `value`, если [Left], в противном случае `null`.
     *
     * ###### Example:
     * ```
     * Right(1).leftOrNull() // Result: null
     * Left(1).leftOrNull() // Result: 1
     * ```
     */
    public fun leftOrNull(): Left? {
        return if (this.isLeft()) left else null
    }

    /**
     * ###### EN:
     * Returns `value` if it is [Right], or `null` otherwise.
     *
     * ###### RU:
     * Возвращает значение `value`, если [Right], в противном случае `null`.
     *
     * ###### Example:
     * ```
     * Right(1).rightOrNull() // Result: 1
     * Left(1).rightOrNull() // Result: null
     * ```
     */
    public fun rightOrNull(): Right? {
        return if (this.isRight()) right else null
    }

    /**
     * ###### EN:
     * Calls the [block] function if [Left].
     *
     * ###### RU:
     * Вызывает функцию [block] если [Left].
     *
     * ###### Example:
     * ```
     * Right(1).onLeft { print("Hello") }
     * Left(1).onLeft { print("Hello") } // Log: Hello
     * ```
     */
    @OptIn(ExperimentalContracts::class)
    public inline fun onLeft(block: (Left) -> Unit): Either<Left, Right> {
        contract { callsInPlace(block, InvocationKind.AT_MOST_ONCE) }
        if (this.isLeft()) block(left)
        return this
    }

    /**
     * ###### EN:
     * Calls the [block] function if [Right].
     *
     * ###### RU:
     * Вызывает функцию [block] если [Right].
     *
     * ###### Example:
     * ```
     * Right(1).onRight { print("Hello") }  // Log: Hello
     * Left(1).onRight { print("Hello") }
     * ```
     */
    @OptIn(ExperimentalContracts::class)
    public inline fun onRight(block: (Right) -> Unit): Either<Left, Right> {
        contract { callsInPlace(block, InvocationKind.AT_MOST_ONCE) }
        if (this.isRight()) block(right)
        return this
    }

    /**
     * ###### Signature:
     * `Either<Left, Right>` -> `Either<Left, Out>`
     *
     * ###### EN:
     * Transforms to `Either<Left, Out>` by applying the [transform] function if [Right].
     *
     * ###### RU:
     * Трансформирует в `Either<Left, Out>`, применяя функцию [transform] если [Right].
     *
     * ###### Example:
     * ```
     * Right(1).map { "foo" } // Result: Right("foo")
     * Left(1).map { "foo" }  // Result: Left(1)
     * ```
     */
    @OptIn(ExperimentalContracts::class)
    public inline fun <Out> map(transform: (Right) -> Out): Either<Left, Out> {
        contract { callsInPlace(transform, InvocationKind.AT_MOST_ONCE) }
        return if (this.isRight()) Right(transform(right)) else this
    }

    /**
     *  ###### Signature:
     * `Either<Left, Right>` -> `Either<Out, Right>`
     *
     * ###### EN:
     * Transforms to `Either<Out, Right>` by applying the [transform] function if [Left].
     *
     * ###### RU:
     * Трансформирует в `Either<Out, Right>`, применяя функцию [transform] если [Left].
     *
     * ###### Example:
     * ```
     * Right(1).mapLeft { "foo" } // Result: Right(1)
     * Left(1).mapLeft { "foo" }  // Result: Left("foo")
     * ```
     */
    @OptIn(ExperimentalContracts::class)
    public inline fun <Out> mapLeft(transform: (Left) -> Out): Either<Out, Right> {
        contract { callsInPlace(transform, InvocationKind.AT_MOST_ONCE) }
        return if (this.isLeft()) Left(transform(left)) else this
    }

    /**
     *  ###### Signature:
     * `Either<Left, Right>` -> `Either<LeftOut, RightOut>`
     *
     * ###### EN:
     * Transforms to `Either<Left Out, RightOut>` by applying the [leftTransform] function if [Left], or the [rightTransform]
     * function if [Right].
     *
     * ###### RU:
     * Трансформирует в `Either<LeftOut, RightOut>`, применяя функцию [leftTransform] если [Left], или
     * функцию [rightTransform] если [Right].
     *
     * ###### Example:
     * ```
     * Right(1).bimap(
     *   leftTransform = { "foo" },
     *   rightTransform = { "bar" },
     * ) // Result: Right("bar")
     *
     * Left(1).bimap(
     *   leftTransform = { "foo" },
     *   rightTransform = { "bar" },
     * ) // Result: Left("foo")
     * ```
     */
    @OptIn(ExperimentalContracts::class)
    public inline fun <LeftOut, RightOut> bimap(
        leftTransform: (Left) -> LeftOut,
        rightTransform: (Right) -> RightOut,
    ): Either<LeftOut, RightOut> {
        contract {
            callsInPlace(leftTransform, InvocationKind.AT_MOST_ONCE)
            callsInPlace(rightTransform, InvocationKind.AT_MOST_ONCE)
        }
        return if (this.isLeft()) Left(leftTransform(left)) else Right(rightTransform(right))
    }

    /**
     * ###### Signature:
     * `Either<Left, Right>` -> `Result`
     *
     * ###### EN:
     * Transforms to the value [Result]. Applies the [leftTransform] function if [Left],
     * or the [rightTransform] function if [Right].
     *
     * ###### RU:
     * Трансформирует в значение [Result]. Применяет функцию [leftTransform] если [Left],
     * или функцию [rightTransform] если [Right].
     *
     * ###### Example:
     * ```
     * Right(1).fold(
     *   leftTransform = { "foo" },
     *   rightTransform = { "bar" },
     * ) // Result: "bar"
     *
     * Left(1).fold(
     *   leftTransform = { "foo" },
     *   rightTransform = { "bar" },
     * ) // Result: "foo"
     * ```
     */
    @OptIn(ExperimentalContracts::class)
    public inline fun <Result> fold(
        leftTransform: (Left) -> Result,
        rightTransform: (Right) -> Result,
    ): Result {
        contract {
            callsInPlace(leftTransform, InvocationKind.AT_MOST_ONCE)
            callsInPlace(rightTransform, InvocationKind.AT_MOST_ONCE)
        }
        return if (this.isLeft()) leftTransform(left) else rightTransform(right)
    }

    /**
     * ###### Signature:
     * `Either<Left, Right>` -> `Result`
     *
     * ###### EN:
     * Transforms `Either<Left, Right>` to the value [Result]. Applies the [transform] function if [Left], otherwise
     * returns the value [initial].
     *
     * ###### RU:
     * Трансформирует в `Either<Left, Right>` в значение [Result]. Применяет функцию [transform] если [Left],
     * иначе возвращает значение [initial].
     *
     * ###### Example:
     * ```
     * Right(1).foldLeft(
     *   initial = "foo",
     *   transform = { "bar" },
     * ) // Result: "foo"
     *
     * Left(1).foldLeft(
     *   initial = "foo",
     *   transform = { "bar" },
     * ) // Result: "bar"
     * ```
     */
    @OptIn(ExperimentalContracts::class)
    public inline fun <Result> foldLeft(
        initial: Result,
        transform: (Left) -> Result,
    ): Result {
        contract { callsInPlace(transform, InvocationKind.AT_MOST_ONCE) }
        return if (this.isLeft()) transform(left) else initial
    }

    /**
     * ###### Signature:
     * `Either<Left, Right>` -> `Result`
     *
     * ###### EN:
     * Transforms `Either<Left, Right>` to the value [Result]. Applies the [transform] function if [Right], otherwise
     * returns the value [initial].
     *
     * ###### RU:
     * Трансформирует в `Either<Left, Right>` в значение [Result]. Применяет функцию [transform] если [Right],
     * иначе возвращает значение [initial].
     *
     * ###### Example:
     * ```
     * Right(1).foldLeft(
     *   initial = "foo",
     *   transform = { "bar" },
     * ) // Result: "bar"
     *
     * Left(1).foldLeft(
     *   initial = "foo",
     *   transform = { "bar" },
     * ) // Result: "foo"
     * ```
     */
    @OptIn(ExperimentalContracts::class)
    public inline fun <Result> foldRight(
        initial: Result,
        transform: (Right) -> Result,
    ): Result {
        contract { callsInPlace(transform, InvocationKind.AT_MOST_ONCE) }
        return if (this.isRight()) transform(right) else initial
    }

    /**
     * ###### Signature:
     * `Either<Left, Right>` -> `Either<Right, Left>`
     *
     * ###### EN:
     * The value of [Right] will be returned to [Left], and the value of [Left] will be returned to [Right].
     *
     * ###### RU:
     * Значение [Right] будет возвращено в [Left], а значение [Left] будет возвращено в [Right].
     *
     * ###### Example:
     * ```
     * Right(1).swap() // Result: Left(1)
     * Left(1).swap() // Result: Right(1)
     * ```
     */
    public fun swap(): Either<Right, Left> = fold(::Right, ::Left)

    public companion object {
        /**
         * ###### EN:
         * Converts the transformation function [transform] into a [Either] structure that returns a polymorphic
         * function that can be applied to all values of [Either].
         *
         * ###### RU:
         * Возвращает полиморфную функцию преобразующую функцию [transform] в структуру [Either].
         */
        public inline fun <Left, RightIn, RightOut> lift(
            crossinline transform: (RightIn) -> RightOut,
        ): (Either<Left, RightIn>) -> Either<Left, RightOut> {
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
        public inline fun <Left, RightIn, RightOut> suspendLift(
            crossinline  transform: suspend (RightIn) -> RightOut,
        ): suspend (Either<Left, RightIn>) -> Either<Left, RightOut> {
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
        public inline fun <LeftIn, LeftOut, RightIn, RightOut> lift(
            crossinline leftTransform: (LeftIn) -> LeftOut,
            crossinline rightTransform: (RightIn) -> RightOut,
        ): (Either<LeftIn, RightIn>) -> Either<LeftOut, RightOut> {
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
        public inline fun <LeftIn, LeftOut, RightIn, RightOut> suspendLift(
            crossinline leftTransform: suspend (LeftIn) -> LeftOut,
            crossinline rightTransform: suspend (RightIn) -> RightOut,
        ): suspend (Either<LeftIn, RightIn>) -> Either<LeftOut, RightOut> {
            return { either ->
                either.bimap(
                    leftTransform = { leftTransform(it) },
                    rightTransform = { rightTransform(it) }
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
 * ###### Signature:
 * `Either<Left, RightIn>` -> `Either<Left, RightOut>`
 *
 * ###### EN:
 * Transforms to `Either<Left, RightOut>` by applying the [transform] function if [Either.Right].
 *
 * ###### RU:
 * Трансформирует в `Either<Left, RightOut>`, применяя функцию [transform] если [Either.Right].
 *
 * ###### Example:
 * ```
 * Right(1).flatMap { Either.Right("bar") } // Result: Right("bar")
 * Right(1).flatMap { Either.Left("zoo") } // Result: Left("zoo")
 *
 * Left(1).flatMap { Either.Right("bar") } // Result: Left(1)
 * Left(1).flatMap { Either.Left("zoo") } // Result: Left(1)
 * ```
 */
@OptIn(ExperimentalContracts::class)
public inline fun <Left, RightIn, RightOut> Either<Left, RightIn>.flatMap(
    transform: (RightIn) -> Either<Left, RightOut>,
): Either<Left, RightOut> {
    contract { callsInPlace(transform, InvocationKind.AT_MOST_ONCE) }
    return when (this) {
        is Either.Left -> this
        is Either.Right -> transform(right)
    }
}

/**
 * ###### Signature:
 * `Either<LeftIn, Right>` -> `Either<LeftOut, Right>`
 *
 * ###### EN:
 * Transforms to `Either<LeftOut, Right>` by applying the [transform] function if [Either.Left].
 *
 * ###### RU:
 * Трансформирует в `Either<LeftOut, Right>`, применяя функцию [transform] если [Either.Left].
 *
 * ###### Example:
 * ```
 * Right(1).flatMapLeft { Either.Right("bar") } // Result: Right(1)
 * Right(1).flatMapLeft { Either.Left("zoo") } // Result: Right(1)
 *
 * Left(1).flatMapLeft { Either.Right("bar") } // Result: Right("bar")
 * Left(1).flatMapLeft { Either.Left("zoo") } // Result: Left("zoo")
 * ```
 */
@OptIn(ExperimentalContracts::class)
public inline fun <LeftIn, LeftOut, Right> Either<LeftIn, Right>.flatMapLeft(
    transform: (LeftIn) -> Either<LeftOut, Right>,
): Either<LeftOut, Right> {
    contract { callsInPlace(transform, InvocationKind.AT_MOST_ONCE) }
    return when (this) {
        is Either.Left -> transform(left)
        is Either.Right -> this
    }
}

/**
 * ###### Signature:
 * `Either<Left, Right1>` & `Either<Left, Right2>` -> `Either<Left, Result>`
 *
 * ###### EN:
 * Combines two [Either.Right] to a single value by applying the [transform] function.
 *
 * ###### RU:
 * Объединяет два [Either.Right] в одно значение, применяя функцию [transform].
 *
 * ###### Example:
 * ```
 * Right(1).zip(Right(1)) { r1, r2 -> r1 + r2 } // Result: Right(2)
 *
 * Right(1).zip(Left("foo")) { r1, r2 -> r1 + r2 } // Result: Left("foo")
 * Left("foo").zip(Right(1)) { r1, r2 -> r1 + r2 } // Result: Left("foo")
 * Left("foo").zip(Left("foo")) { r1, r2 -> r1 + r2 } // Result: Left("foo")
 * ```
 */
@OptIn(ExperimentalContracts::class)
public inline fun <Left, Right1, Right2, Result> Either<Left, Right1>.zip(
    either: Either<Left, Right2>,
    transform: (Right1, Right2) -> Result,
): Either<Left, Result> {
    contract { callsInPlace(transform, InvocationKind.AT_MOST_ONCE) }
    return flatMap { right1 -> either.map { right2 -> transform(right1, right2) } }
}

/**
 * ###### Signature:
 * `Either<Left, Right1>` & `Either<Left, Right2>` -> `Either<Left, Pair<Right1, Right2>`
 *
 * ###### EN:
 * Combines two [Either.Right] to a single value.
 *
 * ###### RU:
 * Объединяет два [Either.Right] в одно значение.
 *
 * @see zip
 */
public fun <Left, Right1, Right2> Either<Left, Right1>.zip(
    either: Either<Left, Right2>,
): Either<Left, Pair<Right1, Right2>> = zip(either, ::Pair)

/**
 * ###### Signature:
 * `Either<Left, Right1>` & `Either<Left, Right2>` & `Either<Left, Right3>` -> `Either<Left, Result>`
 *
 * ###### EN:
 * Combines three [Either.Right] to a single value by applying the [transform] function.
 *
 * ###### RU:
 * Объединяет три [Either.Right] в одно значение, применяя функцию [transform].
 *
 * @see zip
 */
@OptIn(ExperimentalContracts::class)
public inline fun <Left, Right1, Right2, Right3, Result> Either<Left, Right1>.zip(
    either2: Either<Left, Right2>,
    either3: Either<Left, Right3>,
    transform: (Right1, Right2, Right3) -> Result,
): Either<Left, Result> {
    contract { callsInPlace(transform, InvocationKind.AT_MOST_ONCE) }
    return flatMap { right1 ->
        either2.flatMap { right2 ->
            either3.map { right3 ->
                transform(right1, right2, right3)
            }
        }
    }
}

/**
 * ###### Signature:
 * `Either<Left, Right1>` & `Either<Left, Right2>` & `Either<Left, Right3>` -> `Either<Left, Triple<Right1, Right2, Right3>>`
 *
 * ###### EN:
 * Combines three [Either.Right] to a single value.
 *
 * ###### RU:
 * Объединяет три [Either.Right] в одно значение.
 *
 * @see zip
 */
public fun <Left, Right1, Right2, Right3> Either<Left, Right1>.zip(
    either2: Either<Left, Right2>,
    either3: Either<Left, Right3>,
): Either<Left, Triple<Right1, Right2, Right3>> = zip(either2, either3, ::Triple)

/**
 * ###### Signature:
 * `Either<Left, Right1>` & `Either<Left, Right2>` & `Either<Left, Right3>` &
 * `Either<Left, Right4>` -> `Either<Left, Result>`
 *
 * ###### EN:
 * Combines four [Either.Right] to a single value by applying the [transform] function.
 *
 * ###### RU:
 * Объединяет четыре [Either.Right] в одно значение, применяя функцию [transform].
 *
 * @see zip
 */
@OptIn(ExperimentalContracts::class)
public inline fun <Left, Right1, Right2, Right3, Right4, Result> Either<Left, Right1>.zip(
    either2: Either<Left, Right2>,
    either3: Either<Left, Right3>,
    either4: Either<Left, Right4>,
    transform: (Right1, Right2, Right3, Right4) -> Result,
): Either<Left, Result> {
    contract { callsInPlace(transform, InvocationKind.AT_MOST_ONCE) }
    return flatMap { right1 ->
        either2.flatMap { right2 ->
            either3.flatMap { right3 ->
                either4.map { right4 ->
                    transform(right1, right2, right3, right4)
                }
            }
        }
    }
}

/**
 * ###### Signature:
 * `Either<Left, Right1>` & `Either<Left, Right2>` & `Either<Left, Right3>` &
 * `Either<Left, Right4>` & `Either<Left, Right5>` -> `Either<Left, Result>`
 *
 * ###### EN:
 * Combines five [Either.Right] to a single value by applying the [transform] function.
 *
 * ###### RU:
 * Объединяет пять [Either.Right] в одно значение, применяя функцию [transform].
 *
 * @see zip
 */
@OptIn(ExperimentalContracts::class)
public inline fun <Left, Right1, Right2, Right3, Right4, Right5, Result> Either<Left, Right1>.zip(
    either2: Either<Left, Right2>,
    either3: Either<Left, Right3>,
    either4: Either<Left, Right4>,
    either5: Either<Left, Right5>,
    transform: (Right1, Right2, Right3, Right4, Right5) -> Result,
): Either<Left, Result> {
    contract { callsInPlace(transform, InvocationKind.AT_MOST_ONCE) }
    return flatMap { right1 ->
        either2.flatMap { right2 ->
            either3.flatMap { right3 ->
                either4.flatMap { right4 ->
                    either5.map { right5 ->
                        transform(right1, right2, right3, right4, right5)
                    }
                }
            }
        }
    }
}

/**
 * ###### Signature:
 * `Either<Left, Either<Left, Right>>` -> `Either<Left, Right>`
 *
 * ###### EN:
 * Reduces nesting for [Either.Right].
 *
 * ###### RU:
 * Уменьшает вложенность для [Either.Right].
 *
 * ###### Example:
 * ```
 * Right(Right(1)).flatten() // Result: Right(1)
 * ```
 */
public fun <Left, Right> Either<Left, Either<Left, Right>>.flatten(): Either<Left, Right> = flatMap { it }

/**
 * ###### Signature:
 * `Either<Result, Result>` -> `Result`
 *
 * ###### EN:
 * Returns a value if both sides contain the same type.
 *
 * ###### RU:
 * Возвращает значение, если обе стороны содержат один и тот же тип.
 *
 * ###### Example:
 * ```
 * Right(1).take() // Result: 1
 * Left(1).take() // Result: 1
 * ```
 */
public fun <Result> Either<Result, Result>.take(): Result = when (this) {
    is Either.Left -> left
    is Either.Right -> right
}
