package io.github.pavelannin.monadic.refreshable

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class OnTest {
    @Test
    fun onChecked() {
        val refreshing = Refreshable.Refreshing(Unit)
        val refreshed = Refreshable.Refreshed(Unit)
        refreshing.onRefreshing { value -> assertEquals(Unit, value) }
        refreshed.onRefreshing { fail() }
    }

    @Test
    fun onUnchecked() {
        val refreshing = Refreshable.Refreshing(Unit)
        val refreshed = Refreshable.Refreshed(Unit)
        refreshed.onRefreshed { value -> assertEquals(Unit, value) }
        refreshing.onRefreshed { fail() }
    }
}
