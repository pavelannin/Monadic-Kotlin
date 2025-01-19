package io.github.pavelannin.monadic.lce

import kotlin.test.Test
import kotlin.test.assertEquals

class ZipTest {
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
