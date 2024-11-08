package io.github.pavelannin.monadic.function

import kotlin.jvm.JvmName

/**
 * ###### Signature:
 * `(In1) -> (In2) -> Out` -> `(In1, In2) -> Out`
 *
 * ###### EN:
 * The reverse of currying allows you to turn a set of functions with one argument into a function with several arguments.
 *
 * ###### RU:
 * Функция, обратная каррированию, позволяет превратить набор функций с одним аргументом в функцию с несколькими аргументами.
 */
public fun <In1, In2, Out> ((In1) -> (In2) -> Out).uncurried(): (In1, In2) -> Out = { in1: In1, in2: In2 -> this(in1)(in2) }

/**
 * ###### Signature:
 * `(In1) -> (In2) -> Out` -> `(In1, In2) -> Out`
 *
 * ###### EN:
 * The reverse of currying allows you to turn a set of functions with one argument into a function with several arguments.
 *
 * ###### RU:
 * Функция, обратная каррированию, позволяет превратить набор функций с одним аргументом в функцию с несколькими аргументами.
 */
public fun <In1, In2, Out> ((In1) -> suspend (In2) -> Out).suspendUncurried(): suspend (In1, In2) -> Out =
    { in1: In1, in2: In2 -> this(in1)(in2) }

/**
 * ###### Signature:
 * `(In1) -> (In2) -> (In3) -> Out` -> `(In1, In2, In3) -> Out`
 *
 * ###### EN:
 * The reverse of currying allows you to turn a set of functions with one argument into a function with several arguments.
 *
 * ###### RU:
 * Функция, обратная каррированию, позволяет превратить набор функций с одним аргументом в функцию с несколькими аргументами.
 */
public fun <In1, In2, In3, Out> ((In1) -> (In2) -> (In3) -> Out).uncurried(): (In1, In2, In3) -> Out =
    { in1: In1, in2: In2, in3: In3 -> this(in1)(in2)(in3) }

/**
 * ###### Signature:
 * `(In1) -> (In2) -> (In3) -> Out` -> `(In1, In2, In3) -> Out`
 *
 * ###### EN:
 * The reverse of currying allows you to turn a set of functions with one argument into a function with several arguments.
 *
 * ###### RU:
 * Функция, обратная каррированию, позволяет превратить набор функций с одним аргументом в функцию с несколькими аргументами.
 */
public fun <In1, In2, In3, Out> ((In1) -> (In2) -> suspend (In3) -> Out).suspendUncurried(): suspend (In1, In2, In3) -> Out =
    { in1: In1, in2: In2, in3: In3 -> this(in1)(in2)(in3) }

/**
 * ###### Signature:
 * `(In1) -> (In2) -> (In3) -> (In4) -> Out` -> `(In1, In2, In3, In4) -> Out`
 *
 * ###### EN:
 * The reverse of currying allows you to turn a set of functions with one argument into a function with several arguments.
 *
 * ###### RU:
 * Функция, обратная каррированию, позволяет превратить набор функций с одним аргументом в функцию с несколькими аргументами.
 */
public fun <In1, In2, In3, In4, Out> ((In1) -> (In2) -> (In3) -> (In4) -> Out).uncurried(): (In1, In2, In3, In4) -> Out =
    { in1: In1, in2: In2, in3: In3, in4: In4 -> this(in1)(in2)(in3)(in4) }

/**
 * ###### Signature:
 * `(In1) -> (In2) -> (In3) -> (In4) -> Out` -> `(In1, In2, In3, In4) -> Out`
 *
 * ###### EN:
 * The reverse of currying allows you to turn a set of functions with one argument into a function with several arguments.
 *
 * ###### RU:
 * Функция, обратная каррированию, позволяет превратить набор функций с одним аргументом в функцию с несколькими аргументами.
 */
public fun <In1, In2, In3, In4, Out> ((In1) -> (In2) -> (In3) -> suspend (In4) -> Out).suspendUncurried(): suspend (In1, In2, In3, In4) -> Out =
    { in1: In1, in2: In2, in3: In3, in4: In4 -> this(in1)(in2)(in3)(in4) }

/**
 * ###### Signature:
 * `(In1) -> (In2) -> (In3) -> (In4) -> (In5) -> Out` -> `(In1, In2, In3, In4, In5) -> Out`
 *
 * ###### EN:
 * The reverse of currying allows you to turn a set of functions with one argument into a function with several arguments.
 *
 * ###### RU:
 * Функция, обратная каррированию, позволяет превратить набор функций с одним аргументом в функцию с несколькими аргументами.
 */
public fun <In1, In2, In3, In4, In5, Out> ((In1) -> (In2) -> (In3) -> (In4) -> (In5) -> Out).uncurried(): (In1, In2, In3, In4, In5) -> Out =
    { in1: In1, in2: In2, in3: In3, in4: In4, in5: In5 -> this(in1)(in2)(in3)(in4)(in5) }

/**
 * ###### Signature:
 * `(In1) -> (In2) -> (In3) -> (In4) -> (In5) -> Out` -> `(In1, In2, In3, In4, In5) -> Out`
 *
 * ###### EN:
 * The reverse of currying allows you to turn a set of functions with one argument into a function with several arguments.
 *
 * ###### RU:
 * Функция, обратная каррированию, позволяет превратить набор функций с одним аргументом в функцию с несколькими аргументами.
 */
public fun <In1, In2, In3, In4, In5, Out> ((In1) -> (In2) -> (In3) -> (In4) -> suspend (In5) -> Out).suspendUncurried(): suspend (In1, In2, In3, In4, In5) -> Out =
    { in1: In1, in2: In2, in3: In3, in4: In4, in5: In5 -> this(in1)(in2)(in3)(in4)(in5) }
