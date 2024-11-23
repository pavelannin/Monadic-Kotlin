import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

plugins {
    alias(deps.plugins.kotlin.multiplatform)
    alias(deps.plugins.kotlin.serialization)
    alias(deps.plugins.maven.publish)
}

kotlin {
    explicitApi()

    jvm {
        withSourcesJar(true)
    }

    js(IR) {
        browser()
        nodejs()
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs()
    @OptIn(ExperimentalWasmDsl::class)
    wasmWasi()

    androidNativeArm32()
    androidNativeArm64()
    androidNativeX86()
    androidNativeX64()

    iosArm64()
    iosX64()
    iosSimulatorArm64()
    macosArm64()
    macosX64()
    tvosArm64()
    tvosX64()
    tvosSimulatorArm64()
    watchosArm32()
    watchosArm64()
    watchosX64()
    watchosDeviceArm64()
    watchosSimulatorArm64()

    linuxArm64()
    linuxX64()
    mingwX64()

    sourceSets {
        commonMain.dependencies {
            compileOnly(deps.kotlin.serialization.core)
        }
        commonTest.dependencies {
            implementation(deps.kotlin.test)
            implementation(deps.kotlin.serialization.json)
        }
    }

    tasks.withType<KotlinJvmCompile>().configureEach {
        compilerOptions { jvmTarget.set(JvmTarget.JVM_1_8) }
    }
}

mavenPublishing {
    val artifactId = "monadic-checkable-core"
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
