plugins {
    alias(deps.plugins.kotlin.multiplatform)
    id("io.github.pavelannin.multiplatform")
    id("io.github.pavelannin.publish")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(deps.kotlin.std.common)
        }
        commonTest.dependencies {
            implementation(deps.kotlin.test)
            implementation(deps.kotlin.coroutines.test)
        }
    }
}

publish {
    artifactId = "monadic-function-core"
    version = property("publish.function-core.version").toString()
}
