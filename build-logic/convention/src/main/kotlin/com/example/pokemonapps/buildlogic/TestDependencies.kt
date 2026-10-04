package com.example.pokemonapps.buildlogic

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.addUnitTestDependencies() {
    dependencies {
        add("testImplementation", libs.library("junit"))
        add("testImplementation", libs.library("kotlinx-coroutines-test"))
    }
}
