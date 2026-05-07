package me.alex.workflow.utils.models;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record Head(String sha) {
	public static final Codec<Head> CODEC = RecordCodecBuilder.create(instance -> instance.group(
		Codec.STRING.fieldOf("sha").forGetter(Head::sha)
	).apply(instance, Head::new));
}
