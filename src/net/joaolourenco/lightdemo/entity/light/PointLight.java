package net.joaolourenco.lightdemo.entity.light;

import org.lwjgl.util.vector.Vector2f;

public class PointLight extends Light {

	public PointLight(Vector2f location, float red, float green, float blue) {
		super(location, red, green, blue);
		this.type = 1;
	}

	public PointLight(Vector2f location, float red, float green, float blue, float inte) {
		super(location, red, green, blue, inte);
		this.type = 1;
	}

	public void tick() {
	}

}