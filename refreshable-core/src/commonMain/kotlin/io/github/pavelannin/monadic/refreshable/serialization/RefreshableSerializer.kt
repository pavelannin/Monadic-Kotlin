package io.github.pavelannin.monadic.refreshable.serialization

import io.github.pavelannin.monadic.refreshable.Refreshable
import io.github.pavelannin.monadic.refreshable.isRefreshing
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
public class RefreshableSerializer<Value>(
    private val valueSerializer: KSerializer<Value>,
): KSerializer<Refreshable<Value>> {
    // FIXME: https://github.com/Kotlin/kotlinx.serialization/issues/944
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Refreshable") {
        element<Boolean>("isRefreshing")
        element("value", valueSerializer.descriptor, isOptional = true)
    }

    override fun serialize(encoder: Encoder, value: Refreshable<Value>) {
        encoder.encodeStructure(descriptor) {
            encodeBooleanElement(
                descriptor = descriptor,
                index = 0,
                value = value.isRefreshing()
            )
            encodeNullableSerializableElement(
                descriptor = descriptor,
                index = 1,
                serializer = valueSerializer,
                value = value.refreshable,
            )
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun deserialize(decoder: Decoder): Refreshable<Value> {
        return decoder.decodeStructure(descriptor) {
            if (decodeSequentially()) {
                Refreshable(
                    refreshing = decodeBooleanElement(descriptor, 0),
                    value = decodeNullableSerializableElement(descriptor, 1, valueSerializer) as Value,
                )
            } else {
                var refreshing: Boolean? = null
                var value: Value? = null
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> refreshing = decodeBooleanElement(descriptor, 0)
                        1 -> value = decodeNullableSerializableElement(descriptor, 1, valueSerializer)
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                Refreshable(
                    refreshing = checkNotNull(refreshing),
                    value = value as Value,
                )
            }
        }
    }
}
