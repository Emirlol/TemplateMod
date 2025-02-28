package me.rime.templatemod.config

import dev.isxander.yacl3.api.OptionEventListener
import dev.isxander.yacl3.config.v3.JsonFileCodecConfig
import dev.isxander.yacl3.config.v3.register
import dev.isxander.yacl3.dsl.*
import me.rime.rimelib.util.text
import me.rime.templatemod.TemplateMod
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.sound.PositionedSoundInstance
import net.minecraft.sound.SoundEvents
import kotlin.io.path.createFile
import kotlin.io.path.createParentDirectories
import kotlin.io.path.notExists

val configPath = FabricLoader.getInstance().configDir.resolve(TemplateMod.NAMESPACE)
const val configFileName = "config.json"

@Suppress("UnstableApiUsage")
object ConfigHandler : JsonFileCodecConfig<ConfigHandler>(configPath.resolve(configFileName)) {
	val exampleBoolean by register(false, BOOL)
	val exampleInt by register(0, INT)

	fun load() {
		val path = configPath.resolve(configFileName)
		if (path.notExists()) {
			path.createParentDirectories()
			path.createFile()
		}
		loadFromFile()
	}

	fun createGui(parent: Screen?): Screen = YetAnotherConfigLib(TemplateMod.NAMESPACE) {
		save(::saveToFile)
		title("${TemplateMod.NAME} Config".text)
		val general by categories.registering {
			name("General".text)
			val exampleOption by rootOptions.registeringButton {
				name("Example".text)
				text("Click Here".text)
				action { _, _ ->
					MinecraftClient.getInstance().soundManager.play(PositionedSoundInstance.master(SoundEvents.BLOCK_NOTE_BLOCK_PLING, 1.0f))
				}
			}
			val exampleGroup by groups.registering {
				name("Example Group".text)
				val exampleSlider = options.register(exampleInt) {
					name("Example Slider".text)
					controller = slider(0..69, 1)
					binding = exampleInt.asBinding()
				}
				val exampleCheckbox = options.register(exampleBoolean) {
					name("Example Checkbox".text)
					controller = tickBox()
					binding = exampleBoolean.asBinding()
					addListener { opt, event ->
						if (event == OptionEventListener.Event.STATE_CHANGE) {
							exampleSlider.setAvailable(opt.pendingValue())
						}
					}
				}
			}
		}
	}.generateScreen(parent)
}