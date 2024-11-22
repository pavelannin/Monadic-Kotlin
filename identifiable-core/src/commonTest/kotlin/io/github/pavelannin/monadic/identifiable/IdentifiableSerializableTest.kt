package io.github.pavelannin.monadic.identifiable

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class IdentifiableSerializableTest {
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    @Test
    fun serializableSome() {
        val identifiable = Identifiable(id = 1, value = "String")

        val jsonString = json.encodeToString(identifiable)
        val jsonIdentifiable= json.decodeFromString<Identifiable<Int, String>>(jsonString)
        assertEquals(identifiable, jsonIdentifiable)
    }
}
