package net.joaolourenco.lightdemo.entity;

import java.util.Random;

import org.lwjgl.util.vector.Vector2f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;

import net.joaolourenco.lightdemo.Main;
import net.joaolourenco.lightdemo.graphics.Shader;
import net.joaolourenco.lightdemo.world.World;

public abstract class Entity {

	protected static final float SIZE = Main.TILE_SIZE;
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
	public Shader shade = new Shader("res/shaders/entity.frag", "res/shaders/entity.vert");

	public abstract void update();

	public abstract void tick();

	public void render() {
		shade.bind();

		glUniform1f(glGetUniformLocation(shade.getShade(), "dayLight"), this.world.DAY_LIGHT);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ZERO);

		glTranslatef(x, y, 0);
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, this.texture);
		glUniform1i(glGetUniformLocation(shade.getShade(), "texture"), 0);

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