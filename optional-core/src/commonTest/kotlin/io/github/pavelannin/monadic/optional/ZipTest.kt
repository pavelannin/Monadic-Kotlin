package io.github.pavelannin.monadic.optional

import kotlin.test.Test
import kotlin.test.assertEquals

class ZipTest {
    @Test
    fun zip() {
        val f = fun(optional: Optional<*>) = optional.zip(Optional.Some("bar"))  { _, _ -> "foo" }

        val someOptional = Optional.Some(Unit)
        val noneOptional = Optional.None

        assertEquals(Optional.Some("foo"), f(someOptional))
        assertEquals(noneOptional, f(noneOptional))
    }

    @Test
    fun zipPair() {
        val f = fun(optional: Optional<*>) = optional.zip(Optional.Some("bar"))

        val someOptional = Optional.Some(Unit)
        val noneOptional = Optional.None

        assertEquals(Optional.Some(Unit to "bar"), f(someOptional))
        assertEquals(noneOptional, f(noneOptional))
    }

    @Test
    fun zip3() {
        val f = fun(optional: Optional<*>) = optional.zip(
            Optional.Some(Unit),
            Optional.Some(Unit),
        ) { _, _, _ -> "foo" }

        val someOptional = Optional.Some(Unit)
        val noneOptional = Optional.None

        assertEquals(Optional.Some("foo"), f(someOptional))
        assertEquals(noneOptional, f(noneOptional))
    }

    @Test
    fun zipTriple() {
        val f = fun(optional: Optional<*>) = optional.zip(
            Optional.Some("bar"),
            Optional.Some("zoo"),
        )

        val someOptional = Optional.Some(Unit)
        val noneOptional = Optional.None

        assertEquals(Optional.Some(Triple(Unit, "bar", "zoo")), f(someOptional))
        assertEquals(noneOptional, f(noneOptional))
    }

    @Test
    fun zip4() {
        val f = fun(optional: Optional<*>) = optional.zip(
            Optional.Some(Unit),
            Optional.Some(Unit),
            Optional.Some(Unit),
        ) { _, _, _, _ -> "foo" }

        val someOptional = Optional.Some(Unit)
        val noneOptional = Optional.None

        assertEquals(Optional.Some("foo"), f(someOptional))
        assertEquals(noneOptional, f(noneOptional))
    }

    @Test
    fun zip5() {
        val f = fun(optional: Optional<*>) = optional.zip(
            Optional.Some(Unit),
            Optional.Some(Unit),
            Optional.Some(Unit),
            Optional.Some(Unit),
        ) { _, _, _, _, _ -> "foo" }

        val someOptional = Optional.Some(Unit)
        val noneOptional = Optional.None

        assertEquals(Optional.Some("foo"), f(someOptional))
        assertEquals(noneOptional, f(noneOptional))
    }
}
