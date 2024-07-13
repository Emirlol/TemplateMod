plugins {
    id("fabric-loom") version "1.7-SNAPSHOT"
    kotlin("jvm") version "2.0.0"
}

repositories {
    mavenCentral()
    maven("https://maven.isxander.dev/releases") {
        name = "Xander Maven"
    }
    maven("https://maven.terraformersmc.com/releases") {
        name = "Terraformers"
    }
}

dependencies {
    // To change the versions see the gradle.properties file
    minecraft("com.mojang:minecraft:${project.property("minecraft_version")}")
    mappings("net.fabricmc:yarn:${project.property("yarn_mappings")}:v2")
    modImplementation("net.fabricmc:fabric-loader:${project.property("loader_version")}")

    modImplementation("net.fabricmc.fabric-api:fabric-api:${project.property("fabric_version")}")
    modImplementation("net.fabricmc:fabric-language-kotlin:${project.property("fabric_kotlin_version")}")
    modImplementation("dev.isxander:yet-another-config-lib:${project.property("yacl_version")}")
    modImplementation("com.terraformersmc:modmenu:${project.property("modmenu_version")}")
}

tasks {
    processResources {
        expand(
            mapOf(
                "version" to project.property("mod_version"),
                "minecraft_version" to project.property("minecraft_version"),
                "loader_version" to project.property("loader_version"),
                "name" to project.property("mod_name"),
                "mod_id" to project.property("mod_id"),
                "fabric_kotlin_version" to project.property("fabric_kotlin_version"),
                "modmenu_version" to project.property("modmenu_version")
            )
        )
    }
    kotlin {
        jvmToolchain(21)
    }
}

idea {
    module {
        excludeDirs.addAll(listOf(file("run"), file(".kotlin")))
    }
}

