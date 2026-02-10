import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.preReleaseImplementation(dependencyNotation: Any): Dependency? =
    add("preReleaseImplementation", dependencyNotation)
