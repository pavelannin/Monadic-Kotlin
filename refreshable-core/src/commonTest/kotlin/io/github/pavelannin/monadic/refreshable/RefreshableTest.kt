package io.github.pavelannin.monadic.refreshable

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.test.fail

class RefreshableTest {
    @Test
    fun isChecked() {
        val refreshing = Refreshable.Refreshing(Unit)
        val refreshed = Refreshable.Refreshed(Unit)
        assertTrue(refreshing.isRefreshing())
        assertFalse(refreshed.isRefreshing())
    }

    @Test
    fun invoke() {
        assertEquals(Refreshable.Refreshing(Unit), Refreshable(true, Unit))
        assertEquals(Refreshable.Refreshed(Unit), Refreshable(false, Unit))
    }
}
