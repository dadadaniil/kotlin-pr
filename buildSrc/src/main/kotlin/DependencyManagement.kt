import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

/**
 * Custom dependency management to handle version conflicts
 */
fun Project.applyDependencyManagement() {
    configurations.all {
        resolutionStrategy {
            force("com.squareup:javapoet:1.13.0")
            force("org.jetbrains.kotlinx:kotlinx-serialization-core:1.5.1")
            force("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
        }
    }
} 