@Suppress(names = ["UnstableApiUsage"])
dependencyResolutionManagement {
    versionCatalogs {
        create("deps") {
            from(files("../dependencies.versions.toml"))
        }
    }
}

include(":plugin")
