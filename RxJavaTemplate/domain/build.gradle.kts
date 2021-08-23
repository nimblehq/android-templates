plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

apply(from = "../config/jacoco.gradle")

android {
    compileSdk = Versions.ANDROID_COMPILE_SDK_VERSION
    defaultConfig {
        minSdk = Versions.ANDROID_MIN_SDK_VERSION
        targetSdk = Versions.ANDROID_TARGET_SDK_VERSION
        versionCode = Versions.ANDROID_VERSION_CODE
        versionName = Versions.ANDROID_VERSION_NAME
    }

    flavorDimensions(Flavor.DIMENSIONS)
    productFlavors {
        create(Flavor.STAGING) {
            dimension = Flavor.DIMENSIONS
        }

        create(Flavor.PRODUCTION) {
            dimension = Flavor.DIMENSIONS
        }
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

    sourceSets["test"].resources {
        srcDir("src/test/resources")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(project(Module.COMMON_RX))
    api(project(Module.DATA))

    implementation(KOTLIN_STDLIB_JDK7)
    implementation(KOTLIN_STDLIB)

    implementation(ANDROIDX_CORE)
    implementation(ANDROID_SECURITY_CRYPTO)

    implementation(HILT)
    implementation(RX_ANDROID)

    testImplementation(JUNIT)
    testImplementation(KLUENT_ANDROID)
    testImplementation(MOCKITO_KOTLIN)
    testImplementation(ROBOLECTRIC)
}
