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

    sourceSets["test"].java {
        srcDir("src/test/kotlin")
    }

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_1_8
        sourceCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(project(Module.COMMON_RX))

    api(RETROFIT)
    api(RETROFIT_ADAPTER_RXJAVA2)
    api(RETROFIT_CONVERTER_MOSHI)

    api(MOSHI_KOTLIN)
    api(MOSHI_ADAPTERS)

    api(OKHTTP3)
    api(OKHTTP3_LOGGING)

    implementation(KOTLIN_STDLIB_JDK7)
    implementation(KOTLIN_STDLIB)
    implementation(ANDROIDX_CORE)

    testImplementation(JUNIT)
    testImplementation(MOCKITO_KOTLIN)
    testImplementation(project(Module.DOMAIN))
}
