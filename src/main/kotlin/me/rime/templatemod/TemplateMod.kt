package me.rime.templatemod

import me.rime.rimelib.util.register
import me.rime.templatemod.config.ConfigHandler
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.InputUtil

object TemplateMod : ClientModInitializer {
	override fun onInitializeClient() {
		ConfigHandler.load()
		ClientCommandRegistrationCallback.EVENT.register(NAMESPACE) {
			literal("config") {
				executes {
					source.client.let {
						it.send {
							it.setScreen(ConfigHandler.createGui(it.currentScreen))
						}
					}
				}
			}
			incomplete
		}

		createKeybinds()
	}

	private fun createKeybinds() {
		listOf(
			KEY_EXAMPLE
		).forEach { KeyBindingHelper.registerKeyBinding(it) }
	}

	const val NAME = "Template Mod"
	const val NAMESPACE = "templatemod"
	val KEY_EXAMPLE = KeyBinding("key.templatemod.example", InputUtil.GLFW_KEY_G, "category.templatemod.example")
}