package io.github.pavelannin.monadic.optional

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

class OnTest {
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
}
