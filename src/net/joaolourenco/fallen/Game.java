package net.joaolourenco.fallen;

import net.joaolourenco.fallen.graphics.Window;
import net.joaolourenco.fallen.util.RenderUtil;
import net.joaolourenco.fallen.util.Vector3f;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import static org.lwjgl.opengl.GL11.*;

public class Game implements Runnable {

	private Thread thread;
	private boolean running = false;
	public static Game instance;

	public void start() {
		running = true;
		thread = new Thread(this, "Game");
		thread.start();
	}

	public void stop() {
		running = false;
	}

	public void init() {
		Window.createWindow(false);

		System.out.println((new StringBuilder("Using OpenGL Version: ")).append(RenderUtil.getOpenGLVersion()).toString());
		RenderUtil.initGraphics();

		RenderUtil.setClearColor(new Vector3f(0.4666667F, 0.627451F, 1.0F));
	}

	public void run() {
		init();
		long lastTime = System.nanoTime();
		double ns = 1000000000.0 / 60.0;
		double delta = 0;
		long lastTimer = System.currentTimeMillis();
		int frames = 0;
		int updates = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				updates++;
				input();
				update();
				// Time.setDelta(delta / 60);
				delta--;
			}
			render();
			frames++;
			Display.update();
			if (System.currentTimeMillis() - lastTimer > 1000) {
				lastTimer += 1000;
				System.out.println("FPS: " + frames + " UPS: " + updates);
				// GameSettings.FPS = frames;
				updates = 0;
				frames = 0;
			}
			if (Window.isCloseRequested()) running = false;
		}
		System.exit(0);
	}

	public void input() {
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) stop();
		//if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) Transform.setProjection(Transform.getFOV() - 40F, Window.getWidth(), Window.getHeight(), 0.1F, 1000F);
		//else if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) Transform.setProjection(Transform.getFOV() + 40F, Window.getWidth(), Window.getHeight(), 0.1F, 1000F);
		//if (Input.getMouseDown(1)) player.getCamera().grabMouse(false);
	}

	public void update() {

	}

	public void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

	}

}