package io.github.pavelannin.monadic.lce

import kotlin.contracts.InvocationKind
import kotlin.contracts.contract


/**
 * ###### Signature:
 * `LCE<Loading, Content1, Error>` & `LCE<Loading, Content2, Error>` -> `LCE<Loading, Result, Error>`
 *
 * ###### EN:
 * Combines two [LCE.Content] to a single value by applying the [transform] function.
 *
 * ###### RU:
 * Объединяет два [LCE.Content] в одно значение, применяя функцию [transform].
 *
 * ###### Example:
 * ```
 * Content(1).zip(Content(1)) { r1, r2 -> r1 + r2 } // Result: Content(2)
 * Content(1).zip(Loading("foo")) { r1, r2 -> r1 + r2 } // Result: Loading("foo")
 * Content(1).zip(Error("bar")) { r1, r2 -> r1 + r2 } // Result: Error("bar")
 * ```
 */
public inline fun <L, C1, C2, E, Out> LCE<L, C1, E>.zip(
    lce: LCE<L, C2, E>,
    transform: (C1, C2) -> Out,
): LCE<L, Out, E> {
    contract { callsInPlace(transform, InvocationKind.AT_MOST_ONCE) }
    return flatMapContent { c1 ->
        lce.mapContent { c2 ->
            transform(c1, c2)
        }
    }
}

/**
 * ###### Signature:
 * `LCE<Loading, Content1, Error>` & `LCE<Loading, Content2, Error>` -> `LCE<Loading, Pair<Content1, Content2>, Error>`
 *
 * ###### EN:
 * Combines two [LCE.Content] to a single value.
 *
 * ###### RU:
 * Объединяет два [LCE.Content] в одно значение.
 *
 * @see zip
 */
public fun <L, C1, C2, E> LCE<L, C1, E>.zip(
    lce: LCE<L, C2, E>,
): LCE<L, Pair<C1, C2>, E> = zip(lce, ::Pair)

/**
 * ###### Signature:
 * `LCE<Loading, Content1, Error>` & `LCE<Loading, Content2, Error>` & `LCE<Loading, Content3, Error>`
 * -> `LCE<Loading, Result, Error>`
 *
 * ###### EN:
 * Combines three [LCE.Content] to a single value by applying the [transform] function.
 *
 * ###### RU:
 * Объединяет три [LCE.Content] в одно значение, применяя функцию [transform].
 *
 * @see zip
 */
public inline fun <L, C1, C2, C3, E, Out> LCE<L, C1, E>.zip(
    lce2: LCE<L, C2, E>,
    lce3: LCE<L, C3, E>,
    transform: (C1, C2, C3) -> Out,
): LCE<L, Out, E> {
    contract { callsInPlace(transform, InvocationKind.AT_MOST_ONCE) }
    return flatMapContent { c1 ->
        lce2.flatMapContent { c2 ->
            lce3.mapContent { c3 ->
                transform(c1, c2, c3)
            }
        }
    }
}

/**
 * ###### Signature:
 * `LCE<Loading, Content1, Error>` & `LCE<Loading, Content2, Error>`  & `LCE<Loading, Content3, Error>`
 * -> `LCE<Loading, Triple<Content1, Content2>, Error>`
 *
 * ###### EN:
 * Combines three [LCE.Content] to a single value.
 *
 * ###### RU:
 * Объединяет три [LCE.Content] в одно значение.
 *
 * @see zip
 */
public fun <L, C1, C2, C3, E> LCE<L, C1, E>.zip(
    lce2: LCE<L, C2, E>,
    lce3: LCE<L, C3, E>,
): LCE<L, Triple<C1, C2, C3>, E> = zip(lce2, lce3, ::Triple)

/**
 * ###### Signature:
 * `LCE<Loading, Content1, Error>` & `LCE<Loading, Content2, Error>` & `LCE<Loading, Content3, Error>`
 * & `LCE<Loading, Content4, Error>` -> `LCE<Loading, Result, Error>`
 *
 * ###### EN:
 * Combines four [LCE.Content] to a single value by applying the [transform] function.
 *
 * ###### RU:
 * Объединяет четыре [LCE.Content] в одно значение, применяя функцию [transform].
 *
 * @see zip
 */
public inline fun <L, C1, C2, C3, C4, E, Out> LCE<L, C1, E>.zip(
    lce2: LCE<L, C2, E>,
    lce3: LCE<L, C3, E>,
    lce4: LCE<L, C4, E>,
    transform: (C1, C2, C3, C4) -> Out,
): LCE<L, Out, E> {
    contract { callsInPlace(transform, InvocationKind.AT_MOST_ONCE) }
    return flatMapContent { c1 ->
        lce2.flatMapContent { c2 ->
            lce3.flatMapContent { c3 ->
                lce4.mapContent { c4 ->
                    transform(c1, c2, c3, c4)
                }
            }
        }
    }
}

/**
 * ###### Signature:
 * `LCE<Loading, Content1, Error>` & `LCE<Loading, Content2, Error>` & `LCE<Loading, Content3, Error>`
 * & `LCE<Loading, Content4, Error>` & `LCE<Loading, Content5, Error>` -> `LCE<Loading, Result, Error>`
 *
 * ###### EN:
 * Combines five [LCE.Content] to a single value by applying the [transform] function.
 *
 * ###### RU:
 * Объединяет пять [LCE.Content] в одно значение, применяя функцию [transform].
 *
 * @see zip
 */
public inline fun <L, C1, C2, C3, C4, C5, E, Out> LCE<L, C1, E>.zip(
    lce2: LCE<L, C2, E>,
    lce3: LCE<L, C3, E>,
    lce4: LCE<L, C4, E>,
    lce5: LCE<L, C5, E>,
    transform: (C1, C2, C3, C4, C5) -> Out,
): LCE<L, Out, E> {
    contract { callsInPlace(transform, InvocationKind.AT_MOST_ONCE) }
    return flatMapContent { c1 ->
        lce2.flatMapContent { c2 ->
            lce3.flatMapContent { c3 ->
                lce4.flatMapContent { c4 ->
                    lce5.mapContent { c5 ->
                        transform(c1, c2, c3, c4, c5)
                    }
                }
            }
        }
    }
}
