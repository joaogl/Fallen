package net.joaolourenco.fallen.graphics;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import net.joaolourenco.fallen.utils.Buffer;

import org.lwjgl.BufferUtils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

/**
 * Texture holder and loader class.
 * 
 * @author Joao Lourenco
 *
 */
public class Texture {

	// All the textures
	public static int Grass = 0;
	public static int Dirt = 0;
	public static int Mob = 0;
	public static int Player = 0;
	public static int[] Fire = new int[5];

	private static List<Integer> textures = new ArrayList<Integer>();

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
	 * 
	 * @param path
	 *            : String to where the texture is.
	 * @param antialiase
	 *            : boolean does it need antialiase
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

	public static int[] loadFont(String path, int hLength, int vLength, int size) {
		int width = 0;
		int height = 0;
		int index = 0;
		int[] ids = new int[hLength * vLength];
		int[] sheet = null;
		BufferedImage image;
		try {
			image = ImageIO.read(new FileInputStream(path));
			width = image.getWidth();
			height = image.getHeight();
			sheet = new int[width * height];
			image.getRGB(0, 0, width, height, sheet, 0, width);
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int y0 = 0; y0 < vLength; y0++) {
			for (int x0 = 0; x0 < hLength; x0++) {
				int[] letter = new int[size * size];
				for (int y = 0; y < size; y++) {
					for (int x = 0; x < size; x++) {
						letter[x + y * size] = sheet[(x + x0 * size) + (y + y0 * size) * width];
					}
				}

				ByteBuffer buffer = BufferUtils.createByteBuffer(size * size * 4);
				for (int y = 0; y < size; y++) {
					for (int x = 0; x < size; x++) {
						byte a = (byte) ((letter[x + y * size] & 0xff000000) >> 24);
						byte r = (byte) ((letter[x + y * size] & 0xff0000) >> 16);
						byte g = (byte) ((letter[x + y * size] & 0xff00) >> 8);
						byte b = (byte) (letter[x + y * size] & 0xff);
						buffer.put(r).put(g).put(b).put(a);
					}
				}
				buffer.flip();
				int texID = glGenTextures();
				glBindTexture(GL_TEXTURE_2D, texID);
				glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, size, size, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
				glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
				glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
				textures.add(texID);
				ids[index++] = texID;
				glBindTexture(GL_TEXTURE_2D, 0);
			}
		}

		return ids;
	}

	public static int get(int texture) {
		if (texture < 0 || texture >= textures.size()) return 0;
		return textures.get(texture);
	}

}