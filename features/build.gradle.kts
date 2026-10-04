plugins {
    alias(libs.plugins.pokemonapps.android.library)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "com.example.features"
    buildFeatures.viewBinding = true
}

dependencies {
    implementation(project(":core"))
    implementation(project(":data"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.koin.android)
    implementation(libs.github.glide)
}
