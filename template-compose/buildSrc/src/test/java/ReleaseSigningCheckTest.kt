import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

class ReleaseSigningCheckTest {

    @get:Rule
    val folder = TemporaryFolder()

    private val validProperties = """
        KEY_ALIAS=alias
        KEYSTORE_PASSWORD=store-secret
        KEY_PASSWORD=key-secret
    """.trimIndent()

    private fun propertiesFile(content: String) = folder.newFile("signing.properties").apply { writeText(content) }

    private fun keystoreFile() = folder.newFile("release.keystore")

    private fun missingFile(name: String) = folder.root.resolve(name)

    @Test
    fun `returns no problem when every input is valid`() {
        val problems = findSigningProblems(propertiesFile(validProperties), keystoreFile())

        assertTrue(problems.isEmpty())
    }

    @Test
    fun `reports a missing properties file`() {
        val problems = findSigningProblems(missingFile("signing.properties"), keystoreFile())

        assertEquals(1, problems.size)
        assertTrue(problems.single().startsWith("signing.properties is missing."))
    }

    @Test
    fun `reports a missing keystore`() {
        val problems = findSigningProblems(propertiesFile(validProperties), missingFile("release.keystore"))

        assertEquals(1, problems.size)
        assertTrue(problems.single().startsWith("config/release.keystore is missing."))
    }

    @Test
    fun `reports a blank key`() {
        val content = validProperties.replace("KEY_PASSWORD=key-secret", "KEY_PASSWORD=")

        val problems = findSigningProblems(propertiesFile(content), keystoreFile())

        assertEquals(1, problems.size)
        assertTrue(problems.single().startsWith("KEY_PASSWORD"))
    }

    @Test
    fun `lists every problem in one pass`() {
        val problems = findSigningProblems(missingFile("signing.properties"), missingFile("release.keystore"))

        assertEquals(2, problems.size)
    }
}
