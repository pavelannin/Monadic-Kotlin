package io.github.pavelannin.monadic.result

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ResultTest {
    @Test
    fun isOk() {
        val ok = Result.Ok()
        val error = Result.Error()
        assertTrue(ok.isOk())
        assertFalse(error.isOk())
    }

    @Test
    fun isError() {
        val ok = Result.Ok()
        val error = Result.Error()
        assertFalse(ok.isError())
        assertTrue(error.isError())
    }

    @Test
    fun lift() {
        val f = fun(u: Unit) = "foo"
        val lift = Result.lift<Unit, Unit, String>(f)

        val ok = Result.Ok()
        val error = Result.Error()

        assertEquals(Result.Ok("foo"), lift(ok))
        assertEquals(error, lift(error))
    }

    @Test
    fun bilift() {
        val okFun = fun(u: Unit) = "foo"
        val errorFun = fun(u: Unit) = "bar"
        val lift = Result.lift(okFun, errorFun)

        val ok = Result.Ok()
        val error = Result.Error()

        assertEquals(Result.Ok("foo"), lift(ok))
        assertEquals(Result.Error("bar"), lift(error))
    }

    @Test
    fun catch() {
        val okFun = fun() = Result.catch { "foo" }
        val exception = RuntimeException()
        val errorFun = fun() = Result.catch { throw exception }

        assertEquals(Result.Ok("foo"), okFun())
        assertEquals(Result.Error(exception), errorFun())
    }
}
