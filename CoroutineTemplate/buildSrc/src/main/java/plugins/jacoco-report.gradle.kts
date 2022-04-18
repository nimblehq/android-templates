package plugins

plugins {
    jacoco
}

jacoco {
    toolVersion = Versions.JACOCO_VERSION
}

val fileGenerated = setOf(
    "**/R.class",
    "**/R\$*.class",
    "**/*\$ViewBinder*.*",
    "**/*\$InjectAdapter*.*",
    "**/*Injector*.*",
    "**/BuildConfig.*",
    "**/Manifest*.*",
    "**/*_ViewBinding*.*",
    "**/*Adapter*.*",
    "**/*Test*.*",
    // Enum
    "**/*\$Creator*",
    // Nav Component
    "**/*_Factory*",
    "**/*FragmentArgs*",
    "**/*FragmentDirections*",
    "**/FragmentNavArgsLazy.kt",
    "**/*Fragment*navArgs*",
    "**/*ModuleDeps*.*",
    "**/*NavGraphDirections*",
    // Hilt
    "**/*_ComponentTreeDeps*",
    "**/*_HiltComponents*",
    "**/*_HiltModules*",
    "**/Hilt_*"
)

val packagesExcluded = setOf(
    "**/com/bumptech/glide",
    "**/dagger/hilt/internal",
    "**/hilt_aggregated_deps",
    "**/co/nimblehq/coroutine/databinding/**",
    "**/co/nimblehq/coroutine/di/**"
)

val fileFilter = fileGenerated + packagesExcluded

val classDirectoriesTree = files(
    fileTree(project.rootDir) {
        include(
            "**/app/build/intermediates/javac/stagingDebug/classes/**",
            "**/data/build/intermediates/javac/debug/classes/**",
            "**/app/build/tmp/kotlin-classes/stagingDebug/**",
            "**/data/build/tmp/kotlin-classes/debug/**",
            "**/domain/build/classes/kotlin/main/**"
        )
        exclude(fileFilter)
    }
)

val sourceDirectoriesTree = files(
    listOf(
        "${project.rootDir}/app/src/main/java",
        "${project.rootDir}/data/src/main/java",
        "${project.rootDir}/domain/src/main/java"
    )
)

/**
 * Once enabled [testCoverageEnabled], Jacoco will capture the coverage and store them in
 * [${project.module}/jacoco.exec]. We need to add all [jacoco.exec] to here.
 * [${project.module}/build/jacoco/testFlavorDebugTest.exec] won't have the result anymore, so we
 * can safety get rid of them.
 * Reference: https://stackoverflow.com/a/67626100/5187859
 * Issue tracker 1: https://issuetracker.google.com/issues/171125857#comment20
 * Issue tracker 2: https://issuetracker.google.com/issues/195860510
 */
val executionDataTree = fileTree(project.rootDir) {
    include(
        "app/jacoco.exec",
        "data/jacoco.exec",
        "domain/build/jacoco/test.exec"
    )
}

tasks.register<JacocoReport>("jacocoTestReport") {
    group = "Reporting"
    description = "Generate Jacoco coverage reports for Debug build"

    dependsOn(
        ":app:testStagingDebugUnitTest",
        ":data:testDebugUnitTest",
        ":domain:test"
    )

    classDirectories.setFrom(classDirectoriesTree)
    sourceDirectories.setFrom(sourceDirectoriesTree)
    executionData.setFrom(executionDataTree)

    reports {
        xml.isEnabled = true
        html.isEnabled = true
        csv.isEnabled = false
    }
}

tasks.withType<Test> {
    configure<JacocoTaskExtension> {
        isIncludeNoLocationClasses = true
        /*
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

/**
 * Workaround to bypass "Caused by: java.lang.IllegalStateException:
 * Cannot process instrumented class...
 * Please supply original non-instrumented classes." issue.
 *
 * Application projects that depend on variants of libraries that have test coverage enabled will
 * still not work as app code should be instrumented on the fly, while library code should not be.
 * https://issuetracker.google.com/issues/171125857#comment26
 */
tasks.withType<Test>().configureEach {
    configure<JacocoTaskExtension> {
        includes = listOf("com.application.*") // include only application classes
    }
}

tasks.withType<Test> {
    testLogging {
        events("passed", "skipped", "failed")
    }
}
