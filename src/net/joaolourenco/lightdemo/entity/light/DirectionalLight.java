package net.joaolourenco.lightdemo.entity.light;

import org.lwjgl.util.vector.Vector2f;

public class DirectionalLight extends Light {

	public DirectionalLight(Vector2f location, float red, float green, float blue) {
		super(location, red, green, blue);
		this.type = 2;
		this.size = 10;
		this.facing = 10;
	}

	public DirectionalLight(Vector2f location, float red, float green, float blue, float inte) {
		super(location, red, green, blue, inte);
		this.type = 2;
		this.size = 10;
		this.facing = 10;
	}

	public void tick() {
	}

}