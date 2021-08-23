plugins {
    id("com.android.application")

    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")

    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
}

// TODO Migrate these files to gradle.kts
apply(from = "codequality.gradle")
apply(from = "../config/jacoco.gradle")

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
        applicationId = "co.nimblehq.rxjava"
        minSdk = Versions.ANDROID_MIN_SDK_VERSION
        targetSdk = Versions.ANDROID_TARGET_SDK_VERSION
        versionCode = Versions.ANDROID_VERSION_CODE
        versionName = Versions.ANDROID_VERSION_NAME
        multiDexEnabled = true
        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = "co.nimblehq.rxjava.TestRunner"
    }

    buildTypes {
        getByName(BuildType.RELEASE) {
            isMinifyEnabled = true
            isDebuggable = false
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro"
            )
            signingConfig = signingConfigs["config"]
        }
        getByName(BuildType.DEBUG) {
            // For quickly testing build with proguard, enable this
            isMinifyEnabled = false
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

    packagingOptions {
        exclude("META-INF/DEPENDENCIES.txt")
        exclude("META-INF/LICENSE.txt")
        exclude("META-INF/NOTICE.txt")
        exclude("META-INF/NOTICE")
        exclude("META-INF/MANIFEST.MF")
        exclude("META-INF/MANIFEST.mf")
        exclude("META-INF/LICENSE")
        exclude("META-INF/DEPENDENCIES")
        exclude("META-INF/notice.txt")
        exclude("META-INF/license.txt")
        exclude("META-INF/dependencies.txt")
        exclude("META-INF/services/javax.annotation.processing.Processor")
        exclude("META-INF/rxjava.properties")
    }

    testOptions {
        unitTests {
            // Robolectric resource processing/loading
            isIncludeAndroidResources = true
            isReturnDefaultValues = true
        }
    }

    tasks.withType<Test> {
        configure<JacocoTaskExtension> {
            isIncludeNoLocationClasses = true
        }
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(Module.COMMON_RX))
    implementation(project(Module.DOMAIN))

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    kapt(ANDROIDX_LIFECYCLE_COMPILER)
    kapt(GLIDE_COMPILER)
    kapt(HILT_COMPILER)

    implementation(KOTLIN_STDLIB_JDK7)
    implementation(KOTLIN_STDLIB)

    implementation(ANDROID_MATERIAL)
    implementation(ANDROIDX_CORE)
    implementation(ANDROIDX_FRAGMENT_KTX)
    implementation(ANDROIDX_LEGACY_SUPPORT)
    implementation(ANDROIDX_APPCOMPAT)
    implementation(ANDROIDX_RECYCLERVIEW)
    implementation(ANDROIDX_CONSTRAINT_LAYOUT)
    implementation(ANDROIDX_MULTIDEX)

    implementation(ANDROIDX_NAVIGATION_RUNTIME_KTX)
    implementation(ANDROIDX_NAVIGATION_FRAGMENT_KTX)
    implementation(ANDROIDX_NAVIGATION_UI_KTX)

    implementation(ANDROIDX_LIFECYCLE_VIEWMODEL_KTX)
    implementation(ANDROIDX_LIFECYCLE_EXTENSIONS)
    implementation(ANDROIDX_LIFECYCLE_COMMON_JAVA8)
    implementation(ANDROIDX_LIFECYCLE_RUNTIME)

    implementation(RX_BINDING)
    implementation(RX_BINDING_CORE)

    implementation(HILT)
    implementation(TIMBER)
    implementation(GLIDE)
    implementation(RX_PERMISSION)

    debugImplementation(ANDROIDX_FRAGMENT_TESTING)
    debugImplementation(CHUCKER)

    releaseImplementation(CHUCKER_NO_OP)

    // Testing
    testImplementation(JUNIT)
    testImplementation(KLUENT_ANDROID)
    testImplementation(MOCKITO_KOTLIN)
    testImplementation(ROBOLECTRIC)

    androidTestImplementation(ANDROIDX_TEST_RUNNER)
    androidTestImplementation(ANDROIDX_TEST_RULES)

    androidTestImplementation(ANDROIDX_TEST_ESPRESSO_CORE)
    androidTestImplementation(ANDROIDX_TEST_ESPRESSO_INTENTS)
    androidTestImplementation(ANDROIDX_TEST_ESPRESSO_CONTRIB)
    androidTestImplementation(ANDROIDX_TEST_ESPRESSO_WEB)

    androidTestImplementation(HILT_ANDROID_TESTING)
    androidTestImplementation(MOCKITO_INLINE)

    kaptAndroidTest(HILT_ANDROID_COMPILER)
}
