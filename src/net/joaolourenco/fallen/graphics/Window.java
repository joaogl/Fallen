package net.joaolourenco.fallen.graphics;

import net.joaolourenco.fallen.settings.GameSettings;
import net.joaolourenco.fallen.util.RenderUtil;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class Window {

	public static void createWindow(boolean fullscreen) {
		Display.setTitle(GameSettings.fullname);
		try {
			if (fullscreen) Display.setFullscreen(true);
			else Display.setDisplayMode(new DisplayMode(GameSettings.width, GameSettings.height));
			Display.create();
			Keyboard.create();
			Mouse.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		GameSettings.MAJOR_VERSION = RenderUtil.getMajorVersion();
		GameSettings.MINOR_VERSION = RenderUtil.getMinorVersion();
		if (GameSettings.MAJOR_VERSION < 3) {
			System.err.println((new StringBuilder("OpenGL version: ")).append(RenderUtil.getOpenGLVersion()).append(" not supported. Requires V:3.0").toString());
			System.exit(1);
		}
	}

	public static void dispose() {
		Display.destroy();
		Keyboard.destroy();
		Mouse.destroy();
	}

	public static boolean isCloseRequested() {
		return Display.isCloseRequested();
	}

	public static int getWidth() {
		return Display.getDisplayMode().getWidth();
	}

	public static int getHeight() {
		return Display.getDisplayMode().getHeight();
	}

	public static String getTitle() {
		return Display.getTitle();
	}

}