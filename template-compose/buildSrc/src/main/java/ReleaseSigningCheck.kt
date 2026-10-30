import org.gradle.api.GradleException
import org.gradle.api.Project
import java.io.File
import java.util.Properties

/**
 * Fails a release build with one message when its signing inputs are missing.
 *
 * AGP creates no validateSigning task for a release keystore.
 * An APK build runs write<Variant>SigningConfigVersions. A bundle build skips that task,
 * so the check also runs in package<Variant>Bundle.
 * The name match covers every release variant, such as StagingRelease and StagingPreRelease.
 */
fun Project.registerReleaseSigningCheck(
    propertiesFile: File,
    keystoreFile: File,
    requiredKeys: List<String>
) {
    tasks.matching {
        val isSigningConfigTask = it.name.startsWith("write") && it.name.endsWith("SigningConfigVersions")
        val isBundlePackageTask = it.name.startsWith("package") && it.name.endsWith("Bundle")
        (isSigningConfigTask || isBundlePackageTask) && it.name.contains("Release", ignoreCase = true)
    }.configureEach {
        // An up-to-date task skips doFirst, so the check must always run.
        outputs.upToDateWhen { false }
        doFirst {
            val problems = findSigningProblems(propertiesFile, keystoreFile, requiredKeys)
            if (problems.isNotEmpty()) {
                throw GradleException(
                    "Release signing is not set up:\n" + problems.joinToString("\n") { "- $it" }
                )
            }
        }
    }
}

private fun findSigningProblems(
    propertiesFile: File,
    keystoreFile: File,
    requiredKeys: List<String>
) = buildList {
    if (!propertiesFile.exists()) {
        add(
            "signing.properties is missing. " +
                "Copy signing.properties.sample to signing.properties and enter the release values."
        )
    } else {
        val properties = Properties().apply { propertiesFile.inputStream().use { load(it) } }
        requiredKeys
            .filter { properties.getProperty(it).isNullOrBlank() }
            .forEach { add("$it is missing or blank in signing.properties.") }
    }
    if (!keystoreFile.exists()) {
        add("config/release.keystore is missing. Put the release keystore there.")
    }
}
