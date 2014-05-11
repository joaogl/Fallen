package net.joaolourenco.fallen.settings;

public class GameSettings {

	/**
	 * --------------------- // General Game Settings // ---------------------
	 */
		// Game version
		public static String version = "Pre-Release 0.3";
		
		// Game Name
		public static String name = "Fallen";
		public static String fullname = "Fallen - " + version;
		public static String theme = "Beneath the surface";
		
		// Screen Size
		public static int width = 960;
		public static int height = 540;
		
		// OpenGL Version
		public static int MAJOR_VERSION = 0;
		public static String CURRENT_VERSION = "";
		public static int MINOR_VERSION = 0;
		public static int REQUIRED_VERSION = 3;
		public static String REQUIRED_VERSION_MESSAGE = "OpenGL version: " + GameSettings.CURRENT_VERSION + " not supported. This game Requires Version: 3.0";
		
		// Game Variables
		public static int FPS = 0;
		

	/**
	 * --------------------- // Debugging Settings // ---------------------
	 */
		
		// Debugging settings
		public static boolean onEclipse = true;
		
}