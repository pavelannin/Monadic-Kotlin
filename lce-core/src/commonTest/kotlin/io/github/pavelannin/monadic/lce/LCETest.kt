package io.github.pavelannin.monadic.lce

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.test.fail

class LCETest {
    @Test
    fun isLoading() {
        val loadingLce = LCE.Loading(Unit)
        val contentLce = LCE.Content(Unit)
        val errorLce = LCE.Error(Unit)
        assertTrue(loadingLce.isLoading())
        assertFalse(contentLce.isLoading())
        assertFalse(errorLce.isLoading())
    }

    @Test
    fun isContent() {
        val loadingLce = LCE.Loading(Unit)
        val contentLce = LCE.Content(Unit)
        val errorLce = LCE.Error(Unit)
        assertTrue(contentLce.isContent())
        assertFalse(loadingLce.isContent())
        assertFalse(errorLce.isContent())
    }

    @Test
    fun isError() {
        val loadingLce = LCE.Loading(Unit)
        val contentLce = LCE.Content(Unit)
        val errorLce = LCE.Error(Unit)
        assertTrue(errorLce.isError())
        assertFalse(loadingLce.isError())
        assertFalse(contentLce.isError())
    }

    @Test
    fun isComplete() {
        val loadingLce = LCE.Loading(Unit)
        val contentLce = LCE.Content(Unit)
        val errorLce = LCE.Error(Unit)
        assertTrue(contentLce.isComplete())
        assertTrue(errorLce.isComplete())
        assertFalse(loadingLce.isComplete())
    }

    @Test
    fun loadingOrNull() {
        val loadingLce = LCE.Loading(Unit)
        val contentLce = LCE.Content(Unit)
        val errorLce = LCE.Error(Unit)
        assertEquals(Unit, loadingLce.loadingOrNull())
        assertNull(contentLce.loadingOrNull())
        assertNull(errorLce.loadingOrNull())
    }

    @Test
    fun contentOrNull() {
        val loadingLce = LCE.Loading(Unit)
        val contentLce = LCE.Content(Unit)
        val errorLce = LCE.Error(Unit)
        assertEquals(Unit, contentLce.contentOrNull())
        assertNull(loadingLce.contentOrNull())
        assertNull(errorLce.contentOrNull())
    }

    @Test
    fun errorOrNull() {
        val loadingLce = LCE.Loading(Unit)
        val contentLce = LCE.Content(Unit)
        val errorLce = LCE.Error(Unit)
        assertEquals(Unit, errorLce.errorOrNull())
        assertNull(loadingLce.errorOrNull())
        assertNull(contentLce.errorOrNull())
    }

    @Test
    fun onLoading() {
        val loadingLce = LCE.Loading(Unit)
        val contentLce = LCE.Content(Unit)
        val errorLce = LCE.Error(Unit)
        loadingLce.onLoading { loading -> assertEquals(Unit, loading) }
        contentLce.onLoading { fail() }
        errorLce.onLoading { fail() }
    }

    @Test
    fun onContent() {
        val loadingLce = LCE.Loading(Unit)
        val contentLce = LCE.Content(Unit)
        val errorLce = LCE.Error(Unit)
        contentLce.onContent { content -> assertEquals(Unit, content) }
        loadingLce.onContent { fail() }
        errorLce.onContent { fail() }
    }

    @Test
    fun onError() {
        val loadingLce = LCE.Loading(Unit)
        val contentLce = LCE.Content(Unit)
        val errorLce = LCE.Error(Unit)
        errorLce.onError { error -> assertEquals(Unit, error) }
        loadingLce.onError { fail() }
        contentLce.onError { fail() }
    }

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

    @Test
    fun zip() {
        val resultLce = LCE.Content("foo")
        val f = fun(lce: LCE<*, *, *>) = lce.zip(LCE.Content("bar")) { _, _ -> "foo" }

        val loadingLce = LCE.Loading(Unit)
        val contentLce = LCE.Content(Unit)
        val errorLce = LCE.Error(Unit)

        assertEquals(resultLce, f(contentLce))
        assertEquals(loadingLce, f(loadingLce))
        assertEquals(errorLce, f(errorLce))
    }

    @Test
    fun zipPair() {
        val resultLce = LCE.Content(Unit to "bar")
        val f = fun(lce: LCE<*, *, *>) = lce.zip(LCE.Content("bar"))

        val loadingLce = LCE.Loading(Unit)
        val contentLce = LCE.Content(Unit)
        val errorLce = LCE.Error(Unit)

        assertEquals(resultLce, f(contentLce))
        assertEquals(loadingLce, f(loadingLce))
        assertEquals(errorLce, f(errorLce))
    }

    @Test
    fun zip3() {
        val resultLce = LCE.Content("foo")
        val f = fun(lce: LCE<*, *, *>) = lce.zip(
            LCE.Content("bar"),
            LCE.Content("zoo")
        ) { _, _, _ -> "foo" }

        val loadingLce = LCE.Loading(Unit)
        val contentLce = LCE.Content(Unit)
        val errorLce = LCE.Error(Unit)

        assertEquals(resultLce, f(contentLce))
        assertEquals(loadingLce, f(loadingLce))
        assertEquals(errorLce, f(errorLce))
    }

    @Test
    fun zipTriple() {
        val resultLce = LCE.Content(Triple(Unit, "bar", "zoo"))
        val f = fun(lce: LCE<*, *, *>) = lce.zip(LCE.Content("bar"), LCE.Content("zoo"))

        val loadingLce = LCE.Loading(Unit)
        val contentLce = LCE.Content(Unit)
        val errorLce = LCE.Error(Unit)

        assertEquals(resultLce, f(contentLce))
        assertEquals(loadingLce, f(loadingLce))
        assertEquals(errorLce, f(errorLce))
    }

    @Test
    fun zip4() {
        val resultLce = LCE.Content("foo")
        val f = fun(lce: LCE<*, *, *>) = lce.zip(
            LCE.Content("bar"),
            LCE.Content("zoo"),
            LCE.Content("voo"),
        ) { _, _, _ , _ -> "foo" }

        val loadingLce = LCE.Loading(Unit)
        val contentLce = LCE.Content(Unit)
        val errorLce = LCE.Error(Unit)

        assertEquals(resultLce, f(contentLce))
        assertEquals(loadingLce, f(loadingLce))
        assertEquals(errorLce, f(errorLce))
    }

    @Test
    fun zip5() {
        val resultLce = LCE.Content("foo")
        val f = fun(lce: LCE<*, *, *>) = lce.zip(
            LCE.Content("bar"),
            LCE.Content("zoo"),
            LCE.Content("voo"),
            LCE.Content("loo"),
        ) { _, _, _ , _, _ -> "foo" }

        val loadingLce = LCE.Loading(Unit)
        val contentLce = LCE.Content(Unit)
        val errorLce = LCE.Error(Unit)

        assertEquals(resultLce, f(contentLce))
        assertEquals(loadingLce, f(loadingLce))
        assertEquals(errorLce, f(errorLce))
    }
}
