package io.github.pavelannin.monadic.lce

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class OnTest {
    @Test
    fun onLoading() {
        val loadingLce = LCE.Loading(Unit)
        val contentLce = LCE.Content(Unit)
        val errorLce = LCE.Error(Unit)
        loadingLce.onLoading { loading -> assertEquals(Unit, loading) }
        contentLce.onLoading { fail() }
        errorLce.onLoading { fail() }
    }

    @Test
    fun onContent() {
        val loadingLce = LCE.Loading(Unit)
        val contentLce = LCE.Content(Unit)
        val errorLce = LCE.Error(Unit)
        contentLce.onContent { content -> assertEquals(Unit, content) }
        loadingLce.onContent { fail() }
        errorLce.onContent { fail() }
    }

    @Test
    fun onError() {
        val loadingLce = LCE.Loading(Unit)
        val contentLce = LCE.Content(Unit)
        val errorLce = LCE.Error(Unit)
        errorLce.onError { error -> assertEquals(Unit, error) }
        loadingLce.onError { fail() }
        contentLce.onError { fail() }
    }
}
