import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(deps.plugins.kotlin.multiplatform)
    alias(deps.plugins.maven.publish)
}

kotlin {
    explicitApi()
    jvmToolchain(8)

    jvm()
    iosArm64()
    iosX64()
    iosSimulatorArm64()

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

    tasks.withType<Jar> {
        from(rootDir.resolve("LICENSE")) {
            into("META-INF")
        }
    }
}

mavenPublishing {
    val artifactId = "monadic-lce-result"
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
