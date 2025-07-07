rootProject.name = "tests-for-tests"

pluginManagement {
    plugins {
        kotlin("jvm") version "1.9.22"
    }

    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}