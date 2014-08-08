package com.webs.faragames.fallen.settings;

import java.util.ArrayList;
import java.util.List;

import com.webs.faragames.fallen.graphics.Shader;

/**
 * Class to handle General Settings.
 * 
 * @author FARA Games
 *
 */
public class GeneralSettings {

	/**
	 * ---------------------------------- // Game Variables // ----------------------------------
	 **/
		// This is the array list that will hold all the shaders for a clean up at the end of the running process.
		public static List<Shader> shaders = new ArrayList<Shader>();	

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
	 * ---------------------------------- // Entity Settings // ----------------------------------
	 **/
		// Entity Speed Settings
		public final static float defaultEntityWalking = 2.5f;
		public final static float defaultEntityRunning = 4f;
		
		// Light Settings
		public final static int defaultLightPointSize = 5;
		public final static int defaultLightSize = 10;
		public final static int defaultLightFacing = 10;

	/**
	 * ---------------------------------- // Shaders Settings // ----------------------------------
	 **/
		// Shaders Settings
		public final static String entityVertexPath = "res/shaders/entity.vert";
		public final static String lightBlockerPath = "res/shaders/blockLightBlocker.frag";
		public final static String lightSpreaderPath = "res/shaders/blockSpreadLight.frag";
		public final static String lightPath = "res/shaders/light.frag";
		public final static int howManyLightsToShader = 50; // How many lights will be passed to the shaders.

}