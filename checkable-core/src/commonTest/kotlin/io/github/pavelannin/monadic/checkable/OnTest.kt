package io.github.pavelannin.monadic.checkable

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class OnTest {
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
}
