plugins {
    id(Plugins.ANDROID_LIBRARY)
    id(Plugins.KOTLIN_ANDROID)
    id(Plugins.KOVER)
}

android {
    namespace = "co.nimblehq.template.compose.data"
    compileSdk = Versions.ANDROID_COMPILE_SDK

    defaultConfig {
        minSdk = Versions.ANDROID_MIN_SDK

        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro"
            )
        }

        debug {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    lint {
        checkDependencies = true
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
        testImplementation(COROUTINES)
        testImplementation(JUNIT)
        testImplementation(KOTEST)
        testImplementation(MOCKK)
        testImplementation(ROBOLECTRIC)
        testImplementation(TEST_CORE)
        testImplementation(TURBINE)
    }
}
