package io.github.pavelannin.monadic.optional

import io.github.pavelannin.monadic.optional.serialization.OptionalSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.nullable
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

private typealias SerializableOptional<T> = @Serializable(OptionalSerializer::class) Optional<T>

class OptionalSerializerTest {
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    @Test
    fun serializableSome() {
        val serializer = OptionalSerializer(String.serializer())

        val someOptional = Optional.Some("foo")
        val jsonString = json.encodeToString(serializer, someOptional)
        val jsonOptional = json.decodeFromString(serializer, jsonString)
        assertEquals(someOptional, jsonOptional)
    }

    @Test
    fun serializableSomeNullable() {
        val serializer = OptionalSerializer(String.serializer().nullable)

        val someOptional = Optional.Some<String?>(null)
        val jsonString = json.encodeToString(serializer, someOptional)
        val jsonOptional = json.decodeFromString(serializer, jsonString)
        assertEquals(Optional.None, jsonOptional)
    }

    @Test
    fun serializableNone() {
        val serializer = OptionalSerializer(String.serializer())

        val noneOptional = Optional.None
        val jsonString = json.encodeToString(serializer, noneOptional)
        val jsonOptional = json.decodeFromString(serializer, jsonString)
        assertEquals(noneOptional, jsonOptional)
    }

    @Test
    fun jsonSome() {
        @Serializable data class ObjectModel(val field: SerializableOptional<String>)

        val objectModel = ObjectModel(Optional.Some("foo"))
        val jsonString = json.encodeToString(objectModel)
        assertEquals("""{"field":"foo"}""", jsonString)

        val jsonObjectModel = json.decodeFromString<ObjectModel>(jsonString)
        assertEquals(objectModel, jsonObjectModel)
    }

    @Test
    fun jsonSomeNullable() {
        @Serializable data class ObjectModel(val field: SerializableOptional<String?>)

        val objectModel = ObjectModel(Optional.Some(null))
        val jsonString = json.encodeToString(objectModel)
        assertEquals("""{"field":null}""", jsonString)

        val jsonObjectModel = json.decodeFromString<ObjectModel>(jsonString)
        assertEquals(ObjectModel(Optional.None), jsonObjectModel)
    }

    @Test
    fun jsonNone() {
        @Serializable data class ObjectModel(val field: SerializableOptional<String>)

        val objectModel = ObjectModel(Optional.None)
        val jsonString = json.encodeToString(objectModel)
        assertEquals("""{"field":null}""", jsonString)

        val jsonObjectModel = json.decodeFromString<ObjectModel>(jsonString)
        assertEquals(objectModel, jsonObjectModel)
    }

    @Test
    fun jsonList() {
        @Serializable data class ObjectModel(val field: String)
        val serializer = ListSerializer(OptionalSerializer(ObjectModel.serializer()))

        val list = listOf(Optional.Some(ObjectModel("foo")), Optional.None)
        val jsonString = json.encodeToString(serializer, list)
        assertEquals("""[{"field":"foo"},null]""", jsonString)

        val jsonList = json.decodeFromString(serializer, jsonString)
        assertEquals(list, jsonList)
    }
}
