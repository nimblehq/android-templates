package co.nimblehq.template.compose.util

import io.kotest.matchers.shouldBe
import kotlinx.serialization.Serializable
import org.junit.Test

@Serializable
private data class KeyDecoderTestKey(
    val id: String,
    val count: Int,
)

@Serializable
private data class KeyDecoderTestKeyWithDefault(
    val id: String,
    val count: Int = 0,
)

class KeyDecoderTest {

    @Test
    fun `When all arguments are present, it decodes them into the target key`() {
        val args = mapOf<String, Any>("id" to "abc123", "count" to 5)

        val result = KeyDecoder(args).decodeSerializableValue(KeyDecoderTestKey.serializer())

        result shouldBe KeyDecoderTestKey(id = "abc123", count = 5)
    }

    @Test
    fun `When an optional argument is missing, it decodes using the field's default value`() {
        val args = mapOf<String, Any>("id" to "abc123")

        val result = KeyDecoder(args).decodeSerializableValue(KeyDecoderTestKeyWithDefault.serializer())

        result shouldBe KeyDecoderTestKeyWithDefault(id = "abc123", count = 0)
    }
}
