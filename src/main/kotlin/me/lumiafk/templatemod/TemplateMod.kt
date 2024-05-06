package me.lumiafk.templatemod

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.option.KeyBinding
import org.lwjgl.glfw.GLFW

class TemplateMod: ClientModInitializer {
    override fun onInitializeClient() {
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