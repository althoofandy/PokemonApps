import com.android.build.api.dsl.LibraryExtension
import com.example.pokemonapps.buildlogic.library
import com.example.pokemonapps.buildlogic.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply("pokemonapps.android.library")

        extensions.configure<LibraryExtension> {
            buildFeatures.viewBinding = true
        }

        dependencies {
            add("implementation", project(":core:domain"))
            add("implementation", project(":core:ui"))
            add("implementation", libs.library("androidx-lifecycle-viewmodel-ktx"))
            add("implementation", libs.library("koin-android"))

            add("testImplementation", project(":core:testing"))
            add("testImplementation", libs.library("androidx-arch-core-testing"))
        }
    }
}
