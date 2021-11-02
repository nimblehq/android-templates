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
            /**
             * From AGP 4.2, JDK 11 is now bundled, but Jacoco is running on JDK 8. It causes the
             * build failed because of the missing of some classes that do not exist on JDK 8 but
             * JDK 11. We need to exclude that classes temporarily until Jacoco supports running
             * on JDK 11.
             * Android Gradle Plugin 4.2.0 release note: https://developer.android.com/studio/releases#4.2-bundled-jdk-11
             * Reference: https://stackoverflow.com/a/68739364/5187859
             */
            excludes = listOf("jdk.internal.*")
        }
    }

    buildFeatures {
        viewBinding = true
    }

    lintOptions {
        xmlReport = true
        xmlOutput = file("build/reports/lint/lint-result.xml")
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    implementation(project(Module.COMMON_RX))
    implementation(project(Module.DOMAIN))

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    kapt("androidx.lifecycle:lifecycle-compiler:${Versions.ANDROID_LIFECYCLE_VERSION}")
    kapt("com.github.bumptech.glide:compiler:${Versions.GLIDE_VERSION}")
    kapt("com.google.dagger:hilt-compiler:${Versions.HILT_VERSION}")

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.KOTLIN_VERSION}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${Versions.KOTLIN_VERSION}")

    implementation("com.google.android.material:material:${Versions.ANDROID_MATERIAL_VERSION}")
    implementation("androidx.core:core-ktx:${Versions.ANDROID_CORE_VERSION}")
    implementation("androidx.fragment:fragment-ktx:${Versions.ANDROID_FRAGMENT_VERSION}")
    implementation("androidx.legacy:legacy-support-v4:${Versions.ANDROID_LEGACY_SUPPORT_VERSION}")
    implementation("androidx.appcompat:appcompat:${Versions.ANDROIDX_SUPPORT_VERSION}")
    implementation("androidx.recyclerview:recyclerview:${Versions.ANDROIDX_SUPPORT_VERSION}")
    implementation("androidx.constraintlayout:constraintlayout:${Versions.CONSTRAINT_LAYOUT_VERSION}")
    implementation("androidx.multidex:multidex:${Versions.MULTIDEX_VERSION}")

    implementation("androidx.navigation:navigation-runtime-ktx:${Versions.NAVIGATION_VERSION}")
    implementation("androidx.navigation:navigation-fragment-ktx:${Versions.NAVIGATION_VERSION}")
    implementation("androidx.navigation:navigation-ui-ktx:${Versions.NAVIGATION_VERSION}")

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.ANDROID_LIFECYCLE_VERSION}")
    implementation("androidx.lifecycle:lifecycle-extensions:${Versions.ANDROID_LIFECYCLE_VERSION}")
    implementation("androidx.lifecycle:lifecycle-common-java8:${Versions.ANDROID_LIFECYCLE_VERSION}")
    implementation("androidx.lifecycle:lifecycle-runtime:${Versions.ANDROID_LIFECYCLE_VERSION}")

    implementation("com.jakewharton.rxbinding3:rxbinding:${Versions.RXBINDING_VERSION}")
    implementation("com.jakewharton.rxbinding3:rxbinding-core:${Versions.RXBINDING_VERSION}")

    implementation("com.google.dagger:hilt-android:${Versions.HILT_VERSION}")
    implementation("com.jakewharton.timber:timber:${Versions.TIMBER_LOG_VERSION}")
    implementation("com.github.bumptech.glide:glide:${Versions.GLIDE_VERSION}")
    implementation("com.github.tbruyelle:rxpermissions:${Versions.RXPERMISSION_VERSION}")

    debugImplementation("androidx.fragment:fragment-testing:${Versions.ANDROID_FRAGMENT_VERSION}")
    debugImplementation("com.github.chuckerteam.chucker:library:${Versions.CHUCKER_INTERCEPTOR_VERSION}")

    releaseImplementation("com.github.chuckerteam.chucker:library-no-op:${Versions.CHUCKER_INTERCEPTOR_VERSION}")

    // Testing
    testImplementation("junit:junit:${Versions.TEST_JUNIT_VERSION}")
    testImplementation("org.amshove.kluent:kluent-android:${Versions.TEST_KLUENT_VERSION}")
    testImplementation("org.mockito.kotlin:mockito-kotlin:${Versions.TEST_MOCKITO_VERSION}")
    testImplementation("org.robolectric:shadowapi:${Versions.TEST_ROBOLECTRIC_VERSION}")
    testImplementation("org.robolectric:robolectric:${Versions.TEST_ROBOLECTRIC_VERSION}")
    testImplementation("androidx.test:core:${Versions.ANDROID_CORE_VERSION}")
    testImplementation("androidx.test:runner:${Versions.TEST_RUNNER_VERSION}")
    testImplementation("androidx.test:rules:${Versions.TEST_RUNNER_VERSION}")
    testImplementation("androidx.test.ext:junit-ktx:${Versions.TEST_JUNIT_ANDROIDX_EXT_VERSION}")
    testImplementation("com.google.dagger:hilt-android-testing:${Versions.HILT_VERSION}")
    testImplementation("org.jetbrains.kotlin:kotlin-reflect:${Versions.KOTLIN_REFLECT_VERSION}")
    testImplementation("io.mockk:mockk:${Versions.TEST_MOCKK_VERSION}")

    kaptTest("com.google.dagger:hilt-android-compiler:${Versions.HILT_VERSION}")
    testAnnotationProcessor("com.google.dagger:hilt-android-compiler:${Versions.HILT_VERSION}")

    androidTestImplementation("androidx.test:runner:${Versions.ANDROID_TEST_VERSION}")
    androidTestImplementation("androidx.test:rules:${Versions.ANDROID_TEST_VERSION}")

    androidTestImplementation("androidx.test.espresso:espresso-core:${Versions.TEST_ESPRESSO_VERSION}")
    androidTestImplementation("androidx.test.espresso:espresso-intents:${Versions.TEST_ESPRESSO_VERSION}")
    androidTestImplementation("androidx.test.espresso:espresso-contrib:${Versions.TEST_ESPRESSO_VERSION}")
    androidTestImplementation("androidx.test.espresso:espresso-web:${Versions.TEST_ESPRESSO_VERSION}")

    androidTestImplementation("com.google.dagger:hilt-android-testing:${Versions.HILT_VERSION}")
    androidTestImplementation("com.linkedin.dexmaker:dexmaker-mockito-inline:${Versions.TEST_MOCKITO_DEXMAKER_VERSION}")

    kaptAndroidTest("com.google.dagger:hilt-android-compiler:${Versions.HILT_VERSION}")
}
