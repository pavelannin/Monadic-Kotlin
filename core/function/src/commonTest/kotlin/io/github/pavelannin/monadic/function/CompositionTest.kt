package io.github.pavelannin.monadic.function

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class CompositionTest {

    @Test
    fun compose() {
        fun function1(n: Int): Int = n + 1
        fun function2(n: Int): String = n.toString()
        val composition = ::function1 compose ::function2
        assertEquals("2", composition(1))
    }

    @Test
    fun compose2() = runTest {
        suspend fun function1(n: Int): Int = n + 1
        fun function2(n: Int): String = n.toString()
        val composition = ::function1 compose ::function2
        assertEquals("2",  composition(1))
    }
    @Test
    fun compose3() = runTest {
        fun function1(n: Int): Int = n + 1
        suspend fun function2(n: Int): String = n.toString()
        val composition = ::function1 compose ::function2
        assertEquals("2", composition(1))
    }

    @Test
    fun compose4() = runTest {
        suspend fun function1(n: Int): Int = n + 1
        suspend fun function2(n: Int): String = n.toString()
        val composition = ::function1 compose ::function2
        assertEquals("2", composition(1))
    }
}
