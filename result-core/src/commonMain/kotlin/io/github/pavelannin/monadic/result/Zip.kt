package io.github.pavelannin.monadic.result

import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * ###### Signature:
 * `Result<Ok1, Error>` & `Result<Ok2, Error>` -> `Result<Out, Error>`
 *
 * ###### EN:
 * Combines two [Result.Ok] to a single value by applying the [transform] function.
 *
 * ###### RU:
 * Объединяет два [Result.Ok] в одно значение, применяя функцию [transform].
 *
 * ###### Example:
 * ```
 * Ok(1).zip(Ok(1)) { v1, v2 -> v1 + v2 } // Result: Ok(2)
 *
 * Ok(1).zip(Error("foo")) { v1, v2 -> r1 + r2 } // Result: Error("foo")
 * Error("foo").zip(Ok(1)) { v1, v2 -> v1 + v2 } // Result: Error("foo")
 * Error("foo").zip(Error("foo")) { v1, v2 -> v1 + v2 } // Result: Error("foo")
 * ```
 */
public inline fun <Ok1, Ok2, Error, Out> Result<Ok1, Error>.zip(
    result: Result<Ok2, Error>,
    transform: (Ok1, Ok2) -> Out,
): Result<Out, Error> {
    contract { callsInPlace(transform, InvocationKind.AT_MOST_ONCE) }
    return flatMap { ok1 -> result.map { ok2 -> transform(ok1, ok2) } }
}

/**
 * ###### Signature:
 * `Result<Ok1, Error>` & `Result<Ok2, Error>` -> `Result<Pair<Ok1, Ok2, Error>`
 *
 * ###### EN:
 * Combines two [Result.Ok] to a single value.
 *
 * ###### RU:
 * Объединяет два [Result.Ok] в одно значение.
 *
 * @see zip
 */
public fun <Ok1, Ok2, Error> Result<Ok1, Error>.zip(
    result: Result<Ok2, Error>,
): Result<Pair<Ok1, Ok2>, Error> = zip(result, ::Pair)

/**
 * ###### Signature:
 * `Result<Ok1, Error>` & `Result<Ok2, Error>` & `Result<Ok3, Error>` -> `Result<Out, Error>`
 *
 * ###### EN:
 * Combines three [Result.Ok] to a single value by applying the [transform] function.
 *
 * ###### RU:
 * Объединяет три [Result.Ok] в одно значение, применяя функцию [transform].
 *
 * @see zip
 */
public inline fun <Ok1, Ok2, Ok3, Error, Out> Result<Ok1, Error>.zip(
    result2: Result<Ok2, Error>,
    result3: Result<Ok3, Error>,
    transform: (Ok1, Ok2, Ok3) -> Out,
): Result<Out, Error> {
    contract { callsInPlace(transform, InvocationKind.AT_MOST_ONCE) }
    return flatMap { ok1 ->
        result2.flatMap { ok2 ->
            result3.map { ok3 ->
                transform(ok1, ok2, ok3)
            }
        }
    }
}

/**
 * ###### Signature:
 * `Result<Ok1, Error>` & `Result<Ok2, Error>` & `Result<Ok3, Error>` -> `Result<Triple<Ok1, Ok2, Ok3>, Error>`
 *
 * ###### EN:
 * Combines three [Result.Ok] to a single value.
 *
 * ###### RU:
 * Объединяет три [Result.Ok] в одно значение.
 *
 * @see zip
 */
public fun <Ok1, Ok2, Ok3, Error> Result<Ok1, Error>.zip(
    result2: Result<Ok2, Error>,
    result3: Result<Ok3, Error>,
): Result<Triple<Ok1, Ok2, Ok3>, Error> = zip(result2, result3, ::Triple)

/**
 * ###### Signature:
 * `Result<Ok1, Error>` & `Result<Ok2, Error>` & `Result<Ok3, Error>` &
 * `Result<Ok4, Error>` -> `Result<Out, Error>`
 *
 * ###### EN:
 * Combines four [Result.Ok] to a single value by applying the [transform] function.
 *
 * ###### RU:
 * Объединяет четыре [Result.Ok] в одно значение, применяя функцию [transform].
 *
 * @see zip
 */
public inline fun <Ok1, Ok2, Ok3, Ok4, Error, Out> Result<Ok1, Error>.zip(
    result2: Result<Ok2, Error>,
    result3: Result<Ok3, Error>,
    result4: Result<Ok4, Error>,
    transform: (Ok1, Ok2, Ok3, Ok4) -> Out,
): Result<Out, Error> {
    contract { callsInPlace(transform, InvocationKind.AT_MOST_ONCE) }
    return flatMap { ok1 ->
        result2.flatMap { ok2 ->
            result3.flatMap { ok3 ->
                result4.map { ok4 ->
                    transform(ok1, ok2, ok3, ok4)
                }
            }
        }
    }
}

/**
 * ###### Signature:
 * `Result<Ok1, Error>` & `Result<Ok2, Error>` & `Result<Ok3, Error>` &
 * `Result<Ok4, Error>` & `Result<Ok5, Error>` -> `Result<Out, Error>`
 *
 * ###### EN:
 * Combines five [Result.Ok] to a single value by applying the [transform] function.
 *
 * ###### RU:
 * Объединяет пять [Result.Ok] в одно значение, применяя функцию [transform].
 *
 * @see zip
 */
public inline fun <Ok1, Ok2, Ok3, Ok4, Ok5, Error, Out> Result<Ok1, Error>.zip(
    result2: Result<Ok2, Error>,
    result3: Result<Ok3, Error>,
    result4: Result<Ok4, Error>,
    result5: Result<Ok5, Error>,
    transform: (Ok1, Ok2, Ok3, Ok4, Ok5) -> Out,
): Result<Out, Error> {
    contract { callsInPlace(transform, InvocationKind.AT_MOST_ONCE) }
    return flatMap { ok1 ->
        result2.flatMap { ok2 ->
            result3.flatMap { ok3 ->
                result4.flatMap { ok4 ->
                    result5.map { ok5 ->
                        transform(ok1, ok2, ok3, ok4, ok5)
                    }
                }
            }
        }
    }
}
