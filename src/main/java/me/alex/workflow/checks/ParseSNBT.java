package me.alex.workflow.checks;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import org.jspecify.annotations.Nullable;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Pattern;

import static me.alex.workflow.Main.LOGGER;

public final class ParseSNBT implements AbstractCheck {
	public static final List<Pattern> SNBT_PATTERN = List.of(Pattern.compile("itemsOverlay/.*\\.snbt"));
	public static final Object2ObjectOpenHashMap<String, @Nullable Path> SNBT_MAP = new Object2ObjectOpenHashMap<>();

	final String name = "Parse SNBT";

	@Override
	public String getName() {
		return name;
	}

	@Override
	public List<Pattern> getFilePatterns() {
		return SNBT_PATTERN;
	}

	public static @Nullable CompoundTag readSnbt(Path path) {
		try {
			String content = Files.readString(path);
			return TagParser.parseCompoundFully(content);
		} catch (Exception ex) {
			LOGGER.error("Failed to read SNBT File: {}", path.getFileName(), ex);
			return null;
		}
	}

	@Override
	public boolean checkFiles(List<File> files) {
		files.stream().filter(File::isFile).forEach(file -> {
			String fileName = file.getName().replace(".snbt", "");
			SNBT_MAP.put(fileName, file.toPath());
		});
		return AbstractCheck.super.checkFiles(files);
	}

	@Override
	public boolean checkFile(File file) {
		CompoundTag tag = readSnbt(file.toPath());
		if (tag == null) return false;
		String version = String.valueOf(tag.getCompoundOrEmpty("source").getIntOr("dataVersion", -1));
		Path path = file.toPath();
		String fileVersion = path.getName(path.getNameCount() - 2).toString();
		if (!version.equals(fileVersion)) {
			LOGGER.error("{}: File {}/{} has wrong version in data!", getName(), fileVersion, file.getName());
			return false;
		}

		return true;
	}
}
