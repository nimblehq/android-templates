plugins {
    id("java-library")
    id("kotlin")
}

dependencies {
    // noinspection DifferentStdlibGradleVersion
    implementation(KOTLIN_STDLIB_JDK7)

    api(RX_JAVA)
    api(RX_KOTLIN)
}

java {
    targetCompatibility = JavaVersion.VERSION_1_8
    sourceCompatibility = JavaVersion.VERSION_1_8
}
