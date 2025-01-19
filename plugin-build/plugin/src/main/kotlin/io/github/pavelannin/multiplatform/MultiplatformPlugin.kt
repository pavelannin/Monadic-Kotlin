package io.github.pavelannin.multiplatform

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

class MultiplatformPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target.multiplatformExtension) {
            explicitApi()
            jvmToolchain(8)

            jvm()

            iosArm64()
            iosX64()
            iosSimulatorArm64()

            sourceSets.all {
                languageSettings.optIn("kotlin.contracts.ExperimentalContracts")
                languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
            }
        }
        target.tasks.withType<Jar> {
            from(target.rootDir.resolve("LICENSE")) { into("META-INF") }
        }
    }
}

internal val Project.multiplatformExtension: KotlinMultiplatformExtension
    get() = kotlinExtension as KotlinMultiplatformExtension

