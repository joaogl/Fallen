package com.webs.faragames.fallen.graphics;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.IntBuffer;

import javax.imageio.ImageIO;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
/**
 * Texture holder and loader class.
 * @author FARA Games
 *
 */
public class Texture {

	// All the textures
	public static int Grass = 0;
	public static int Dirt = 0;
	public static int Mob = 0;
	public static int Player = 0;
	public static int[] Fire = new int[5];

	/**
	 * Function to load some early needed resorces.
	 */
	public static void preload() {
		
	}

	/**
	 * Function to load all the resorces.
	 */
	public static void load() {
		Grass = loadTexture("res/textures/grass.png", false);
		Dirt = loadTexture("res/textures/dirt.png", false);
		Mob = loadTexture("res/textures/mob.png", false);
		Player = loadTexture("res/textures/player.png", false);
		Fire[0] = loadTexture("res/textures/fire1.png", false);
		Fire[1] = loadTexture("res/textures/fire2.png", false);
		Fire[2] = loadTexture("res/textures/fire3.png", false);
		Fire[3] = loadTexture("res/textures/fire4.png", false);
		Fire[4] = loadTexture("res/textures/fire5.png", false);
	}

	/**
	 * Method used to load textures.
	 * @param path : String to where the texture is.
	 * @param antialiase : boolean does it need antialiase
	 * @return int Texture ID.
	 */
	private static int loadTexture(String path, boolean antialiase) {
		// Setting up all the variables
		BufferedImage image;
		int width = 0;
		int height = 0;
		int[] pixels = null;
		try {
			// Loading the image 
			image = ImageIO.read(new FileInputStream(path));
			width = image.getWidth();
			height = image.getHeight();
			pixels = new int[width * height];
			// Moving the RGB data to the pixels array
			image.getRGB(0, 0, width, height, pixels, 0, width);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Processing the pixels array for OpenGL
		for (int i = 0; i < width * height; i++) {
			int a = (pixels[i] & 0xff000000) >> 24;
			int r = (pixels[i] & 0xff0000) >> 16;
			int g = (pixels[i] & 0xff00) >> 8;
			int b = (pixels[i] & 0xff);
			pixels[i] = a << 24 | b << 16 | g << 8 | r;
		}

		// Creating the buffer
		IntBuffer buffer = Buffer.createIntBuffer(pixels);

		// Creating the texture
		int texture = glGenTextures();
		// Activating and Binding the new texture
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, texture);
		// Loading the new texture to the graphics card.
		glTexImage2D(GL_TEXTURE_2D, 0, 3, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
		// Setting the Texture settings.
		int ps = GL_NEAREST;
		if (antialiase) ps = GL_LINEAR;
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, ps);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, ps);
		// Unbinding the texture.
		glBindTexture(GL_TEXTURE_2D, 0);
		// Returning the texture ID.
		return texture;
	}

}