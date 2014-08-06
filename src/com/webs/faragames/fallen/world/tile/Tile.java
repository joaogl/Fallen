package com.webs.faragames.fallen.world.tile;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;

import com.webs.faragames.fallen.entity.Entity;
import com.webs.faragames.fallen.entity.light.Light;
import com.webs.faragames.fallen.graphics.Buffer;
import com.webs.faragames.fallen.graphics.Shader;
import com.webs.faragames.fallen.settings.GeneralSettings;
import com.webs.faragames.fallen.world.World;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;

/**
 * Abstract Class for all the Tile Types
 * 
 * @author FARA Games
 *
 */
public abstract class Tile {

	/**
	 * Is the tile collidable? Will the light end on the tile or keep spreading?
	 */
	protected boolean collidable = true, lightCollidable = true;
	/**
	 * Size and location of the tile.
	 */
	protected int width, height, x = 9999999, y = 9999999;
	/**
	 * Texture ID for the tile.
	 */
	protected int tex;
	/**
	 * Shader ID for the tiles.
	 */
	public Shader shade = new Shader("res/shaders/blockLightBlocker.frag", "res/shaders/entity.vert");

	/**
	 * Constructor for tiles with a different with and height.
	 * 
	 * @param width
	 *            : Width of the Tile.
	 * @param height
	 *            : Height of the Tile.
	 * @param tex
	 *            : Texture ID from the Texture class for the Tile.
	 */
	public Tile(int width, int height, int tex) {
		this.width = width;
		this.height = height;
		this.tex = tex;
	}

	/**
	 * Constructor for square tiles.
	 * 
	 * @param size
	 *            : width and height of the Tile.
	 * @param tex
	 *            : Texture ID from the Texture class for the Tile.
	 */
	public Tile(int size, int tex) {
		this.width = size;
		this.height = size;
		this.tex = tex;
	}

	/**
	 * Update Method, called by the World class 60 times per second.
	 */
	public void update() {
		// If the tile is using a shader that doesnt block the light and the ligthCollidable is true, change it, and vice versa.
		if (this.lightCollidable && !this.shade.getFragPath().equalsIgnoreCase("res/shaders/blockLightBlocker.frag")) this.shade = new Shader("res/shaders/blockSpreadLight.frag");
		else if (!this.lightCollidable && !this.shade.getFragPath().equalsIgnoreCase("res/shaders/blockSpreadLight.frag")) this.shade = new Shader("res/shaders/blockLightBlocker.frag");
	}

	/**
	 * Method to bind the Texture uniforms, this is what make the shaders and stuff.
	 * 
	 * @param w
	 *            : instance of the World Class
	 * @param ent
	 *            : List of entities that emit light.
	 */
	public void bindUniforms(World w, ArrayList<Entity> ent) {
		// Binding the shader program.
		shade.bind();
		// Setting up all the variables that will be passed to the shader
		float[] positions = new float[ent.size() * 2];
		float[] colors = new float[ent.size() * 3];
		float[] intensities = new float[ent.size()];
		float[] inUse = new float[50];
		float[] type = new float[50];
		float[] size = new float[50];
		float[] facing = new float[50];

		// Putting all the coordinates inside a float array.
		for (int i = 0; i < ent.size() * 2; i += 2) {
			float xx = ent.get(i >> 1).getX() - w.getXOffset();
			float yy = GeneralSettings.HEIGHT - (ent.get(i >> 1).getY() - w.getYOffset());

			positions[i] = xx;
			positions[i + 1] = yy;
		}

		// Putting all the light intensities inside a float array.
		for (int i = 0; i < ent.size(); i++) {
			intensities[i] = ((Light) ent.get(i)).intensity;
		}

		// Putting the info's about the light state (on or off) inside a float array.
		for (int i = 0; i < 50; i++) {
			if (i < ent.size() && ent.get(i) != null) inUse[i] = 1;
			else inUse[i] = 0;
		}

		// Putting all the colors inside a float array.
		for (int i = 0; i < ent.size() * 3; i += 3) {
			colors[i] = ((Light) ent.get(i / 3)).red;
			colors[i + 1] = ((Light) ent.get(i / 3)).green;
			colors[i + 2] = ((Light) ent.get(i / 3)).blue;
		}

		// Putting the size, type and facing of the light inside a float array.
		for (int i = 0; i < ent.size(); i++) {
			if (ent.get(i) != null) {
				type[i] = ((Light) ent.get(i)).getType();
				size[i] = ((Light) ent.get(i)).getSize();
				facing[i] = ((Light) ent.get(i)).getFacing();
			} else {
				size[i] = 0;
				type[i] = 0;
				facing[i] = 0;
			}
		}

		// Sending to the shader the current dayLight
		glUniform1f(glGetUniformLocation(shade.getShader(), "dayLight"), w.DAY_LIGHT);

		// Sending all the previus information from the floats to the shader.
		glUniform2(glGetUniformLocation(shade.getShader(), "lightPosition"), Buffer.createFloatBuffer(positions));
		glUniform3(glGetUniformLocation(shade.getShader(), "lightColor"), Buffer.createFloatBuffer(colors));
		glUniform1(glGetUniformLocation(shade.getShader(), "lightIntensity"), Buffer.createFloatBuffer(intensities));
		glUniform1(glGetUniformLocation(shade.getShader(), "lightInUse"), Buffer.createFloatBuffer(inUse));
		glUniform1(glGetUniformLocation(shade.getShader(), "lightType"), Buffer.createFloatBuffer(type));
		glUniform1(glGetUniformLocation(shade.getShader(), "lightSize"), Buffer.createFloatBuffer(size));
		glUniform1(glGetUniformLocation(shade.getShader(), "lightFacing"), Buffer.createFloatBuffer(facing));
	}

