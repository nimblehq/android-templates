plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kover)
}

android {
    namespace = "co.nimblehq.template.compose.data"
    compileSdk = Versions.ANDROID_COMPILE_SDK

    defaultConfig {
        minSdk = Versions.ANDROID_MIN_SDK

        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro"
            )
        }

        debug {
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

    lint {
        checkDependencies = true
        xmlReport = true
        xmlOutput = file("build/reports/lint/lint-result.xml")
    }
}

dependencies {
    implementation(project(Modules.DOMAIN))

    implementation(libs.androidx.core)
    implementation(libs.androidx.datastorePreferences)
    implementation(libs.androidx.securityCrypto)

    implementation(libs.javax.inject)

    api(libs.bundles.retrofit)
    api(libs.bundles.okhttp)
    api(libs.moshi)
    implementation(libs.bundles.moshi)

    testImplementation(libs.bundles.unitTest)
    testImplementation(libs.test.core.ktx)
    testImplementation(libs.test.turbine)
    testImplementation(libs.test.robolectric)
}
