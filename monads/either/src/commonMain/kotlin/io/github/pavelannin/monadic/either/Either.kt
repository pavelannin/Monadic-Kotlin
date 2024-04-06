package io.github.pavelannin.monadic.either

/**
 * The [Either] monad is a functional programming construct used to handle alternative results or errors.
 * It is a pair of values [Left] (for incorrect or alternative values) and [Right] (for correct ones).
 * The Either monad allows you to handle both variants in the same type.
 * This makes its application in error handling very useful.
 */
public sealed class Either<out Left, out Right> {
    /** The left value of the monad is [Either]. */
    public data class Left<T>(val value: T) : Either<T, Nothing>()

    /** The right value of the monad is [Either]. */
    public data class Right<T>(val value: T) : Either<Nothing, T>()

    public companion object {
        /**
         * Converts the transformation function [transform] into a [Either] structure that returns a polymorphic function that can be
         * applied to all values of [Either].
         */
        public inline infix fun <Left, In, Out> lift(
            crossinline transform: (In) -> Out,
        ): (Either<Left, In>) -> Either<Left, Out> = { either -> either.map(transform) }

        /**
         * Converts transformation functions [leftTransform] and [rightTransform] into a [Either] structure that returns a polymorphic
         * function that can be applied to all values of [Either].
         */
        public inline fun <InLeft, InRight, OutLeft, OutRight> lift(
            crossinline leftTransform: (InLeft) -> OutLeft,
            crossinline rightTransform: (InRight) -> OutRight,
        ): (Either<InLeft, InRight>) -> Either<OutLeft, OutRight> = { either -> either.bimap(leftTransform, rightTransform) }

        /**
         *
         */
        public inline operator fun <reified Left : Throwable, Right> invoke(
            crossinline block: () -> Right,
        ): Either<Left, Right> = try {
            Right(block())
        } catch (error: Throwable) {
            if (error is Left) Left(error) else throw error
        }
    }
}

/** Transforms the right side [Either]. */
public inline infix fun <Left, In, Out> Either<Left, In>.map(
    crossinline transform: (In) -> Out,
): Either<Left, Out> = when (this) {
    is Either.Left -> this
    is Either.Right -> Either.Right(transform(value))
}

/** Transforms the left side [Either]. */
public inline infix fun <In, Right, Out> Either<In, Right>.mapLeft(
    crossinline transform: (In) -> Out,
): Either<Out, Right> = when (this) {
    is Either.Left -> Either.Left(transform(value))
    is Either.Right -> this
}

/**
 * Transforms [Either].
 *
 * Applies [leftTransform] if it is [Either.Left], or [rightTransform] if it is [Either.Right].
 */
public inline fun <InLeft, InRight, OutLeft, OutRight> Either<InLeft, InRight>.bimap(
    crossinline leftTransform: (InLeft) -> OutLeft,
    crossinline rightTransform: (InRight) -> OutRight,
): Either<OutLeft, OutRight> = when (this) {
    is Either.Left -> Either.Left(leftTransform(value))
    is Either.Right -> Either.Right(rightTransform(value))
}

/**
 * Transforms [Either] to the value of [Result].
 *
 * Applies [leftTransform] if it is [Either.Left], or [rightTransform] if it is [Either.Right].
 */
public inline fun <Left, Right, Result> Either<Left, Right>.fold(
    crossinline leftTransform: (Left) -> Result,
    crossinline rightTransform: (Right) -> Result,
): Result = when (this) {
    is Either.Left -> leftTransform(value)
    is Either.Right -> rightTransform(value)
}

/**
 * Transforms [Either] to the value of [Result].
 *
 * Applies [transform] if it is [Either.Left], or returns [rightValue] if it is [Either.Right].
 */
public inline fun <Left, Result> Either<Left, *>.foldLeft(
    rightValue: Result,
    crossinline transform: (Left) -> Result,
): Result = when (this) {
    is Either.Left -> transform(value)
    is Either.Right -> rightValue
}

/**
 * Transforms [Either] to the value of [Result].
 *
 * Applies [transform] if it is [Either.Right], or returns [leftValue] if it is [Either.Left].
 */
public inline fun <Right, Result> Either<*, Right>.foldRight(
    leftValue: Result,
    crossinline transform: (Right) -> Result,
): Result = when (this) {
    is Either.Left -> leftValue
    is Either.Right -> transform(value)
}

/** Transforms the right side [Either]. */
public inline infix fun <Left, In, Out> Either<Left, In>.flatMap(
    crossinline transform: (In) -> Either<Left, Out>,
): Either<Left, Out> = when (this) {
    is Either.Left -> this
    is Either.Right -> transform(value)
}

/** Transforms the left side [Either]. */
public inline infix fun <In, Right, Out> Either<In, Right>.flatMapLeft(
    crossinline transform: (In) -> Either<Out, Right>,
): Either<Out, Right> = when (this) {
    is Either.Left -> transform(value)
    is Either.Right -> this
}

/**
 * Combine two [Either] into one value with the transformation [transform].
 * If both functions return [Either.Right], then the result will be [Either.Right], otherwise [Either.Left].
 */
public inline fun <Left, Right1, Right2, Result> Either<Left, Right1>.zip(
    either: Either<Left, Right2>,
    crossinline transform: (Right1, Right2) -> Result,
): Either<Left, Result> = flatMap { right1 -> either.map { right2 -> transform(right1, right2) } }

/** Reduces nesting [Either]. */
public inline fun <Left, Right> Either<Left, Either<Left, Right>>.flatten(): Either<Left, Right> = flatMap { it }

/** If it is [Either.Left], then return the value of left to [Either.Right] or vice versa. */
public inline fun <Left, Right> Either<Left, Right>.swap(): Either<Right, Left> = fold({ Either.Right(it) }, { Either.Left(it) })

/** Returns a value if both sides contain the same type. */
public inline fun <Result> Either<Result, Result>.take(): Result = when (this) {
    is Either.Left -> value
    is Either.Right -> value
}

/** Returns a value if it is [Either.Left], or `null` otherwise. */
public inline fun <Left> Either<Left, *>.leftOrNull(): Left? = when (this) {
    is Either.Left -> value
    is Either.Right -> null
}

/** Returns `true` if it is [Either.Left], or `false` otherwise. */
public inline val <Left> Either<Left, *>.isLeft: Boolean
    get() = when (this) {
        is Either.Left -> true
        is Either.Right -> false
    }

/** Returns a value if it is [Either.Right], or `null` otherwise. */
public inline fun <Right> Either<*, Right>.rightOrNull(): Right? = when (this) {
    is Either.Left -> null
    is Either.Right -> value
}

/** Returns `true` if it is [Either.Right], or `false` otherwise. */
public inline val <Right> Either<*, Right>.isRight: Boolean
    get() = when (this) {
        is Either.Left -> false
        is Either.Right -> true
    }
