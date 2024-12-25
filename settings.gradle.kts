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

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.9.0"
}

@Suppress(names = ["UnstableApiUsage"])
dependencyResolutionManagement {
    versionCatalogs {
        create("deps") {
            from(files("dependencies.versions.toml"))
        }
    }
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Monadic"
includeBuild("plugin-build")
include(
    ":bom",
    ":checkable-core",
    ":either-core",
    ":function-core",
    ":identifiable-core",
    ":lce-core",
    ":lce-either",
    ":lce-result",
    ":optional-core",
    ":optional-either",
    ":refreshable-core",
    ":result-core",
)
