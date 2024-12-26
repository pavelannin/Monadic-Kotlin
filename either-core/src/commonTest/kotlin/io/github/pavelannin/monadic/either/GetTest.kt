package io.github.pavelannin.monadic.either

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class GetTest {
    @Test
    fun leftOrNull() {
        val left = Either.Left()
        val right = Either.Right()
        assertNotNull(left.leftOrNull())
        assertNull(right.leftOrNull())
    }

    @Test
    fun rightOrNull() {
        val left = Either.Left()
        val right = Either.Right()
        assertNull(left.rightOrNull())
        assertNotNull(right.rightOrNull())
    }

    @Test
    fun leftOr() {
        val left = Either.Left()
        val right = Either.Right()
        assertNotNull(left.leftOrDefault(Either.Left()))
        assertNotNull(right.leftOrDefault(Either.Left()))
    }

    @Test
    fun rightOr() {
        val left = Either.Left(Unit)
        val right = Either.Right(Unit)
        assertNotNull(left.rightOrDefault(Either.Right()))
        assertNotNull(right.rightOrDefault(Either.Right()))
    }

    @Test
    fun leftOrElse() {
        val left = Either.Left()
        val right = Either.Right()
        assertNotNull(left.leftOrElse { Either.Left() })
        assertNotNull(right.leftOrElse { Either.Left() })
    }

    @Test
    fun rightOrElse() {
        val left = Either.Left(Unit)
        val right = Either.Right(Unit)
        assertNotNull(left.rightOrElse { Either.Right() })
        assertNotNull(right.rightOrElse { Either.Right() })
    }

    @Test
    fun take() {
        val f = fun(r: Either<*, *>) = r.take()

        val right = Either.Right()
        val left = Either.Left()

        assertEquals(Unit, f(right))
        assertEquals(Unit, f(left))
    }
}
