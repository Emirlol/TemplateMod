package me.lumiafk.templatemod

import me.lumiafk.templatemod.config.TemplateConfigHandler
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.option.KeyBinding
import org.lwjgl.glfw.GLFW

class TemplateMod: ClientModInitializer {
    override fun onInitializeClient() {
        check(TemplateConfigHandler.load()) { "Failed to load config." }
        ClientCommandRegistrationCallback.EVENT.register { dispatcher, _ ->
            dispatcher.register(
                literal("template").then(literal("config").executes { context ->
                    context.source.client.let {
                        it.send {
                            it.setScreen(TemplateConfigHandler.createGui(it.currentScreen))
                        }
                    }
                    1
                })
            )
        }
        createKeybinds()
    }

    private fun createKeybinds() {
        listOf(
            KEY_EXAMPLE
        ).forEach { KeyBindingHelper.registerKeyBinding(it) }
    }

    companion object {
        val KEY_EXAMPLE = KeyBinding("key.templatemod.example", GLFW.GLFW_KEY_G, "category.templatemod.example")
    }
}