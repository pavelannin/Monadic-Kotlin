package io.github.pavelannin.monadic.either

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
public class EitherSerializer<Left, Right>(
    private val leftSerializer: KSerializer<Left>,
    private val rightSerializer: KSerializer<Right>,
) : KSerializer<Either<Left, Right>> {

    // FIXME: https://github.com/Kotlin/kotlinx.serialization/issues/944
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Either") {
        element<String>("type")
        element("left", leftSerializer.descriptor, isOptional = true)
        element("right", rightSerializer.descriptor, isOptional = true)
    }

    override fun serialize(encoder: Encoder, value: Either<Left, Right>) {
        encoder.encodeStructure(descriptor) {
            encodeStringElement(
                descriptor = descriptor,
                index = 0,
                value = if (value.isLeft()) leftType else rightType,
            )
            encodeNullableSerializableElement(
                descriptor = descriptor,
                index = 1,
                serializer = leftSerializer,
                value = if (value.isLeft()) value.left else null,
            )
            encodeNullableSerializableElement(
                descriptor = descriptor,
                index = 2,
                serializer = rightSerializer,
                value = if (value.isRight()) value.right else null,
            )
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun deserialize(decoder: Decoder): Either<Left, Right> {
        return decoder.decodeStructure(descriptor) {
            if (decodeSequentially()) {
                when (val type = decodeStringElement(descriptor, 0)) {
                    leftType -> Either.Left(decodeNullableSerializableElement(descriptor, 1, leftSerializer) as Left)
                    rightType -> Either.Right(decodeNullableSerializableElement(descriptor, 2, rightSerializer) as Right)
                    else -> error("Unknown type: $type")
                }
            } else {
                var type: String? = null
                var left: Left? = null
                var right: Right? = null
                while (true) {
                    when (val index = decodeElementIndex(descriptor)) {
                        0 -> type = decodeStringElement(descriptor, 0)
                        1 -> left = decodeNullableSerializableElement(descriptor, 1, leftSerializer)
                        2 -> right = decodeNullableSerializableElement(descriptor, 2, rightSerializer)
                        CompositeDecoder.DECODE_DONE -> break
                        else -> error("Unexpected index: $index")
                    }
                }
                when (type) {
                    leftType -> Either.Left(left as Left)
                    rightType -> Either.Right(right as Right)
                    else -> error("Unknown type: $type")
                }
            }
        }
    }

    private companion object {
        private const val leftType = "left"
        private const val rightType = "right"
    }
}
