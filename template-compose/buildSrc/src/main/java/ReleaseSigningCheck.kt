import org.gradle.api.GradleException
import org.gradle.api.Project
import java.io.File
import java.util.Properties

private val REQUIRED_SIGNING_KEYS = listOf("KEY_ALIAS", "KEYSTORE_PASSWORD", "KEY_PASSWORD")

/** Reads a properties file. Returns empty properties when the file does not exist. */
fun File.readProperties(): Properties = Properties().apply {
    if (exists()) inputStream().use { load(it) }
}

/**
 * Fails a release build with one message when its signing inputs are missing.
 *
 * AGP has no task that validates a release keystore.
 * So the check runs inside a task that each release build already runs:
 * - An APK build runs `write<Variant>SigningConfigVersions`.
 * - A bundle build skips that task. It runs `package<Variant>Bundle`.
 *
 * The name match covers all release variants, such as StagingRelease and StagingPreRelease.
 */
fun Project.registerReleaseSigningCheck(
    propertiesFile: File,
    keystoreFile: File
) {
    tasks.matching {
        val isSigningConfigTask = it.name.startsWith("write") && it.name.endsWith("SigningConfigVersions")
        val isBundlePackageTask = it.name.startsWith("package") && it.name.endsWith("Bundle")
        (isSigningConfigTask || isBundlePackageTask) && it.name.contains("Release", ignoreCase = true)
    }.configureEach {
        // An up-to-date task skips doFirst, so the check must always run.
        outputs.upToDateWhen { false }
        doFirst {
            val problems = findSigningProblems(propertiesFile, keystoreFile)
            if (problems.isNotEmpty()) {
                throw GradleException(
                    "Release signing is not set up:\n" + problems.joinToString("\n") { "- $it" }
                )
            }
        }
    }
}

internal fun findSigningProblems(
    propertiesFile: File,
    keystoreFile: File,
    requiredKeys: List<String> = REQUIRED_SIGNING_KEYS
) = buildList {
    if (!propertiesFile.exists()) {
        add(
            "signing.properties is missing. " +
                "Copy signing.properties.sample to signing.properties and enter the release values."
        )
    } else {
        val properties = propertiesFile.readProperties()
        requiredKeys
            .filter { properties.getProperty(it).isNullOrBlank() }
            .forEach { add("$it is missing or blank in signing.properties.") }
    }
    if (!keystoreFile.exists()) {
        add("config/release.keystore is missing. Put the release keystore there.")
    }
}
