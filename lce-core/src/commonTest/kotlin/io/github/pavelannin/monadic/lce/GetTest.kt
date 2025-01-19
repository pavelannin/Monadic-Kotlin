package io.github.pavelannin.monadic.lce

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class GetTest {
    @Test
    fun loadingOrNull() {
        val loadingLce = LCE.Loading(Unit)
        val contentLce = LCE.Content(Unit)
        val errorLce = LCE.Error(Unit)
        assertEquals(Unit, loadingLce.loadingOrNull())
        assertNull(contentLce.loadingOrNull())
        assertNull(errorLce.loadingOrNull())
    }

    @Test
    fun contentOrNull() {
        val loadingLce = LCE.Loading(Unit)
        val contentLce = LCE.Content(Unit)
        val errorLce = LCE.Error(Unit)
        assertEquals(Unit, contentLce.contentOrNull())
        assertNull(loadingLce.contentOrNull())
        assertNull(errorLce.contentOrNull())
    }

    @Test
    fun errorOrNull() {
        val loadingLce = LCE.Loading(Unit)
        val contentLce = LCE.Content(Unit)
        val errorLce = LCE.Error(Unit)
        assertEquals(Unit, errorLce.errorOrNull())
        assertNull(loadingLce.errorOrNull())
        assertNull(contentLce.errorOrNull())
    }
}
