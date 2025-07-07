plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kover)
}

android {
    namespace = "co.nimblehq.sample.compose.data"
    compileSdk = libs.versions.androidCompileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.androidMinSdk.get().toInt()

        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName(BuildType.RELEASE) {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro"
            )
        }

        getByName(BuildType.DEBUG) {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    lintOptions {
        isCheckDependencies = true
        xmlReport = true
        xmlOutput = file("build/reports/lint/lint-result.xml")
    }
}

dependencies {
    implementation(projects.domain)

    implementation(libs.androidx.core)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.security.crypto)
    implementation(libs.hilt.android)
    implementation(libs.kotlin.stdlib)
    implementation(libs.javax.inject)

    implementation(libs.kotlinx.coroutines.core)

    api(libs.bundles.retrofit)
    api(libs.bundles.okhttp)
    api(libs.bundles.moshi)
    implementation(libs.moshi)

    // Testing
    testImplementation(libs.bundles.unitTest)
    testImplementation(libs.test.core.ktx)
    testImplementation(libs.test.robolectric)
    testImplementation(libs.test.turbine)
}

kover {
    currentProject {
        createVariant("custom") {
            add("debug")
        }
    }
}
