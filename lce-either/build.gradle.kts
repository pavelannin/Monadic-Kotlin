plugins {
    alias(deps.plugins.kotlin.multiplatform)
    id("io.github.pavelannin.multiplatform")
    id("io.github.pavelannin.publish")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(deps.kotlin.serialization.core)
            api(projects.lceCore)
            api(projects.eitherCore)
        }
        commonTest.dependencies {
            implementation(deps.kotlin.test)
        }
    }
}

publish {
    artifactId = "monadic-lce-either"
    version = property("publish.lce-either.version").toString()
}
