package io.github.pavelannin.monadic.lce

import io.github.pavelannin.monadic.lce.LCE.Content
import io.github.pavelannin.monadic.lce.LCE.Error
import io.github.pavelannin.monadic.lce.LCE.Loading
import kotlinx.serialization.Serializable
import kotlin.Boolean
import kotlin.Nothing
import kotlin.OptIn
import kotlin.Pair
import kotlin.Triple
import kotlin.Unit
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * ###### EN:
 * The [LCE] type is a functional construct used to handle the state of an asynchronous
 * operation (network request, database operation, or file storage operation).
 * Contains the values [Loading] (for loading), [Content] (for a successful value) and [Error] (for errors).
 *
 * The [LCE] type allows you to process all the values of an asynchronous operation in one type,
 * applicable to the View state in MVI approaches.
 *
 * ###### RU:
 * Тип [LCE] - это функциональная конструкция, используемая для обработки состояния асинхронной
 * операции (сетевой запрос, работа с БД или работа с файловым хранилищем).
 * Собержит значения [Loading] (для загрузки), [Content] (для успешного значения) и [Error] (для ошибок).
 *
 * Тип [LCE] позволяет обрабатывать все значения асинхронной операции в одном типе, применим для состояния View в MVI подходах.
 *
 * ###### Example:
 * ```
 * val loading = LCE.Loading(1)
 * val content = LCE.Content(1)
 * val error = LCE.Error(1)
 * ```
 */
@Serializable(LCESerializer::class)
public sealed class LCE<out Loading, out Content, out Error> {
    public data class Loading<T>(public val loading: T) : LCE<T, Nothing, Nothing>() {

        public companion object {
            public operator fun invoke(): Loading<Unit> = Loading(Unit)
        }
    }

    public data class Content<T>(public val content: T) : LCE<Nothing, T, Nothing>() {

        public companion object {
            public operator fun invoke(): Content<Unit> = Content(Unit)
        }
    }

    public data class Error<T>(public val error: T) : LCE<Nothing, Nothing, T>() {

        public companion object {
            public operator fun invoke(): Error<Unit> = Error(Unit)
        }
    }

    /**
     * ###### EN:
     * Returns `true` if it is [Loading], or `false` otherwise.
     *
     * ###### RU:
     * Возвращает значение `true`, если [Loading], в противном случае `false`.
     *
     * ###### Example:
     * ```
     * Loading(1).isLoading // Result: true
     * Content(1).isLoading // Result: false
     * Error(1).isLoading // Result: false
     * ```
     */
    @OptIn(ExperimentalContracts::class)
    public fun isLoading(): Boolean {
        contract { returns(true) implies (this@LCE is LCE.Loading<Loading>) }
        return this is LCE.Loading
    }

    /**
     * ###### EN:
     * Returns `true` if it is [Content], or `false` otherwise.
     *
     * ###### RU:
     * Возвращает значение `true`, если [Content], в противном случае `false`.
     *
     * ###### Example:
     * ```
     * Loading(1).isContent // Result: false
     * Content(1).isContent // Result: true
     * Error(1).isContent // Result: false
     * ```
     */
    @OptIn(ExperimentalContracts::class)
    public fun isContent(): Boolean {
        contract { returns(true) implies (this@LCE is LCE.Content<Content>) }
        return this is LCE.Content
    }

    /**
     * ###### EN:
     * Returns `true` if it is [Error], or `false` otherwise.
     *
     * ###### RU:
     * Возвращает значение `true`, если [Error], в противном случае `false`.
     *
     * ###### Example:
     * ```
     * Loading(1).isError // Result: false
     * Content(1).isError // Result: false
     * Error(1).isError // Result: true
     * ```
     */
    @OptIn(ExperimentalContracts::class)
    public fun isError(): Boolean {
        contract { returns(true) implies (this@LCE is LCE.Error<Error>) }
        return this is LCE.Error
    }

    /**
     * ###### EN:
     * Returns the value `true` if [Content] or [Error], otherwise `false`.
     *
     * ###### RU:
     * Возвращает значение `true`, если [Content] или [Error], в противном случае `false`.
     *
     * ###### Example:
     * ```
     * Loading(1).isComplete // Result: false
     * Content(1).isComplete // Result: true
     * Error(1).isComplete // Result: true
     * ```
     */
    @OptIn(ExperimentalContracts::class)
    public fun isComplete(): Boolean {
        contract { returns(false) implies (this@LCE is LCE.Loading<Loading>) }
        return isContent() || isError()
    }