	/**
	 * Method called by the World Class to render the Tile.
	 * 
	 * @param x
	 *            : location to where the Tile is going to be rendered.
	 * @param y
	 *            : location to where the Tile is going to be rendered.
	 * @param w
	 *            : instance of the World Class
	 * @param ent
	 *            : List of entities that emit light.
	 */
	public void render(int x, int y, World w, ArrayList<Entity> ent) {
		// Binding the Uniforms to make the light effects.
		bindUniforms(w, ent);

		// Setting up OpenGL for render
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ZERO);

		// Updating the Tile coordinates.
		this.x = x;
		this.y = y;
		glTranslatef(x, y, 0);
		// Activating and Binding the Tile Texture.
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, this.tex);
		// Sending the texture to the shader.
		glUniform1i(glGetUniformLocation(shade.getShader(), "texture"), 0);

		// Drawing the QUAD.
		glBegin(GL_QUADS);
		{
			glTexCoord2f(0, 0);
			glVertex2f(0, 0);

			glTexCoord2f(0, 1);
			glVertex2f(0, this.height);

			glTexCoord2f(1, 1);
			glVertex2f(this.width, this.height);

			glTexCoord2f(1, 0);
			glVertex2f(this.width, 0);
		}
		glEnd();
		// Releasing the Texture.
		glBindTexture(GL_TEXTURE_2D, 0);
		// Getting the location back to the inicial coordinates.
		glTranslatef(-x, -y, 0);

		// Disabling BLEND and releasing shader for next render.
		glDisable(GL_BLEND);
		shade.release();
		glClear(GL_STENCIL_BUFFER_BIT);
	}

	/**
	 * Method to return the Tiles vertices.
	 * 
	 * @return Vector2f[] with the vertices.
	 */
	public Vector2f[] getVertices() {
		return new Vector2f[] { new Vector2f(this.x, this.y), new Vector2f(this.x, this.y + this.height), new Vector2f(this.x + this.width, this.y + this.height), new Vector2f(this.x + this.width, this.y) };
	}

	/**
	 * Method to get if the Tile is Light Collidable or not.
	 * 
	 * @return boolean, true if its collidable, false if its not.
	 */
	public boolean isLightCollidable() {
		return this.lightCollidable;
	}

	/**
	 * Method to define if the Light is Collidable or not.
	 * 
	 * @param a
	 *            : true if its collidable, false if its not.
	 */
	public void isLightCollidable(boolean a) {
		this.lightCollidable = a;
	}

	/**
	 * Method to get if Entities will collide with the Tile.
	 * 
	 * @return boolean, true if they collide, false if they dont.
	 */
	public boolean isCollidable() {
		return this.collidable;
	}

	/**
	 * Method to define if the Entities will collide with the Tile.
	 * 
	 * @param a
	 *            : Boolean true if they collide, false if they dont.
	 */
	public void isCollidable(boolean a) {
		this.collidable = a;
	}

	/**
	 * Method to get the Tile X location.
	 * 
	 * @return int with the x coordinates.
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * Method to get the Tile Y location.
	 * 
	 * @return int with the y coordinates.
	 */
	public int getY() {
		return this.y;
	}

}