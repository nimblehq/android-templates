package co.nimblehq.template.compose.config

import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.shouldBe
import org.junit.Test
import org.w3c.dom.Document
import org.w3c.dom.Element
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory

class NetworkSecurityConfigTest {

    private val mainConfig = parse("src/main/res/xml/network_security_config.xml")
    private val stagingConfig = parse("src/staging/res/xml/network_security_config.xml")

    @Test
    fun `When reading the main config, no element permits cleartext traffic`() {
        mainConfig.cleartextPermittingElements().shouldBeEmpty()
        mainConfig.documentElement.children("base-config").single()
            .getAttribute("cleartextTrafficPermitted") shouldBe "false"
    }

    @Test
    fun `When reading the main config, user certificates are trusted only in debug overrides`() {
        val userCertificates = mainConfig.descendants("certificates")
            .filter { it.getAttribute("src") == "user" }

        userCertificates.shouldNotBeEmpty()
        userCertificates.filterNot { it.hasAncestor("debug-overrides") }
            .shouldBeEmpty()
    }

    @Test
    fun `When reading the main config, its base config trusts no extra certificates`() {
        mainConfig.documentElement.children("base-config").single()
            .descendants("certificates")
            .shouldBeEmpty()
    }

    @Test
    fun `When reading the staging config, no element permits cleartext traffic`() {
        stagingConfig.cleartextPermittingElements().shouldBeEmpty()
        stagingConfig.documentElement.children("base-config").single()
            .getAttribute("cleartextTrafficPermitted") shouldBe "false"
    }

    @Test
    fun `When reading the staging config, its base config declares system and user trust anchors`() {
        stagingConfig.documentElement.children("base-config").single()
            .children("trust-anchors").single()
            .certificateSources() shouldContainAll listOf("system", "user")
    }

    @Test
    fun `When reading the staging config, every trust anchor trusts system and user certificates`() {
        val trustAnchors = stagingConfig.descendants("trust-anchors")

        trustAnchors.shouldNotBeEmpty()
        trustAnchors.forEach {
            it.certificateSources() shouldContainAll listOf("system", "user")
        }
    }

    private fun parse(path: String) = DocumentBuilderFactory.newInstance()
        .newDocumentBuilder()
        .parse(File(path))

    private fun Document.cleartextPermittingElements() = descendants("*")
        .filter { it.getAttribute("cleartextTrafficPermitted") == "true" }

    private fun Document.descendants(tag: String) = documentElement.descendants(tag)

    private fun Element.descendants(tag: String): List<Element> {
        val nodes = getElementsByTagName(tag)
        return (0 until nodes.length).map { nodes.item(it) as Element }
    }

    private fun Element.children(tag: String): List<Element> {
        val nodes = getElementsByTagName(tag)
        return (0 until nodes.length)
            .map { nodes.item(it) as Element }
            .filter { it.parentNode == this }
    }

    private fun Element.hasAncestor(tag: String): Boolean {
        var node = parentNode
        while (node != null) {
            if (node.nodeName == tag) return true
            node = node.parentNode
        }
        return false
    }

    private fun Element.certificateSources() = descendants("certificates")
        .map { it.getAttribute("src") }
}
