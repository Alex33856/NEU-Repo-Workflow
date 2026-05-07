package me.alex.workflow.utils.models;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record PullRequestContext(Head head, String prNumber, String org, String repo) {
	public static final Codec<PullRequestContext> CODEC = RecordCodecBuilder.create(instance -> instance.group(
		Head.CODEC.fieldOf("head").forGetter(PullRequestContext::head),
		Codec.STRING.fieldOf("number").forGetter(PullRequestContext::prNumber),
		Codec.STRING.fieldOf("organization").forGetter(PullRequestContext::org),
		Codec.STRING.fieldOf("repository").forGetter(PullRequestContext::repo)
	).apply(instance, PullRequestContext::new));
}