    /**
     * ###### EN:
     * Returns `value` if it is [Loading], or `null` otherwise.
     *
     * ###### RU:
     * Возвращает значение `value`, если [Loading], в противном случае `null`.
     *
     * ###### Example:
     * ```
     * Loading(1).loadingOrNull() // Result: 1
     * Content(1).loadingOrNull() // Result: null
     * Error(1).loadingOrNull() // Result: null
     * ```
     */
    public fun loadingOrNull(): Loading? {
        return if (this.isLoading()) loading else null
    }

    /**
     * ###### EN:
     * Returns `value` if it is [Content], or `null` otherwise.
     *
     * ###### RU:
     * Возвращает значение `value`, если [Content], в противном случае `null`.
     *
     * ###### Example:
     * ```
     * Loading(1).contentOrNull() // Result: null
     * Content(1).contentOrNull() // Result: 1
     * Error(1).contentOrNull() // Result: null
     * ```
     */
    public fun contentOrNull(): Content? {
        return if (this.isContent()) content else null
    }

    /**
     * ###### EN:
     * Returns `value` if it is [Error], or `null` otherwise.
     *
     * ###### RU:
     * Возвращает значение `value`, если [Error], в противном случае `null`.
     *
     * ###### Example:
     * ```
     * Loading(1).errorOrNull() // Result: null
     * Content(1).errorOrNull() // Result: null
     * Error(1).errorOrNull() // Result: 1
     * ```
     */
    public fun errorOrNull(): Error? {
        return if (this.isError()) error else null
    }

    /**
     * ###### EN:
     * Calls the [block] function if [Loading].
     *
     * ###### RU:
     * Вызывает функцию [block] если [Loading].
     *
     * ###### Example:
     * ```
     * Loading(1).onLoading { print("Hello") } // Log: Hello
     * Content(1).onLoading { print("Hello") }
     * Error(1).onLoading { print("Hello") }
     * ```
     */
    @OptIn(ExperimentalContracts::class)
    public inline fun onLoading(block: (Loading) -> Unit): LCE<Loading, Content, Error> {
        contract { callsInPlace(block, InvocationKind.AT_MOST_ONCE) }
        if (this.isLoading()) block(loading)
        return this
    }

    /**
     * ###### EN:
     * Calls the [block] function if [Content].
     *
     * ###### RU:
     * Вызывает функцию [block] если [Content].
     *
     * ###### Example:
     * ```
     * Loading(1).onContent { print("Hello") }
     * Content(1).onContent { print("Hello") } // Log: Hello
     * Error(1).onContent { print("Hello") }
     * ```
     */
    @OptIn(ExperimentalContracts::class)
    public inline fun onContent(block: (Content) -> Unit): LCE<Loading, Content, Error> {
        contract { callsInPlace(block, InvocationKind.AT_MOST_ONCE) }
        if (this.isContent()) block(content)
        return this
    }

    /**
     * ###### EN:
     * Calls the [block] function if [Error].
     *
     * ###### RU:
     * Вызывает функцию [block] если [Error].
     *
     * ###### Example:
     * ```
     * Loading(1).onError { print("Hello") }
     * Content(1).onError { print("Hello") }
     * Error(1).onError { print("Hello") } // Log: Hello
     * ```
     */
    @OptIn(ExperimentalContracts::class)
    public inline fun onError(block: (Error) -> Unit): LCE<Loading, Content, Error> {
        contract { callsInPlace(block, InvocationKind.AT_MOST_ONCE) }
        if (this.isError()) block(error)
        return this
    }

    /**
     * ###### Signature:
     * `LCE<Loading, Content, Error>` -> `LCE<Out, Content, Error>`
     *
     * ###### EN:
     * Transforms to `LCE<Out, Content, Error>` by applying the [transform] function if [Loading].
     *
     * ###### RU:
     * Трансформирует в `LCE<Out, Content, Error>`, применяя функцию [transform] если [Loading].
     *
     * ###### Example:
     * ```
     * Loading(1).mapLoading { "foo" } // Result: Loading("foo")
     * Content(1).mapLoading { "foo" } // Result: Content(1)
     * Error(1).mapLoading { "foo" }} // Result: Error(1)
     * ```
     */
    @OptIn(ExperimentalContracts::class)
    public inline fun <Out> mapLoading(
        transform: (Loading) -> Out,
    ): LCE<Out, Content, Error> {
        contract { callsInPlace(transform, InvocationKind.AT_MOST_ONCE) }
        return flatMapLoading { loading -> Loading(transform(loading)) }
    }

