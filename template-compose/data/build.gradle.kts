plugins {
    id(Plugins.ANDROID_LIBRARY)
    id("kotlin-android")

    id(Plugins.KOVER)
}

android {
    compileSdk = Versions.ANDROID_COMPILE_SDK_VERSION
    defaultConfig {
        minSdk = Versions.ANDROID_MIN_SDK_VERSION
        targetSdk = Versions.ANDROID_TARGET_SDK_VERSION

        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName(BuildTypes.RELEASE) {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro"
            )
        }

        getByName(BuildTypes.DEBUG) {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_11
        sourceCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    lintOptions {
        isCheckDependencies = true
        xmlReport = true
        xmlOutput = file("build/reports/lint/lint-result.xml")
    }
}

dependencies {
    implementation(project(Modules.DOMAIN))

    with(Dependencies.AndroidX) {
        implementation(CORE_KTX)
        implementation(DATASTORE_PREFERENCES)
        implementation(SECURITY_CRYPTO)
    }

    with(Dependencies.Hilt) {
        implementation(JAVAX_INJECT)
    }

    with(Dependencies.Network) {
        api(RETROFIT)
        api(RETROFIT_CONVERTER_MOSHI)

        api(OKHTTP)
        api(OKHTTP_LOGGING_INTERCEPTOR)

        api(MOSHI)
        implementation(MOSHI_ADAPTERS)
        implementation(MOSHI_KOTLIN)
    }

    with(Dependencies.Test) {
        testImplementation(ANDROIX_TEST_CORE)
        testImplementation(COROUTINES)
        testImplementation(JUNIT)
        testImplementation(KOTEST)
        testImplementation(MOCKK)
        testImplementation(ROBOLECTRIC)
        testImplementation(TURBINE)
    }
}
