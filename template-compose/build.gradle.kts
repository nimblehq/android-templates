// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id(Plugins.ANDROID_APPLICATION) version Versions.GRADLE apply false
    id(Plugins.ANDROID_LIBRARY) version Versions.GRADLE apply false
    id(Plugins.KOTLIN_JVM) version Versions.KOTLIN apply false
    id(Plugins.KOTLIN_ANDROID) version Versions.KOTLIN apply false
    id(Plugins.DETEKT) version Versions.DETEKT
    id(Plugins.KOVER) version Versions.KOVER
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

detekt {
    toolVersion = Versions.DETEKT

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
