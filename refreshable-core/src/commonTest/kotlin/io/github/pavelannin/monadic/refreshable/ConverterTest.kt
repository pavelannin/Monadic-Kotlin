package io.github.pavelannin.monadic.refreshable

import kotlin.test.Test
import kotlin.test.assertEquals

class ConverterTest {
    @Test
    fun toRefreshing() {
        val f = fun(refreshable: Refreshable<*>) = refreshable.toRefreshing()

        val refreshing = Refreshable.Refreshing(Unit)
        val refreshed = Refreshable.Refreshed(Unit)

        assertEquals(Refreshable.Refreshing(Unit), f(refreshing))
        assertEquals(Refreshable.Refreshing(Unit), f(refreshed))
    }

    @Test
    fun toRefreshed() {
        val f = fun(refreshable: Refreshable<*>) = refreshable.toRefreshed()

        val refreshing = Refreshable.Refreshing(Unit)
        val refreshed = Refreshable.Refreshed(Unit)

        assertEquals(Refreshable.Refreshed(Unit), f(refreshing))
        assertEquals(Refreshable.Refreshed(Unit), f(refreshed))
    }
}
