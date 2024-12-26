package io.github.pavelannin.monadic.either

import kotlin.test.Test
import kotlin.test.assertEquals

class ZipTest {
    @Test
    fun zip() {
        val f = fun(either: Either<*, *>) = either.zip(Either.Right())  { _, _ -> "foo" }

        val right = Either.Right()
        val left = Either.Left()

        assertEquals(Either.Right("foo"), f(right))
        assertEquals(left, f(left))
    }

    @Test
    fun zipPair() {
        val f = fun(either: Either<*, *>) = either.zip(Either.Right("bar"))

        val right = Either.Right()
        val left = Either.Left()

        assertEquals(Either.Right(Unit to "bar"), f(right))
        assertEquals(left, f(left))
    }

    @Test
    fun zip3() {
        val f = fun(either: Either<*, *>) = either.zip(
            Either.Right(Unit),
            Either.Right(Unit),
        ) { _, _, _ -> "foo" }

        val right = Either.Right()
        val left = Either.Left()

        assertEquals(Either.Right("foo"), f(right))
        assertEquals(left, f(left))
    }

    @Test
    fun zipTriple() {
        val f = fun(either: Either<*, *>) = either.zip(
            Either.Right("bar"),
            Either.Right("zoo"),
        )

        val right = Either.Right()
        val left = Either.Left()

        assertEquals(Either.Right(Triple(Unit, "bar", "zoo")), f(right))
        assertEquals(left, f(left))
    }

    @Test
    fun zip4() {
        val f = fun(either: Either<*, *>) = either.zip(
            Either.Right(Unit),
            Either.Right(Unit),
            Either.Right(Unit),
        ) { _, _, _, _ -> "foo" }

        val right = Either.Right()
        val left = Either.Left()

        assertEquals(Either.Right("foo"), f(right))
        assertEquals(left, f(left))
    }

    @Test
    fun zip5() {
        val f = fun(either: Either<*, *>) = either.zip(
            Either.Right(Unit),
            Either.Right(Unit),
            Either.Right(Unit),
            Either.Right(Unit),
        ) { _, _, _, _, _ -> "foo" }

        val right = Either.Right()
        val left = Either.Left()

        assertEquals(Either.Right("foo"), f(right))
        assertEquals(left, f(left))
    }
}
