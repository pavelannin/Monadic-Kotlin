package io.github.pavelannin.monadic.optional

import io.github.pavelannin.monadic.optional.serialization.OptionalNullableSerializer
import io.github.pavelannin.monadic.optional.serialization.OptionalSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.nullable
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class OptionalNullableSerializerTest {
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    @Test
    fun serializableSome() {
        val serializer = OptionalNullableSerializer(String.serializer())

        val someOptional = Optional.Some("foo")
        val jsonString = json.encodeToString(serializer, someOptional)
        val jsonOptional = json.decodeFromString(serializer, jsonString)
        assertEquals(someOptional, jsonOptional)
    }

    @Test
    fun serializableSomeNullable() {
        val serializer = OptionalNullableSerializer(String.serializer().nullable)

        val someOptional = Optional.Some<String?>(null)
        val jsonString = json.encodeToString(serializer, someOptional)
        val jsonOptional = json.decodeFromString(serializer, jsonString)
        assertEquals(someOptional, jsonOptional)
    }

    @Test
    fun serializableNone() {
        val serializer = OptionalNullableSerializer(String.serializer())

        val noneOptional = Optional.None
        val jsonString = json.encodeToString(serializer, noneOptional)
        val jsonOptional = json.decodeFromString(serializer, jsonString)
        assertEquals(noneOptional, jsonOptional)
    }
}
