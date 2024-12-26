package io.github.pavelannin.monadic.either

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class EitherTest {
    @Test
    fun isRight() {
        val right = Either.Right()
        val left = Either.Left()
        assertTrue(right.isRight())
        assertFalse(left.isRight())
    }

    @Test
    fun isLeft() {
        val right = Either.Right()
        val left = Either.Left()
        assertFalse(right.isLeft())
        assertTrue(left.isLeft())
    }

    @Test
    fun lift() {
        val f = fun(u: Unit) = "foo"
        val lift = Either.lift<Unit, Unit, String>(f)

        val right = Either.Right()
        val left = Either.Left()

        assertEquals(Either.Right("foo"), lift(right))
        assertEquals(left, lift(left))
    }

    @Test
    fun bilift() {
        val rightFun = fun(u: Unit) = "foo"
        val leftFun = fun(u: Unit) = "bar"
        val lift = Either.lift(leftFun, rightFun)

        val right = Either.Right()
        val left = Either.Left()

        assertEquals(Either.Right("foo"), lift(right))
        assertEquals(Either.Left("bar"), lift(left))
    }

    @Test
    fun catch() {
        val rightFun = fun() = Either.catch { "foo" }
        val exception = RuntimeException()
        val leftFun = fun() = Either.catch { throw exception }

        assertEquals(Either.Right("foo"), rightFun())
        assertEquals(Either.Left(exception), leftFun())
    }
}
