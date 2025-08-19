plugins {
    alias(deps.plugins.kotlin.multiplatform)
    id("io.github.pavelannin.multiplatform")
    id("io.github.pavelannin.publish")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(deps.kotlin.serialization.core)
            api(projects.optionalCore)
            api(projects.resultCore)
        }
        commonTest.dependencies {
            implementation(deps.kotlin.test)
        }
    }
}

publish {
    artifactId = "monadic-optional-result"
    version = property("publish.optional-result.version").toString()
}
