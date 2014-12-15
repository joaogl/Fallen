package net.joaolourenco.fallen.world.tile;

/**
 * Class for all the Solid, this is, collidable Tiles.
 * 
 * @author FARA Games
 *
 */
public class SolidTile extends Tile {

	/**
	 * Constructor to create a square Tile.
	 * 
	 * @param size
	 *            : with and height for the Tile.
	 * @param tex
	 *            : Texture ID from the Texture class for the Tile.
	 */
	public SolidTile(int size, int tex) {
		super(size, tex);
	}

	/**
	 * Constructor to create a square Tile with Light.
	 * 
	 * @param size
	 *            : with and height for the Tile.
	 * @param tex
	 *            : Texture ID from the Texture class for the Tile.
	 * @param light
	 *            : true to make the Light collide with the Tile, false to make the Light spread through the Tile.
	 */
	public SolidTile(int size, int tex, boolean light) {
		super(size, tex);
		this.lightCollidable = light;
	}

	/**
	 * Method update called by the World Class 60 times per second.
	 */
	public void update() {
		super.update();
	}

}