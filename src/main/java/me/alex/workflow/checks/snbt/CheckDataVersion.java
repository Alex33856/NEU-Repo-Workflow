package me.alex.workflow.checks.snbt;

import me.alex.workflow.checks.ChildCheck;
import me.alex.workflow.checks.ParseSNBT;

import static com.mojang.realmsclient.client.RealmsError.LOGGER;

public class CheckDataVersion implements ChildCheck<ParseSNBT.Item> {
	final String name = "Check SNBT Version";

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean checkData(ParseSNBT.Item data) {
		String version = String.valueOf(data.tag().getCompoundOrEmpty("source").getIntOr("dataVersion", -1));
		String fileVersion = data.path().getName(data.path().getNameCount() - 2).toString();
		if (!version.equals(fileVersion)) {
			LOGGER.error("{}: File {}/{} has wrong version in data!", getName(), fileVersion, data.path().getFileName());
			return false;
		}

		return true;
	}
}
