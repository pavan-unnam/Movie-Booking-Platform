rootProject.name = "Movie-Booking-Platform"

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
        gradlePluginPortal()
    }

    versionCatalogs {
        create("libs") {
            // This block is implicitly linked to the libs.versions.toml file
        }
    }
}

include("shared-domain")
include("common-web")
include("theatre-service")
include("payment-service")