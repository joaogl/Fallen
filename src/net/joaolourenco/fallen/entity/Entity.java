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

package net.joaolourenco.fallen.entity;

import java.util.ArrayList;
import java.util.Random;

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
 * Abstract Class for all the Entity Types
 * 
 * @author Joao Lourenco
 *
 */
public abstract class Entity {

	/**
	 * Size and location of the Entity.
	 */
	protected float x, y, width, height;
	/**
	 * Texture ID for the Entity.
	 */
	protected int texture;
	/**
	 * Instance of the World class.
	 */
	protected World world;
	/**
	 * Boolean to remove the entity from the world.
	 */
	private boolean removed = false;
	/**
	 * Random is always needed in entities.
	 */
	protected Random random = new Random();
	/**
	 * Is the Entity collidable? Will the light end on the Entity or keep spreading?
	 */
	protected boolean collidable = true, lightCollidable = true;
	/**
	 * Shader ID for the entity.
	 */
	public Shader shade = new Shader(GeneralSettings.lightBlockerPath, GeneralSettings.entityVertexPath);

	/**
	 * Constructor for the Entities.
	 * 
	 * @param x
	 *            : coordinates to the entity position.
	 * @param y
	 *            : coordinates to the entity position.
	 * @param width
	 *            : entity width.
	 * @param height
	 *            : entity height.
	 */
	public Entity(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	/**
	 * Method called by the World Class 60 times per second.
	 */
	public abstract void update();

	/**
	 * Method called by the World Class once per second.
	 */
	public abstract void tick();

	/**
	 * Method to bind the Texture uniforms, this is what make the shaders and stuff.
	 * 
	 * @param ent
	 *            : List of entities that emit light.
	 */
	public void bindUniforms(ArrayList<Entity> ent) {
		// Is there 50 Lights or less?
		int howMany = GeneralSettings.howManyLightsToShader;
		if (howMany < ent.size()) howMany = ent.size();
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
			if (i < ent.size() && ent.get(i >> 1) != null && ((Light)ent.get(i)).getLightState()) {
				float xx = ent.get(i >> 1).getX() - this.world.getXOffset();
				float yy = GeneralSettings.HEIGHT - (ent.get(i >> 1).getY() - this.world.getYOffset());

				positions[i] = xx;
				positions[i + 1] = yy;
			}
		}

		// Putting all the light intensities inside a float array.
		for (int i = 0; i < howMany; i++) {
			if (i < ent.size() && ent.get(i) != null && ((Light)ent.get(i)).getLightState()) intensities[i] = ((Light) ent.get(i)).intensity;
		}

		// Putting all the colors inside a float array.
		for (int i = 0; i < howMany * 3; i += 3) {
			if (i < ent.size() && ent.get(i / 3) != null && ((Light)ent.get(i)).getLightState()) {
				colors[i] = ((Light) ent.get(i / 3)).red;
				colors[i + 1] = ((Light) ent.get(i / 3)).green;
				colors[i + 2] = ((Light) ent.get(i / 3)).blue;
			}
		}

		// Putting the size, type and facing of the light inside a float array.
		for (int i = 0; i < howMany; i++) {
			if (i < ent.size() && ent.get(i) != null && ((Light)ent.get(i)).getLightState()) {
				type[i] = ((Light) ent.get(i)).getType();
				size[i] = ((Light) ent.get(i)).getSize();
				facing[i] = ((Light) ent.get(i)).getFacing();
			}
		}

		// Sending to the shader the current dayLight
		glUniform1f(glGetUniformLocation(shade.getShader(), "dayLight"), this.world.DAY_LIGHT * 2);

		// Sending all the previus information from the floats to the shader.
		glUniform2(glGetUniformLocation(shade.getShader(), "lightPosition"), Buffer.createFloatBuffer(positions));
		glUniform3(glGetUniformLocation(shade.getShader(), "lightColor"), Buffer.createFloatBuffer(colors));
		glUniform1(glGetUniformLocation(shade.getShader(), "lightIntensity"), Buffer.createFloatBuffer(intensities));
		glUniform1(glGetUniformLocation(shade.getShader(), "lightType"), Buffer.createFloatBuffer(type));
		glUniform1(glGetUniformLocation(shade.getShader(), "lightSize"), Buffer.createFloatBuffer(size));
		glUniform1(glGetUniformLocation(shade.getShader(), "lightFacing"), Buffer.createFloatBuffer(facing));
	}

	/**
	 * Method called by the World Class to render the Entity.
	 * 
	 * @param ent
	 *            : List of entities that emit light.
	 */
	public void render(ArrayList<Entity> ent) {
		// Binding the Uniforms to make the light effects.
		bindUniforms(ent);

		// Setting up OpenGL for render
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ZERO);

		// Updating the Entity coordinates.
		glTranslatef(x, y, 0);
		// Activating and Binding the Tile Texture.
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, this.texture);
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
	 * Method to return the Enity vertices.
	 * 
	 * @return Vector2f[] with the vertices.
	 */
	public Vector2f[] getVertices() {
		return new Vector2f[] {
				new Vector2f(this.x, this.y), new Vector2f(this.x, this.y + this.height), new Vector2f(this.x + this.width, this.y + this.height), new Vector2f(this.x + this.width, this.y) };
	}

	/**
	 * Method to check if the Entity has been removed or not.
	 * 
	 * @return boolean, true if has been removed, false if hasn't.
	 */
	public boolean isRemoved() {
		return removed;
	}

	/**
	 * Method to remove an Entity from the world.
	 */
	public void remove() {
		removed = true;
	}

	/**
	 * Method to initialize a Entity on the world.
	 * 
	 * @param world
	 *            : Instance of the world class.
	 */
	public void init(World world) {
		this.world = world;
	}

	/**
	 * Method to get the moving speed of an Entity.
	 * 
	 * @param running
	 *            : Boolean true if they are running, false if they are not.
	 */
	public float getSpeed(boolean running) {
		float speed = 0;
		if (running) speed = GeneralSettings.defaultEntityRunning;
		else speed = GeneralSettings.defaultEntityWalking;
		return speed;
	}

	/**
	 * Method to get the current Entity x coordinates.
	 * 
	 * @return int with the position.
	 */
	public int getX() {
		return (int) this.x;
	}

	/**
	 * Method to get the current Entity y coordinates.
	 * 
	 * @return int with the position.
	 */
	public int getY() {
		return (int) this.y;
	}

	/**
	 * Method to change the Entity's x coordinates.
	 * 
	 * @param x
	 *            : float with the new coordinates.
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * Method to change the Entity's y coordinates.
	 * 
	 * @param y
	 *            : float with the new coordinates.
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * Method to get if the Entity is Light Collidable or not.
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
	 * Method to get collision property.
	 * 
	 * @return boolean, true if they collide, false if they dont.
	 */
	public boolean isCollidable() {
		return this.collidable;
	}

	/**
	 * Method to define the collision property.
	 * 
	 * @param a
	 *            : Boolean true if they collide, false if they dont.
	 */
	public void isCollidable(boolean a) {
		this.collidable = a;
	}

}