package io.github.pavelannin.monadic.optional

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.test.fail

class OptionalTest {

    @Test
    fun isSome() {
        val someOptional = Optional.Some(Unit)
        val noneOptional = Optional.None
        assertTrue(someOptional.isSome())
        assertFalse(noneOptional.isSome())
    }

    @Test
    fun isNone() {
        val someOptional = Optional.Some(Unit)
        val noneOptional = Optional.None
        assertTrue(noneOptional.isNone())
        assertFalse(someOptional.isNone())
    }

    @Test
    fun getOrNull() {
        val someOptional = Optional.Some(Unit)
        val noneOptional = Optional.None
        assertEquals(Unit, someOptional.getOrNull())
        assertNull(noneOptional.getOrNull())
    }

    @Test
    fun onSome() {
        val someOptional = Optional.Some(Unit)
        val noneOptional = Optional.None
        someOptional.onSome { value -> assertEquals(Unit, value) }
        noneOptional.onSome { fail() }
    }

    @Test
    fun onNone() {
        val someOptional = Optional.Some(Unit)
        val noneOptional = Optional.None
        noneOptional.onNone { assertTrue(true) }
        someOptional.onNone { fail() }
    }

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
    fun fromNullable() {
        assertEquals(Optional.Some(Unit), Optional.fromNullable(Unit))
        assertEquals(Optional.None, Optional.fromNullable(null))
    }

    @Test
    fun invoke() {
        assertEquals(Optional.Some(Unit), Optional(Unit))
        assertEquals(Optional.Some(null), Optional(null))
    }

    @Test
    fun catch() {
        assertEquals(Optional.Some(Unit), Optional.catch { Unit })
        assertEquals(Optional.None, Optional.catch { error("any") })
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
    fun zip2() {
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
    fun zip3() {
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
    fun zip4() {
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
