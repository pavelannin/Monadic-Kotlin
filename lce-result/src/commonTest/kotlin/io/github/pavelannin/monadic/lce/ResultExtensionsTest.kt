package io.github.pavelannin.monadic.lce

import io.github.pavelannin.monadic.result.Result
import kotlin.test.Test
import kotlin.test.assertEquals

class ResultExtensionsTest {
    @Test
    fun convertCompanion() {
        val ok = Result.Ok()
        val error = Result.Error()
        assertEquals(LCE.Content(), LCE(ok))
        assertEquals(LCE.Error(), LCE(error))
    }

    @Test
    fun convertExtension() {
        val ok = Result.Ok()
        val error = Result.Error()
        assertEquals(LCE.Content(), ok.toLCE())
        assertEquals(LCE.Error(), error.toLCE())
    }
}
