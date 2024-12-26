package io.github.pavelannin.monadic.checkable

import kotlin.test.Test
import kotlin.test.assertEquals

class MapTest {
    @Test
    fun map() {
        val f = fun(checkable: Checkable<*>) = checkable.map { _, _ -> "foo" }

        val checked = Checkable.Checked(Unit)
        val unchecked = Checkable.Unchecked(Unit)

        assertEquals(Checkable.Checked("foo"), f(checked))
        assertEquals(Checkable.Unchecked("foo"), f(unchecked))
    }

    @Test
    fun bimap() {
        val f = fun(checkable: Checkable<*>) = checkable.map(
            checkedTransform = { "foo" },
            uncheckedTransform = { "bar" },
        )

        val checked = Checkable.Checked(Unit)
        val unchecked = Checkable.Unchecked(Unit)

        assertEquals(Checkable.Checked("foo"), f(checked))
        assertEquals(Checkable.Unchecked("bar"), f(unchecked))
    }

    @Test
    fun fold() {
        val f = fun(checkable: Checkable<*>) = checkable.fold { _, _ -> "foo" }

        val checked = Checkable.Checked(Unit)
        val unchecked = Checkable.Unchecked(Unit)

        assertEquals("foo", f(checked))
        assertEquals("foo", f(unchecked))
    }

    @Test
    fun bifold() {
        val f = fun(checkable: Checkable<*>) = checkable.fold(
            checkedTransform = { "foo" },
            uncheckedTransform = { "bar" },
        )

        val checked = Checkable.Checked(Unit)
        val unchecked = Checkable.Unchecked(Unit)

        assertEquals("foo", f(checked))
        assertEquals("bar", f(unchecked))
    }

    @Test
    fun flatMap() {
        val f = fun(checkable: Checkable<*>) = checkable.flatMap() { _, _ -> Checkable.Checked("foo") }

        val checked = Checkable.Checked(Unit)
        val unchecked = Checkable.Unchecked(Unit)

        assertEquals(Checkable.Checked("foo"), f(checked))
        assertEquals(Checkable.Checked("foo"), f(unchecked))
    }

    @Test
    fun biflatMap() {
        val f = fun(checkable: Checkable<*>) = checkable.flatMap(
            checkedTransform = { Checkable.Checked("foo") },
            uncheckedTransform = { Checkable.Checked("bar") },
        )

        val checked = Checkable.Checked(Unit)
        val unchecked = Checkable.Unchecked(Unit)

        assertEquals(Checkable.Checked("foo"), f(checked))
        assertEquals(Checkable.Checked("bar"), f(unchecked))
    }

    @Test
    fun flatten() {
        val f = fun(checkable: Checkable<Checkable<*>>) = checkable.flatten()

        val checked = Checkable.Checked(Checkable.Checked(Unit))
        val unchecked = Checkable.Unchecked(Checkable.Checked(Unit))

        assertEquals(Checkable.Checked(Unit), f(checked))
        assertEquals(Checkable.Checked(Unit), f(unchecked))
    }
}
