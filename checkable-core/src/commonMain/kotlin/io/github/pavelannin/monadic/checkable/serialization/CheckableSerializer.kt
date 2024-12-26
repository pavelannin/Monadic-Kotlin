package io.github.pavelannin.monadic.checkable.serialization

import io.github.pavelannin.monadic.checkable.Checkable
import io.github.pavelannin.monadic.checkable.isChecked
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
public class CheckableSerializer<Value>(
    private val valueSerializer: KSerializer<Value>,
): KSerializer<Checkable<Value>> {
    // FIXME: https://github.com/Kotlin/kotlinx.serialization/issues/944
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Checkable") {
        element<Boolean>("isChecked")
        element("value", valueSerializer.descriptor, isOptional = true)
    }

    override fun serialize(encoder: Encoder, value: Checkable<Value>) {
        encoder.encodeStructure(descriptor) {
            encodeBooleanElement(
                descriptor = descriptor,
                index = 0,
                value = value.isChecked()
            )
            encodeNullableSerializableElement(
                descriptor = descriptor,
                index = 1,
                serializer = valueSerializer,
                value = value.checkable,
            )
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun deserialize(decoder: Decoder): Checkable<Value> {
        return decoder.decodeStructure(descriptor) {
            if (decodeSequentially()) {
                Checkable(
                    checked = decodeBooleanElement(descriptor, 0),
                    value = decodeNullableSerializableElement(descriptor, 1, valueSerializer) as Value,
                )
            } else {
                var checked: Boolean? = null
                var value: Value? = null
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> checked = decodeBooleanElement(descriptor, 0)
                        1 -> value = decodeNullableSerializableElement(descriptor, 1, valueSerializer)
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                Checkable(
                    checked = checkNotNull(checked),
                    value = value as Value,
                )
            }
        }
    }
}
