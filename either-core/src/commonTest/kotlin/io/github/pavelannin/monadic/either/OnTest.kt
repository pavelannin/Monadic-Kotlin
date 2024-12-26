package io.github.pavelannin.monadic.either

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class OnTest {
    @Test
    fun onRight() {
        val right = Either.Right()
        val left = Either.Left()
        right.onRight { value -> assertEquals(Unit, value) }
        left.onRight { fail() }
    }

    @Test
    fun onLeft() {
        val right = Either.Right()
        val left = Either.Left()
        right.onLeft { fail() }
        left.onLeft { value -> assertEquals(Unit, value) }
    }
}
