package me.ancientri.templatemod.config

import com.mojang.serialization.Codec
import dev.isxander.yacl3.api.Binding
import dev.isxander.yacl3.api.OptionEventListener
import dev.isxander.yacl3.dsl.*
import me.ancientri.rimelib.config.dfu.JsonCodecConfigManager
import me.ancientri.rimelib.util.client
import me.ancientri.rimelib.util.text.text
import me.ancientri.templatemod.TemplateMod
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.sound.PositionedSoundInstance
import net.minecraft.sound.SoundEvents
import org.slf4j.Logger
import java.nio.file.Path
import kotlin.reflect.KMutableProperty

object ConfigHandler : JsonCodecConfigManager<Config, ConfigBuilder>() {
	override val codec: Codec<Config>
		get() = Config.CODEC

	override val configPath: Path = FabricLoader.getInstance().configDir.resolve(TemplateMod.NAMESPACE).resolve("config.json")

	override val default: Config
		get() = Config()

	override val logger: Logger = TemplateMod.loggerFactory.createLogger(this)

	override fun builder(config: Config): ConfigBuilder = ConfigBuilder(config)

	fun generateScreen(parent: Screen?): Screen = YetAnotherConfigLib(TemplateMod.NAMESPACE) {
		with(builder(config)) {
			save { saveConfig(build()) }
			title("${TemplateMod.NAME} Config".text)
			val general by categories.registering {
				name("General".text)
				val exampleOption by rootOptions.registeringButton {
					name("Example".text)
					text("Click Here".text)
					action { _, _ ->
						client.soundManager.play(PositionedSoundInstance.master(SoundEvents.BLOCK_NOTE_BLOCK_PLING, 1.0f))
					}
				}
				val exampleGroup by groups.registering {
					name("Example Group".text)
					val exampleSlider = options.register("exampleSlider") {
						name("Example Slider".text)
						binding = binding(ConfigBuilder::exampleInt)
						controller = slider(0..69, 1)
					}
					val exampleCheckbox = options.register("exampleBoolean") {
						name("Example Checkbox".text)
						binding = binding(ConfigBuilder::exampleBoolean)
						controller = tickBox()
						addListener { opt, event ->
							if (event == OptionEventListener.Event.STATE_CHANGE) {
								exampleSlider.setAvailable(opt.pendingValue())
							}
						}
					}
				}
			}
		}
	}.generateScreen(parent)

	context(config: ConfigBuilder)
	fun <V> binding(property: KMutableProperty<V>): Binding<V> {
		val getter = property.getter
		val setter = property.setter
		return Binding.generic<V>(
			getter.call(default),
			{ getter.call(config) },
			{ value -> setter.call(config, value) }
		)
	}
}
