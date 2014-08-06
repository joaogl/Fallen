package com.webs.faragames.fallen.entity.mob;

import com.webs.faragames.fallen.entity.Entity;

public abstract class Mob extends Entity {

	public Mob(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	protected boolean frozen = false;
	protected boolean inBed = false;
	protected int side;
	public int dir;

	public boolean isFrozen() {
		return frozen;
	}

	public void freeze() {
		frozen = true;
	}

	public void unFreeze() {
		frozen = false;
	}

	public void getSide(float xa, float ya) {
		if (xa > 0) this.side = 0;
		else if (xa < 0) this.side = 1;
		if (ya > 0) this.side = 2;
		else if (ya < 0) this.side = 3;
	}

	public boolean inBed() {
		return this.inBed;
	}

	public void inBed(boolean a) {
		this.inBed = a;
	}

}