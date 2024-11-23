package io.github.pavelannin.monadic.refreshable

import io.github.pavelannin.monadic.refreshable.serialization.RefreshableSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class RefreshableSerializerTest {
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    @Test
    fun serializableRefreshing() {
        val serializer = RefreshableSerializer(String.serializer())
        val refreshing = Refreshable.Refreshing("foo")
        val jsonString = json.encodeToString(serializer, refreshing)
        val jsonOptional = json.decodeFromString(serializer, jsonString)
        assertEquals(refreshing, jsonOptional)
    }

    @Test
    fun serializableRefreshed() {
        val serializer = RefreshableSerializer(String.serializer())
        val checked = Refreshable.Refreshed("foo")
        val jsonString = json.encodeToString(serializer, checked)
        val jsonOptional = json.decodeFromString(serializer, jsonString)
        assertEquals(checked, jsonOptional)
    }
}
