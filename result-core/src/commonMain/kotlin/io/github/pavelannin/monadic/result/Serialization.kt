package io.github.pavelannin.monadic.result

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
public class ResultSerializer<Ok, Error>(
    private val okSerializer: KSerializer<Ok>,
    private val errorSerializer: KSerializer<Error>,
) : KSerializer<Result<Ok, Error>> {

    // FIXME: https://github.com/Kotlin/kotlinx.serialization/issues/944
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Result") {
        element<String>("type")
        element("ok", okSerializer.descriptor, isOptional = true)
        element("error", errorSerializer.descriptor, isOptional = true)
    }

    override fun serialize(encoder: Encoder, value: Result<Ok, Error>) {
        encoder.encodeStructure(descriptor) {
            encodeStringElement(
                descriptor = descriptor,
                index = 0,
                value = if (value.isOk()) OK else ERROR,
            )
            encodeNullableSerializableElement(
                descriptor = descriptor,
                index = 1,
                serializer = okSerializer,
                value = if (value.isOk()) value.ok else null,
            )
            encodeNullableSerializableElement(
                descriptor = descriptor,
                index = 2,
                serializer = errorSerializer,
                value = if (value.isError()) value.error else null,
            )
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun deserialize(decoder: Decoder): Result<Ok, Error> {
        return decoder.decodeStructure(descriptor) {
            if (decodeSequentially()) {
                when (val type = decodeStringElement(descriptor, 0)) {
                    OK -> Result.Ok(decodeNullableSerializableElement(descriptor, 1, okSerializer) as Ok)
                    ERROR -> Result.Error(decodeNullableSerializableElement(descriptor, 2, errorSerializer) as Error)
                    else -> error("Unknown type: $type")
                }
            } else {
                var type: String? = null
                var ok: Ok? = null
                var error: Error? = null
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> type = decodeStringElement(descriptor, 0)
                        1 -> ok = decodeNullableSerializableElement(descriptor, 1, okSerializer)
                        2 -> error = decodeNullableSerializableElement(descriptor, 2, errorSerializer)
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                when (type) {
                    OK -> Result.Ok(ok as Ok)
                    ERROR -> Result.Error(error as Error)
                    else -> error("Unknown type: $type")
                }
            }
        }
    }

    private companion object {
        private const val OK = "ok"
        private const val ERROR = "error"
    }
}
