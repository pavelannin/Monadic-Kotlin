package io.github.pavelannin.monadic.function

import kotlin.jvm.JvmName

/**
 * ###### Signature:
 * `(In1, In2) -> Out` -> `(In1) -> (In2) -> Out`
 *
 * ###### EN:
 * Currying allows you to turn a function with multiple arguments into a set of functions with one argument.
 *
 * For example, the function f(a, b, c) can be turned into a set of functions g(a), h(a, b), i(a, b, c).
 *
 * ###### RU:
 * Каррирование позволяет превратить функцию с несколькими аргументами в набор функций с одним аргументом.
 *
 * Например, функция f(a, b, c) может быть преобразована в набор функций g(a), h(a, b), i(a, b, c).
 */
public inline fun <In1, In2, Out> ((In1, In2) -> Out).curried(): (In1) -> (In2) -> Out = { in1: In1 ->
    { in2: In2 -> this(in1, in2) }
}

/**
 * ###### Signature:
 * `(In1, In2) -> Out` -> `(In1) -> (In2) -> Out`
 *
 * ###### EN:
 * Currying allows you to turn a function with multiple arguments into a set of functions with one argument.
 *
 * For example, the function f(a, b, c) can be turned into a set of functions g(a), h(a, b), i(a, b, c).
 *
 * ###### RU:
 * Каррирование позволяет превратить функцию с несколькими аргументами в набор функций с одним аргументом.
 *
 * Например, функция f(a, b, c) может быть преобразована в набор функций g(a), h(a, b), i(a, b, c).
 */
public inline fun <In1, In2, Out> (suspend (In1, In2) -> Out).suspendCurried(): (In1) -> suspend (In2) -> Out = { in1: In1 ->
    { in2: In2 -> this(in1, in2) }
}

/**
 * ###### Signature:
 * `(In1, In2, In3) -> Out` -> `(In1) -> (In2) -> (In3) -> Out`
 *
 * ###### EN:
 * Currying allows you to turn a function with multiple arguments into a set of functions with one argument.
 *
 * For example, the function f(a, b, c) can be turned into a set of functions g(a), h(a, b), i(a, b, c).
 *
 * ###### RU:
 * Каррирование позволяет превратить функцию с несколькими аргументами в набор функций с одним аргументом.
 *
 * Например, функция f(a, b, c) может быть преобразована в набор функций g(a), h(a, b), i(a, b, c).
 */
public fun <In1, In2, In3, Out> ((In1, In2, In3) -> Out).curried(): (In1) -> (In2) -> (In3) -> Out = { in1: In1 ->
    { in2: In2 ->
        { in3: In3 -> this(in1, in2, in3) }
    }
}

/**
 * ###### Signature:
 * `(In1, In2, In3) -> Out` -> `(In1) -> (In2) -> (In3) -> Out`
 *
 * ###### EN:
 * Currying allows you to turn a function with multiple arguments into a set of functions with one argument.
 *
 * For example, the function f(a, b, c) can be turned into a set of functions g(a), h(a, b), i(a, b, c).
 *
 * ###### RU:
 * Каррирование позволяет превратить функцию с несколькими аргументами в набор функций с одним аргументом.
 *
 * Например, функция f(a, b, c) может быть преобразована в набор функций g(a), h(a, b), i(a, b, c).
 */

public fun <In1, In2, In3, Out> (suspend (In1, In2, In3) -> Out).suspendCurried(): (In1) -> (In2) -> suspend (In3) -> Out = { in1: In1 ->
    { in2: In2 ->
        { in3: In3 -> this(in1, in2, in3) }
    }
}

