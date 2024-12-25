plugins {
    alias(deps.plugins.kotlin.multiplatform)
    id("io.github.pavelannin.multiplatform")
    id("io.github.pavelannin.publish")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            compileOnly(deps.kotlin.serialization.core)
            api(project(":lce-core"))
            api(project(":result-core"))
        }
        commonTest.dependencies {
            implementation(deps.kotlin.test)
        }
    }
}

publish {
    artifactId = "monadic-lce-result"
    version = property("publish.lce-result.version").toString()
}
