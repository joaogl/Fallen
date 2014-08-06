package com.webs.faragames.fallen.world;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;

import com.webs.faragames.fallen.entity.Entity;
import com.webs.faragames.fallen.entity.light.Light;
import com.webs.faragames.fallen.entity.light.PointLight;
import com.webs.faragames.fallen.entity.light.SpotLight;
import com.webs.faragames.fallen.entity.mob.Block;
import com.webs.faragames.fallen.entity.mob.Player;
import com.webs.faragames.fallen.graphics.Texture;
import com.webs.faragames.fallen.settings.GeneralSettings;
import com.webs.faragames.fallen.world.tile.SolidTile;
import com.webs.faragames.fallen.world.tile.Tile;

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

		Vector2f location = new Vector2f((3 << GeneralSettings.TILE_SIZE_MASK) + GeneralSettings.TILE_SIZE / 2, (0 << GeneralSettings.TILE_SIZE_MASK) + GeneralSettings.TILE_SIZE / 2);
		SpotLight l = new SpotLight(location, (float) Math.random() * 10, (float) Math.random() * 10, (float) Math.random() * 10, 0.8f);
		l.init(this);
		this.entities.add(l);

		location = new Vector2f((0 << GeneralSettings.TILE_SIZE_MASK) + GeneralSettings.TILE_SIZE / 2, (3 << GeneralSettings.TILE_SIZE_MASK) + GeneralSettings.TILE_SIZE / 2);
		PointLight l2 = new PointLight(location, (float) Math.random() * 10, (float) Math.random() * 10, (float) Math.random() * 10, 0.8f);
		l2.init(this);
		this.entities.add(l2);

		for (int i = 1; i <= blockCount; i++) {
			int x = (int) (Math.random() * (GeneralSettings.WIDTH - GeneralSettings.TILE_SIZE));
			int y = (int) (Math.random() * (GeneralSettings.HEIGHT - GeneralSettings.TILE_SIZE));
			Block b = new Block(x, y, GeneralSettings.TILE_SIZE, GeneralSettings.TILE_SIZE, false);
			b.init(this);
			this.entities.add(b);
		}

		for (int i = 1; i <= entitiesCount; i++) {
			int x = (int) (Math.random() * (GeneralSettings.WIDTH - GeneralSettings.TILE_SIZE));
			int y = (int) (Math.random() * (GeneralSettings.HEIGHT - GeneralSettings.TILE_SIZE));
			Block b = new Block(x, y, GeneralSettings.TILE_SIZE, GeneralSettings.TILE_SIZE, true);
			b.init(this);
			this.entities.add(b);
		}

		setTile(0, 0, new SolidTile(GeneralSettings.TILE_SIZE, Texture.Grass));
		setTile(1, 1, new SolidTile(GeneralSettings.TILE_SIZE, Texture.Dirt));
		setTile(2, 2, new SolidTile(GeneralSettings.TILE_SIZE, Texture.Grass));
		setTile(3, 3, new SolidTile(GeneralSettings.TILE_SIZE, Texture.Dirt));
		setTile(4, 4, new SolidTile(GeneralSettings.TILE_SIZE, Texture.Grass));
		setTile(5, 5, new SolidTile(GeneralSettings.TILE_SIZE, Texture.Dirt));
		setTile(6, 6, new SolidTile(GeneralSettings.TILE_SIZE, Texture.Grass));
		setTile(7, 7, new SolidTile(GeneralSettings.TILE_SIZE, Texture.Dirt));
		setTile(8, 8, new SolidTile(GeneralSettings.TILE_SIZE, Texture.Grass));
		setTile(9, 9, new SolidTile(GeneralSettings.TILE_SIZE, Texture.Dirt));

		// setTile(0, 2, new FireTile(GeneralSettings.TILE_SIZE, Texture.Fire[0], this));

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

		int x0 = this.xOffset >> GeneralSettings.TILE_SIZE_MASK;
		int x1 = (this.xOffset >> GeneralSettings.TILE_SIZE_MASK) + 14;
		int y0 = this.yOffset >> GeneralSettings.TILE_SIZE_MASK;
		int y1 = (this.yOffset >> GeneralSettings.TILE_SIZE_MASK) + 11;
		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				Tile tile = getTile(x, y);
				if (tile != null) tile.render(x << GeneralSettings.TILE_SIZE_MASK, y << GeneralSettings.TILE_SIZE_MASK, this, getNearByLights(x << GeneralSettings.TILE_SIZE_MASK, y << GeneralSettings.TILE_SIZE_MASK));
			}
		}
		glColor3f(1f, 1f, 1f);
		for (Entity e : this.entities) {
			if (e != null && getDistance(e, player) < 800) {
				if (e instanceof Light) {
					((Light) e).renderShadows(entities, worldTiles);
					((Light) e).render();
				} else e.render(getNearByLights(e.getX(), e.getY()));
			}
		}
		glTranslatef(this.xOffset, this.yOffset, 0f);
	}

	public double getDistance(Entity a, Entity b) {
		return Math.sqrt(Math.pow((b.getX() - a.getX()), 2) + Math.pow((b.getY() - a.getY()), 2));
	}

	public double getDistance(Entity a, float x, float y) {
		return Math.sqrt(Math.pow((x - a.getX()), 2) + Math.pow((y - a.getY()), 2));
	}

	public ArrayList<Entity> getNearByLights(float x, float y) {
		ArrayList<Entity> ent = new ArrayList<Entity>();

		for (Entity e : entities)
			if (e instanceof Light && getDistance(e, x, y) < 800) ent.add(e);

		return ent;
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