plugins {
    alias(deps.plugins.kotlin.multiplatform) apply false
    alias(deps.plugins.kotlin.serialization) apply false
    alias(deps.plugins.kotlin.benchmark) apply false
    alias(deps.plugins.kotlin.jvm.allopen) apply false
}
