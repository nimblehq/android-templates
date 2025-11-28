plugins {
    id(libs.plugins.javaLibrary.get().pluginId)
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.android.lint)
    alias(libs.plugins.kover)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

lint {
    checkDependencies = true
    xmlReport = true
    xmlOutput = file("build/reports/lint/lint-result.xml")
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.javax.inject)

    testImplementation(libs.bundles.unitTest)
}

kover {
    currentProject {
        createVariant("custom") {
            add("jvm")
        }
    }
}
