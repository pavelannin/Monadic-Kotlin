package io.github.pavelannin.monadic.lce

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
public class LCESerializer<Loading, Content, Error>(
    private val loadingSerializer: KSerializer<Loading>,
    private val contentSerializer: KSerializer<Content>,
    private val errorSerializer: KSerializer<Error>,
) : KSerializer<LCE<Loading, Content, Error>> {

    // FIXME: https://github.com/Kotlin/kotlinx.serialization/issues/944
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("LCE") {
        element<String>("type")
        element("loading", loadingSerializer.descriptor, isOptional = true)
        element("content", contentSerializer.descriptor, isOptional = true)
        element("error", errorSerializer.descriptor, isOptional = true)
    }

    override fun serialize(encoder: Encoder, value: LCE<Loading, Content, Error>) {
        encoder.encodeStructure(descriptor) {
            encodeStringElement(
                descriptor = descriptor,
                index = 0,
                value = when (value) {
                    is LCE.Loading -> loadingType
                    is LCE.Content -> contentType
                    is LCE.Error -> errorType
                }
            )
            encodeNullableSerializableElement(
                descriptor = descriptor,
                index = 1,
                serializer = loadingSerializer,
                value = if (value is LCE.Loading) value.loading else null,
            )
            encodeNullableSerializableElement(
                descriptor = descriptor,
                index = 2,
                serializer = contentSerializer,
                value = if (value is LCE.Content) value.content else null,
            )
            encodeNullableSerializableElement(
                descriptor = descriptor,
                index = 3,
                serializer = errorSerializer,
                value = if (value is LCE.Error) value.error else null,
            )
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun deserialize(decoder: Decoder): LCE<Loading, Content, Error> {
        return decoder.decodeStructure(descriptor) {
            if (decodeSequentially()) {
                when (val type = decodeStringElement(descriptor, 0)) {
                    loadingType -> LCE.Loading(decodeNullableSerializableElement(descriptor, 1, loadingSerializer) as Loading)
                    contentType -> LCE.Content(decodeNullableSerializableElement(descriptor, 2, contentSerializer) as Content)
                    errorType -> LCE.Error(decodeNullableSerializableElement(descriptor, 3, errorSerializer) as Error)
                    else -> error("Unknown type: $type")
                }
            } else {
                var type: String? = null
                var loading: Loading? = null
                var content: Content? = null
                var error: Error? = null
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> type = decodeStringElement(descriptor, 0)
                        1 -> loading = decodeNullableSerializableElement(descriptor, 1, loadingSerializer)
                        2 -> content = decodeNullableSerializableElement(descriptor, 2, contentSerializer)
                        3 -> error = decodeNullableSerializableElement(descriptor, 3, errorSerializer)
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                when (type) {
                    loadingType -> LCE.Loading(loading as Loading)
                    contentType -> LCE.Content(content as Content)
                    errorType -> LCE.Error(error as Error)
                    else -> error("Unknown type: $type")
                }
            }
        }
    }

    private companion object {
        private const val loadingType = "loading"
        private const val contentType = "content"
        private const val errorType = "error"
    }
}