/**
 * ###### Signature:
 * `(In1, In2, In3, In4) -> Out` -> `(In1) -> (In2) -> (In3) -> (In4) -> Out`
 *
 * ###### EN:
 * Currying allows you to turn a function with multiple arguments into a set of functions with one argument.
 *
 * For example, the function f(a, b, c) can be turned into a set of functions g(a), h(a, b), i(a, b, c).
 *
 * ###### RU:
 * Каррирование позволяет превратить функцию с несколькими аргументами в набор функций с одним аргументом.
 *
 * Например, функция f(a, b, c) может быть преобразована в набор функций g(a), h(a, b), i(a, b, c).
 */
public fun <In1, In2, In3, In4, Out> ((In1, In2, In3, In4) -> Out).curried(): (In1) -> (In2) -> (In3) -> (In4) -> Out = { in1: In1 ->
    { in2: In2 ->
        { in3: In3 ->
            { in4: In4 -> this(in1, in2, in3, in4) }
        }
    }
}

/**
 * ###### Signature:
 * `(In1, In2, In3, In4) -> Out` -> `(In1) -> (In2) -> (In3) -> (In4) -> Out`
 *
 * ###### EN:
 * Currying allows you to turn a function with multiple arguments into a set of functions with one argument.
 *
 * For example, the function f(a, b, c) can be turned into a set of functions g(a), h(a, b), i(a, b, c).
 *
 * ###### RU:
 * Каррирование позволяет превратить функцию с несколькими аргументами в набор функций с одним аргументом.
 *
 * Например, функция f(a, b, c) может быть преобразована в набор функций g(a), h(a, b), i(a, b, c).
 */
public fun <In1, In2, In3, In4, Out> (suspend (In1, In2, In3, In4) -> Out).suspendCurried(): (In1) -> (In2) -> (In3) -> suspend (In4) -> Out =
    { in1: In1 ->
        { in2: In2 ->
            { in3: In3 ->
                { in4: In4 -> this(in1, in2, in3, in4) }
            }
        }
    }

/**
 * ###### Signature:
 * `(In1, In2, In3, In4, In5) -> Out` -> `(In1) -> (In2) -> (In3) -> (In4) -> (In5) -> Out`
 *
 * ###### EN:
 * Currying allows you to turn a function with multiple arguments into a set of functions with one argument.
 *
 * For example, the function f(a, b, c) can be turned into a set of functions g(a), h(a, b), i(a, b, c).
 *
 * ###### RU:
 * Каррирование позволяет превратить функцию с несколькими аргументами в набор функций с одним аргументом.
 *
 * Например, функция f(a, b, c) может быть преобразована в набор функций g(a), h(a, b), i(a, b, c).
 */
public fun <In1, In2, In3, In4, In5, Out> ((In1, In2, In3, In4, In5) -> Out).curried(): (In1) -> (In2) -> (In3) -> (In4) -> (In5) -> Out =
    { in1: In1 ->
        { in2: In2 ->
            { in3: In3 ->
                { in4: In4 ->
                    { in5: In5 -> this(in1, in2, in3, in4, in5) }
                }
            }
        }
    }

/**
 * ###### Signature:
 * `(In1, In2, In3, In4, In5) -> Out` -> `(In1) -> (In2) -> (In3) -> (In4) -> (In5) -> Out`
 *
 * ###### EN:
 * Currying allows you to turn a function with multiple arguments into a set of functions with one argument.
 *
 * For example, the function f(a, b, c) can be turned into a set of functions g(a), h(a, b), i(a, b, c).
 *
 * ###### RU:
 * Каррирование позволяет превратить функцию с несколькими аргументами в набор функций с одним аргументом.
 *
 * Например, функция f(a, b, c) может быть преобразована в набор функций g(a), h(a, b), i(a, b, c).
 */
public fun <In1, In2, In3, In4, In5, Out> (suspend (In1, In2, In3, In4, In5) -> Out).suspendCurried(): (In1) -> (In2) -> (In3) -> (In4) -> suspend (In5) -> Out =
    { in1: In1 ->
        { in2: In2 ->
            { in3: In3 ->
                { in4: In4 ->
                    { in5: In5 -> this(in1, in2, in3, in4, in5) }
                }
            }
        }
    }
