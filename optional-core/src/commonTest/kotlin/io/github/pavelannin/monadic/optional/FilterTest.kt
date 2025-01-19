package io.github.pavelannin.monadic.optional

import kotlin.test.Test
import kotlin.test.assertEquals

class FilterTest {
    @Test
    fun filter() {
        val f = fun(optional: Optional<*>, predicate: Boolean) = optional.filter { predicate }

        val someOptional = Optional.Some(Unit)
        val noneOptional = Optional.None

        assertEquals(someOptional, f(someOptional, true))
        assertEquals(noneOptional, f(someOptional, false))
        assertEquals(noneOptional, f(noneOptional, true))
        assertEquals(noneOptional, f(noneOptional, false))
    }

    @Test
    fun filterNot() {
        val f = fun(optional: Optional<*>, predicate: Boolean) = optional.filterNot { predicate }

        val someOptional = Optional.Some(Unit)
        val noneOptional = Optional.None

        assertEquals(noneOptional, f(someOptional, true))
        assertEquals(someOptional, f(someOptional, false))
        assertEquals(noneOptional, f(noneOptional, true))
        assertEquals(noneOptional, f(noneOptional, false))
    }

    @Test
    fun filterIsInstance() {
        val f = fun(optional: Optional<*>) = optional.filterIsInstance<String>()

        val someOptional = Optional.Some("foo")
        val someOptional2 = Optional.Some(Unit)
        val noneOptional = Optional.None

        assertEquals(Optional.Some("foo"), f(someOptional))
        assertEquals(Optional.None, f(someOptional2))
        assertEquals(Optional.None, f(noneOptional))
    }
}
