import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kover)
}

val signingPropertiesFile = file("$rootDir/signing.properties")
val signingProperties = Properties().apply {
    if (signingPropertiesFile.exists()) signingPropertiesFile.inputStream().use { load(it) }
}
val releaseKeystoreFile = file("$rootDir/config/release.keystore")
val requiredSigningKeys = listOf("KEY_ALIAS", "KEYSTORE_PASSWORD", "KEY_PASSWORD")
val getVersionCode: () -> Int = {
    if (project.hasProperty("versionCode")) {
        (project.property("versionCode") as String).toInt()
    } else {
        libs.versions.androidVersionCode.get().toInt()
    }
}

android {
    namespace = "co.nimblehq.template.compose"
    compileSdk = libs.versions.androidCompileSdk.get().toInt()

    defaultConfig {
        applicationId = "co.nimblehq.template.compose"
        minSdk = libs.versions.androidMinSdk.get().toInt()
        targetSdk = libs.versions.androidTargetSdk.get().toInt()
        versionCode = getVersionCode()
        versionName = libs.versions.androidVersionName.get()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        create(BuildTypes.RELEASE) {
            // Copy signing.properties.sample to signing.properties and fill it for release builds.
            storeFile = releaseKeystoreFile
            storePassword = signingProperties.getProperty("KEYSTORE_PASSWORD")
            keyPassword = signingProperties.getProperty("KEY_PASSWORD")
            keyAlias = signingProperties.getProperty("KEY_ALIAS")
        }

        getByName(BuildTypes.DEBUG) {
            storeFile = file("../config/debug.keystore")
            storePassword = "oQ4mL1jY2uX7wD8q"
            keyAlias = "debug-key-alias"
            keyPassword = "oQ4mL1jY2uX7wD8q"
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isDebuggable = false
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs[BuildTypes.RELEASE]
            buildConfigField("String", "BASE_API_URL", "\"https://jsonplaceholder.typicode.com/\"")
        }

        create(BuildTypes.PRERELEASE) {
            initWith(getByName(BuildTypes.RELEASE))
        }

        debug {
            // For quickly testing build with proguard, enable this
            isMinifyEnabled = false
            signingConfig = signingConfigs[BuildTypes.DEBUG]
            buildConfigField("String", "BASE_API_URL", "\"https://jsonplaceholder.typicode.com/\"")
        }
    }

    flavorDimensions += Flavors.DIMENSION_VERSION
    productFlavors {
        create(Flavors.STAGING) {
            applicationIdSuffix = ".staging"
        }

        create(Flavors.PRODUCTION) {}
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
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
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

// Fail early with one message when a release build lacks its signing inputs.
// AGP creates no validateSigning task for a release keystore.
// The check runs in the signing-config task, which runs before packaging.
// An APK build runs write<Variant>SigningConfigVersions. A bundle build skips that task, so it also runs
// package<Variant>Bundle. The name match covers every release variant, such as StagingRelease and StagingPreRelease.
tasks.matching {
    val isSigningConfigTask = it.name.startsWith("write") && it.name.endsWith("SigningConfigVersions")
    val isBundlePackageTask = it.name.startsWith("package") && it.name.endsWith("Bundle")
    (isSigningConfigTask || isBundlePackageTask) && it.name.contains("Release", ignoreCase = true)
}.configureEach {
    // An up-to-date task skips doFirst, so the check must always run.
    outputs.upToDateWhen { false }
    doFirst {
        val problems = buildList {
            if (!signingPropertiesFile.exists()) {
                add("signing.properties is missing. Copy signing.properties.sample to signing.properties and enter the release values.")
            } else {
                requiredSigningKeys
                    .filter { signingProperties.getProperty(it).isNullOrBlank() }
                    .forEach { add("$it is missing or blank in signing.properties.") }
            }
            if (!releaseKeystoreFile.exists()) {
                add("config/release.keystore is missing. Put the release keystore there.")
            }
        }
        if (problems.isNotEmpty()) {
            throw GradleException(
                "Release signing is not set up:\n" + problems.joinToString("\n") { "- $it" }
            )
        }
    }
}

dependencies {
    implementation(projects.data)
    implementation(projects.domain)

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(libs.bundles.androidx)
    implementation(libs.androidx.datastore.preferences)

    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)
    implementation(libs.accompanist.permissions)
    debugImplementation(libs.compose.ui.tooling)

    implementation(libs.bundles.hilt)
    ksp(libs.hilt.compiler)

    implementation(libs.kotlinx.collections.immutable)

    implementation(libs.timber)
    debugImplementation(libs.chucker)
    preReleaseImplementation(libs.chucker)
    releaseImplementation(libs.chucker.noOp)

    implementation(libs.nimble.common)

    // Unit test
    testImplementation(libs.bundles.unitTest)
    testImplementation(libs.test.turbine)

    // UI test with Robolectric
    testImplementation(platform(libs.compose.bom))
    testImplementation(libs.bundles.uiTest)
}

/*
 * Kover configs
 */
dependencies {
    kover(projects.data)
    kover(projects.domain)
}

kover {
    currentProject {
        createVariant("custom") {
            addWithDependencies("stagingDebug")
        }
    }
    reports {
        // filters for all report types of all build variants
        filters {
            excludes {
                androidGeneratedClasses()
                annotatedBy(
                    // Compose
                    "androidx.compose.ui.tooling.preview.Preview",
                    // DI
                    "dagger.Module",
                )
                classes(
                    // DataStore
                    "*Kt\$Dsl*",
                    "*OuterClass*",
                    // DI
                    "*.*_ComponentTreeDeps*",
                    "*.*_HiltComponents*",
                    "*.*_HiltModules*",
                    "*.*_MembersInjector*",
                    "*.*_Factory*",
                    "*.Hilt_*",
                    // Enum
                    "*.*\$Creator*",
                    // Compose
                    "*ComposableSingletons*",
                )
                inheritedFrom(
                    // Compose
                    "androidx.compose.ui.tooling.preview.PreviewParameterProvider",
                    // DI
                    "dagger.internal.Factory",
                )
                packages(
                    // DI
                    "dagger.hilt.internal",
                    "hilt_aggregated_deps",
                )
            }
        }
    }
}
