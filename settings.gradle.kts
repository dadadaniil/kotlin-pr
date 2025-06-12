pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
    versionCatalogs {
        create("libs") {
            // Make sure we use a compatible version of kotlinx.serialization
            version("kotlinxSerialization", "1.5.1")
            // Make sure we use a compatible version of javapoet
            library("javapoet", "com.squareup", "javapoet").version("1.13.0")
        }
    }
}

rootProject.name = "Network"
include(":app")
