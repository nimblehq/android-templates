// Libraries (Alphabet sorted)
// TODO Refactor by creating Dependencies.kt to grouping libraries
// https://github.com/android/compose-samples/blob/main/Jetsnack/buildSrc/src/main/java/com/example/jetsnack/buildsrc/Dependencies.kt
const val ANDROID_MATERIAL =
    "com.google.android.material:material:${Versions.ANDROID_MATERIAL_VERSION}"
const val ANDROID_SECURITY_CRYPTO =
    "androidx.security:security-crypto:${Versions.ANDROID_CRYPTO_VERSION}"

const val ANDROIDX_APPCOMPAT = "androidx.appcompat:appcompat:${Versions.ANDROIDX_SUPPORT_VERSION}"
const val ANDROIDX_CONSTRAINT_LAYOUT =
    "androidx.constraintlayout:constraintlayout:${Versions.CONSTRAINT_LAYOUT_VERSION}"
const val ANDROIDX_CORE = "androidx.core:core-ktx:${Versions.ANDROID_CORE_VERSION}"
const val ANDROIDX_FRAGMENT_KTX =
    "androidx.fragment:fragment-ktx:${Versions.ANDROID_FRAGMENT_VERSION}"
const val ANDROIDX_LEGACY_SUPPORT =
    "androidx.legacy:legacy-support-v4:${Versions.ANDROID_LEGACY_SUPPORT_VERSION}"

const val ANDROIDX_LIFECYCLE_VIEWMODEL_KTX =
    "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.ANDROID_LIFECYCLE_VERSION}"
const val ANDROIDX_LIFECYCLE_EXTENSIONS =
    "androidx.lifecycle:lifecycle-extensions:${Versions.ANDROID_LIFECYCLE_VERSION}"
const val ANDROIDX_LIFECYCLE_COMMON_JAVA8 =
    "androidx.lifecycle:lifecycle-common-java8:${Versions.ANDROID_LIFECYCLE_VERSION}"
const val ANDROIDX_LIFECYCLE_RUNTIME =
    "androidx.lifecycle:lifecycle-runtime:${Versions.ANDROID_LIFECYCLE_VERSION}"
const val ANDROIDX_LIFECYCLE_COMPILER =
    "androidx.lifecycle:lifecycle-compiler:${Versions.ANDROID_LIFECYCLE_VERSION}"

const val ANDROIDX_MULTIDEX = "androidx.multidex:multidex:${Versions.MULTIDEX_VERSION}"

const val ANDROIDX_NAVIGATION_RUNTIME_KTX =
    "androidx.navigation:navigation-runtime-ktx:${Versions.NAVIGATION_VERSION}"
const val ANDROIDX_NAVIGATION_FRAGMENT_KTX =
    "androidx.navigation:navigation-fragment-ktx:${Versions.NAVIGATION_VERSION}"
const val ANDROIDX_NAVIGATION_UI_KTX =
    "androidx.navigation:navigation-ui-ktx:${Versions.NAVIGATION_VERSION}"

const val ANDROIDX_RECYCLERVIEW =
    "androidx.recyclerview:recyclerview:${Versions.ANDROIDX_SUPPORT_VERSION}"

const val CHUCKER = "com.github.chuckerteam.chucker:library:${Versions.CHUCKER_INTERCEPTOR_VERSION}"
const val CHUCKER_NO_OP =
    "com.github.chuckerteam.chucker:library-no-op:${Versions.CHUCKER_INTERCEPTOR_VERSION}"

const val GLIDE = "com.github.bumptech.glide:glide:${Versions.GLIDE_VERSION}"
const val GLIDE_COMPILER = "com.github.bumptech.glide:compiler:${Versions.GLIDE_VERSION}"

const val HILT = "com.google.dagger:hilt-android:${Versions.HILT_VERSION}"
const val HILT_COMPILER = "com.google.dagger:hilt-compiler:${Versions.HILT_VERSION}"
const val HILT_ANDROID_COMPILER = "com.google.dagger:hilt-android-compiler:${Versions.HILT_VERSION}"

