/*
 * Copyright 2014 Joao Lourenco
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.joaolourenco.fallen.world.tile;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import net.joaolourenco.fallen.entity.Entity;
import net.joaolourenco.fallen.entity.light.Light;
import net.joaolourenco.fallen.graphics.Shader;
import net.joaolourenco.fallen.settings.GeneralSettings;
import net.joaolourenco.fallen.utils.Buffer;
import net.joaolourenco.fallen.utils.Vector2f;
import net.joaolourenco.fallen.world.World;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;

/**
 * Abstract Class for all the Tile Types
 * 
 * @author Joao Lourenco
 *
 */
public abstract class Tile {

	/**
	 * Is the tile collidable? Will the light end on the tile or keep spreading?
	 */
	protected boolean collidable = true, lightCollidable = true, lightAffected = true;
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
	public Shader shade = new Shader(GeneralSettings.lightBlockerPath, GeneralSettings.entityVertexPath);

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
	 * Constructor for square tiles which are not affected by environment light.
	 * 
	 * @param size
	 *            : width and height of the Tile.
	 * @param tex
	 *            : Texture ID from the Texture class for the Tile.
	 * @param lightable
	 *            : Is tile affected by environment light.
	 */
	public Tile(int size, int tex, boolean lightable) {
		this.width = size;
		this.height = size;
		this.tex = tex;
		this.lightAffected = lightable;
	}

	/**
	 * Update Method, called by the World class 60 times per second.
	 */
	public void update() {
		if (Keyboard.isKeyDown(Keyboard.KEY_R)) shade.recompile();
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
		// Is there 50 Lights or less?
		int howMany = ent.size();
		if (ent.size() > GeneralSettings.howManyLightsToShader) howMany = GeneralSettings.howManyLightsToShader;
		// Binding the shader program.
		shade.bind();
		// Setting up all the variables that will be passed to the shader
		float[] positions = new float[howMany * 2];
		float[] colors = new float[howMany * 3];
		float[] intensities = new float[howMany];
		float[] type = new float[howMany];
		float[] size = new float[howMany];
		float[] facing = new float[howMany];

		// Putting all the coordinates inside a float array.
		for (int i = 0; i < howMany * 2; i += 2) {
			if (i < ent.size() && ent.get(i >> 1) != null && ((Light) ent.get(i)).getLightState()) {
				float xx = ent.get(i >> 1).getX() - w.getXOffset();
				float yy = GeneralSettings.HEIGHT - (ent.get(i >> 1).getY() - w.getYOffset());

				positions[i] = xx;
				positions[i + 1] = yy;
			}
		}

		// Putting all the light intensities inside a float array.
		for (int i = 0; i < howMany; i++) {
			if (i < ent.size() && ent.get(i) != null && ((Light) ent.get(i)).getLightState()) intensities[i] = ((Light) ent.get(i)).intensity;
		}

		// Putting all the colors inside a float array.
		for (int i = 0; i < howMany * 3; i += 3) {
			if (i < ent.size() && ent.get(i / 3) != null && ((Light) ent.get(i)).getLightState()) {
				colors[i] = ((Light) ent.get(i / 3)).red;
				colors[i + 1] = ((Light) ent.get(i / 3)).green;
				colors[i + 2] = ((Light) ent.get(i / 3)).blue;
			}
		}

		// Putting the size, type and facing of the light inside a float array.
		for (int i = 0; i < howMany; i++) {
			if (i < ent.size() && ent.get(i) != null && ((Light) ent.get(i)).getLightState()) {
				type[i] = ((Light) ent.get(i)).getType();
				size[i] = ((Light) ent.get(i)).getSize();
				facing[i] = ((Light) ent.get(i)).getFacing();
			}
		}

		// Sending to the shader the current dayLight
		float day_light = 1f;
		if (this.lightAffected) day_light = w.DAY_LIGHT;
		glUniform1f(glGetUniformLocation(shade.getShader(), "dayLight"), day_light * 2);

		// Sending all the previus information from the floats to the shader.
		glUniform2(glGetUniformLocation(shade.getShader(), "lightPosition"), Buffer.createFloatBuffer(positions));
		glUniform3(glGetUniformLocation(shade.getShader(), "lightColor"), Buffer.createFloatBuffer(colors));
		glUniform1(glGetUniformLocation(shade.getShader(), "lightIntensity"), Buffer.createFloatBuffer(intensities));
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
		return new Vector2f[] {
				new Vector2f(this.x, this.y), new Vector2f(this.x, this.y + this.height), new Vector2f(this.x + this.width, this.y + this.height), new Vector2f(this.x + this.width, this.y) };
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

		// If the tile is using a shader that doesnt block the light and the ligthCollidable is true, change it, and vice versa.
		//if (this.lightCollidable && !this.shade.getFragPath().equalsIgnoreCase(GeneralSettings.lightBlockerPath)) this.shade = new Shader(GeneralSettings.lightBlockerPath, GeneralSettings.entityVertexPath);
		//else if (!this.lightCollidable && !this.shade.getFragPath().equalsIgnoreCase(GeneralSettings.lightSpreaderPath)) this.shade = new Shader(GeneralSettings.lightSpreaderPath, GeneralSettings.entityVertexPath);
	}

	/**
	 * Method to define if the Tile is affected by Environment Light or not.
	 * 
	 * @param a
	 *            : true if its affected, false if its not.
	 */
	public void isLightAffected(boolean a) {
		this.lightAffected = a;
	}

	/**
	 * Method to get if the Tile is affected by Environment Light or not.
	 * 
	 * @return boolean, true if its affected, false if its not.
	 */
	public boolean isLightAffected() {
		return this.lightAffected;
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