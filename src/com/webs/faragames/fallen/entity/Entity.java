package com.webs.faragames.fallen.entity;

import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.util.vector.Vector2f;

import com.webs.faragames.fallen.entity.light.Light;
import com.webs.faragames.fallen.graphics.Buffer;
import com.webs.faragames.fallen.graphics.Shader;
import com.webs.faragames.fallen.settings.GeneralSettings;
import com.webs.faragames.fallen.world.World;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;

public abstract class Entity {

	protected static final float SIZE = GeneralSettings.TILE_SIZE;
	protected float x, y, width, height;
	protected int side;
	public int dir;
	protected int texture;
	protected World world;
	private boolean removed = false;
	protected boolean frozen = false;
	protected boolean inBed = false;
	protected Random random = new Random();
	protected boolean collidable = true, lightCollidable = true;
	public Shader shade = new Shader("res/shaders/blockLightBlocker.frag", "res/shaders/entity.vert");

	public abstract void update();

	public abstract void tick();

	public void render(ArrayList<Entity> ent) {
		shade.bind();

		float[] positions = new float[ent.size() * 2];
		float[] colors = new float[ent.size() * 3];
		float[] intensities = new float[ent.size()];
		float[] inUse = new float[50];
		float[] type = new float[50];
		float[] size = new float[50];
		float[] facing = new float[50];

		for (int i = 0; i < ent.size() * 2; i += 2) {
			float xx = ent.get(i >> 1).getX() - this.world.getXOffset();
			float yy = GeneralSettings.HEIGHT - (ent.get(i >> 1).getY() - this.world.getYOffset());

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

		glUniform1f(glGetUniformLocation(shade.getShader(), "dayLight"), this.world.DAY_LIGHT);

		glUniform2(glGetUniformLocation(shade.getShader(), "lightPosition"), Buffer.createFloatBuffer(positions));
		glUniform3(glGetUniformLocation(shade.getShader(), "lightColor"), Buffer.createFloatBuffer(colors));
		glUniform1(glGetUniformLocation(shade.getShader(), "lightIntensity"), Buffer.createFloatBuffer(intensities));
		glUniform1(glGetUniformLocation(shade.getShader(), "lightInUse"), Buffer.createFloatBuffer(inUse));
		glUniform1(glGetUniformLocation(shade.getShader(), "lightType"), Buffer.createFloatBuffer(type));
		glUniform1(glGetUniformLocation(shade.getShader(), "lightSize"), Buffer.createFloatBuffer(size));
		glUniform1(glGetUniformLocation(shade.getShader(), "lightFacing"), Buffer.createFloatBuffer(facing));

		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ZERO);

		glTranslatef(x, y, 0);
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, this.texture);
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

	public Vector2f[] getVertices() {
		return new Vector2f[] { new Vector2f(this.x, this.y), new Vector2f(this.x, this.y + this.height), new Vector2f(this.x + this.width, this.y + this.height), new Vector2f(this.x + this.width, this.y) };
	}

	public boolean isRemoved() {
		return removed;
	}

	public boolean isFrozen() {
		return frozen;
	}

	public void freeze() {
		frozen = true;
	}

	public void unFreeze() {
		frozen = false;
	}

	public void remove() {
		removed = true;
	}

	public void init(World world) {
		this.world = world;
	}

	public float getSpeed(boolean running) {
		float speed = 0;
		if (running) speed = 5f;
		else speed = 2.5f;
		return speed;
	}

	public void getSide(float xa, float ya) {
		if (xa > 0) this.side = 0;
		else if (xa < 0) this.side = 1;
		if (ya > 0) this.side = 2;
		else if (ya < 0) this.side = 3;
	}

	public int getX() {
		return (int) this.x;
	}

	public int getY() {
		return (int) this.y;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public boolean inBed() {
		return this.inBed;
	}

	public void inBed(boolean a) {
		this.inBed = a;
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

}