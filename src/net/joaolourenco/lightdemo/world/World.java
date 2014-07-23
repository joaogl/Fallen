package net.joaolourenco.lightdemo.world;

import java.util.ArrayList;

import net.joaolourenco.lightdemo.Main;
import net.joaolourenco.lightdemo.entity.Entity;
import net.joaolourenco.lightdemo.entity.light.Light;
import net.joaolourenco.lightdemo.entity.light.RadialLight;
import net.joaolourenco.lightdemo.entity.mob.Block;
import net.joaolourenco.lightdemo.entity.mob.Player;
import net.joaolourenco.lightdemo.graphics.Texture;
import net.joaolourenco.lightdemo.world.tile.FireTile;
import net.joaolourenco.lightdemo.world.tile.SolidTile;
import net.joaolourenco.lightdemo.world.tile.Tile;

import org.lwjgl.util.vector.Vector2f;

import static org.lwjgl.opengl.GL11.*;

public class World {

	public float DAY_LIGHT = 1f;
	public ArrayList<Entity> entities = new ArrayList<Entity>();
	public Tile[] worldTiles;
	private int width, height, xOffset, yOffset;
	public static Player player;
	protected boolean goingUp = false;

	public World(int width, int height) {
		this.width = width;
		this.height = height;
		this.xOffset = 0;
		this.yOffset = 0;
		this.height = height;
		this.worldTiles = new Tile[this.width * this.height];

		int blockCount = 5 + (int) (Math.random() * 1);
		int entitiesCount = 5 + (int) (Math.random() * 1);

		Vector2f location = new Vector2f((6 << Main.TILE_SIZE_MASK) + Main.TILE_SIZE / 2, (5 << Main.TILE_SIZE_MASK) + Main.TILE_SIZE / 2);
		RadialLight l = new RadialLight(location, (float) Math.random() * 10, (float) Math.random() * 10, (float) Math.random() * 10, 0.8f);
		l.init(this);
		this.entities.add(l);

		for (int i = 1; i <= blockCount; i++) {
			int x = (int) (Math.random() * (Main.WIDTH - Main.TILE_SIZE));
			int y = (int) (Math.random() * (Main.HEIGHT - Main.TILE_SIZE));
			Block b = new Block(x, y, Main.TILE_SIZE, Main.TILE_SIZE, false);
			b.init(this);
			this.entities.add(b);
		}

		for (int i = 1; i <= entitiesCount; i++) {
			int x = (int) (Math.random() * (Main.WIDTH - Main.TILE_SIZE));
			int y = (int) (Math.random() * (Main.HEIGHT - Main.TILE_SIZE));
			Block b = new Block(x, y, Main.TILE_SIZE, Main.TILE_SIZE, true);
			b.init(this);
			this.entities.add(b);
		}

		setTile(0, 0, new SolidTile(Main.TILE_SIZE, Texture.Grass));
		setTile(1, 1, new SolidTile(Main.TILE_SIZE, Texture.Dirt));
		setTile(2, 2, new SolidTile(Main.TILE_SIZE, Texture.Grass));
		setTile(3, 3, new SolidTile(Main.TILE_SIZE, Texture.Dirt));
		setTile(4, 4, new SolidTile(Main.TILE_SIZE, Texture.Grass));
		setTile(5, 5, new SolidTile(Main.TILE_SIZE, Texture.Dirt));
		setTile(6, 6, new SolidTile(Main.TILE_SIZE, Texture.Grass));
		setTile(7, 7, new SolidTile(Main.TILE_SIZE, Texture.Dirt));
		setTile(8, 8, new SolidTile(Main.TILE_SIZE, Texture.Grass));
		setTile(9, 9, new SolidTile(Main.TILE_SIZE, Texture.Dirt));

		setTile(0, 2, new FireTile(Main.TILE_SIZE, Texture.Fire[0], this));

		player = new Player();
		player.init(this);
		this.entities.add(player);
	}

	public void setOffset(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}

	public int getXOffset() {
		return this.xOffset;
	}

	public int getYOffset() {
		return this.yOffset;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public void render() {
		glTranslatef(-this.xOffset, -this.yOffset, 0f);
		glColor3f(1f, 1f, 1f);

		int x0 = this.xOffset >> Main.TILE_SIZE_MASK;
		int x1 = (this.xOffset >> Main.TILE_SIZE_MASK) + 14;
		int y0 = this.yOffset >> Main.TILE_SIZE_MASK;
		int y1 = (this.yOffset >> Main.TILE_SIZE_MASK) + 11;
		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				Tile tile = getTile(x, y);
				if (tile != null) tile.render(x << Main.TILE_SIZE_MASK, y << Main.TILE_SIZE_MASK, this);
			}
		}
		glColor3f(1f, 1f, 1f);
		for (Entity e : this.entities) {
			if (e != null && getDistance(e, player) < 800) {
				if (e instanceof Light) {
					((Light) e).renderShadows(entities, worldTiles);
					e.render();
				} else e.render();
			}
		}
		glTranslatef(this.xOffset, this.yOffset, 0f);
	}

	public double getDistance(Entity a, Entity b) {
		return Math.sqrt(Math.pow((b.getX() - a.getX()), 2) + Math.pow((b.getY() - a.getY()), 2));
	}

	public void update() {
		for (Entity e : this.entities)
			if (e != null) e.update();

		for (Tile t : this.worldTiles)
			if (t != null) t.update();

		if (this.DAY_LIGHT <= 0.1f) this.goingUp = true;
		else if (this.DAY_LIGHT >= 1.0f) this.goingUp = false;

		if (this.goingUp) this.DAY_LIGHT += 0.001f;
		else this.DAY_LIGHT -= 0.001f;
	}

	public void tick() {
		for (Entity e : this.entities)
			if (e != null) e.tick();
	}

	public void setTile(int x, int y, Tile tile) {
		this.worldTiles[x + y * this.width] = tile;
	}

	public Tile getTile(int x, int y) {
		if (x < 0 || x >= width || y < 0 || y >= height) return null;
		return this.worldTiles[x + y * this.width];
	}

}