    /**
     * ###### Signature:
     * `LCE<Loading, Content, Error>` -> `LCE<Loading, Out, Error>`
     *
     * ###### EN:
     * Transforms to `LCE<Loading, Out, Error>` by applying the [transform] function if [Content].
     *
     * ###### RU:
     * Трансформирует в `LCE<Loading, Out, Error>`, применяя функцию [transform] если [Content].
     *
     * ###### Example:
     * ```
     * Loading(1).mapContent { "foo" } // Result: Loading(1)
     * Content(1).mapContent { "foo" } // Result: Content("foo")
     * Error(1).mapContent { "foo" }} // Result: Error(1)
     * ```
     */
    @OptIn(ExperimentalContracts::class)
    public inline fun <Out> mapContent(
        transform: (Content) -> Out,
    ): LCE<Loading, Out, Error> {
        contract { callsInPlace(transform, InvocationKind.AT_MOST_ONCE) }
        return flatMapContent { content -> Content(transform(content)) }
    }

    /**
     * ###### Signature:
     * `LCE<Loading, Content, Error>` -> `LCE<Loading, Content, Out>`
     *
     * ###### EN:
     * Transforms to `LCE<Loading, Content, Out>` by applying the [transform] function if [Error].
     *
     * ###### RU:
     * Трансформирует в `LCE<Loading, Content, Out>`, применяя функцию [transform] если [Error].
     *
     * ###### Example:
     * ```
     * Loading(1).mapError { "foo" } // Result: Loading(1)
     * Content(1).mapError { "foo" } // Result: Content(1)
     * Error(1).mapError { "foo" }} // Result: Error("foo")
     * ```
     */
    @OptIn(ExperimentalContracts::class)
    public inline fun <Out> mapError(
        transform: (Error) -> Out,
    ): LCE<Loading, Content, Out> {
        contract { callsInPlace(transform, InvocationKind.AT_MOST_ONCE) }
        return flatMapError { error -> Error(transform(error)) }
    }

