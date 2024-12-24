package io.github.pavelannin.monadic.result

import kotlin.test.Test
import kotlin.test.assertEquals

class AndTest {
    @Test
    fun and() {
        val result = Result.Ok("foo")
        val f = fun(r: Result<*, *>) = r.and(result)

        val ok = Result.Ok(Unit)
        val error = Result.Error(Unit)

        assertEquals(result, f(ok))
        assertEquals(error, f(error))
    }

    @Test
    fun andThen() {
        val result = Result.Ok("foo")
        val f = fun(r: Result<*, *>) = r.andThen { result }

        val ok = Result.Ok(Unit)
        val error = Result.Error(Unit)

        assertEquals(result, f(ok))
        assertEquals(error, f(error))
    }
}
