plugins {
    alias(libs.plugins.pokemonapps.android.feature)
}

android {
    namespace = "com.example.feature.favorite"
}

dependencies {
    implementation(libs.androidx.lifecycle.livedata.ktx)
}
