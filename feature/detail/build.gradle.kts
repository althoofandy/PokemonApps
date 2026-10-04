plugins {
    alias(libs.plugins.pokemonapps.android.feature)
}

android {
    namespace = "com.example.feature.detail"
}

dependencies {
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.github.glide)
}
