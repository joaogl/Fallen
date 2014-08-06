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

public abstract class Tile {

	protected boolean collidable = true, lightCollidable = true;
	protected int width, height, x = 9999999, y = 9999999;
	protected int tex;
	public Shader shade = new Shader("res/shaders/blockLightBlocker.frag", "res/shaders/entity.vert");

	public Tile(int width, int height, int tex) {
		this.width = width;
		this.height = height;
		this.tex = tex;
	}

	public Tile(int size, int tex) {
		this.width = size;
		this.height = size;
		this.tex = tex;
	}

	public void update() {
		/*
		 * if (this.lightCollidable && !shade.getFragPath().contains("lightBlocker")) shade = new Shader("res/shaders/lightBlocker.frag", "res/shaders/entity.vert"); else if (!this.lightCollidable && !shade.getFragPath().contains("lightMix")) shade = new Shader("res/shaders/lightMix.frag", "res/shaders/entity.vert");
		 */
	}

	public void render(int x, int y, World w, ArrayList<Entity> ent) {
		shade.bind();

		float[] positions = new float[ent.size() * 2];
		float[] colors = new float[ent.size() * 3];
		float[] intensities = new float[ent.size()];
		float[] inUse = new float[50];
		float[] type = new float[50];
		float[] size = new float[50];
		float[] facing = new float[50];

		for (int i = 0; i < ent.size() * 2; i += 2) {
			float xx = ent.get(i >> 1).getX() - w.getXOffset();
			float yy = GeneralSettings.HEIGHT - (ent.get(i >> 1).getY() - w.getYOffset());

			positions[i] = xx;
			positions[i + 1] = yy;
		}

		for (int i = 0; i < ent.size(); i++) {
			intensities[i] = ((Light) ent.get(i)).intensity;
		}

		for (int i = 0; i < 50; i++) {
			if (i < ent.size() && ent.get(i) != null) inUse[i] = 1;
			else inUse[i] = 0;
		}

		for (int i = 0; i < ent.size() * 3; i += 3) {
			colors[i] = ((Light) ent.get(i / 3)).red;
			colors[i + 1] = ((Light) ent.get(i / 3)).green;
			colors[i + 2] = ((Light) ent.get(i / 3)).blue;
		}

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

		glUniform1f(glGetUniformLocation(shade.getShader(), "dayLight"), w.DAY_LIGHT);

		glUniform2(glGetUniformLocation(shade.getShader(), "lightPosition"), Buffer.createFloatBuffer(positions));
		glUniform3(glGetUniformLocation(shade.getShader(), "lightColor"), Buffer.createFloatBuffer(colors));
		glUniform1(glGetUniformLocation(shade.getShader(), "lightIntensity"), Buffer.createFloatBuffer(intensities));
		glUniform1(glGetUniformLocation(shade.getShader(), "lightInUse"), Buffer.createFloatBuffer(inUse));
		glUniform1(glGetUniformLocation(shade.getShader(), "lightType"), Buffer.createFloatBuffer(type));
		glUniform1(glGetUniformLocation(shade.getShader(), "lightSize"), Buffer.createFloatBuffer(size));
		glUniform1(glGetUniformLocation(shade.getShader(), "lightFacing"), Buffer.createFloatBuffer(facing));

		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ZERO);

		this.x = x;
		this.y = y;
		glTranslatef(x, y, 0);
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, this.tex);
		glUniform1i(glGetUniformLocation(shade.getShader(), "texture"), 0);

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
		glBindTexture(GL_TEXTURE_2D, 0);
		glTranslatef(-x, -y, 0);

		glDisable(GL_BLEND);
		shade.release();
		glClear(GL_STENCIL_BUFFER_BIT);
	}

	public boolean isLightCollidable() {
		return this.lightCollidable;
	}

	public void isLightCollidable(boolean a) {
		this.lightCollidable = a;
	}

	public boolean isCollidable() {
		return this.collidable;
	}

	public void isCollidable(boolean a) {
		this.collidable = a;
	}

	public Vector2f[] getVertices() {
		return new Vector2f[] { new Vector2f(this.x, this.y), new Vector2f(this.x, this.y + this.height), new Vector2f(this.x + this.width, this.y + this.height), new Vector2f(this.x + this.width, this.y) };
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

}