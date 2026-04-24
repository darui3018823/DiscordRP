plugins {
    id("org.jetbrains.intellij.platform") version "2.15.0"
    kotlin("jvm") version "2.3.20"
    id("io.gitlab.arturbosch.detekt") version "1.23.7"
}

group = "com.github.darui3018823"
version = "2.0.1"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
    intellijPlatform {
        defaultRepositories()
    }
}

dependencies {
    intellijPlatform {
        intellijIdeaCommunity("2024.3.5")
        bundledPlugin("Git4Idea")
    }
    implementation("org.json:json:20230227")
}

intellijPlatform {
    pluginConfiguration {
        id = "com.github.darui3018823.discordrp"
        name = "Discord Rich Presence"
        version = project.version.toString()
        ideaVersion {
            sinceBuild = "233"
        }
    }
    buildSearchableOptions = false
    instrumentCode = true
}

kotlin {
    jvmToolchain(21)
}

detekt {
    config.setFrom("$projectDir/detekt.yml")
}
