plugins {
    alias(libs.plugins.pokemonapps.jvm.library)
}

dependencies {
    api(project(":core:domain"))
    api(libs.junit)
    api(libs.kotlinx.coroutines.test)
}
