plugins {
    id("java-library")
    id("kotlin")

    jacoco
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation("javax.inject:javax.inject:${Versions.JAVAX_INJECT_VERSION}")

    // Testing
    testImplementation("junit:junit:${Versions.TEST_JUNIT_VERSION}")
    testImplementation("io.mockk:mockk:${Versions.TEST_MOCKK_VERSION}")
    testImplementation("io.kotest:kotest-assertions-core:${Versions.TEST_KOTEST_VERSION}")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.KOTLINX_COROUTINES_VERSION}")
}
