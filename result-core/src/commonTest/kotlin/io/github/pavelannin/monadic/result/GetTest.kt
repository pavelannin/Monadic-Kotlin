package io.github.pavelannin.monadic.result

import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class GetTest {
    @Test
    fun okOrNull() {
        val ok = Result.Ok()
        val error = Result.Error()
        assertNotNull(ok.okOrNull())
        assertNull(error.okOrNull())
    }

    @Test
    fun errorOrNull() {
        val ok = Result.Ok()
        val error = Result.Error()
        assertNull(ok.errorOrNull())
        assertNotNull(error.errorOrNull())
    }

    @Test
    fun okOr() {
        val ok = Result.Ok()
        val error = Result.Error()
        assertNotNull(ok.okOr(Result.Ok()))
        assertNotNull(error.okOr(Result.Ok()))
    }

    @Test
    fun errorOr() {
        val ok = Result.Ok(Unit)
        val error = Result.Error(Unit)
        assertNotNull(ok.errorOr(Result.Error()))
        assertNotNull(error.errorOr(Result.Error()))
    }

    @Test
    fun okOrElse() {
        val ok = Result.Ok()
        val error = Result.Error()
        assertNotNull(ok.okOrElse { Result.Ok() })
        assertNotNull(error.okOrElse { Result.Ok() })
    }

    @Test
    fun errorOrElse() {
        val ok = Result.Ok(Unit)
        val error = Result.Error(Unit)
        assertNotNull(ok.errorOrElse { Result.Error() })
        assertNotNull(error.errorOrElse { Result.Error() })
    }
}
