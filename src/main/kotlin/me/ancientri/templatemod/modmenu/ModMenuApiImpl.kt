package me.ancientri.templatemod.modmenu

import com.terraformersmc.modmenu.api.ConfigScreenFactory
import com.terraformersmc.modmenu.api.ModMenuApi
import me.ancientri.templatemod.config.ConfigHandler

class ModMenuApiImpl : ModMenuApi {
	override fun getModConfigScreenFactory() = ConfigScreenFactory(ConfigHandler::generateScreen)
}