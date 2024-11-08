package io.github.pavelannin.monadic.function

/**
 * ###### Signature:
 * `(In) -> Interim` & `(Interim) -> Out` -> `(In) -> Out`
 *
 * ###### EN:
 * Function composition is an operation in which one function is
 * applied first, and the result of this function is used as an argument for another function.
 *
 * For example, if we have two functions f(x) and g(x), then the composition of the functions g(f(x))
 * means that we first calculate f(x), and then use the result as an argument for the function g(x).
 *
 * ###### RU:
 * Композиция функций - это операция, при которой сначала применяется одна функция, а результат работы
 * этой функции используется в качестве аргумента для другой функции.
 *
 * Например, если у нас есть две функции f(x) и g(x), то состав функций g(f(x)) означает,
 * что мы сначала вычисляем f(x), а затем используем результат в качестве аргумента для функции g(x).
 *
 * @receiver
 * ###### EN:
 * A function that takes a value in [In] and returns a value in [Interim].
 * ###### RU:
 * Функция, которая принимает значение в [In] и возвращает значение в [Interim].
 *
 * @param function
 * ###### EN:
 * Function that takes a value in [Interim] and returns a value in [Out].
 * ###### RU:
 * Функция, которая принимает значение в [Interim] и возвращает значение в [Out].
 *
 * @return
 * ###### EN:
 * A new function that takes a value in [In] and returns a value in [Out].
 * ###### RU:
 * Новая функция, которая принимает значение в [In] и возвращает значение в [Out].
 */
public inline infix fun <In, Interim, Out> ((In) -> Interim).compose(
    crossinline function: (Interim) -> Out,
): (In) -> Out = { inParam: In -> function(this(inParam)) }

/**
 * ###### Signature:
 * `(In) -> Interim` & `(Interim) -> Out` -> `(In) -> Out`
 *
 * ###### EN:
 * Function composition is an operation in which one function is
 * applied first, and the result of this function is used as an argument for another function.
 *
 * For example, if we have two functions f(x) and g(x), then the composition of the functions g(f(x))
 * means that we first calculate f(x), and then use the result as an argument for the function g(x).
 *
 * ###### RU:
 * Композиция функций - это операция, при которой сначала применяется одна функция, а результат работы
 * этой функции используется в качестве аргумента для другой функции.
 *
 * Например, если у нас есть две функции f(x) и g(x), то состав функций g(f(x)) означает,
 * что мы сначала вычисляем f(x), а затем используем результат в качестве аргумента для функции g(x).
 *
 * @receiver
 * ###### EN:
 * A function that takes a value in [In] and returns a value in [Interim].
 * ###### RU:
 * Функция, которая принимает значение в [In] и возвращает значение в [Interim].
 *
 * @param function
 * ###### EN:
 * Function that takes a value in [Interim] and returns a value in [Out].
 * ###### RU:
 * Функция, которая принимает значение в [Interim] и возвращает значение в [Out].
 *
 * @return
 * ###### EN:
 * A new function that takes a value in [In] and returns a value in [Out].
 * ###### RU:
 * Новая функция, которая принимает значение в [In] и возвращает значение в [Out].
 */
public inline infix fun <In, Interim, Out> (suspend (In) -> Interim).suspendCompose(
    crossinline function: (suspend (Interim) -> Out),
): suspend (In) -> Out  = { inParam: In -> function(this(inParam)) }
