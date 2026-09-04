package co.nimblehq.template.compose.util

import android.net.Uri
import io.kotest.matchers.shouldBe
import kotlinx.serialization.Serializable
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@Serializable
private data class DeepLinkMatcherTestKey(
    val id: String,
    val count: Int,
)

@RunWith(RobolectricTestRunner::class)
class DeepLinkMatcherTest {

    @Test
    fun `When the request scheme differs from the pattern, it does not match`() {
        val pattern = DeepLinkPattern(DeepLinkMatcherTestKey.serializer(), Uri.parse("https://example.com/home"))
        val request = DeepLinkRequest(Uri.parse("http://example.com/home"))

        DeepLinkMatcher(request, pattern).match() shouldBe null
    }

    @Test
    fun `When the request host differs from the pattern, it does not match`() {
        val pattern = DeepLinkPattern(DeepLinkMatcherTestKey.serializer(), Uri.parse("https://example.com/home"))
        val request = DeepLinkRequest(Uri.parse("https://other.com/home"))

        DeepLinkMatcher(request, pattern).match() shouldBe null
    }

    @Test
    fun `When the request has a different number of path segments than the pattern, it does not match`() {
        val pattern = DeepLinkPattern(DeepLinkMatcherTestKey.serializer(), Uri.parse("https://example.com/items/{id}"))
        val request = DeepLinkRequest(Uri.parse("https://example.com/items"))

        DeepLinkMatcher(request, pattern).match() shouldBe null
    }

    @Test
    fun `When the request exactly matches a static pattern, it returns an empty arg map`() {
        val pattern = DeepLinkPattern(DeepLinkMatcherTestKey.serializer(), Uri.parse("https://example.com/home"))
        val request = DeepLinkRequest(Uri.parse("https://example.com/home"))

        val result = DeepLinkMatcher(request, pattern).match()

        result?.args shouldBe emptyMap()
    }

    @Test
    fun `When the request has a matching path arg, it parses and returns the typed value`() {
        val pattern = DeepLinkPattern(DeepLinkMatcherTestKey.serializer(), Uri.parse("https://example.com/items/{id}"))
        val request = DeepLinkRequest(Uri.parse("https://example.com/items/abc123"))

        val result = DeepLinkMatcher(request, pattern).match()

        result?.args shouldBe mapOf("id" to "abc123")
    }

    @Test
    fun `When a path arg cannot be parsed into its declared type, it does not match`() {
        val pattern = DeepLinkPattern(DeepLinkMatcherTestKey.serializer(), Uri.parse("https://example.com/items/{count}"))
        val request = DeepLinkRequest(Uri.parse("https://example.com/items/notANumber"))

        DeepLinkMatcher(request, pattern).match() shouldBe null
    }

    @Test
    fun `When the request has a recognized query arg, it parses and returns the typed value`() {
        val pattern = DeepLinkPattern(DeepLinkMatcherTestKey.serializer(), Uri.parse("https://example.com/items?count={count}"))
        val request = DeepLinkRequest(Uri.parse("https://example.com/items?count=42"))

        val result = DeepLinkMatcher(request, pattern).match()

        result?.args shouldBe mapOf("count" to 42)
    }

    @Test
    fun `When a recognized query arg cannot be parsed into its declared type, it does not match`() {
        val pattern = DeepLinkPattern(DeepLinkMatcherTestKey.serializer(), Uri.parse("https://example.com/items?count={count}"))
        val request = DeepLinkRequest(Uri.parse("https://example.com/items?count=notANumber"))

        DeepLinkMatcher(request, pattern).match() shouldBe null
    }

    @Test
    fun `When the request has a query arg not declared by the pattern, it is ignored`() {
        val pattern = DeepLinkPattern(DeepLinkMatcherTestKey.serializer(), Uri.parse("https://example.com/items"))
        val request = DeepLinkRequest(Uri.parse("https://example.com/items?foo=bar"))

        val result = DeepLinkMatcher(request, pattern).match()

        result?.args shouldBe emptyMap()
    }
}
