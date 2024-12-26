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
    fun invoke() {
        assertEquals(Checkable.Checked(Unit), Checkable(true, Unit))
        assertEquals(Checkable.Unchecked(Unit), Checkable(false, Unit))
    }
}
