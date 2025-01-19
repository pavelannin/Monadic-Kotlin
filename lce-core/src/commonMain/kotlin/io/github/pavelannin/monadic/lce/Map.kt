package io.github.pavelannin.monadic.lce

import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * ###### Signature:
 * `LCE<Loading, Content, Error>` -> `LCE<Out, Content, Error>`
 *
 * ###### EN:
 * Transforms to `LCE<Out, Content, Error>` by applying the [transform] function if [LCE.Loading].
 *
 * ###### RU:
 * Трансформирует в `LCE<Out, Content, Error>`, применяя функцию [transform] если [LCE.Loading].
 *
 * ###### Example:
 * ```
 * Loading(1).mapLoading { "foo" } // Result: Loading("foo")
 * Content(1).mapLoading { "foo" } // Result: Content(1)
 * Error(1).mapLoading { "foo" }} // Result: Error(1)
 * ```
 */
public inline fun <L, C, E, Out> LCE<L, C, E>.mapLoading(
    transform: (L) -> Out,
): LCE<Out, C, E> {
    contract { callsInPlace(transform, InvocationKind.AT_MOST_ONCE) }
    return flatMapLoading { loading -> LCE.Loading(transform(loading)) }
}

/**
 * ###### Signature:
 * `LCE<Loading, Content, Error>` -> `LCE<Loading, Out, Error>`
 *
 * ###### EN:
 * Transforms to `LCE<Loading, Out, Error>` by applying the [transform] function if [LCE.Content].
 *
 * ###### RU:
 * Трансформирует в `LCE<Loading, Out, Error>`, применяя функцию [transform] если [LCE.Content].
 *
 * ###### Example:
 * ```
 * Loading(1).mapContent { "foo" } // Result: Loading(1)
 * Content(1).mapContent { "foo" } // Result: Content("foo")
 * Error(1).mapContent { "foo" }} // Result: Error(1)
 * ```
 */
public inline fun <L, C, E, Out> LCE<L, C, E>. mapContent(
    transform: (C) -> Out,
): LCE<L, Out, E> {
    contract { callsInPlace(transform, InvocationKind.AT_MOST_ONCE) }
    return flatMapContent { content -> LCE.Content(transform(content)) }
}

/**
 * ###### Signature:
 * `LCE<Loading, Content, Error>` -> `LCE<Loading, Content, Out>`
 *
 * ###### EN:
 * Transforms to `LCE<Loading, Content, Out>` by applying the [transform] function if [LCE.Error].
 *
 * ###### RU:
 * Трансформирует в `LCE<Loading, Content, Out>`, применяя функцию [transform] если [LCE.Error].
 *
 * ###### Example:
 * ```
 * Loading(1).mapError { "foo" } // Result: Loading(1)
 * Content(1).mapError { "foo" } // Result: Content(1)
 * Error(1).mapError { "foo" }} // Result: Error("foo")
 * ```
 */
public inline fun <L, C, E, Out> LCE<L, C, E>.mapError(
    transform: (E) -> Out,
): LCE<L, C, Out> {
    contract { callsInPlace(transform, InvocationKind.AT_MOST_ONCE) }
    return flatMapError { error -> LCE.Error(transform(error)) }
}

/**
 *  ###### Signature:
 * `LCE<Loading, Content, Error>` -> `LCE<LoadingOut, ContentOut, ErrorOut>`
 *
 * ###### EN:
 * Transforms to `LCE<LoadingOut, ContentOut, ErrorOut>` by applying the [loadingTransform] function if [LCE.Loading],
 * the [contentTransform] function if [LCE.Content], or the [errorTransform] function if [LCE.Error].
 *
 * ###### RU:
 * Трансформирует в `LCE<LoadingOut, ContentOut, ErrorOut>`, применяя функцию [loadingTransform] если [LCE.Loading],
 * функцию [contentTransform] если [LCE.Content], или функцию [errorTransform] если [LCE.Error].
 *
 * ###### Example:
 * ```
 * Loading(1).map(
 *   loadingTransform = { "foo" },
 *   contentTransform = { "bar" },
 *   errorTransform = { "zoo" },
 * ) // Result: Loading("foo")
 *
 * Content(1).map(
 *   loadingTransform = { "foo" },
 *   contentTransform = { "bar" },
 *   errorTransform = { "zoo" },
 * ) // Result: Content("bar")
 *
 * Error(1).map(
 *   loadingTransform = { "foo" },
 *   contentTransform = { "bar" },
 *   errorTransform = { "zoo" },
 * ) // Result: Error("zoo")
 * ```
 */
public inline fun <L, C, E, LOut, COut, EOut> LCE<L, C, E>.map(
    loadingTransform: (L) -> LOut,
    contentTransform: (C) -> COut,
    errorTransform: (E) -> EOut,
): LCE<LOut, COut, EOut> {
    contract {
        callsInPlace(loadingTransform, InvocationKind.AT_MOST_ONCE)
        callsInPlace(contentTransform, InvocationKind.AT_MOST_ONCE)
        callsInPlace(errorTransform, InvocationKind.AT_MOST_ONCE)
    }
    return mapLoading(loadingTransform)
        .mapContent(contentTransform)
        .mapError(errorTransform)
}

