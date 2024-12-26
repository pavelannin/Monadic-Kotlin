package io.github.pavelannin.monadic.either

import kotlin.test.Test
import kotlin.test.assertEquals

class MapTest {
    @Test
    fun map() {
        val f = fun(either: Either<*, *>) = either.map { "foo" }
        val either = Either.Right("foo")

        val right = Either.Right()
        val left = Either.Left()

        assertEquals(either, f(right))
        assertEquals(left, f(left))
    }

    @Test
    fun mapLeft() {
        val f = fun(either: Either<*, *>) = either.mapLeft { "foo" }
        val either = Either.Left("foo")

        val right = Either.Right()
        val left = Either.Left()

        assertEquals(right, f(right))
        assertEquals(either, f(left))
    }

    @Test
    fun bimap() {
        val f = fun(either: Either<*, *>) = either.bimap(
            rightTransform = { "foo" },
            leftTransform = { "bar" },
        )
        val rightEither = Either.Right("foo")
        val leftEither = Either.Left("bar")

        val right = Either.Right()
        val left = Either.Left()

        assertEquals(rightEither, f(right))
        assertEquals(leftEither, f(left))
    }

    @Test
    fun fold() {
        val f = fun(either: Either<*, *>) = either.fold(
            rightTransform = { "foo" },
            leftTransform = { "bar" },
        )

        val right = Either.Right()
        val left = Either.Left()

        assertEquals("foo", f(right))
        assertEquals("bar", f(left))
    }

    @Test
    fun foldRight() {
        val f = fun(either: Either<*, *>) = either.foldRight("bar") { "foo" }

        val right = Either.Right()
        val left = Either.Left()

        assertEquals("foo", f(right))
        assertEquals("bar", f(left))
    }

    @Test
    fun foldLeft() {
        val f = fun(either: Either<*, *>) = either.foldLeft("foo") { "bar" }

        val right = Either.Right()
        val left = Either.Left()

        assertEquals("foo", f(right))
        assertEquals("bar", f(left))
    }

    @Test
    fun flatMap() {
        val either = Either.Right("foo")
        val f = fun(r: Either<*, *>) = r.flatMap { either }

        val right = Either.Right()
        val left = Either.Left()

        assertEquals(either, f(right))
        assertEquals(left, f(left))
    }

    @Test
    fun flatMapLeft() {
        val either = Either.Right("bar")
        val f = fun(r: Either<*, *>) = r.flatMapLeft { either }

        val right = Either.Right()
        val left = Either.Left()

        assertEquals(right, f(right))
        assertEquals(either, f(left))
    }

    @Test
    fun flatten() {
        val f = fun(r: Either<*, Either<*, *>>) = r.flatten()

        val right = Either.Right(Either.Right())
        val left = Either.Left(Either.Left())

        assertEquals(Either.Right(), f(right))
        assertEquals(left, f(left))
    }
}
