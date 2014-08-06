package com.webs.faragames.fallen.settings;

public class GeneralSettings {

	/**
	 * ---------------------------------- // General Game Settings // ----------------------------------
	 **/
		// Game version
		public final static String version = "BETA V0.1";
	
		// Game Name
		public final static String name = "Fallen";
		public final static String fullname = name + " " + version;
	
		// Window Size		
		public final static int WIDTH = 800;
		public final static int HEIGHT = 600;
		public final static int TILE_SIZE = 64;
		public final static int TILE_SIZE_MASK = 6;		

	/**
	 * ---------------------------------- // Debugging Settings // ----------------------------------
	 **/
		// Game debugging
		public final static boolean useAverageFPS = false;
		public final static int ticksPerAverage = 10;

	/**
	 * ---------------------------------- // Entities Settings // ----------------------------------
	 **/
		
		// Entities Size
		public static int entWidth = 16;
		public static int entHeight = 16;
	
}