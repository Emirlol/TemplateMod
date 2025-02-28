package me.rime.templatemod

import net.minecraft.client.MinecraftClient
import net.minecraft.text.Text

object Util {
    val client get() = MinecraftClient.getInstance()
    val player get() = client.player

    val String.text get() = Text.of(this)
    val String.literal get() = Text.literal(this)
}