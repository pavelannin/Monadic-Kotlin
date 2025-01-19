package io.github.pavelannin.monadic.optional

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class MapTest {
    @Test
    fun map() {
        val f = fun(optional: Optional<*>) = optional.map { "foo" }
        val resultOptional = Optional.Some("foo")

        val someOptional = Optional.Some(Unit)
        val noneOptional = Optional.None

        assertEquals(resultOptional, f(someOptional))
        assertEquals(noneOptional, f(noneOptional))
    }

    @Test
    fun fold() {
        val f = fun(optional: Optional<*>) = optional.fold { "foo" }

        val someOptional = Optional.Some(Unit)
        val noneOptional = Optional.None

        assertEquals("foo", f(someOptional))
        assertNull(f(noneOptional))
    }

    @Test
    fun biFold() {
        val f = fun(optional: Optional<*>) = optional.fold(
            someTransform = { "foo" },
            noneTransform = { "bar" },
        )

        val someOptional = Optional.Some(Unit)
        val noneOptional = Optional.None

        assertEquals("foo", f(someOptional))
        assertEquals("bar", f(noneOptional))
    }

    @Test
    fun flatMap() {
        val resultOptional = Optional.Some("foo")
        val f = fun(optional: Optional<*>) = optional.flatMap { resultOptional }

        val someOptional = Optional.Some(Unit)
        val noneOptional = Optional.None

        assertEquals(resultOptional, f(someOptional))
        assertEquals(noneOptional, f(noneOptional))
    }

    @Test
    fun flatten() {
        val f = fun(optional: Optional<Optional<*>>) = optional.flatten()

        val someOptional = Optional.Some(Optional.Some(Unit))
        val noneOptional = Optional.Some(Optional.None)
        val noneOptional2 = Optional.None

        assertEquals(Optional.Some(Unit), f(someOptional))
        assertEquals(Optional.None, f(noneOptional))
        assertEquals(Optional.None, f(noneOptional2))
    }
}
