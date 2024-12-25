plugins {
    alias(deps.plugins.kotlin.multiplatform)
    alias(deps.plugins.kotlin.serialization)
    id("io.github.pavelannin.multiplatform")
    id("io.github.pavelannin.publish")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            compileOnly(deps.kotlin.serialization.core)
        }
        commonTest.dependencies {
            implementation(deps.kotlin.test)
            implementation(deps.kotlin.serialization.json)
        }
    }
}

publish {
    artifactId = "monadic-result-core"
    version = property("publish.result-core.version").toString()
}
