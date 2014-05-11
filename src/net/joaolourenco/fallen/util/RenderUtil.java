package net.joaolourenco.fallen.util;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL32.*;

public class RenderUtil {

	public static void setTextures(boolean enabled) {
		if (enabled) glEnable(GL_TEXTURE_2D);
		else glDisable(GL_TEXTURE_2D);
	}

	public static void unbindTextures() {
		glBindTexture(GL_TEXTURE_2D, 0);
	}

	public static void setClearColor(Vector3f color) {
		glClearColor(color.getX(), color.getY(), color.getZ(), 1.0F);
	}

	public static void initGraphics() {
		glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
		glFrontFace(GL_CW);
		glCullFace(GL_BACK);
		glEnable(GL_CULL_FACE);
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_DEPTH_CLAMP);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}

	public static String getOpenGLVersion() {
		return glGetString(GL_VERSION);
	}

	public static int getMajorVersion() {
		return Integer.parseInt(String.valueOf(getOpenGLVersion().charAt(0)));
	}

	public static int getMinorVersion() {
		return Integer.parseInt(String.valueOf(getOpenGLVersion().charAt(2)));
	}
}