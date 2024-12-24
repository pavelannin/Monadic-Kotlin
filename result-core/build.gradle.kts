import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(deps.plugins.android.library)
    alias(deps.plugins.kotlin.multiplatform)
    alias(deps.plugins.kotlin.serialization)
    alias(deps.plugins.maven.publish)
}

android {
    namespace = "io.github.pavelannin"
    compileSdk = 34
}

kotlin {
    explicitApi()

    jvmToolchain(8)

    androidTarget()
    jvm()
    iosArm64()
    iosX64()
    iosSimulatorArm64()

    sourceSets {
        all {
            languageSettings.apply {
                optIn("kotlin.contracts.ExperimentalContracts")
            }
        }
        commonMain.dependencies {
            compileOnly(deps.kotlin.serialization.core)
        }
        commonTest.dependencies {
            implementation(deps.kotlin.test)
            implementation(deps.kotlin.serialization.json)
        }
    }

    tasks.withType<Jar> {
        from(rootDir.resolve("LICENSE")) {
            into("META-INF")
        }
    }
}

mavenPublishing {
    val artifactId = "monadic-result-core"
    publishToMavenCentral(SonatypeHost.S01, automaticRelease = true)
    signAllPublications()
    coordinates("io.github.pavelannin", artifactId, "0.1.0")
    pom {
        name.set(artifactId)
        description.set("Monadic is a distributed multiplatform Kotlin framework that provides a way to write code from functional programming.")
        url.set("https://github.com/pavelannin/Monadic-Kotlin")
        licenses {
            license {
                name.set("Apache-2.0")
                url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }
        developers {
            developer {
                name.set("Pavel Annin")
                email.set("pavelannin.dev@gmail.com")
            }
        }
        scm {
            connection.set("scm:git:github.com/pavelannin/Monadic-Kotlin.git")
            developerConnection.set("scm:git:ssh://github.com/pavelannin/Monadic-Kotlin.git")
            url.set("https://github.com/pavelannin/Monadic-Kotlin/tree/main")
        }
    }
}
