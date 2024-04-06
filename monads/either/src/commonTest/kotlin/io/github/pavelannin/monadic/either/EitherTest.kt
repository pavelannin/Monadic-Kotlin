package io.github.pavelannin.monadic.either

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class EitherTest {
    private val rightValue = 1
    private val leftValue = 100
    private val right: Either<Int, Int> = Either.Right(value = rightValue)
    private val left: Either<Int, Int> = Either.Left(value = leftValue)

    @Test
    fun map() {
        assertEquals(Either.Right(rightValue + 1), right.map { it + 1 })
        assertEquals(left, left.map { it + 1 })
    }

    @Test
    fun mapLeft() {
        assertEquals(right, right.mapLeft { it + 1 })
        assertEquals(Either.Left(leftValue + 1), left.mapLeft { it + 1 })
    }

    @Test
    fun bimap() {
        assertEquals(Either.Right(rightValue + 1), right.bimap(leftTransform = { it + 2 }, rightTransform = { it + 1 }))
        assertEquals(Either.Left(leftValue + 2), left.bimap(leftTransform = { it + 2 }, rightTransform = { it + 1 }))
    }

    @Test
    fun fold() {
        assertEquals(rightValue + 1, right.fold(leftTransform = { it + 2 }, rightTransform = { it + 1 }))
        assertEquals(leftValue + 2, left.fold(leftTransform = { it + 2 }, rightTransform = { it + 1 }))
    }

    @Test
    fun foldLeft() {
        assertEquals(0, right.foldLeft(rightValue = 0) { it + 1 })
        assertEquals(leftValue + 1, left.foldLeft(rightValue = 0) { it + 1 })
    }

    @Test
    fun foldRight() {
        assertEquals(0, left.foldRight(leftValue = 0) { it + 1 })
        assertEquals(rightValue + 1, right.foldRight(leftValue = 0) { it + 1 })
    }

    @Test
    fun flatMap() {
        assertEquals(Either.Right(rightValue + 1), right.flatMap { Either.Right(it + 1) })
        assertEquals(left, left.flatMap { Either.Right(it + 1) })
    }

    @Test
    fun flatMapLeft() {
        assertEquals(right, right.flatMapLeft { Either.Right(it + 1) })
        assertEquals(Either.Left(leftValue + 1), left.flatMapLeft { Either.Left(it + 1) })
    }

    @Test
    fun zip() {
        assertEquals(Either.Right(rightValue + rightValue), right.zip(right) { a, b -> a + b })
        assertEquals(left, right.zip(left) { a, b -> a + b })
        assertEquals(left, left.zip(right) { a, b -> a + b })
        assertEquals(left, left.zip(left) { a, b -> a + b })
    }

    @Test
    fun flatten() {
        val rightCascade = Either.Right(right)
        val leftCascade = Either.Left(leftValue)
        assertEquals(right, rightCascade.flatten())
        assertEquals(left, leftCascade.flatten())
    }

    @Test
    fun swap() {
        assertEquals(Either.Left(value = rightValue), right.swap())
        assertEquals(Either.Right(value = leftValue), left.swap())
    }

    @Test
    fun take() {
        assertEquals(rightValue, right.take())
        assertEquals(leftValue, left.take())
    }

    @Test
    fun leftOrNull() {
        assertEquals(leftValue, left.leftOrNull())
        assertNull(right.leftOrNull())
    }

    @Test
    fun isLeft() {
        assertTrue(left.isLeft)
        assertFalse(right.isLeft)
    }

    @Test
    fun rightOrNull() {
        assertEquals(rightValue, right.rightOrNull())
        assertNull(left.rightOrNull())
    }

    @Test
    fun isRight() {
        assertTrue(right.isRight)
        assertFalse(left.isRight)
    }

    @Test
    fun lift() {
        val func = Either.lift<Int, Int, Int> { it + 1 }
        assertEquals(Either.Right(rightValue + 1), func(right))
        assertEquals(left, func(left))
    }

    @Test
    fun bilift() {
        val func = Either.lift<Int, Int, Int, Int>({ it + 1 }, { it + 2 })
        assertEquals(Either.Left(leftValue + 1), func(left))
        assertEquals(Either.Right(rightValue + 2), func(right))
    }

    @Test
    fun either() {
        val expectedException = RuntimeException()
        assertEquals(right, Either { rightValue })
        assertEquals(Either.Left(value = expectedException), Either<Exception, Int> { throw expectedException })
    }
}
