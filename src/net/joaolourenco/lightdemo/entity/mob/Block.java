package net.joaolourenco.lightdemo.entity.mob;

import java.util.Random;

import net.joaolourenco.lightdemo.Main;
import net.joaolourenco.lightdemo.entity.Entity;
import net.joaolourenco.lightdemo.graphics.Texture;

import org.lwjgl.input.Keyboard;

public class Block extends Entity {

	public boolean xdir, ydir;
	private boolean movable;

	public Block(int x, int y, int width, int height, boolean movable) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.xdir = true;
		this.ydir = true;
		this.movable = movable;
		this.texture = Texture.Mob;
	}

	public void update() {
		if (movable) {
			int tomove = new Random().nextInt(3);
			if (new Random().nextInt(10) >= 5) {
				if (xdir) this.x += tomove;
				else this.x -= tomove;
			} else {
				if (ydir) this.y += tomove;
				else this.y -= tomove;
			}
			if (this.x >= (this.world.getWidth() << Main.TILE_SIZE_MASK) || this.x < 0) this.xdir = !this.xdir;
			if (this.y >= (this.world.getHeight() << Main.TILE_SIZE_MASK) || this.y < 0) this.ydir = !this.ydir;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_R)) shade.recompile();
	}

	public void tick() {
	}

}