    /**
     *  ###### Signature:
     * `LCE<Loading, Content, Error>` -> `LCE<LoadingOut, ContentOut, ErrorOut>`
     *
     * ###### EN:
     * Transforms to `LCE<LoadingOut, ContentOut, ErrorOut>` by applying the [loadingTransform] function if [Loading],
     * the [contentTransform] function if [Content], or the [errorTransform] function if [Error].
     *
     * ###### RU:
     * Трансформирует в `LCE<LoadingOut, ContentOut, ErrorOut>`, применяя функцию [loadingTransform] если [Loading],
     * функцию [contentTransform] если [Content], или функцию [errorTransform] если [Error].
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
    @OptIn(ExperimentalContracts::class)
    public inline fun <LoadingOut, ContentOut, ErrorOut> map(
        loadingTransform: (Loading) -> LoadingOut,
        contentTransform: (Content) -> ContentOut,
        errorTransform: (Error) -> ErrorOut,
    ): LCE<LoadingOut, ContentOut, ErrorOut> {
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
     * Transforms `LCE<Loading, Content, Error>` to the value [Result] by applying the function
     * [loadingTransform] if [Loading], the function [contentTransform] if [Content], or the
     * function [errorTransform] if [Error].
     *
     * ###### RU:
     * Трансформирует `LCE<Loading, Content, Error>` в значение [Result], применяя функцию
     * [loadingTransform] если [Loading], функцию [contentTransform] если [Content],или функцию
     * [errorTransform] если [Error].
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
    @OptIn(ExperimentalContracts::class)
    public inline fun <Result> fold(
        loadingTransform: (Loading) -> Result,
        contentTransform: (Content) -> Result,
        errorTransform: (Error) -> Result,
    ): Result {
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

    public companion object
}

/**
 * ###### Signature:
 * `LCE<LoadingIn, Content, Error>` -> `LCE<LoadingOut, Content, Error>`
 *
 * ###### EN:
 * Transforms to `LCE<LoadingOut, Content, Error>` by applying the [transform] function if [LCE.Loading].
 *
 * ###### RU:
 * Трансформирует в `LCE<LoadingOut, Content, Error>`, применяя функцию [transform] если [LCE.Loading].
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
@OptIn(ExperimentalContracts::class)
public inline fun <LoadingIn, LoadingOut, Content, Error> LCE<LoadingIn, Content, Error>.flatMapLoading(
    transform: (LoadingIn) -> LCE<LoadingOut, Content, Error>,
): LCE<LoadingOut, Content, Error> {
    contract { callsInPlace(transform, InvocationKind.AT_MOST_ONCE) }
    return when (this) {
        is LCE.Loading -> transform(loading)
        is LCE.Content -> this
        is LCE.Error -> this
    }
}

/**
 * ###### Signature:
 * `LCE<Loading, ContentIn, Error>` -> `LCE<Loading, ContentIn, Error>`
 *
 * ###### EN:
 * Transforms to `LCE<Loading, ContentOut, Error>` by applying the [transform] function if [LCE.Content].
 *
 * ###### RU:
 * Трансформирует в `LCE<Loading, ContentOut, Error>`, применяя функцию [transform] если [LCE.Content].
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
@OptIn(ExperimentalContracts::class)
public inline fun <Loading, ContentIn, ContentOut, Error> LCE<Loading, ContentIn, Error>.flatMapContent(
    transform: (ContentIn) -> LCE<Loading, ContentOut, Error>,
): LCE<Loading, ContentOut, Error> {
    contract { callsInPlace(transform, InvocationKind.AT_MOST_ONCE) }
    return when (this) {
        is LCE.Loading -> this
        is LCE.Content -> transform(content)
        is LCE.Error -> this
    }
}

/**
 * ###### Signature:
 * `LCE<Loading, Content, ErrorIn>` -> `LCE<Loading, Content, ErrorOut>`
 *
 * ###### EN:
 * Transforms to `LCE<Loading, Content, ErrorOut>` by applying the [transform] function if [LCE.Error].
 *
 * ###### RU:
 * Трансформирует в `LCE<Loading, Content, ErrorOut>`, применяя функцию [transform] если [LCE.Error].
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
@OptIn(ExperimentalContracts::class)
public inline fun <Loading, Content, ErrorIn, ErrorOut> LCE<Loading, Content, ErrorIn>.flatMapError(
    transform: (ErrorIn) -> LCE<Loading, Content, ErrorOut>,
): LCE<Loading, Content, ErrorOut> {
    contract { callsInPlace(transform, InvocationKind.AT_MOST_ONCE) }
    return when (this) {
        is LCE.Loading -> this
        is LCE.Content -> this
        is LCE.Error -> transform(error)
    }
}

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
@OptIn(ExperimentalContracts::class)
public inline fun <Loading, Content1, Content2, Error, Result> LCE<Loading, Content1, Error>.zip(
    lce: LCE<Loading, Content2, Error>,
    transform: (Content1, Content2) -> Result,
): LCE<Loading, Result, Error> {
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
public fun <Loading, Content1, Content2, Error> LCE<Loading, Content1, Error>.zip(
    lce: LCE<Loading, Content2, Error>,
): LCE<Loading, Pair<Content1, Content2>, Error> = zip(lce, ::Pair)

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
@OptIn(ExperimentalContracts::class)
public inline fun <Loading, Content1, Content2, Content3, Error, Result> LCE<Loading, Content1, Error>.zip(
    lce2: LCE<Loading, Content2, Error>,
    lce3: LCE<Loading, Content3, Error>,
    transform: (Content1, Content2, Content3) -> Result,
): LCE<Loading, Result, Error> {
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
public fun <Loading, Content1, Content2, Content3, Error> LCE<Loading, Content1, Error>.zip(
    lce2: LCE<Loading, Content2, Error>,
    lce3: LCE<Loading, Content3, Error>,
): LCE<Loading, Triple<Content1, Content2, Content3>, Error> = zip(lce2, lce3, ::Triple)

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
@OptIn(ExperimentalContracts::class)
public inline fun <Loading, Content1, Content2, Content3, Content4, Error, Result> LCE<Loading, Content1, Error>.zip(
    lce2: LCE<Loading, Content2, Error>,
    lce3: LCE<Loading, Content3, Error>,
    lce4: LCE<Loading, Content4, Error>,
    transform: (Content1, Content2, Content3, Content4) -> Result,
): LCE<Loading, Result, Error> {
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
@OptIn(ExperimentalContracts::class)
public inline fun <Loading, Content1, Content2, Content3, Content4, Content5, Error, Result> LCE<Loading, Content1, Error>.zip(
    lce2: LCE<Loading, Content2, Error>,
    lce3: LCE<Loading, Content3, Error>,
    lce4: LCE<Loading, Content4, Error>,
    lce5: LCE<Loading, Content5, Error>,
    transform: (Content1, Content2, Content3, Content4, Content5) -> Result,
): LCE<Loading, Result, Error> {
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
