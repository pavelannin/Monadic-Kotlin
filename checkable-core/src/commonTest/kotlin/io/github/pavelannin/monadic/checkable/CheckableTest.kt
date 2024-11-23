package io.github.pavelannin.monadic.checkable

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.test.fail

class CheckableTest {
    @Test
    fun isChecked() {
        val checked = Checkable.Checked(Unit)
        val unchecked = Checkable.Unchecked(Unit)
        assertTrue(checked.isChecked())
        assertFalse(unchecked.isChecked())
    }

    @Test
    fun onChecked() {
        val checked = Checkable.Checked(Unit)
        val unchecked = Checkable.Unchecked(Unit)
        checked.onChecked { value -> assertEquals(Unit, value) }
        unchecked.onChecked { fail() }
    }

    @Test
    fun onUnchecked() {
        val checked = Checkable.Checked(Unit)
        val unchecked = Checkable.Unchecked(Unit)
        unchecked.onUnchecked { value -> assertEquals(Unit, value) }
        checked.onUnchecked { fail() }
    }

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
    fun toChecked() {
        val f = fun(checkable: Checkable<*>) = checkable.toChecked()

        val checked = Checkable.Checked(Unit)
        val unchecked = Checkable.Unchecked(Unit)

        assertEquals(Checkable.Checked(Unit), f(checked))
        assertEquals(Checkable.Checked(Unit), f(unchecked))
    }

    @Test
    fun toUnchecked() {
        val f = fun(checkable: Checkable<*>) = checkable.toUnchecked()

        val checked = Checkable.Checked(Unit)
        val unchecked = Checkable.Unchecked(Unit)

        assertEquals(Checkable.Unchecked(Unit), f(checked))
        assertEquals(Checkable.Unchecked(Unit), f(unchecked))
    }

    @Test
    fun invoke() {
        assertEquals(Checkable.Checked(Unit), Checkable(true, Unit))
        assertEquals(Checkable.Unchecked(Unit), Checkable(false, Unit))
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
