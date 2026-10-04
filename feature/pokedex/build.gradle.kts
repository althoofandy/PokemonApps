plugins {
    alias(libs.plugins.pokemonapps.android.feature)
}

android {
    namespace = "com.example.feature.pokedex"
}

dependencies {
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.paging.runtime)
}
