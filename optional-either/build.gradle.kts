plugins {
    alias(deps.plugins.kotlin.multiplatform)
    id("io.github.pavelannin.multiplatform")
    id("io.github.pavelannin.publish")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            compileOnly(deps.kotlin.serialization.core)
            api(project(":optional-core"))
            api(project(":either-core"))
        }
        commonTest.dependencies {
            implementation(deps.kotlin.test)
        }
    }
}

publish {
    artifactId = "monadic-optional-either"
    version = property("publish.optional-either.version").toString()
}
