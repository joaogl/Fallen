package net.joaolourenco.fallen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class Main {

	public static void main(String args[]) {
		boolean useLog = false;
		if (useLog) {
			try {
				PrintStream out = new PrintStream("log.txt");
				System.setOut(out);
				System.setErr(out);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		System.out.println((new StringBuilder("Using Java Version: ")).append(System.getProperty("java.version")).toString());
		loadNatives();
		Game game = new Game();
		game.start();
	}

	private static void loadNatives() {
		String os = System.getProperty("os.name").toLowerCase();
		String folder = "";
		if (os.indexOf("win") >= 0) {
			System.out.println("OS: Windows");
			folder = "windows";
		} else if (os.indexOf("mac") >= 0) {
			System.out.println("OS: Mac");
			folder = "macosx";
		} else if (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0 || os.indexOf("aix") > 0) {
			System.out.println("OS: Unix or Linux");
			folder = "linux";
		} else if (os.indexOf("sunos") >= 0) {
			System.out.println("OS: Solaris");
			folder = "solaris";
		} else {
			System.err.println("OS NOT SUPPORTED");
			System.exit(1);
		}
		String jarDir = System.getProperty("user.dir");
		String nativeLibDir = (new StringBuilder(String.valueOf(jarDir))).append(File.separator).append("natives").append(File.separator).append(folder).toString();
		System.setProperty("org.lwjgl.librarypath", nativeLibDir);
	}

}