package me.alex.workflow;

import com.mojang.logging.LogUtils;
import me.alex.workflow.checks.AbstractCheck;
import me.alex.workflow.checks.ParseJSON;
import me.alex.workflow.checks.ParseSNBT;
import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;
import org.slf4j.Logger;

public final class Main {
	public static final Logger LOGGER = LogUtils.getLogger();

	static AbstractCheck[] CHECKS = new AbstractCheck[]{
		new ParseSNBT(),
		new ParseJSON(),
	};

	static void main() {
		SharedConstants.tryDetectVersion();
		Bootstrap.bootStrap();
		// add checking files
	}
}
