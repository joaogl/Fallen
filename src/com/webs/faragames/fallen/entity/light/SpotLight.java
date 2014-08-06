package com.webs.faragames.fallen.entity.light;

import org.lwjgl.util.vector.Vector2f;

public class SpotLight extends Light {

	public SpotLight(Vector2f location, float red, float green, float blue) {
		super(location, red, green, blue);
		this.type = 3;
		this.size = 10;
		this.facing = 10;
	}

	public SpotLight(Vector2f location, float red, float green, float blue, float inte) {
		super(location, red, green, blue, inte);
		this.type = 3;
		this.size = 10;
		this.facing = 10;
	}

	public void tick() {
	}

}