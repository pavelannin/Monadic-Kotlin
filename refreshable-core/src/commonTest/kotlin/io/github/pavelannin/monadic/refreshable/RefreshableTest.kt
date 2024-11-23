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

    @Test
    fun map() {
        val f = fun(refreshable: Refreshable<*>) = refreshable.map { _, _ -> "foo" }

        val refreshing = Refreshable.Refreshing(Unit)
        val refreshed = Refreshable.Refreshed(Unit)

        assertEquals(Refreshable.Refreshing("foo"), f(refreshing))
        assertEquals(Refreshable.Refreshed("foo"), f(refreshed))
    }

    @Test
    fun bimap() {
        val f = fun(refreshable: Refreshable<*>) = refreshable.map(
            refreshingTransform = { "foo" },
            refreshedTransform = { "bar" },
        )

        val refreshing = Refreshable.Refreshing(Unit)
        val refreshed = Refreshable.Refreshed(Unit)

        assertEquals(Refreshable.Refreshing("foo"), f(refreshing))
        assertEquals(Refreshable.Refreshed("bar"), f(refreshed))
    }

    @Test
    fun fold() {
        val f = fun(refreshable: Refreshable<*>) = refreshable.fold { _, _ -> "foo" }

        val refreshing = Refreshable.Refreshing(Unit)
        val refreshed = Refreshable.Refreshed(Unit)

        assertEquals("foo", f(refreshing))
        assertEquals("foo", f(refreshed))
    }

    @Test
    fun bifold() {
        val f = fun(refreshable: Refreshable<*>) = refreshable.fold(
            refreshingTransform = { "foo" },
            refreshedTransform = { "bar" },
        )

        val refreshing = Refreshable.Refreshing(Unit)
        val refreshed = Refreshable.Refreshed(Unit)

        assertEquals("foo", f(refreshing))
        assertEquals("bar", f(refreshed))
    }

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

    @Test
    fun invoke() {
        assertEquals(Refreshable.Refreshing(Unit), Refreshable(true, Unit))
        assertEquals(Refreshable.Refreshed(Unit), Refreshable(false, Unit))
    }

    @Test
    fun flatMap() {
        val f = fun(refreshable: Refreshable<*>) = refreshable.flatMap() { _, _ -> Refreshable.Refreshing("foo") }

        val refreshing = Refreshable.Refreshing(Unit)
        val refreshed = Refreshable.Refreshed(Unit)

        assertEquals(Refreshable.Refreshing("foo"), f(refreshing))
        assertEquals(Refreshable.Refreshing("foo"), f(refreshed))
    }

    @Test
    fun biflatMap() {
        val f = fun(refreshable: Refreshable<*>) = refreshable.flatMap(
            refreshingTransform = { Refreshable.Refreshing("foo") },
            refreshedTransform = { Refreshable.Refreshing("bar") },
        )

        val refreshing = Refreshable.Refreshing(Unit)
        val refreshed = Refreshable.Refreshed(Unit)

        assertEquals(Refreshable.Refreshing("foo"), f(refreshing))
        assertEquals(Refreshable.Refreshing("bar"), f(refreshed))
    }

    @Test
    fun flatten() {
        val f = fun(refreshable: Refreshable<Refreshable<*>>) = refreshable.flatten()

        val refreshing = Refreshable.Refreshing(Refreshable.Refreshing(Unit))
        val refreshed = Refreshable.Refreshed(Refreshable.Refreshing(Unit))

        assertEquals(Refreshable.Refreshing(Unit), f(refreshing))
        assertEquals(Refreshable.Refreshing(Unit), f(refreshed))
    }
}
