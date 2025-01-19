package io.github.pavelannin.monadic.optional

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class GetTest {
    @Test
    fun getOrNull() {
        val someOptional = Optional.Some(Unit)
        val noneOptional = Optional.None
        assertEquals(Unit, someOptional.getOrNull())
        assertNull(noneOptional.getOrNull())
    }
}
