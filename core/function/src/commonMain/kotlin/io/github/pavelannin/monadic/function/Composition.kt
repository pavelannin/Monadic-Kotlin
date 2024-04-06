package io.github.pavelannin.monadic.function

/**
 * Function composition is an operation in which one function is
 * applied first, and the result of this function is used as an argument for another function.
 * For example, if we have two functions f(x) and g(x), then the composition of the functions g(f(x))
 * means that we first calculate f(x), and then use the result as an argument for the function g(x).
 *
 * Backward composition of functions.
 *
 * @receiver A function that takes a value in [In] and returns a value in [Interim].
 * @param function function that takes a value in [Interim] and returns a value in [Out].
 * @return A new function that takes a value in [In] and returns a value in [Out].
 */
public inline infix fun <In, Interim, Out> ((In) -> Interim).compose(
    crossinline function: (Interim) -> Out,
): (In) -> Out = { inParam: In -> function(this(inParam)) }

/**
 * Function composition is an operation in which one function is
 * applied first, and the result of this function is used as an argument for another function.
 * For example, if we have two functions f(x) and g(x), then the composition of the functions g(f(x))
 * means that we first calculate f(x), and then use the result as an argument for the function g(x).
 *
 * Backward composition of functions.
 *
 * @receiver A function that takes a value in [In] and returns a value in [Interim].
 * @param function function that takes a value in [Interim] and returns a value in [Out].
 * @return A new function that takes a value in [In] and returns a value in [Out].
 */
public inline infix fun <In, Interim, Out> (suspend (In) -> Interim).compose(
    crossinline function: (suspend (Interim) -> Out),
): suspend (In) -> Out  = { inParam: In -> function(this(inParam)) }

/**
 * Function composition is an operation in which one function is
 * applied first, and the result of this function is used as an argument for another function.
 * For example, if we have two functions f(x) and g(x), then the composition of the functions g(f(x))
 * means that we first calculate f(x), and then use the result as an argument for the function g(x).
 *
 * Backward composition of functions.
 *
 * @receiver A function that takes a value in [In] and returns a value in [Interim].
 * @param function function that takes a value in [Interim] and returns a value in [Out].
 * @return A new function that takes a value in [In] and returns a value in [Out].
 */
public inline infix fun <In, Interim, Out> (suspend (In) -> Interim).compose(
    crossinline function: (Interim) -> Out,
): suspend (In) -> Out  = { inParam: In -> function(this(inParam)) }

/**
 * Function composition is an operation in which one function is
 * applied first, and the result of this function is used as an argument for another function.
 * For example, if we have two functions f(x) and g(x), then the composition of the functions g(f(x))
 * means that we first calculate f(x), and then use the result as an argument for the function g(x).
 *
 * Backward composition of functions.
 *
 * @receiver A function that takes a value in [In] and returns a value in [Interim].
 * @param function function that takes a value in [Interim] and returns a value in [Out].
 * @return A new function that takes a value in [In] and returns a value in [Out].
 */
public inline infix fun <In, Interim, Out> ((In) -> Interim).compose(
    crossinline function: (suspend (Interim) -> Out),
): suspend (In) -> Out  = { inParam: In -> function(this(inParam)) }
