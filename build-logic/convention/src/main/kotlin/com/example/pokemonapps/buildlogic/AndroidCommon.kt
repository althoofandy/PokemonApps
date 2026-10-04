package com.example.pokemonapps.buildlogic

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion

internal const val COMPILE_SDK = 35
internal const val MIN_SDK = 24
internal const val TARGET_SDK = 34
internal val JAVA_VERSION = JavaVersion.VERSION_17

internal fun configureAndroidCommon(extension: CommonExtension) = with(extension) {
    compileSdk = COMPILE_SDK
    defaultConfig.minSdk = MIN_SDK
    compileOptions.sourceCompatibility = JAVA_VERSION
    compileOptions.targetCompatibility = JAVA_VERSION
}
