package io.github.pavelannin.monadic.lce

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.test.fail

class LCETest {
    @Test
    fun isLoading() {
        val loadingLce = LCE.Loading(Unit)
        val contentLce = LCE.Content(Unit)
        val errorLce = LCE.Error(Unit)
        assertTrue(loadingLce.isLoading())
        assertFalse(contentLce.isLoading())
        assertFalse(errorLce.isLoading())
    }

    @Test
    fun isContent() {
        val loadingLce = LCE.Loading(Unit)
        val contentLce = LCE.Content(Unit)
        val errorLce = LCE.Error(Unit)
        assertTrue(contentLce.isContent())
        assertFalse(loadingLce.isContent())
        assertFalse(errorLce.isContent())
    }

    @Test
    fun isError() {
        val loadingLce = LCE.Loading(Unit)
        val contentLce = LCE.Content(Unit)
        val errorLce = LCE.Error(Unit)
        assertTrue(errorLce.isError())
        assertFalse(loadingLce.isError())
        assertFalse(contentLce.isError())
    }

    @Test
    fun isComplete() {
        val loadingLce = LCE.Loading(Unit)
        val contentLce = LCE.Content(Unit)
        val errorLce = LCE.Error(Unit)
        assertTrue(contentLce.isComplete())
        assertTrue(errorLce.isComplete())
        assertFalse(loadingLce.isComplete())
    }
}
