package net.joaolourenco.lightdemo.world.tile;

public class SolidTile extends Tile {

	public SolidTile(int size, int tex) {
		super(size, tex);
	}

	public SolidTile(int size, int tex, boolean light) {
		super(size, tex);
		this.lightCollidable = light;
	}

	public void update() {
		super.update();
	}

}