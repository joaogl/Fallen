package com.webs.faragames.fallen;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileSystem {

	public static String Dir = getAppDir("Fallen").getAbsolutePath();
	public static String sep = System.getProperty("file.separator");

	public static EnumOS getOs() {
		String s = System.getProperty("os.name").toLowerCase();
		return s.contains("win") ? EnumOS.WINDOWS : (s.contains("mac") ? EnumOS.MACOS : (s.contains("solaris") ? EnumOS.SOLARIS : (s.contains("sunos") ? EnumOS.SOLARIS : (s.contains("linux") ? EnumOS.LINUX : (s.contains("unix") ? EnumOS.LINUX : EnumOS.UNKNOWN)))));
	}

	private static File getAppDir(String par0Str) {
		String s1 = System.getProperty("user.home", ".");
		File file1;
		switch (EnumOSHelper.OS[getOs().ordinal()]) {
			case 1:
			case 2:
				file1 = new File(s1, '.' + par0Str + '/');
				break;
			case 3:
				String s2 = System.getenv("APPDATA");
				if (s2 != null) file1 = new File(s2, "." + par0Str + '/');
				else file1 = new File(s1, '.' + par0Str + '/');
				break;
			case 4:
				file1 = new File(s1, "Library/Application Support/" + par0Str);
				break;
			default:
				file1 = new File(s1, par0Str + '/');
		}

		if (!file1.exists() && !file1.mkdirs()) throw new RuntimeException("The working directory could not be created: " + file1);
		else return file1;
	}

	public static void Logger(String text) {
		try {
			File file = new File("Log.log");
			if (file.exists()) {
				PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("Log.log", true)));
				out.println(text);
				out.close();
			} else {
				PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("Log.log", true)));
				out.println("=== New File for the Game Logger ===");
				out.println(text);
				out.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(text);
	}

}
