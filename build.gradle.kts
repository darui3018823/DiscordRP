plugins {
    id("org.jetbrains.intellij.platform") version "2.14.0"
    kotlin("jvm") version "2.1.0"
}

group = "com.github.darui3018823"
version = "1.0.0"

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
    implementation("com.github.jagrosh:DiscordIPC:a8d6631cc9")
    implementation("net.java.dev.jna:jna:5.14.0")
    implementation("net.java.dev.jna:jna-platform:5.14.0")
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
