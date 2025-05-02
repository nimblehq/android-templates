plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kover)
}

val keystoreProperties = rootDir.loadGradleProperties("signing.properties")

android {
    signingConfigs {
        create(BuildType.RELEASE) {
            // Remember to edit signing.properties to have the correct info for release build.
            storeFile = file("../config/release.keystore")
            storePassword = keystoreProperties.getProperty("KEYSTORE_PASSWORD") as String
            keyPassword = keystoreProperties.getProperty("KEY_PASSWORD") as String
            keyAlias = keystoreProperties.getProperty("KEY_ALIAS") as String
        }

        getByName(BuildType.DEBUG) {
            storeFile = file("../config/debug.keystore")
            storePassword = "oQ4mL1jY2uX7wD8q"
            keyAlias = "debug-key-alias"
            keyPassword = "oQ4mL1jY2uX7wD8q"
        }
    }

    namespace = "co.nimblehq.sample.compose"
    compileSdk = libs.versions.androidCompileSdk.get().toInt()
    defaultConfig {
        applicationId = "co.nimblehq.sample.compose"
        minSdk = libs.versions.androidMinSdk.get().toInt()
        targetSdk = libs.versions.androidTargetSdk.get().toInt()
        versionCode = libs.versions.androidVersionCode.get().toInt()
        versionName = libs.versions.androidVersionName.get()
        testInstrumentationRunner = "co.nimblehq.sample.compose.HiltTestRunner"
    }

    buildTypes {
        getByName(BuildType.RELEASE) {
            isMinifyEnabled = true
            isDebuggable = false
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs[BuildType.RELEASE]
            buildConfigField("String", "BASE_API_URL", "\"https://jsonplaceholder.typicode.com/\"")
        }

        getByName(BuildType.DEBUG) {
            // For quickly testing build with proguard, enable this
            isMinifyEnabled = false
            signingConfig = signingConfigs[BuildType.DEBUG]
            buildConfigField("String", "BASE_API_URL", "\"https://jsonplaceholder.typicode.com/\"")
        }
    }

    flavorDimensions += Flavor.DIMENSION_VERSION
    productFlavors {
        create(Flavor.STAGING) {
            applicationIdSuffix = ".staging"
        }

        create(Flavor.PRODUCTION) {}
    }

    sourceSets["test"].resources {
        srcDir("src/test/resources")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    packaging {
        jniLibs {
            // Resolve "libmockkjvmtiagent.so" https://github.com/mockk/mockk/issues/297#issuecomment-901924678
            useLegacyPackaging = true
        }
        resources {
            merges += listOf("/META-INF/LICENSE.md", "/META-INF/LICENSE-notice.md")
        }
    }

    lint {
        checkDependencies = true
        xmlReport = true
        xmlOutput = file("build/reports/lint/lint-result.xml")
    }

    testOptions {
        unitTests {
            // Robolectric resource processing/loading https://github.com/robolectric/robolectric/pull/4736
            isIncludeAndroidResources = true
        }
        // Disable device's animation for instrument testing
        // animationsDisabled = true
    }
}

dependencies {
    implementation(projects.data)
    implementation(projects.domain)

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(libs.bundles.androidx)

    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)
    debugImplementation(libs.compose.ui.tooling)

    implementation(libs.accompanist.permissions)
    implementation(libs.accompanist.systemUiController)

    implementation(libs.androidx.datastore.preferences)

    implementation(libs.bundles.hilt)
    ksp(libs.hilt.compiler)

    implementation(libs.timber)

    implementation(libs.nimble.common)

    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlinx.coroutines.core)

    debugImplementation(libs.chucker)
    releaseImplementation(libs.chucker.noOp)

    // Unit test
    testImplementation(libs.bundles.unitTest)
    testImplementation(libs.test.turbine)

    // UI test with Robolectric
    testImplementation(platform(libs.compose.bom))
    testImplementation(libs.test.compose.ui.junit4)
    testImplementation(libs.test.rules)
    testImplementation(libs.test.robolectric)

    // UI test
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.test.compose.ui.junit4)
    androidTestImplementation(libs.test.rules)
    androidTestImplementation(libs.test.mockk.android)
    androidTestImplementation(libs.test.navigation)
    androidTestImplementation(libs.test.hilt.android)
    kspAndroidTest(libs.test.hilt.android.kotlin)

    // Unable to resolve activity for Intent { act=android.intent.action.MAIN cat=[android.intent.category.LAUNCHER]
    // cmp=co.nimblehq.sample.compose/androidx.activity.ComponentActivity } --
    // see https://github.com/robolectric/robolectric/pull/4736 for details
    // Ref: https://developer.android.com/develop/ui/compose/testing#setup
    debugImplementation(libs.test.compose.ui.manifest)
}

/*
 * Kover configs
 */
dependencies {
    kover(projects.data)
    kover(projects.domain)
}

koverReport {
    defaults {
        mergeWith("stagingDebug")
        filters {
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

            excludes {
                classes(excludedFiles)
            }
        }
    }
}
