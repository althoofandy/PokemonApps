plugins {
    alias(libs.plugins.pokemonapps.jvm.library)
}

dependencies {
    api(project(":core:model"))
    api(libs.androidx.paging.common)
    api(libs.kotlinx.coroutines.core)
    implementation(libs.koin.core)
}
