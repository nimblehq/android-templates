// Top-level build file where you can add configuration options common to all sub-projects/modules.
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.detekt)
    alias(libs.plugins.kover)
    alias(libs.plugins.ktlint)
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

detekt {
    toolVersion = libs.versions.detekt.get()

    source = files(
        "app/src/main/java",
        "data/src/main/java",
        "domain/src/main/java",
        "buildSrc/src/main/java"
    )
    parallel = false
    config = files("detekt-config.yml")
    buildUponDefaultConfig = false
    disableDefaultRuleSets = false

    debug = false
    ignoreFailures = false

    ignoredBuildTypes = listOf(BuildTypes.RELEASE)
    ignoredFlavors = listOf(Flavors.PRODUCTION)
}

ktlint {
    android.set(true)
    outputToConsole.set(true)
    outputColorName.set("RED")
    ignoreFailures.set(false)

    reporters {
        reporter(ReporterType.PLAIN)
        reporter(ReporterType.SARIF)
        reporter(ReporterType.CHECKSTYLE)
        reporter(ReporterType.HTML)
    }

    filter {
        exclude("**/build/**")
        exclude("**/generated/**")
        exclude("**/*.kts")
    }
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
    jvmTarget = JavaVersion.VERSION_17.toString()
    reports {
        xml {
            outputLocation.set(file("build/reports/detekt/detekt.xml"))
        }
        html {
            outputLocation.set(file("build/reports/detekt/detekt.html"))
        }
    }
}

// Run ktlintFormat before building
subprojects {
    afterEvaluate {
        tasks.findByName("preBuild")?.dependsOn(rootProject.tasks.named("ktlintFormat"))
    }
}
