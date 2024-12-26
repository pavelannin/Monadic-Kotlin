package io.github.pavelannin.monadic.checkable

import kotlin.test.Test
import kotlin.test.assertEquals

class ConverterTest {
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
}
