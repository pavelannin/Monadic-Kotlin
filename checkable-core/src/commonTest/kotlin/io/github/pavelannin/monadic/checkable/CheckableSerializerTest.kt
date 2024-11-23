package io.github.pavelannin.monadic.checkable

import io.github.pavelannin.monadic.checkable.serialization.CheckableSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.nullable
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class CheckableSerializerTest {
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    @Test
    fun serializableChecked() {
        val serializer = CheckableSerializer(String.serializer())
        val checked = Checkable.Checked("foo")
        val jsonString = json.encodeToString(serializer, checked)
        val jsonOptional = json.decodeFromString(serializer, jsonString)
        assertEquals(checked, jsonOptional)
    }

    @Test
    fun serializableUnchecked() {
        val serializer = CheckableSerializer(String.serializer())
        val checked = Checkable.Unchecked("foo")
        val jsonString = json.encodeToString(serializer, checked)
        val jsonOptional = json.decodeFromString(serializer, jsonString)
        assertEquals(checked, jsonOptional)
    }
}
