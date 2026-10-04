plugins {
    alias(libs.plugins.pokemonapps.android.library)
    alias(libs.plugins.legacy.kapt)
}

android {
    namespace = "com.example.data"
}

dependencies {
    implementation(project(":core"))
    implementation(libs.androidx.paging.runtime)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    implementation(libs.koin.android)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    kapt(libs.androidx.room.compiler)
}
