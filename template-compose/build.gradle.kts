// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:${Versions.BUILD_GRADLE_VERSION}")
        classpath("com.google.dagger:hilt-android-gradle-plugin:${Versions.HILT_VERSION}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN_VERSION}")
    }
}

plugins {
    id("io.gitlab.arturbosch.detekt").version(Versions.DETEKT_VERSION)
    id("org.jetbrains.kotlinx.kover").version(Versions.KOVER_VERSION)
}

allprojects {
    repositories {
        google()
        maven(url = "https://jitpack.io")
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

detekt {
    toolVersion = Versions.DETEKT_VERSION

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

    ignoredBuildTypes = listOf("release")
    ignoredFlavors = listOf("production")
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
    jvmTarget = JavaVersion.VERSION_11.toString()
    reports {
        xml {
            outputLocation.set(file("build/reports/detekt/detekt.xml"))
        }
        html {
            outputLocation.set(file("build/reports/detekt/detekt.html"))
        }
    }
}

koverMerged {
    enable()

    val excludedFiles = listOf(
        "*.BuildConfig.*",
        "*.BuildConfig",
        // Enum
        "*.*\$Creator*",
        // DI
        "*.di.*",
        // Hilt
        "*.*_ComponentTreeDeps*",
        "*.*_HiltComponents*",
        "*.*_HiltModules*",
        "*.*_MembersInjector*",
        "*.*_Factory*",
        "*.Hilt_*",
        "dagger.hilt.internal.*",
        "hilt_aggregated_deps.*",
        // Jetpack Compose
        "*.ComposableSingletons*",
        "*.*\$*Preview\$*",
        "*.ui.preview.*",
    )

    filters {
        classes {
            excludes += excludedFiles
        }
    }
}
