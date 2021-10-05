// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:${Versions.BUILD_GRADLE_VERSION}")
        classpath("com.google.dagger:hilt-android-gradle-plugin:${Versions.HILT_VERSION}")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.NAVIGATION_VERSION}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN_VERSION}")
    }
}

plugins {
    id("io.gitlab.arturbosch.detekt").version(Versions.DETEKT_VERSION)
}

allprojects {
    repositories {
        google()
        jcenter()
        maven { url = uri("https://jitpack.io") }
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

detekt {
    toolVersion = Versions.DETEKT_VERSION

    input = files("app/src/main/java", "data/src/main/java")
    parallel = false
    config = files("detekt-config.yml")
    buildUponDefaultConfig = false
    disableDefaultRuleSets = false

    debug = false
    ignoreFailures = false

    ignoredBuildTypes = listOf("release")
    ignoredFlavors = listOf("production")

    reports {
        xml {
            enabled = true
            destination = file("build/reports/detekt/detekt-result.xml")
        }
        html {
            enabled = true
            destination = file("build/reports/detekt/detekt-result.html")
        }
    }
}
