package net.joaolourenco.lightdemo;

import net.joaolourenco.lightdemo.graphics.Texture;
import net.joaolourenco.lightdemo.world.World;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

import static org.lwjgl.opengl.GL11.*;

public class Main implements Runnable {

	private Thread thread;
	private boolean running = false;

	public final static int WIDTH = 800;
	public final static int HEIGHT = 600;
	public final static int TILE_SIZE = 64;
	public final static int TILE_SIZE_MASK = 6;
	public static World world;

	public static void main(String[] args) {
		Main main = new Main();
		main.start();
	}

	public synchronized void start() {
		thread = new Thread(this, "Demo");
		running = true;
		thread.start();
	}

	private void init() {
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.setTitle("Demo of 2D lights to Joao Games!");
			Display.create(new PixelFormat(0, 16, 1));
		} catch (LWJGLException e) {
			e.printStackTrace();
		}

		Texture.load();
		world = new World(12, 16);

		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, WIDTH, HEIGHT, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);

		glEnable(GL_TEXTURE_2D);
		glEnable(GL_STENCIL_TEST);
		glClearColor(0, 0, 0, 0);
	}

	private void cleanup() {
		// Shaders Clean up
		Display.destroy();
	}

	public void run() {
		init();
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
				update();
				updates++;
				delta--;
			}
			render();
			frames++;
			Display.update();
			if (System.currentTimeMillis() - lastTimer > 1000) {
				lastTimer += 1000;
				Display.setTitle("Demo of 2D lights to Joao Games! FPS: " + frames + " UPS: " + updates + " Average: " + avg);
				world.tick();
				sum += frames;
				checking++;
				if (checking == 10) {
					avg = (sum / checking);
					checking = 0;
					sum = 0;
				}
				updates = 0;
				frames = 0;
			}
			if (Display.isCloseRequested()) running = false;
		}
		cleanup();
	}

	private void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
		world.render();
	}

	private void update() {
		world.update();
	}

}