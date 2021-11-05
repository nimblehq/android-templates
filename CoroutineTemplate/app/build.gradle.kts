plugins {
    id("com.android.application")

    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")

    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
}

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
        }
    }

    flavorDimensions(Flavor.DIMENSIONS)
    productFlavors {
        create(Flavor.STAGING) {
            applicationIdSuffix = ".staging"
        }

        create(Flavor.PRODUCTION) {}
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

    implementation("org.jetbrains.kotlin:kotlin-stdlib:${Versions.KOTLIN_VERSION}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.KOTLINX_COROUTINES_VERSION}")

    kapt("com.google.dagger:hilt-compiler:${Versions.HILT_VERSION}")
}
