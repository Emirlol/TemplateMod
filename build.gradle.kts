plugins {
	alias(libs.plugins.loom)
	alias(libs.plugins.kotlin)
	alias(libs.plugins.modPublish)
	alias(libs.plugins.kotlinxSerialization)
	alias(libs.plugins.ksp)
}

repositories {
	mavenCentral()
	maven("https://maven.isxander.dev/releases") {
		name = "Xander Maven"
	}
	maven("https://maven.terraformersmc.com/releases") {
		name = "Terraformers"
	}
	maven("https://ancientri.me/maven/releases") {
		name = "AncientRime"
	}
}

val modName = property("mod_name") as String
val modId = property("mod_id") as String
val modVersion = property("mod_version") as String
val minecraftVersion = libs.versions.minecraft.get()
group = property("maven_group") as String
version = "$modVersion+$minecraftVersion"

dependencies {
	minecraft(libs.minecraft)
	mappings(variantOf(libs.yarn) { classifier("v2") })
	modImplementation(libs.fabricLoader)

	modImplementation(libs.fabricApi)
	modImplementation(libs.fabricLanguageKotlin)
	modImplementation(libs.yacl)
	modImplementation(libs.modMenu)
	include(modImplementation(libs.rimelib.get())!!) // Loom doesn't like `Provider` types, so we have to `.get()` it
	api(libs.pods4k)

	compileOnly(libs.init.annotation)
	compileOnly(libs.config.annotation)
	ksp(libs.init.processor)
	ksp(libs.config.processor)
}

tasks {
	processResources {
		val props = mapOf(
			"version" to version,
			"minecraft_version" to minecraftVersion,
			"loader_version" to libs.versions.fabricLoader.get(),
			"fabric_kotlin_version" to libs.versions.fabricLanguageKotlin.get(),
			"modmenu_version" to libs.versions.modMenu.get(),
			"yacl_version" to libs.versions.yacl.get(),
			"rimelib_version" to libs.versions.rimelib.get(),
		)
		inputs.properties(props)
		filesMatching("fabric.mod.json") {
			expand(props)
		}
	}
	jar {
		from("LICENSE") {
			rename { "${it}_${base.archivesName.get()}" }
		}
	}
}

kotlin {
	jvmToolchain(21)
	compilerOptions {
		freeCompilerArgs.add("-Xcontext-parameters")
	}
}
