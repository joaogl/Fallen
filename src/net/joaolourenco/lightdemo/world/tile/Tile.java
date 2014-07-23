package net.joaolourenco.lightdemo.world.tile;

import net.joaolourenco.lightdemo.graphics.Shader;
import net.joaolourenco.lightdemo.world.World;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;

public abstract class Tile {

	protected boolean collidable = true, lightCollidable = true;
	protected int width, height, x = 9999999, y = 9999999;
	protected int tex;
	public Shader shade = new Shader("res/shaders/lightBlocker.frag", "res/shaders/entity.vert");

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
		if (Keyboard.isKeyDown(Keyboard.KEY_R)) shade.recompile();
		if (this.lightCollidable && !shade.getFragPath().contains("lightBlocker")) shade = new Shader("res/shaders/lightBlocker.frag", "res/shaders/entity.vert");
		else if (!this.lightCollidable && !shade.getFragPath().contains("lightMix")) shade = new Shader("res/shaders/lightMix.frag", "res/shaders/entity.vert");
	}

	public void render(int x, int y, World w) {
		shade.bind();

		glUniform1f(glGetUniformLocation(shade.getShade(), "dayLight"), w.DAY_LIGHT);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ZERO);

		this.x = x;
		this.y = y;
		glTranslatef(x, y, 0);
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, this.tex);
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