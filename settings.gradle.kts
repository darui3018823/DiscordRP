pluginManagement {
    repositories {
        maven("https://packages.jetbrains.com/repositories/public")
        gradlePluginPortal()
        mavenCentral()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "DiscordRP"
