object Dependencies {
    object AndroidX {
        const val CORE_KTX = "androidx.core:core-ktx:${Versions.CORE_KTX}"
        const val LIFECYCLE_RUNTIME_KTX = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.LIFECYCLE}"
        const val LIFECYCLE_RUNTIME_COMPOSE = "androidx.lifecycle:lifecycle-runtime-compose:${Versions.LIFECYCLE}"

        const val DATASTORE_PREFERENCES = "androidx.datastore:datastore-preferences:${Versions.DATASTORE_PREFERENCES}"
        const val SECURITY_CRYPTO = "androidx.security:security-crypto:${Versions.SECURITY_CRYPTO}"
    }

    object Compose {
        const val BOM = "androidx.compose:compose-bom:${Versions.COMPOSE_BOM}"
        const val UI = "androidx.compose.ui:ui"
        const val UI_GRAPHICS = "androidx.compose.ui:ui-graphics"
        const val UI_TOOLING = "androidx.compose.ui:ui-tooling"
        const val MATERIAL = "androidx.compose.material:material"
        const val NAVIGATION = "androidx.navigation:navigation-compose:${Versions.COMPOSE_NAVIGATION}"

        const val ACCOMPANIST_PERMISSIONS = "com.google.accompanist:accompanist-permissions:${Versions.ACCOMPANIST}"
    }

    object Hilt {
        const val ANDROID = "com.google.dagger:hilt-android:${Versions.HILT}"
        const val NAVIGATION_COMPOSE = "androidx.hilt:hilt-navigation-compose:${Versions.HILT_NAVIGATION_COMPOSE}"
        const val COMPILER = "com.google.dagger:hilt-compiler:${Versions.HILT}"

        const val JAVAX_INJECT = "javax.inject:javax.inject:${Versions.JAVAX_INJECT}"
    }

    object Kotlin {
        const val COROUTINES_CORE = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.KOTLIN_COROUTINES}"
    }

    object Log {
        const val TIMBER = "com.jakewharton.timber:timber:${Versions.TIMBER}"

        const val CHUCKER = "com.github.chuckerteam.chucker:library:${Versions.CHUCKER}"
        const val CHUCKER_NO_OP = "com.github.chuckerteam.chucker:library-no-op:${Versions.CHUCKER}"
    }

    object Network {
        const val RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}"
        const val RETROFIT_CONVERTER_MOSHI = "com.squareup.retrofit2:converter-moshi:${Versions.RETROFIT}"

        const val OKHTTP = "com.squareup.okhttp3:okhttp:${Versions.OKHTTP}"
        const val OKHTTP_LOGGING_INTERCEPTOR = "com.squareup.okhttp3:logging-interceptor:${Versions.OKHTTP}"

        const val MOSHI = "com.squareup.moshi:moshi:${Versions.MOSHI}"
        const val MOSHI_ADAPTERS = "com.squareup.moshi:moshi-adapters:${Versions.MOSHI}"
        const val MOSHI_KOTLIN = "com.squareup.moshi:moshi-kotlin:${Versions.MOSHI}"
    }

    object Util {
        const val COMMON_KTX = "com.github.nimblehq:android-common-ktx:${Versions.COMMON_KTX}"
    }

    object Test {
        const val COMPOSE_UI_TEST_JUNIT = "androidx.compose.ui:ui-test-junit4"
        const val COROUTINES = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.KOTLIN_COROUTINES}"

        const val JUNIT = "junit:junit:${Versions.JUNIT}"

        const val KOTEST = "io.kotest:kotest-assertions-core:${Versions.KOTEST}"
        const val MOCKK = "io.mockk:mockk:${Versions.MOCKK}"

        const val ROBOLECTRIC = "org.robolectric:robolectric:${Versions.ROBOLECTRIC}"

        const val TEST_CORE = "androidx.test:core:${Versions.CORE}"
        const val TURBINE = "app.cash.turbine:turbine:${Versions.TURBINE}"
    }
}
