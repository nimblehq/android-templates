import org.jetbrains.kotlin.konan.properties.loadProperties

plugins {
    id(Plugins.ANDROID_APPLICATION)
    id(Plugins.KOTLIN_ANDROID)
    id(Plugins.KOTLIN_PARCELIZE)
    id(Plugins.KOVER)
}

val signingProperties = loadProperties("$rootDir/signing.properties")
val getVersionCode: () -> Int = {
    if (project.hasProperty("versionCode")) {
        (project.property("versionCode") as String).toInt()
    } else {
        Versions.ANDROID_VERSION_CODE
    }
}

android {
    namespace = "co.nimblehq.template.compose"
    compileSdk = Versions.ANDROID_COMPILE_SDK

    defaultConfig {
        applicationId = "co.nimblehq.template.compose"
        minSdk = Versions.ANDROID_MIN_SDK
        targetSdk = Versions.ANDROID_TARGET_SDK
        versionCode = getVersionCode()
        versionName = Versions.ANDROID_VERSION_NAME

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        create(BuildTypes.RELEASE) {
            // Remember to edit signing.properties to have the correct info for release build.
            storeFile = file("../config/release.keystore")
            storePassword = signingProperties.getProperty("KEYSTORE_PASSWORD") as String
            keyPassword = signingProperties.getProperty("KEY_PASSWORD") as String
            keyAlias = signingProperties.getProperty("KEY_ALIAS") as String
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

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.COMPOSE_COMPILER
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

dependencies {
    implementation(project(Modules.DATA))
    implementation(project(Modules.DOMAIN))

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    with(Dependencies.AndroidX) {
        implementation(CORE_KTX)
        implementation(LIFECYCLE_RUNTIME_KTX)
        implementation(LIFECYCLE_RUNTIME_COMPOSE)
    }

    with(Dependencies.Compose) {
        implementation(platform(BOM))
        implementation(UI)
        implementation(UI_TOOLING)
        implementation(MATERIAL)
        implementation(NAVIGATION)

        implementation(ACCOMPANIST_PERMISSIONS)
    }

    with(Dependencies.Koin) {
        implementation(CORE)
        implementation(ANDROID)
        implementation(COMPOSE)
    }

    with(Dependencies.Log) {
        implementation(TIMBER)
    }

    with(Dependencies.Util) {
        implementation(COMMON_KTX)
    }

    with(Dependencies.Test) {
        // Unit test
        testImplementation(COROUTINES)
        testImplementation(JUNIT)
        testImplementation(KOTEST)
        testImplementation(MOCKK)
        testImplementation(TURBINE)

        // UI test with Robolectric
        testImplementation(platform(Dependencies.Compose.BOM))
        testImplementation(COMPOSE_UI_TEST_JUNIT)
        testImplementation(ROBOLECTRIC)
    }
}

/*
 * Kover configs
 */
dependencies {
    kover(project(Modules.DATA))
    kover(project(Modules.DOMAIN))
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
