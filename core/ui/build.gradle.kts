plugins {
    alias(libs.plugins.pokemonapps.android.library)
}

android {
    namespace = "com.example.core.ui"
    buildFeatures.viewBinding = true
}

dependencies {
    api(libs.androidx.core.ktx)
    api(libs.androidx.appcompat)
    api(libs.androidx.fragment.ktx)
    api(libs.androidx.lifecycle.runtime.ktx)
    api(libs.material)
    implementation(libs.github.glide)
}
