plugins {
    `java-platform`
    id("io.github.pavelannin.publish")
}

javaPlatform {
    allowDependencies()
}

dependencies {
    constraints {
        api("io.github.pavelannin:monadic-checkable-core:${property("publish.checkable-core.version").toString()}")
        api("io.github.pavelannin:monadic-either-core:${property("publish.either-core.version").toString()}")
        api("io.github.pavelannin:monadic-function-core:${property("publish.function-core.version").toString()}")
        api("io.github.pavelannin:monadic-identifiable-core:${property("publish.identifiable-core.version").toString()}")
        api("io.github.pavelannin:monadic-lce-core:${property("publish.lce-core.version").toString()}")
        api("io.github.pavelannin:monadic-lce-either:${property("publish.lce-either.version").toString()}")
        api("io.github.pavelannin:monadic-lce-result:${property("publish.lce-result.version").toString()}")
        api("io.github.pavelannin:monadic-optional-core:${property("publish.optional-core.version").toString()}")
        api("io.github.pavelannin:monadic-optional-either:${property("publish.optional-either.version").toString()}")
        api("io.github.pavelannin:monadic-refreshable-core:${property("publish.refreshable-core.version").toString()}")
        api("io.github.pavelannin:monadic-result-core:${property("publish.result-core.version").toString()}")
    }
}

publish {
    artifactId = "monadic-bom"
    version = property("publish.bom.version").toString()
}
