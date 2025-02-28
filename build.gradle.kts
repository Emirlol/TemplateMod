plugins {
	alias(libs.plugins.loom)
	alias(libs.plugins.kotlin)
	alias(libs.plugins.modPublish)
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

val modName = property("mod_name") as String
val modId = property("mod_id") as String
group = property("maven_group") as String
version = "${libs.versions.modVersion.get()}+${libs.versions.minecraft.get()}"

dependencies {
	minecraft(libs.minecraft)
	mappings("net.fabricmc:yarn:${libs.versions.yarnMappings.get()}:v2") // Gradle version catalogue doesn't like the :v2 suffix
	modImplementation(libs.fabricLoader)

	modImplementation(libs.fabricApi)
	modImplementation(libs.fabricLanguageKotlin)
	modImplementation(libs.yacl)
	modImplementation(libs.modMenu)
}

tasks {
	processResources {
		val props = mapOf(
			"name" to modName,
			"mod_id" to modId,
			"version" to version,
			"minecraft_version" to libs.versions.minecraft.get(),
			"loader_version" to libs.versions.fabricLoader.get(),
			"fabric_kotlin_version" to libs.versions.fabricLanguageKotlin.get(),
			"modmenu_version" to libs.versions.modMenu.get(),
			"yacl_version" to libs.versions.yacl.get()
		)
		inputs.properties(props)
		filesMatching("fabric.mod.json") {
			expand(props)
		}
		filesMatching("assets/$modId/lang/*.json") {
			expand(
				mapOf(
					"namespace" to modId
				)
			)
		}
	}
	jar {
		from("LICENSE") {
			rename { "${it}_${base.archivesName.get()}"}
		}
	}
}

kotlin {
	jvmToolchain(21)
}
