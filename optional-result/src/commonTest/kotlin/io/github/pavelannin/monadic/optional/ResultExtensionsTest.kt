package io.github.pavelannin.monadic.optional

import io.github.pavelannin.monadic.result.Result
import kotlin.test.Test
import kotlin.test.assertEquals

class ResultExtensionsTest {

    @Test
    fun convertCompanion() {
        val f = fun(optional: Optional<*>) = Result(optional) { "foo" }

        val someOptional = Optional.Some(Unit)
        val noneOptional = Optional.None

        assertEquals(Result.Ok(Unit), f(someOptional))
        assertEquals(Result.Error("foo"), f(noneOptional))
    }

    @Test
    fun convertExtension() {
        val f = fun(optional: Optional<*>) = optional.toResult { "foo" }

        val someOptional = Optional.Some(Unit)
        val noneOptional = Optional.None

        assertEquals(Result.Ok(Unit), f(someOptional))
        assertEquals(Result.Error("foo"), f(noneOptional))
    }
}
