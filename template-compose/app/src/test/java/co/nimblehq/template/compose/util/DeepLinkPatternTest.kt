package co.nimblehq.template.compose.util

import android.net.Uri
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import kotlinx.serialization.Serializable
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@Serializable
private data class DeepLinkPatternTestKey(
    val id: String,
    val count: Int,
    val tags: List<String> = emptyList(),
)

@RunWith(RobolectricTestRunner::class)
class DeepLinkPatternTest {

    @Test
    fun `When the uri pattern has a static and an argument path segment, it parses them in order`() {
        val pattern = DeepLinkPattern(
            DeepLinkPatternTestKey.serializer(),
            Uri.parse("https://example.com/items/{id}")
        )

        pattern.pathSegments[0].isParamArg shouldBe false
        pattern.pathSegments[0].stringValue shouldBe "items"
        pattern.pathSegments[1].isParamArg shouldBe true
        pattern.pathSegments[1].stringValue shouldBe "id"
        pattern.pathSegments[1].typeParser.invoke("abc123") shouldBe "abc123"
    }

    @Test
    fun `When the uri pattern has a query argument, it resolves the parser for its declared type`() {
        val pattern = DeepLinkPattern(
            DeepLinkPatternTestKey.serializer(),
            Uri.parse("https://example.com/items?count={count}")
        )

        pattern.queryValueParsers["count"]?.invoke("5") shouldBe 5
    }

    @Test
    fun `When the uri pattern references a non-primitive argument, it throws`() {
        val uriPattern = Uri.parse("https://example.com/items?tags=tags")

        shouldThrow<IllegalArgumentException> {
            DeepLinkPattern(DeepLinkPatternTestKey.serializer(), uriPattern)
        }
    }
}
