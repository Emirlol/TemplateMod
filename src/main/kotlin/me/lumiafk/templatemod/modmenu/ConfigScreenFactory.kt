package me.lumiafk.templatemod.modmenu

import com.terraformersmc.modmenu.api.ConfigScreenFactory
import com.terraformersmc.modmenu.api.ModMenuApi
import me.lumiafk.templatemod.config.TemplateConfigHandler

class ConfigScreenFactory: ModMenuApi {
    override fun getModConfigScreenFactory() = ConfigScreenFactory(TemplateConfigHandler::createGui)
}