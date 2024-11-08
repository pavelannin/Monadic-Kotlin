import com.vanniktech.maven.publish.SonatypeHost

plugins {
    `java-platform`
    alias(deps.plugins.maven.publish)
}

javaPlatform {
    allowDependencies()
}

dependencies {
    constraints {
        api("io.github.pavelannin:monadic-either-core:0.3.3")
        api("io.github.pavelannin:monadic-function-core:0.2.0")
        api("io.github.pavelannin:monadic-lce-core:0.1.0")
        api("io.github.pavelannin:monadic-lce-either:0.1.0")
    }
}

mavenPublishing {
    val artifactId = "monadic-bom"
    publishToMavenCentral(SonatypeHost.S01, automaticRelease = true)
    signAllPublications()
    coordinates("io.github.pavelannin", artifactId, "2024.11.08")

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
