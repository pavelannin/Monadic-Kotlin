package io.github.pavelannin.monadic.lce

import io.github.pavelannin.monadic.either.Either
import kotlin.test.Test
import kotlin.test.assertEquals

class EitherExtensionsTest {
    @Test
    fun convertCompanion() {
        val leftEither = Either.Left(Unit)
        val rightEither = Either.Right(Unit)
        assertEquals(LCE.Error(Unit), LCE(leftEither))
        assertEquals(LCE.Content(Unit), LCE(rightEither))
    }

    @Test
    fun convertExtension() {
        val leftEither = Either.Left(Unit)
        val rightEither = Either.Right(Unit)
        assertEquals(LCE.Error(Unit), leftEither.toLCE())
        assertEquals(LCE.Content(Unit), rightEither.toLCE())
    }
}
