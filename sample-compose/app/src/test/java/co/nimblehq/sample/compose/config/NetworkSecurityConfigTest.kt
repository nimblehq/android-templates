package co.nimblehq.sample.compose.config

import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.shouldBe
import org.junit.Test
import org.w3c.dom.Element
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory

class NetworkSecurityConfigTest {

    private val mainConfig = parse("src/main/res/xml/network_security_config.xml")
    private val stagingConfig = parse("src/staging/res/xml/network_security_config.xml")

    @Test
    fun `When reading the staging config, it blocks cleartext traffic`() {
        stagingConfig.baseConfig().getAttribute("cleartextTrafficPermitted") shouldBe "false"
    }

    @Test
    fun `When reading the staging config, its base config trusts system and user certificates`() {
        stagingConfig.baseConfig().certificateSources() shouldContainExactlyInAnyOrder listOf("system", "user")
    }

    @Test
    fun `When reading the main config, it blocks cleartext traffic`() {
        mainConfig.baseConfig().getAttribute("cleartextTrafficPermitted") shouldBe "false"
    }

    @Test
    fun `When reading the main config, it trusts user certificates only in debug overrides`() {
        val root = mainConfig.documentElement
        val debugOverrides = root.children("debug-overrides").single()

        debugOverrides.certificateSources() shouldContainExactlyInAnyOrder listOf("system", "user")
        root.children("base-config").single().certificateSources() shouldBe emptyList()
    }

    private fun parse(path: String) = DocumentBuilderFactory.newInstance()
        .newDocumentBuilder()
        .parse(File(path))

    private fun org.w3c.dom.Document.baseConfig() = documentElement.children("base-config").single()

    private fun Element.children(tag: String): List<Element> {
        val nodes = getElementsByTagName(tag)
        return (0 until nodes.length).map { nodes.item(it) as Element }
            .filter { it.parentNode == this }
    }

    private fun Element.certificateSources(): List<String> {
        val nodes = getElementsByTagName("certificates")
        return (0 until nodes.length).map { (nodes.item(it) as Element).getAttribute("src") }
    }
}
