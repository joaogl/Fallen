package net.joaolourenco.lightdemo.entity.light;

import java.util.ArrayList;
import java.util.Random;

import net.joaolourenco.lightdemo.Main;
import net.joaolourenco.lightdemo.entity.Entity;
import net.joaolourenco.lightdemo.graphics.Shader;
import net.joaolourenco.lightdemo.world.tile.Tile;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

public abstract class Light extends Entity {

	public Vector2f location;
	public float red, green, blue, intensity;
	public Shader shade = new Shader("res/shaders/light.frag");

	public Light(Vector2f location, float red, float green, float blue) {
		this.location = location;
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.intensity = (float) (new Random().nextGaussian() / 5) * 3;
	}

	public Light(Vector2f location, float red, float green, float blue, float inte) {
		this.location = location;
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.intensity = inte;
	}

	public void update() {
		if (Keyboard.isKeyDown(Keyboard.KEY_R)) shade.recompile();
	}

	public abstract void tick();

	public void render() {
		this.shade.bind();
		float xx = this.location.getX() - this.world.getXOffset();
		float yy = this.location.getY() - this.world.getYOffset();
		glUniform1f(glGetUniformLocation(shade.getShade(), "lightInt"), this.intensity);
		glUniform2f(glGetUniformLocation(shade.getShade(), "lightLocation"), xx, Main.HEIGHT - yy);
		glUniform3f(glGetUniformLocation(shade.getShade(), "lightColor"), this.red, this.green, this.blue);
		glEnable(GL_BLEND);
		glBlendFunc(GL_ONE, GL_ONE);

		glBegin(GL_QUADS);
		{
			glVertex2f(0 + this.world.getXOffset(), 0 + this.world.getYOffset());
			glVertex2f(0 + this.world.getXOffset(), Main.HEIGHT + this.world.getYOffset());
			glVertex2f(Main.WIDTH + this.world.getXOffset(), Main.HEIGHT + this.world.getYOffset());
			glVertex2f(Main.WIDTH + this.world.getXOffset(), 0 + this.world.getYOffset());
		}
		glEnd();

		glDisable(GL_BLEND);
		this.shade.release();
		glClear(GL_STENCIL_BUFFER_BIT);
	}

	public void renderShadows(ArrayList<Entity> entities, Tile[] tiles) {
		glColorMask(false, false, false, false);
		glStencilFunc(GL_ALWAYS, 1, 1);
		glStencilOp(GL_KEEP, GL_KEEP, GL_REPLACE);

		for (Entity entity : entities) {
			if (entity.isLightCollidable()) {
				Vector2f[] vertices = entity.getVertices();
				for (int i = 0; i < vertices.length; i++) {
					Vector2f currentVertex = vertices[i];
					Vector2f nextVertex = vertices[(i + 1) % vertices.length];
					Vector2f edge = Vector2f.sub(nextVertex, currentVertex, null);
					Vector2f normal = new Vector2f(edge.getY(), -edge.getX());
					Vector2f lightToCurrent = Vector2f.sub(currentVertex, this.location, null);
					if (Vector2f.dot(normal, lightToCurrent) > 0) {
						Vector2f point1 = Vector2f.add(currentVertex, (Vector2f) Vector2f.sub(currentVertex, this.location, null).scale(800), null);
						Vector2f point2 = Vector2f.add(nextVertex, (Vector2f) Vector2f.sub(nextVertex, this.location, null).scale(800), null);
						glBegin(GL_QUADS);
						{
							glVertex2f(currentVertex.getX(), currentVertex.getY());
							glVertex2f(point1.getX(), point1.getY());
							glVertex2f(point2.getX(), point2.getY());
							glVertex2f(nextVertex.getX(), nextVertex.getY());
						}
						glEnd();
					}
				}
			}
		}

		for (Tile tile : tiles) {
			if (tile != null && tile.isLightCollidable()) {
				Vector2f[] vertices = tile.getVertices();
				for (int i = 0; i < vertices.length; i++) {
					Vector2f currentVertex = vertices[i];
					Vector2f nextVertex = vertices[(i + 1) % vertices.length];
					Vector2f edge = Vector2f.sub(nextVertex, currentVertex, null);
					Vector2f normal = new Vector2f(edge.getY(), -edge.getX());
					Vector2f lightToCurrent = Vector2f.sub(currentVertex, this.location, null);
					if (Vector2f.dot(normal, lightToCurrent) > 0) {
						Vector2f point1 = Vector2f.add(currentVertex, (Vector2f) Vector2f.sub(currentVertex, this.location, null).scale(800), null);
						Vector2f point2 = Vector2f.add(nextVertex, (Vector2f) Vector2f.sub(nextVertex, this.location, null).scale(800), null);
						glBegin(GL_QUADS);
						{
							glVertex2f(currentVertex.getX(), currentVertex.getY());
							glVertex2f(point1.getX(), point1.getY());
							glVertex2f(point2.getX(), point2.getY());
							glVertex2f(nextVertex.getX(), nextVertex.getY());
						}
						glEnd();
					}
				}
			}
		}

		glStencilOp(GL_KEEP, GL_KEEP, GL_KEEP);
		glStencilFunc(GL_EQUAL, 0, 1);
		glColorMask(true, true, true, true);
	}

	public void setX(float x) {
		this.location.x = x;
	}

	public void setY(float y) {
		this.location.y = y;
	}

	public int getX() {
		return (int) this.location.x;
	}

	public int getY() {
		return (int) this.location.y;
	}

}