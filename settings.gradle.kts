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

include(
    ":bom",
    ":checkable-core",
    ":either-core",
    ":function-core",
    ":identifiable-core",
    ":lce-core",
    ":lce-either",
    ":optional-core",
    ":optional-either",
    ":refreshable-core",
)
