package io.github.pavelannin.monadic.optional.serialization

import io.github.pavelannin.monadic.optional.Optional
import io.github.pavelannin.monadic.optional.getOrNull
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@OptIn(ExperimentalSerializationApi::class)
public class OptionalSerializer<Value>(
    private val valueSerializer: KSerializer<Value>,
): KSerializer<Optional<Value>> {
    override val descriptor: SerialDescriptor = valueSerializer.descriptor

    override fun serialize(encoder: Encoder, value: Optional<Value>) {
        encoder.encodeNullableSerializableValue(valueSerializer, value.getOrNull())
    }

    override fun deserialize(decoder: Decoder): Optional<Value> {
        return Optional.fromNullable(decoder.decodeNullableSerializableValue(valueSerializer))
    }
}
