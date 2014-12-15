package net.joaolourenco.fallen.entity.mob;

import net.joaolourenco.fallen.entity.Entity;

/**
 * Abstract class for all the Mob
 * 
 * @author FARA Games
 *
 */
public abstract class Mob extends Entity {

	/**
	 * Is the player frozen
	 */
	protected boolean frozen = false;
	/**
	 * Is the player in Bed
	 */
	protected boolean inBed = false;
	/**
	 * Where is the player facing
	 */
	protected int side;

	/**
	 * Constructor for a normal Mob.
	 * 
	 * @param x
	 *            : mob x coordinates.
	 * @param y
	 *            : mob y coordinates.
	 * @param width
	 *            : mob width.
	 * @param height
	 *            : mob height.
	 */
	public Mob(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	/**
	 * Method to get if the Mob is frozen.
	 * 
	 * @return boolean, true if its frozen, false if its not.
	 */
	public boolean isFrozen() {
		return frozen;
	}

	/**
	 * Method to freeze the Mob.
	 */
	public void freeze() {
		frozen = true;
	}

	/**
	 * Method to unfreeze the Mob.
	 */
	public void unFreeze() {
		frozen = false;
	}

	/**
	 * Method to check if Mob is in Bed.
	 * 
	 * @return boolean, true if its in bed, false if its not.
	 */
	public boolean inBed() {
		return this.inBed;
	}

	/**
	 * Method to change in Bed setting.
	 * 
	 * @param a
	 *            : true if its in bed, false if its not.
	 */
	public void inBed(boolean a) {
		this.inBed = a;
	}

	/**
	 * Method to get the player facing side.
	 * 
	 * @param xa
	 *            : where is the player going.
	 * @param ya
	 *            : where is the player going.
	 */
	public void getSide(float xa, float ya) {
		if (xa > 0) this.side = 0;
		else if (xa < 0) this.side = 1;
		if (ya > 0) this.side = 2;
		else if (ya < 0) this.side = 3;
	}

}