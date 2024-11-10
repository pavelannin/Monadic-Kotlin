package io.github.pavelannin.monadic.optional.serialization

import io.github.pavelannin.monadic.optional.Optional
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.nullable
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

public typealias OptionalCatching<T> = @Serializable(OptionalCatchingSerializer::class) Optional<T>

@OptIn(ExperimentalSerializationApi::class)
public class OptionalCatchingSerializer<Value>(
    private val valueSerializer: KSerializer<Value>,
): KSerializer<Optional<Value>> {
    override val descriptor: SerialDescriptor = valueSerializer.descriptor.nullable

    override fun serialize(encoder: Encoder, value: Optional<Value>) {
        encoder.encodeNullableSerializableValue(valueSerializer, value.getOrNull())
    }

    override fun deserialize(decoder: Decoder): Optional<Value> {
        return try {
            Optional.fromNullable(decoder.decodeNullableSerializableValue(valueSerializer))
        } catch (ignore: Throwable) {
            Optional.None
        }
    }
}
