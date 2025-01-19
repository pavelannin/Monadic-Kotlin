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
}
