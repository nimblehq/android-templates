plugins {
    id(Plugins.JAVA_LIBRARY)
    id("kotlin")

    id(Plugins.KOVER)
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    with(Dependencies.Kotlin) {
        implementation(COROUTINES_CORE)
    }

    with(Dependencies.Hilt) {
        implementation(JAVAX_INJECT)
    }

    with(Dependencies.Test) {
        testImplementation(COROUTINES)
        testImplementation(JUNIT)
        testImplementation(KOTEST)
        testImplementation(MOCKK)
    }
}
