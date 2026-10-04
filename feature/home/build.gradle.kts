plugins {
    alias(libs.plugins.pokemonapps.android.library)
}

android {
    namespace = "com.example.feature.home"
    buildFeatures.viewBinding = true
}

dependencies {
    implementation(project(":core:ui"))
}
