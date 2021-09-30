plugins {
    id("java-library")
    id("kotlin")
}

dependencies {
    // noinspection DifferentStdlibGradleVersion
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.KOTLIN_VERSION}")

    api("io.reactivex.rxjava2:rxjava:${Versions.RXJAVA_VERSION}")
    api("io.reactivex.rxjava2:rxkotlin:${Versions.RXKOTLIN_VERSION}")
}

java {
    targetCompatibility = JavaVersion.VERSION_1_8
    sourceCompatibility = JavaVersion.VERSION_1_8
}
