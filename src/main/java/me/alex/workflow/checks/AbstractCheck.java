package me.alex.workflow.checks;

import java.io.File;
import java.util.List;
import java.util.regex.Pattern;

public interface AbstractCheck {
	List<Pattern> getFilePatterns();

	boolean checkFile(File file);

	default boolean checkFiles(List<File> file) {
		boolean res = true;
		for (File f : file) {
			res &= checkFile(f);
		}
		return res;
	}
}
