plugins {
    alias(libs.plugins.pokemonapps.android.library)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "com.example.core"
    buildFeatures.viewBinding = true
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
}
