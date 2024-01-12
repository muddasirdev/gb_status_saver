import java.util.Properties
import java.io.FileInputStream
import java.io.File

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {

    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        jcenter()

        maven {
            name = "GitHubPackages"

            url = uri("https://maven.pkg.github.com/Cuberto/liquid-swipe-android")
            credentials {
                username = "muddasirdev"
                password = "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"
            }
        }
    }
}

rootProject.name = "Status Saver"
include(":app")
 