const val KOTLIN_STDLIB_JDK7 = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.KOTLIN_VERSION}"
const val KOTLIN_STDLIB = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.KOTLIN_VERSION}"

const val MOSHI_KOTLIN = "com.squareup.moshi:moshi-kotlin:${Versions.MOSHI_VERSION}"
const val MOSHI_ADAPTERS = "com.squareup.moshi:moshi-adapters:${Versions.MOSHI_VERSION}"

const val OKHTTP3 = "com.squareup.okhttp3:okhttp:${Versions.OKHTTP_VERSION}"
const val OKHTTP3_LOGGING = "com.squareup.okhttp3:logging-interceptor:${Versions.OKHTTP_VERSION}"

const val RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT_VERSION}"
const val RETROFIT_ADAPTER_RXJAVA2 =
    "com.squareup.retrofit2:adapter-rxjava2:${Versions.RETROFIT_VERSION}"
const val RETROFIT_CONVERTER_MOSHI =
    "com.squareup.retrofit2:converter-moshi:${Versions.RETROFIT_VERSION}"

const val RX_ANDROID = "io.reactivex.rxjava2:rxandroid:${Versions.RXANDROID_VERSION}"

const val RX_BINDING = "com.jakewharton.rxbinding3:rxbinding:${Versions.RXBINDING_VERSION}"
const val RX_BINDING_CORE =
    "com.jakewharton.rxbinding3:rxbinding-core:${Versions.RXBINDING_VERSION}"

const val RX_JAVA = "io.reactivex.rxjava2:rxjava:${Versions.RXJAVA_VERSION}"
const val RX_KOTLIN = "io.reactivex.rxjava2:rxkotlin:${Versions.RXKOTLIN_VERSION}"
const val RX_PERMISSION = "com.github.tbruyelle:rxpermissions:${Versions.RXPERMISSION_VERSION}"

const val TIMBER = "com.jakewharton.timber:timber:${Versions.TIMBER_LOG_VERSION}"

// Testing
const val ANDROIDX_FRAGMENT_TESTING =
    "androidx.fragment:fragment-testing:${Versions.ANDROID_FRAGMENT_VERSION}"

const val ANDROIDX_TEST_RUNNER = "androidx.test:runner:${Versions.ANDROID_TEST_VERSION}"
const val ANDROIDX_TEST_RULES = "androidx.test:rules:${Versions.ANDROID_TEST_VERSION}"

const val ANDROIDX_TEST_ESPRESSO_CORE =
    "androidx.test.espresso:espresso-core:${Versions.TEST_ESPRESSO_VERSION}"
const val ANDROIDX_TEST_ESPRESSO_INTENTS =
    "androidx.test.espresso:espresso-intents:${Versions.TEST_ESPRESSO_VERSION}"
const val ANDROIDX_TEST_ESPRESSO_CONTRIB =
    "androidx.test.espresso:espresso-contrib:${Versions.TEST_ESPRESSO_VERSION}"
const val ANDROIDX_TEST_ESPRESSO_WEB =
    "androidx.test.espresso:espresso-web:${Versions.TEST_ESPRESSO_VERSION}"

const val HILT_ANDROID_TESTING = "com.google.dagger:hilt-android-testing:${Versions.HILT_VERSION}"

const val JUNIT = "junit:junit:${Versions.TEST_JUNIT_VERSION}"
const val KLUENT_ANDROID = "org.amshove.kluent:kluent-android:${Versions.TEST_KLUENT_VERSION}"

const val MOCKITO_INLINE =
    "com.linkedin.dexmaker:dexmaker-mockito-inline:${Versions.TEST_MOCKITO_DEXMAKER_VERSION}"
const val MOCKITO_KOTLIN = "org.mockito.kotlin:mockito-kotlin:${Versions.TEST_MOCKITO_VERSION}"

const val ROBOLECTRIC = "org.robolectric:shadowapi:${Versions.TEST_ROBOLECTRIC_VERSION}"
