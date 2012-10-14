package com.openvehicles.OVMS;

import java.util.LinkedList;

public final class DataLog {
	public static final int LOG_SIZE = 100;
	private static LinkedList<String> log;

	public static void Log(String paramString) {
		if (log == null)
			log = new LinkedList();
		while (true) {
			log.add(paramString);
			return;
			if (log.size() > 100)
				log.remove();
		}
	}

	public static String getLog() {
		StringBuilder localStringBuilder = new StringBuilder();
		for (int i = 0;; i++) {
			if (i >= log.size())
				return localStringBuilder.toString();
			localStringBuilder.append((String) log.get(i) + "\n");
		}
	}
}