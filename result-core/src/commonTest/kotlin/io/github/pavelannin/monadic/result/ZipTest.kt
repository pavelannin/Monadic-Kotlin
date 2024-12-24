package io.github.pavelannin.monadic.result

import kotlin.test.Test
import kotlin.test.assertEquals

class ZipTest {
    @Test
    fun zip() {
        val f = fun(r: Result<*, *>) = r.zip(Result.Ok())  { _, _ -> "foo" }

        val ok = Result.Ok()
        val error = Result.Error()

        assertEquals(Result.Ok("foo"), f(ok))
        assertEquals(error, f(error))
    }

    @Test
    fun zipPair() {
        val f = fun(r: Result<*, *>) = r.zip(Result.Ok("bar"))

        val ok = Result.Ok()
        val error = Result.Error()

        assertEquals(Result.Ok(Unit to "bar"), f(ok))
        assertEquals(error, f(error))
    }

    @Test
    fun zip3() {
        val f = fun(r: Result<*, *>) = r.zip(
            Result.Ok(Unit),
            Result.Ok(Unit),
        ) { _, _, _ -> "foo" }

        val ok = Result.Ok()
        val error = Result.Error()

        assertEquals(Result.Ok("foo"), f(ok))
        assertEquals(error, f(error))
    }

    @Test
    fun zipTriple() {
        val f = fun(r: Result<*, *>) = r.zip(
            Result.Ok("bar"),
            Result.Ok("zoo"),
        )

        val ok = Result.Ok()
        val error = Result.Error()

        assertEquals(Result.Ok(Triple(Unit, "bar", "zoo")), f(ok))
        assertEquals(error, f(error))
    }

    @Test
    fun zip4() {
        val f = fun(r: Result<*, *>) = r.zip(
            Result.Ok(Unit),
            Result.Ok(Unit),
            Result.Ok(Unit),
        ) { _, _, _, _ -> "foo" }

        val ok = Result.Ok()
        val error = Result.Error()

        assertEquals(Result.Ok("foo"), f(ok))
        assertEquals(error, f(error))
    }

    @Test
    fun zip5() {
        val f = fun(r: Result<*, *>) = r.zip(
            Result.Ok(Unit),
            Result.Ok(Unit),
            Result.Ok(Unit),
            Result.Ok(Unit),
        ) { _, _, _, _, _ -> "foo" }

        val ok = Result.Ok()
        val error = Result.Error()

        assertEquals(Result.Ok("foo"), f(ok))
        assertEquals(error, f(error))
    }
}
