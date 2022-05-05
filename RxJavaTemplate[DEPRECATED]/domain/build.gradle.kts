plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

apply(from = "../config/jacoco.gradle.kts")

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

    sourceSets["test"].resources {
        srcDir("src/test/resources")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    lintOptions {
        xmlReport = true
        xmlOutput = file("build/reports/lint/lint-result.xml")
    }
}

dependencies {
    implementation(project(Module.COMMON_RX))
    api(project(Module.DATA))

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.KOTLIN_VERSION}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${Versions.KOTLIN_VERSION}")

    implementation("androidx.core:core-ktx:${Versions.ANDROID_CORE_VERSION}")
    implementation("androidx.security:security-crypto:${Versions.ANDROID_CRYPTO_VERSION}")

    implementation("com.google.dagger:hilt-android:${Versions.HILT_VERSION}")
    implementation("io.reactivex.rxjava2:rxandroid:${Versions.RXANDROID_VERSION}")

    testImplementation("junit:junit:${Versions.TEST_JUNIT_VERSION}")
    testImplementation("org.amshove.kluent:kluent-android:${Versions.TEST_KLUENT_VERSION}")
    testImplementation("org.mockito.kotlin:mockito-kotlin:${Versions.TEST_MOCKITO_VERSION}")
    testImplementation("org.robolectric:shadowapi:${Versions.TEST_ROBOLECTRIC_VERSION}")
}
