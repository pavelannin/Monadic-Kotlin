package io.github.pavelannin.monadic.result

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class OnTest {
    @Test
    fun onOk() {
        val ok = Result.Ok()
        val error = Result.Error()
        ok.onOk { value -> assertEquals(Unit, value) }
        error.onOk { fail() }
    }

    @Test
    fun onError() {
        val ok = Result.Ok()
        val error = Result.Error()
        ok.onError { fail() }
        error.onError { value -> assertEquals(Unit, value) }
    }
}
