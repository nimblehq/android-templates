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

    api("com.squareup.retrofit2:retrofit:${Versions.RETROFIT_VERSION}")
    api("com.squareup.retrofit2:adapter-rxjava2:${Versions.RETROFIT_VERSION}")
    api("com.squareup.retrofit2:converter-moshi:${Versions.RETROFIT_VERSION}")

    api("com.squareup.moshi:moshi-kotlin:${Versions.MOSHI_VERSION}")
    api("com.squareup.moshi:moshi-adapters:${Versions.MOSHI_VERSION}")

    api("com.squareup.okhttp3:okhttp:${Versions.OKHTTP_VERSION}")
    api("com.squareup.okhttp3:logging-interceptor:${Versions.OKHTTP_VERSION}")

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.KOTLIN_VERSION}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${Versions.KOTLIN_VERSION}")
    implementation("androidx.core:core-ktx:${Versions.ANDROID_CORE_VERSION}")

    testImplementation("junit:junit:${Versions.TEST_JUNIT_VERSION}")
    testImplementation("org.mockito.kotlin:mockito-kotlin:${Versions.TEST_MOCKITO_VERSION}")
    testImplementation(project(Module.DOMAIN))
}