plugins {
    alias(libs.plugins.pokemonapps.android.feature)
}

android {
    namespace = "com.example.feature.detail"
}

dependencies {
    implementation(libs.github.glide)
}
