package com.webs.faragames.fallen;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

import com.webs.faragames.fallen.entity.mob.Player;
import com.webs.faragames.fallen.graphics.Font;
import com.webs.faragames.fallen.graphics.Shader;
import com.webs.faragames.fallen.graphics.Texture;
import com.webs.faragames.fallen.settings.GeneralSettings;
import com.webs.faragames.fallen.world.World;

import static org.lwjgl.opengl.GL11.*;

/**
 * Main Class for the game.
 * 
 * @author FARA Games
 *
 */
public class Main implements Runnable {

	/**
	 * Thread where the game will run.
	 */
	private Thread thread;
	/**
	 * Variable that keeps track if the game is running or not.
	 */
	private boolean running = false;

	/**
	 * This is the instance for the world.
	 */
	public static World world;
	/**
	 * Player Instance.
	 */
	public static Player player;

	/**
	 * Main method that runs the game.
	 * 
	 * @param args
	 *            : String[] with anykind of arguments.
	 */
	public static void main(String[] args) {
		Main main = new Main();
		main.start();
	}

	/**
	 * Method that starts the game.
	 */
	public synchronized void start() {
		thread = new Thread(this, GeneralSettings.fullname);
		running = true;
		thread.start();
	}

	/**
	 * Method that initializes the game.
	 */
	private void init() {
		// Setting up the Display
		try {
			Display.setDisplayMode(new DisplayMode(GeneralSettings.WIDTH, GeneralSettings.HEIGHT));
			Display.setTitle(GeneralSettings.fullname);
			Display.create(new PixelFormat(0, 16, 1));
		} catch (LWJGLException e) {
			e.printStackTrace();
		}

		// Loading all the textures
		Texture.preload();
		Texture.load();
		// Creating the world
		world = new World(12, 16);

		// Creating and adding the player to the world.
		player = new Player(50, 50, 64, 64);
		world.addEntity(player);

		// Setting up all the Projections stuff for OpenGL
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, GeneralSettings.WIDTH, GeneralSettings.HEIGHT, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);

		glEnable(GL_TEXTURE_2D);
		glEnable(GL_STENCIL_TEST);
		glClearColor(0, 0, 0, 0);
	}

	/**
	 * This Method is called on the end of the game to clean the memory.
	 */
	private void cleanup() {
		// Cleaning all the Shaders
		for (Shader shader : GeneralSettings.shaders)
			if (shader != null) shader.cleanUp();
		// Cleaning all the Font
		for (Font font : GeneralSettings.font)
			if (font != null) font.cleanup();
		// Destroying the Display.
		Display.destroy();
	}

	/**
	 * This Method holds the game loop.
	 */
	public void run() {
		// Calling the initialize Method to setup our environment 
		init();
		// Variables for the FPS counter
		long lastTime = System.nanoTime();
		double ns = 1000000000.0 / 60.0;
		double delta = 0;
		long lastTimer = System.currentTimeMillis();
		int frames = 0;
		int updates = 0;
		int checking = 0;
		int sum = 0;
		int avg = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				// 60 times per second this is reached
				update();
				updates++;
				delta--;
			}
			// Render as many times as u can
			render();
			frames++;
			Display.update();
			if (System.currentTimeMillis() - lastTimer > 1000) {
				lastTimer += 1000;
				// Once per second this is reached
				String title = GeneralSettings.fullname + " FPS: " + frames + " UPS: " + updates;
				if (GeneralSettings.useAverageFPS) title += " Average: " + avg;
				Display.setTitle(title);
				tick();
				sum += frames;
				checking++;
				if (checking == GeneralSettings.ticksPerAverage) {
					avg = (sum / checking);
					checking = 0;
					sum = 0;
				}
				updates = 0;
				frames = 0;
			}
			if (Display.isCloseRequested()) running = false;
		}
		// If the game is closed, cleanup!
		cleanup();
	}

	/**
	 * This is the render Method.
	 */
	private void render() {
		// Clear the screen from the last render.
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
		// Render the new stuff.
		world.render();
	}

	/**
	 * This is the update Method and it is updated 60 times per second.
	 */
	private void update() {
		world.update();
	}

	/**
	 * This is the tick Method and it is updated 1 times per second.
	 */
	private void tick() {
		world.tick();
	}

}