package io.github.pavelannin.monadic.optional

import io.github.pavelannin.monadic.either.Either
import kotlin.test.Test
import kotlin.test.assertEquals

class EitherExtensionsTest {

    @Test
    fun convertCompanion() {
        val f = fun(optional: Optional<*>) = Either(optional) { "foo" }

        val someOptional = Optional.Some(Unit)
        val noneOptional = Optional.None

        assertEquals(Either.Right(Unit), f(someOptional))
        assertEquals(Either.Left("foo"), f(noneOptional))
    }

    @Test
    fun convertExtension() {
        val f = fun(optional: Optional<*>) = optional.toEither { "foo" }

        val someOptional = Optional.Some(Unit)
        val noneOptional = Optional.None

        assertEquals(Either.Right(Unit), f(someOptional))
        assertEquals(Either.Left("foo"), f(noneOptional))
    }
}
