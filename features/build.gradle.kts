plugins {
    alias(libs.plugins.pokemonapps.android.feature)
}

android {
    namespace = "com.example.features"
}

dependencies {
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.paging.runtime)
    implementation(libs.github.glide)
}
