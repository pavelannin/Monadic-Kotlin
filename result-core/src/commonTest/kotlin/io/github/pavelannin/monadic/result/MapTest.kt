package io.github.pavelannin.monadic.result

import kotlin.test.Test
import kotlin.test.assertEquals

class MapTest {
    @Test
    fun map() {
        val f = fun(result: Result<*, *>) = result.map { "foo" }
        val result = Result.Ok("foo")

        val ok = Result.Ok()
        val error = Result.Error()

        assertEquals(result, f(ok))
        assertEquals(error, f(error))
    }

    @Test
    fun mapError() {
        val f = fun(result: Result<*, *>) = result.mapError { "foo" }
        val result = Result.Error("foo")

        val ok = Result.Ok()
        val error = Result.Error()

        assertEquals(ok, f(ok))
        assertEquals(result, f(error))
    }

    @Test
    fun bimap() {
        val f = fun(result: Result<*, *>) = result.bimap(
            okTransform = { "foo" },
            errorTransform = { "bar" },
        )
        val okResult = Result.Ok("foo")
        val errorResult = Result.Error("bar")

        val ok = Result.Ok()
        val error = Result.Error()

        assertEquals(okResult, f(ok))
        assertEquals(errorResult, f(error))
    }

    @Test
    fun fold() {
        val f = fun(result: Result<*, *>) = result.fold(
            okTransform = { "foo" },
            errorTransform = { "bar" },
        )

        val ok = Result.Ok()
        val error = Result.Error()

        assertEquals("foo", f(ok))
        assertEquals("bar", f(error))
    }

    @Test
    fun foldOk() {
        val f = fun(result: Result<*, *>) = result.foldOk("bar") { "foo" }

        val ok = Result.Ok()
        val error = Result.Error()

        assertEquals("foo", f(ok))
        assertEquals("bar", f(error))
    }

    @Test
    fun foldError() {
        val f = fun(result: Result<*, *>) = result.foldError("foo") { "bar" }

        val ok = Result.Ok()
        val error = Result.Error()

        assertEquals("foo", f(ok))
        assertEquals("bar", f(error))
    }

    @Test
    fun flatMap() {
        val result = Result.Ok("foo")
        val f = fun(r: Result<*, *>) = r.flatMap { result }

        val ok = Result.Ok()
        val error = Result.Error()

        assertEquals(result, f(ok))
        assertEquals(error, f(error))
    }

    @Test
    fun flatMapError() {
        val result = Result.Ok("bar")
        val f = fun(r: Result<*, *>) = r.flatMapError { result }

        val ok = Result.Ok()
        val error = Result.Error()

        assertEquals(ok, f(ok))
        assertEquals(result, f(error))
    }

    @Test
    fun flatten() {
        val f = fun(r: Result<Result<*, *>, *>) = r.flatten()

        val ok = Result.Ok(Result.Ok())
        val error = Result.Error(Result.Error())

        assertEquals(Result.Ok(), f(ok))
        assertEquals(error, f(error))
    }

    @Test
    fun take() {
        val f = fun(r: Result<*, *>) = r.take()

        val ok = Result.Ok()
        val error = Result.Error()

        assertEquals(Unit, f(ok))
        assertEquals(Unit, f(error))
    }
}
