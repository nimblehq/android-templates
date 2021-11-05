plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    compileSdk = Versions.ANDROID_COMPILE_SDK_VERSION
    defaultConfig {
        minSdk = Versions.ANDROID_MIN_SDK_VERSION
        targetSdk = Versions.ANDROID_TARGET_SDK_VERSION
        versionCode = Versions.ANDROID_VERSION_CODE
        versionName = Versions.ANDROID_VERSION_NAME

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName(BuildType.RELEASE) {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_1_8
        sourceCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    lintOptions {
        isCheckDependencies = true
        xmlReport = true
        xmlOutput = file("build/reports/lint/lint-result.xml")
    }
}

dependencies {
    implementation(project(Module.DOMAIN))

    implementation("androidx.core:core-ktx:${Versions.ANDROIDX_CORE_KTX_VERSION}")
    implementation("androidx.security:security-crypto:${Versions.ANDROID_CRYPTO_VERSION}")
    implementation("com.google.dagger:hilt-android:${Versions.HILT_VERSION}")
    implementation("com.squareup.moshi:moshi:${Versions.MOSHI_VERSION}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${Versions.KOTLIN_VERSION}")
    implementation("javax.inject:javax.inject:${Versions.JAVAX_INJECT_VERSION}")

    api("com.squareup.retrofit2:converter-moshi:${Versions.RETROFIT_VERSION}")
    api("com.squareup.retrofit2:retrofit:${Versions.RETROFIT_VERSION}")

    api("com.squareup.moshi:moshi-adapters:${Versions.MOSHI_VERSION}")
    api("com.squareup.moshi:moshi-kotlin:${Versions.MOSHI_VERSION}")

    api("com.squareup.okhttp3:okhttp:${Versions.OKHTTP_VERSION}")
    api("com.squareup.okhttp3:logging-interceptor:${Versions.OKHTTP_VERSION}")
}
