plugins {
    id("com.android.application")

    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")

    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")

    jacoco
}

apply(from = "../config/jacoco.gradle.kts")

val keystoreProperties = rootDir.loadGradleProperties("signing.properties")

android {
    signingConfigs {
        create("config") {
            // Remember to edit signing.properties to have the correct info for release build.
            storeFile = file("../keystore_production")
            storePassword = keystoreProperties.getProperty("KEYSTORE_PASSWORD") as String
            keyPassword = keystoreProperties.getProperty("KEY_PASSWORD") as String
            keyAlias = keystoreProperties.getProperty("KEY_ALIAS") as String
        }
    }

    compileSdk = Versions.ANDROID_COMPILE_SDK_VERSION
    defaultConfig {
        applicationId = "co.nimblehq.coroutine"
        minSdk = Versions.ANDROID_MIN_SDK_VERSION
        targetSdk = Versions.ANDROID_TARGET_SDK_VERSION
        versionCode = Versions.ANDROID_VERSION_CODE
        versionName = Versions.ANDROID_VERSION_NAME
    }

    buildTypes {
        getByName(BuildType.RELEASE) {
            isMinifyEnabled = true
            isDebuggable = false
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs["config"]
            buildConfigField("String", "BASE_API_URL", "\"https://jsonplaceholder.typicode.com/\"")
        }
        getByName(BuildType.DEBUG) {
            // For quickly testing build with proguard, enable this
            isMinifyEnabled = false
            buildConfigField("String", "BASE_API_URL", "\"https://jsonplaceholder.typicode.com/\"")
            /**
             * From AGP 4.2.0, Jacoco generates the report incorrectly, and the report is missing
             * some code coverage from module. On the new version of Gradle, they introduce a new
             * flag [testCoverageEnabled], we must enable this flag if using Jacoco to capture
             * coverage and creates a report in the build directory.
             * Reference: https://developer.android.com/reference/tools/gradle-api/7.1/com/android/build/api/dsl/BuildType#istestcoverageenabled
             */
            isTestCoverageEnabled = true
        }
    }

    flavorDimensions(Flavor.DIMENSIONS)
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    composeOptions {
        kotlinCompilerVersion = Versions.KOTLIN_VERSION
        kotlinCompilerExtensionVersion = Versions.COMPOSE_VERSION
    }

    buildFeatures {
        viewBinding = true
        compose = true
    }

    lintOptions {
        isCheckDependencies = true
        xmlReport = true
        xmlOutput = file("build/reports/lint/lint-result.xml")
    }

    testOptions {
        unitTests {
            // Robolectric resource processing/loading
            isIncludeAndroidResources = true
            isReturnDefaultValues = true
        }
    }
}

jacoco {
    toolVersion = Versions.JACOCO_VERSION
}

kapt {
    correctErrorTypes = true
}

dependencies {
    implementation(project(Module.DATA))
    implementation(project(Module.DOMAIN))

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation("androidx.activity:activity-ktx:${Versions.ANDROIDX_ACTIVITY_KTX_VERSION}")
    implementation("androidx.appcompat:appcompat:${Versions.ANDROIDX_SUPPORT_VERSION}")
    implementation("androidx.constraintlayout:constraintlayout:${Versions.CONSTRAINT_LAYOUT_VERSION}")
    implementation("androidx.core:core-ktx:${Versions.ANDROIDX_CORE_KTX_VERSION}")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${Versions.ANDROIDX_LIFECYCLE_VERSION}")

    implementation("androidx.compose.ui:ui:${Versions.COMPOSE_VERSION}")
    implementation("androidx.compose.ui:ui-tooling:${Versions.COMPOSE_VERSION}")
    implementation("androidx.compose.foundation:foundation:${Versions.COMPOSE_VERSION}")
    implementation("androidx.compose.material:material:${Versions.COMPOSE_VERSION}")

    implementation("androidx.navigation:navigation-fragment-ktx:${Versions.ANDROIDX_NAVIGATION_VERSION}")
    implementation("androidx.navigation:navigation-runtime-ktx:${Versions.ANDROIDX_NAVIGATION_VERSION}")
    implementation("androidx.navigation:navigation-ui-ktx:${Versions.ANDROIDX_NAVIGATION_VERSION}")

    implementation("com.google.dagger:hilt-android:${Versions.HILT_VERSION}")

    implementation("com.jakewharton.timber:timber:${Versions.TIMBER_LOG_VERSION}")

    implementation("com.github.nimblehq:android-common-ktx:${Versions.ANDROID_COMMON_KTX_VERSION}")

    implementation("org.jetbrains.kotlin:kotlin-stdlib:${Versions.KOTLIN_VERSION}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.KOTLINX_COROUTINES_VERSION}")

    kapt("com.google.dagger:hilt-compiler:${Versions.HILT_VERSION}")

    debugImplementation("androidx.fragment:fragment-testing:${Versions.ANDROIDX_FRAGMENT_VERSION}")

    // Testing
    testImplementation("io.kotest:kotest-assertions-core:${Versions.TEST_KOTEST_VERSION}")
    testImplementation("junit:junit:${Versions.TEST_JUNIT_VERSION}")
    testImplementation("org.robolectric:robolectric:${Versions.TEST_ROBOLECTRIC_VERSION}")
    testImplementation("androidx.test:core:${Versions.ANDROIDX_CORE_KTX_VERSION}")
    testImplementation("androidx.test:runner:${Versions.TEST_RUNNER_VERSION}")
    testImplementation("androidx.test:rules:${Versions.TEST_RUNNER_VERSION}")
    testImplementation("androidx.test.ext:junit-ktx:${Versions.TEST_JUNIT_ANDROIDX_EXT_VERSION}")
    testImplementation("com.google.dagger:hilt-android-testing:${Versions.HILT_VERSION}")
    testImplementation("org.jetbrains.kotlin:kotlin-reflect:${Versions.KOTLIN_REFLECT_VERSION}")
    testImplementation("io.mockk:mockk:${Versions.TEST_MOCKK_VERSION}")

    kaptTest("com.google.dagger:hilt-android-compiler:${Versions.HILT_VERSION}")
    testAnnotationProcessor("com.google.dagger:hilt-android-compiler:${Versions.HILT_VERSION}")
}
