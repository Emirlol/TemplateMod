package me.ancientri.templatemod

import me.ancientri.rimelib.util.LoggerFactory
import me.ancientri.rimelib.util.command.IncompleteCommand
import me.ancientri.rimelib.util.command.register
import me.ancientri.templatemod.config.ConfigHandler
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.InputUtil

object TemplateMod {
	@Deprecated("This method will be called by the fabric loader, don't call it manually.", level = DeprecationLevel.ERROR)
	fun init() {
		ConfigHandler // Initialize the config handler
		ClientCommandRegistrationCallback.EVENT.register(NAMESPACE) {
			literal("config") {
				executes {
					source.client.let {
						it.send {
							it.setScreen(ConfigHandler.generateScreen(it.currentScreen))
						}
					}
				}
			}
			command = IncompleteCommand.getInstance()
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
	val KEY_CATEGORY = KeyBinding.Category(Identifier.of(NAMESPACE, "example"))
	val KEY_EXAMPLE = KeyBinding("key.templatemod.example", InputUtil.GLFW_KEY_G, KEY_CATEGORY)
	val loggerFactory = LoggerFactory(NAMESPACE)
}