package me.ancientri.templatemod.config

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import me.ancientri.symbols.config.ConfigClass

@ConfigClass
data class Config(
	val exampleBoolean: Boolean = false,
	val exampleInt: Int = 0,
) {
	companion object {
		val CODEC: Codec<Config> = RecordCodecBuilder.create {
			it.group(
				Codec.BOOL.fieldOf("exampleBoolean").forGetter(Config::exampleBoolean),
				Codec.INT.fieldOf("exampleInt").forGetter(Config::exampleInt)
			).apply(it, ::Config)
		}
	}
}
