package io.github.pavelannin.monadic.optional.serialization

import io.github.pavelannin.monadic.optional.Optional
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.nullable
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

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
