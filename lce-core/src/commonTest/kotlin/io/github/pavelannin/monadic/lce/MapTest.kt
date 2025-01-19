package io.github.pavelannin.monadic.lce

import kotlin.test.Test
import kotlin.test.assertEquals

class MapTest {
    @Test
    fun mapLoading() {
        val f = fun(lce: LCE<*, *, *>) = lce.mapLoading { "foo" }
        val resultLce = LCE.Loading("foo")

        val loadingLce = LCE.Loading(Unit).mapLoading { "foo" }
        val contentLce = LCE.Content(Unit).mapLoading { "foo" }
        val errorLce = LCE.Error(Unit).mapLoading { "foo" }

        assertEquals(resultLce, f(loadingLce))
        assertEquals(contentLce, f(contentLce))
        assertEquals(errorLce, f(errorLce))
    }

    @Test
    fun mapContent() {
        val f = fun(lce: LCE<*, *, *>) = lce.mapContent { "foo" }
        val resultLce = LCE.Content("foo")

        val loadingLce = LCE.Loading(Unit)
        val contentLce = LCE.Content(Unit)
        val errorLce = LCE.Error(Unit)

        assertEquals(resultLce, f(contentLce))
        assertEquals(loadingLce, f(loadingLce))
        assertEquals(errorLce, f(errorLce))
    }

    @Test
    fun mapError() {
        val f = fun(lce: LCE<*, *, *>) = lce.mapError { "foo" }
        val resultLce = LCE.Error("foo")

        val loadingLce = LCE.Loading(Unit)
        val contentLce = LCE.Content(Unit)
        val errorLce = LCE.Error(Unit)

        assertEquals(resultLce, f(errorLce))
        assertEquals(loadingLce, f(loadingLce))
        assertEquals(contentLce, f(contentLce))
    }

    @Test
    fun map() {
        val f = fun(lce: LCE<*, *, *>) = lce.map(
            loadingTransform = { "foo" },
            contentTransform = { "bar" },
            errorTransform = { "zoo" },
        )

        val loadingLce = LCE.Loading(Unit)
        val contentLce = LCE.Content(Unit)
        val errorLce = LCE.Error(Unit)

        assertEquals(LCE.Loading("foo"), f(loadingLce))
        assertEquals(LCE.Content("bar"), f(contentLce))
        assertEquals(LCE.Error("zoo"), f(errorLce))
    }

    @Test
    fun fold() {
        val f = fun(lce: LCE<*, *, *>) = lce.fold(
            loadingTransform = { "foo" },
            contentTransform = { "bar" },
            errorTransform = { "zoo" },
        )

        val loadingLce = LCE.Loading(Unit)
        val contentLce = LCE.Content(Unit)
        val errorLce = LCE.Error(Unit)

        assertEquals("foo", f(loadingLce))
        assertEquals("bar", f(contentLce))
        assertEquals("zoo", f(errorLce))
    }

    @Test
    fun flatMapLoading() {
        val resultLce = LCE.Content("foo")
        val f = fun(lce: LCE<*, *, *>) = lce.flatMapLoading { resultLce }

        val loadingLce = LCE.Loading(Unit)
        val contentLce = LCE.Content(Unit)
        val errorLce = LCE.Error(Unit)

        assertEquals(resultLce, f(loadingLce))
        assertEquals(contentLce, f(contentLce))
        assertEquals(errorLce, f(errorLce))
    }

    @Test
    fun flatMapContent() {
        val resultLce = LCE.Loading("foo")
        val f = fun(lce: LCE<*, *, *>) = lce.flatMapContent { resultLce }

        val loadingLce = LCE.Loading(Unit)
        val contentLce = LCE.Content(Unit)
        val errorLce = LCE.Error(Unit)

        assertEquals(resultLce, f(contentLce))
        assertEquals(loadingLce, f(loadingLce))
        assertEquals(errorLce, f(errorLce))
    }

    @Test
    fun flatMapError() {
        val resultLce = LCE.Content("foo")
        val f = fun(lce: LCE<*, *, *>) = lce.flatMapError { resultLce }

        val loadingLce = LCE.Loading(Unit)
        val contentLce = LCE.Content(Unit)
        val errorLce = LCE.Error(Unit)

        assertEquals(resultLce, f(errorLce))
        assertEquals(loadingLce, f(loadingLce))
        assertEquals(contentLce, f(contentLce))
    }
}