/**
 *  ###### Signature:
 * `LCE<Loading, Content, Error>` -> `Result`
 *
 * ###### EN:
 * Transforms `LCE<Loading, Content, Error>` to the value [Out] by applying the function
 * [loadingTransform] if [LCE.Loading], the function [contentTransform] if [LCE.Content], or the
 * function [errorTransform] if [LCE.Error].
 *
 * ###### RU:
 * Трансформирует `LCE<Loading, Content, Error>` в значение [Out], применяя функцию
 * [loadingTransform] если [LCE.Loading], функцию [contentTransform] если [LCE.Content],или функцию
 * [errorTransform] если [LCE.Error].
 *
 * ###### Example:
 * ```
 * Loading(1).fold(
 *   loadingTransform = { "foo" },
 *   contentTransform = { "bar" },
 *   errorTransform = { "zoo" },
 * ) // Result: "foo"
 *
 * Content(1).fold(
 *   loadingTransform = { "foo" },
 *   contentTransform = { "bar" },
 *   errorTransform = { "zoo" },
 * ) // Result: "bar"
 *
 * Error(1).fold(
 *   loadingTransform = { "foo" },
 *   contentTransform = { "bar" },
 *   errorTransform = { "zoo" },
 * ) // Result: "zoo"
 * ```
 */
public inline fun <L, C, E, Out> LCE<L, C, E>.fold(
    loadingTransform: (L) -> Out,
    contentTransform: (C) -> Out,
    errorTransform: (E) -> Out,
): Out {
    contract {
        callsInPlace(loadingTransform, InvocationKind.AT_MOST_ONCE)
        callsInPlace(contentTransform, InvocationKind.AT_MOST_ONCE)
        callsInPlace(errorTransform, InvocationKind.AT_MOST_ONCE)
    }
    return when (this) {
        is LCE.Loading -> loadingTransform(loading)
        is LCE.Content -> contentTransform(content)
        is LCE.Error -> errorTransform(error)
    }
}


/**
 * ###### Signature:
 * `LCE<Loading, Content, Error>` -> `LCE<Out, Content, Error>`
 *
 * ###### EN:
 * Transforms to `LCE<Out, Content, Error>` by applying the [transform] function if [LCE.Loading].
 *
 * ###### RU:
 * Трансформирует в `LCE<Out, Content, Error>`, применяя функцию [transform] если [LCE.Loading].
 *
 * ###### Example:
 * ```
 * Loading(1).flatMapLoading { Loading("foo") } // Result: Loading("foo")
 * Loading(1).flatMapLoading { Content(2) } // Result: Content(2)
 * Loading(1).flatMapLoading { Error(2) }} // Result: Error(2)
 *
 * Content(1).flatMapLoading { Loading("foo") } // Result: Content(1)
 * Error(1).flatMapLoading { Loading("foo") } // Result: Error(1)
 * ```
 */
public inline fun <L, C, E, Out> LCE<L, C, E>.flatMapLoading(
    transform: (L) -> LCE<Out, C, E>,
): LCE<Out, C, E> {
    contract { callsInPlace(transform, InvocationKind.AT_MOST_ONCE) }
    return when (this) {
        is LCE.Loading -> transform(loading)
        is LCE.Content -> this
        is LCE.Error -> this
    }
}

/**
 * ###### Signature:
 * `LCE<Loading, Content, Error>` -> `LCE<Loading, Out, Error>`
 *
 * ###### EN:
 * Transforms to `LCE<Loading, Out, Error>` by applying the [transform] function if [LCE.Content].
 *
 * ###### RU:
 * Трансформирует в `LCE<Loading, Out, Error>`, применяя функцию [transform] если [LCE.Content].
 *
 * ###### Example:
 * ```
 * Content(1).flatMapContent { Loading(2) } // Result: Loading(2)
 * Content(1).flatMapContent { Content("foo") } // Result: Content("foo")
 * Content(1).flatMapContent { Error(2) }} // Result: Error(2)
 *
 * Loading(1).flatMapContent { Content("foo") } // Result: Loading(1)
 * Error(1).flatMapContent { Content("foo") } // Result: Error(1)
 * ```
 */
public inline fun <L, C, E, Out> LCE<L, C, E>.flatMapContent(
    transform: (C) -> LCE<L, Out, E>,
): LCE<L, Out, E> {
    contract { callsInPlace(transform, InvocationKind.AT_MOST_ONCE) }
    return when (this) {
        is LCE.Loading -> this
        is LCE.Content -> transform(content)
        is LCE.Error -> this
    }
}

/**
 * ###### Signature:
 * `LCE<Loading, Content, Error>` -> `LCE<Loading, Content, Out>`
 *
 * ###### EN:
 * Transforms to `LCE<Loading, Content, Out>` by applying the [transform] function if [LCE.Error].
 *
 * ###### RU:
 * Трансформирует в `LCE<Loading, Content, Out>`, применяя функцию [transform] если [LCE.Error].
 *
 * ###### Example:
 * ```
 * Error(1).flatMapError { Loading(2) } // Result: Loading(2)
 * Error(1).flatMapError { Content(2) } // Result: Content(2)
 * Error(1).flatMapError { Error(""foo") }} // Result: Error("foo")
 *
 * Loading(1).flatMapError { Content("foo") } // Result: Loading(1)
 * Content(1).flatMapError { Content("foo") } // Result: Content(1)
 * ```
 */
public inline fun <L, C, E, Out> LCE<L, C, E>.flatMapError(
    transform: (E) -> LCE<L, C, Out>,
): LCE<L, C, Out> {
    contract { callsInPlace(transform, InvocationKind.AT_MOST_ONCE) }
    return when (this) {
        is LCE.Loading -> this
        is LCE.Content -> this
        is LCE.Error -> transform(error)
    }
}
