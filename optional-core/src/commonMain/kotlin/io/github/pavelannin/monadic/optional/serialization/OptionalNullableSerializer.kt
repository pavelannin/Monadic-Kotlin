package io.github.pavelannin.monadic.optional.serialization

import io.github.pavelannin.monadic.optional.Optional
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure

@OptIn(ExperimentalSerializationApi::class)
public class OptionalNullableSerializer<Value>(
    private val valueSerializer: KSerializer<Value>,
): KSerializer<Optional<Value>> {
    // FIXME: https://github.com/Kotlin/kotlinx.serialization/issues/944
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Optional") {
        element<String>("type")
        element("value", valueSerializer.descriptor, isOptional = true)
    }

    override fun serialize(encoder: Encoder, value: Optional<Value>) {
        encoder.encodeStructure(descriptor) {
            encodeStringElement(
                descriptor = descriptor,
                index = 0,
                value = when (value) {
                    Optional.None -> noneType
                    is Optional.Some -> someType
                }
            )
            encodeNullableSerializableElement(
                descriptor = descriptor,
                index = 1,
                serializer = valueSerializer,
                value = if (value is Optional.Some) value.some else null,
            )
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun deserialize(decoder: Decoder): Optional<Value> {
        return decoder.decodeStructure(descriptor) {
            if (decodeSequentially()) {
                when (val type = decodeStringElement(descriptor, 0)) {
                    someType -> Optional.Some(decodeNullableSerializableElement(descriptor, 1, valueSerializer) as Value)
                    noneType -> Optional.None
                    else -> error("Unknown type: $type")
                }
            } else {
                var type: String? = null
                var value: Value? = null
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> type = decodeStringElement(descriptor, 0)
                        1 -> value = decodeNullableSerializableElement(descriptor, 1, valueSerializer)
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                when (type) {
                    someType -> Optional.Some(value as Value)
                    noneType -> Optional.None
                    else -> error("Unknown type: $type")
                }
            }
        }
    }

    private companion object {
        private const val someType = "some"
        private const val noneType = "none"
    }
}
